import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/** How a thing is created & cached */
sealed interface Binding<T : Any> {
    val type: KClass<T>
    fun get(resolver: Resolver, scope: ScopeContext?): T
}

/** Resolves dependencies */
fun interface Provider<T : Any> {
    fun invoke(resolver: Resolver): T
}

/** Global container + binding registry */
class Container(private val bindings: MutableMap<KClass<*>, Binding<*>> = mutableMapOf()) : Resolver {

    /** Global singleton cache */
    private val singletons = mutableMapOf<KClass<*>, Any>()

    // ---------- Public API ----------

    /** Register a fixed instance (always the same object) */
    inline fun <reified T : Any> bindInstance(instance: T) {
        bindings[T::class] = InstanceBinding(T::class, instance)
    }

    /** Register a lazily-created singleton (created once, cached globally) */
    inline fun <reified T : Any> bindSingleton(noinline provider: Provider<T>) {
        bindings[T::class] = SingletonBinding(T::class, provider)
    }

    /** Register a factory (create a brand-new instance every time) */
    inline fun <reified T : Any> bindFactory(noinline provider: Provider<T>) {
        bindings[T::class] = FactoryBinding(T::class, provider)
    }

    /** Register a scoped provider (cached within a given ScopeContext) */
    inline fun <reified T : Any> bindScoped(scopeName: String, noinline provider: Provider<T>) {
        val k = ScopedKey(T::class, scopeName)
        bindings[k] = ScopedBinding(T::class, scopeName, provider)
    }

    /** Resolve by reified type (optionally within a scope) */
    inline fun <reified T : Any> get(scope: ScopeContext? = currentScope()): T =
        get(T::class, scope)

    /** Resolve by class (implements Resolver) */
    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(type: KClass<T>, scope: ScopeContext?): T {
        // 1) Exact scoped binding?
        val scopedBinding = bindings[ScopedKey(type, scope?.name ?: "")] as Binding<T>?
        if (scopedBinding != null) return scopedBinding.get(this, scope)

        // 2) Normal binding?
        val binding = bindings[type] as Binding<T>?
        if (binding != null) return binding.get(this, scope)

        // 3) Fallback: build via reflection (and cache globally as singleton)
        return resolveByReflection(type, scope)
    }

    // ---------- Internals ----------

    private fun <T : Any> resolveByReflection(type: KClass<T>, scope: ScopeContext?): T {
        // Check global singleton cache first
        @Suppress("UNCHECKED_CAST")
        singletons[type]?.let { return it as T }

        val ctor = type.primaryConstructor
            ?: error("No primary constructor for ${type.qualifiedName}")
        val args = ctor.parameters.map { p ->
            val dep = p.type.classifier as? KClass<Any>
                ?: error("Unsupported parameter type: ${p.type}")
            get(dep, scope)
        }.toTypedArray()
        val created = ctor.call(*args)
        singletons[type] = created
        return created
    }

    // --- SingletonBinding needs to access this ---
    @Suppress("UNCHECKED_CAST")
    internal fun <T : Any> getOrCreateSingleton(type: KClass<T>, provider: Provider<T>, scope: ScopeContext?): T {
        singletons[type]?.let { return it as T }
        val v = provider.invoke(this)
        singletons[type] = v
        return v
    }

    // ----- Scope plumbing (opt-in, non-thread-local) -----
    private val scopeLocal = ThreadLocal<ScopeContext?>()

    fun withScope(scope: ScopeContext, block: Container.() -> Unit) {
        val prev = scopeLocal.get()
        scopeLocal.set(scope)
        try {
            this.block()
        } finally {
            scopeLocal.set(prev)
        }
    }

    fun currentScope(): ScopeContext? = scopeLocal.get()
}

/** Resolution interface so providers can call resolver.get<Foo>() safely */
interface Resolver {
    fun <T : Any> get(type: KClass<T>, scope: ScopeContext? = null): T
    inline fun <reified T : Any> get(scope: ScopeContext? = null): T = get(T::class, scope)
}

/** A named cache for scoped instances (e.g., "request") */
class ScopeContext(val name: String) {
    internal val cache = mutableMapOf<KClass<*>, Any>()
    fun clear() = cache.clear()
}

// ---------- Binding implementations ----------

private data class InstanceBinding<T : Any>(
    override val type: KClass<T>,
    val instance: T
) : Binding<T> {
    override fun get(resolver: Resolver, scope: ScopeContext?) = instance
}

private data class SingletonBinding<T : Any>(
    override val type: KClass<T>,
    val provider: Provider<T>
) : Binding<T> {
    override fun get(resolver: Resolver, scope: ScopeContext?): T {
        val c = (resolver as? Container)
            ?: error("SingletonBinding requires Container resolver")
        return c.getOrCreateSingleton(type, provider, scope)
    }
}

private data class FactoryBinding<T : Any>(
    override val type: KClass<T>,
    val provider: Provider<T>
) : Binding<T> {
    override fun get(resolver: Resolver, scope: ScopeContext?): T =
        provider.invoke(resolver)
}

private data class ScopedBinding<T : Any>(
    override val type: KClass<T>,
    val scopeName: String,
    val provider: Provider<T>
) : Binding<T> {
    override fun get(resolver: Resolver, scope: ScopeContext?): T {
        val actual = scope ?: error("No active scope '$scopeName' for ${type.simpleName}")
        @Suppress("UNCHECKED_CAST")
        actual.cache[type]?.let { return it as T }
        val v = provider.invoke(resolver)
        actual.cache[type] = v
        return v
    }
}

/** Key for bindings that are scope-specific */
private data class ScopedKey<T : Any>(val type: KClass<T>, val scope: String)
