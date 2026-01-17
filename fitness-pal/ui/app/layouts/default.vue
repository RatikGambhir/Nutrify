<script setup lang="ts">
import { Home, Dumbbell, Utensils, Settings, MessageCircle, HelpCircle, Search, PanelLeftClose, PanelLeft } from 'lucide-vue-next'
import Button from '~/components/ui/button/Button.vue'

const route = useRoute()
const toast = useToast()

const sidebarOpen = ref(true)
const sidebarCollapsed = ref(false)

const mainNavItems = [
  { label: 'Home', icon: Home, to: '/' },
  { label: 'Workouts', icon: Dumbbell, to: '/workouts' },
  { label: 'Nutrition', icon: Utensils, to: '/nutrition' },
  { label: 'Settings', icon: Settings, to: '/settings' }
]

const secondaryNavItems = [
  { label: 'Feedback', icon: MessageCircle, to: 'https://github.com/nuxt-ui-pro/dashboard', external: true },
  { label: 'Help & Support', icon: HelpCircle, to: 'https://github.com/nuxt/ui-pro', external: true }
]

const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value
}

onMounted(async () => {
  const cookie = useCookie('cookie-consent')
  if (cookie.value === 'accepted') {
    return
  }

  toast.add({
    title: 'We use first-party cookies to enhance your experience on our website.',
    description: 'Click Accept to continue.',
    color: 'info'
  })
  cookie.value = 'accepted'
})
</script>

<template>
  <div class="flex h-screen bg-background">
    <!-- Sidebar -->
    <aside
      class="flex flex-col border-r bg-card transition-all duration-300"
      :class="sidebarCollapsed ? 'w-16' : 'w-64'"
    >
      <!-- Header -->
      <div class="flex h-16 items-center border-b px-4">
        <NuxtLink to="/" class="flex items-center gap-2">
          <div class="w-8 h-8 bg-primary rounded" />
          <span v-if="!sidebarCollapsed" class="text-lg font-bold text-foreground">Nutrify</span>
        </NuxtLink>
      </div>

      <!-- Search Button -->
      <div class="p-2">
        <Button
          variant="outline"
          :class="sidebarCollapsed ? 'w-full justify-center' : 'w-full justify-start'"
        >
          <Search class="h-4 w-4" />
          <span v-if="!sidebarCollapsed" class="ml-2">Search...</span>
        </Button>
      </div>

      <!-- Main Navigation -->
      <nav class="flex-1 space-y-1 p-2">
        <NuxtLink
          v-for="item in mainNavItems"
          :key="item.to"
          :to="item.to"
          class="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-colors hover:bg-accent hover:text-accent-foreground"
          :class="{
            'bg-accent text-accent-foreground': route.path === item.to || (item.to !== '/' && route.path.startsWith(item.to)),
            'justify-center': sidebarCollapsed
          }"
        >
          <component :is="item.icon" class="h-5 w-5 shrink-0" />
          <span v-if="!sidebarCollapsed">{{ item.label }}</span>
        </NuxtLink>
      </nav>

      <!-- Secondary Navigation -->
      <div class="border-t p-2">
        <a
          v-for="item in secondaryNavItems"
          :key="item.to"
          :href="item.to"
          target="_blank"
          rel="noopener noreferrer"
          class="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-colors hover:bg-accent hover:text-accent-foreground"
          :class="{ 'justify-center': sidebarCollapsed }"
        >
          <component :is="item.icon" class="h-5 w-5 shrink-0" />
          <span v-if="!sidebarCollapsed">{{ item.label }}</span>
        </a>
      </div>

      <!-- Collapse Toggle -->
      <div class="border-t p-2">
        <Button
          variant="ghost"
          size="sm"
          :class="sidebarCollapsed ? 'w-full justify-center' : 'w-full justify-start'"
          @click="toggleSidebar"
        >
          <PanelLeftClose v-if="!sidebarCollapsed" class="h-4 w-4" />
          <PanelLeft v-else class="h-4 w-4" />
          <span v-if="!sidebarCollapsed" class="ml-2">Collapse</span>
        </Button>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 overflow-auto">
      <slot />
    </main>
  </div>
</template>