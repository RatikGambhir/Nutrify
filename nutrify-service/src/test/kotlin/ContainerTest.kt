package com.nutrify

import com.google.genai.Client
import com.nutrify.lib.SQLFactory
import com.nutrify.lib.SupabaseManager
import com.zaxxer.hikari.HikariDataSource
import io.mockk.mockk
import io.mockk.every
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

// Test classes - Empty objects (no constructor parameters)
class EmptyObject1
class EmptyObject2
class EmptyObject3

// Test classes - Objects with nested dependencies
class NestedObject1
class NestedObject2(val dep: NestedObject1)
class NestedObject3(val dep1: NestedObject1, val dep2: NestedObject2)

class ContainerTest {

    @Test
    fun testBindAllWithEmptyObjects() {
        val container = Container()
        val emptyObjects = listOf(
            EmptyObject1(),
            EmptyObject2(),
            EmptyObject3()
        )

        container.bindAll(emptyObjects)

        assertNotNull(container.instanceExists(EmptyObject1::class))
        assertNotNull(container.instanceExists(EmptyObject2::class))
        assertNotNull(container.instanceExists(EmptyObject3::class))
    }

    @Test
    fun testBindAllWithNestedObjects() {
        val container = Container()

        val nested1 = NestedObject1()
        val nested2 = NestedObject2(nested1)
        val nested3 = NestedObject3(nested1, nested2)

        val nestedObjects = listOf(
            nested1,
            nested2,
            nested3
        )

        container.bindAll(nestedObjects)

        assertNotNull(container.instanceExists(NestedObject1::class))
        assertNotNull(container.instanceExists(NestedObject2::class))
        assertNotNull(container.instanceExists(NestedObject3::class))
    }

    @Test
    fun testBindAllWithMockedSupabaseManagerAndGeminiClient() {
        val container = Container()

        val mockDataSource = mockk<HikariDataSource>(relaxed = true)
        val mockSQLFactory = mockk<SQLFactory>(relaxed = true)
        val mockGeminiGenAIClient = mockk<Client>(relaxed = true)

        val supabaseManager = SupabaseManager(mockDataSource, mockSQLFactory)
        val geminiClient = GeminiClient(mockGeminiGenAIClient)

        val instances = listOf(supabaseManager, geminiClient)
        container.bindAll(instances)

        val boundSupabaseManager = container.instanceExists(SupabaseManager::class)
        assertNotNull(boundSupabaseManager)
        assertSame(supabaseManager, boundSupabaseManager)

        val boundGeminiClient = container.instanceExists(GeminiClient::class)
        assertNotNull(boundGeminiClient)
        assertSame(geminiClient, boundGeminiClient)

        assertEquals(mockDataSource, boundSupabaseManager.dataSource)
        assertEquals(mockSQLFactory, boundSupabaseManager.sqlFactory)
        assertEquals(mockGeminiGenAIClient, boundGeminiClient.client)
    }

    @Test
    fun testBindAllWithMockedDependenciesInList() {
        val container = Container()

        val mockDataSource = mockk<HikariDataSource>(relaxed = true)
        val mockSQLFactory = mockk<SQLFactory>(relaxed = true)
        val mockGeminiGenAIClient = mockk<Client>(relaxed = true)

        val supabaseManager = SupabaseManager(mockDataSource, mockSQLFactory)
        val geminiClient = GeminiClient(mockGeminiGenAIClient)

        val allComponents = listOf(
            supabaseManager,
            geminiClient
        )

        container.bindAll(allComponents)

        assertNotNull(container.instanceExists(SupabaseManager::class))
        assertNotNull(container.instanceExists(GeminiClient::class))

        val retrievedSupabaseManager = container.instanceExists(SupabaseManager::class)!!
        val retrievedGeminiClient = container.instanceExists(GeminiClient::class)!!

        assertSame(mockDataSource, retrievedSupabaseManager.dataSource)
        assertSame(mockSQLFactory, retrievedSupabaseManager.sqlFactory)
        assertSame(mockGeminiGenAIClient, retrievedGeminiClient.client)
    }

    @Test
    fun testBindAllDoesNotDuplicateBindings() {
        val container = Container()

        val mockDataSource = mockk<HikariDataSource>(relaxed = true)
        val mockSQLFactory = mockk<SQLFactory>(relaxed = true)

        val supabaseManager1 = SupabaseManager(mockDataSource, mockSQLFactory)
        val supabaseManager2 = SupabaseManager(mockDataSource, mockSQLFactory)

        container.bindAll(listOf(supabaseManager1))

        container.bindAll(listOf(supabaseManager2))

        val boundManager = container.instanceExists(SupabaseManager::class)
        assertNotNull(boundManager)
        assertSame(supabaseManager1, boundManager)
    }
}
