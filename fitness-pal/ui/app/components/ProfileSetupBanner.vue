<script setup lang="ts">
const router = useRouter()
const userStore = useUserStore()

const DISMISS_COOKIE_NAME = 'profile-setup-dismissed'
const DISMISS_DURATION_DAYS = 7

const isDismissed = ref(false)

onMounted(() => {
  const dismissedUntil = useCookie(DISMISS_COOKIE_NAME).value
  if (dismissedUntil) {
    const dismissedDate = new Date(dismissedUntil as string)
    if (dismissedDate > new Date()) {
      isDismissed.value = true
    }
  }
})

const shouldShowBanner = computed(() => {
  return !userStore.hasCompletedProfile && !isDismissed.value
})

const completeNow = () => {
  router.push('/setup-profile')
}

const remindLater = () => {
  const expiryDate = new Date()
  expiryDate.setDate(expiryDate.getDate() + DISMISS_DURATION_DAYS)

  const cookie = useCookie(DISMISS_COOKIE_NAME, {
    expires: expiryDate
  })
  cookie.value = expiryDate.toISOString()

  isDismissed.value = true
}
</script>

<template>
  <UAlert
    v-if="shouldShowBanner"
    icon="i-lucide-user-circle"
    color="primary"
    variant="subtle"
    title="Complete Your Profile"
    description="Set up your fitness profile to get personalized recommendations and track your progress more effectively."
    :close-button="{ icon: 'i-lucide-x', color: 'gray', variant: 'ghost' }"
    @close="remindLater"
  >
    <template #actions>
      <UButton
        color="primary"
        variant="solid"
        size="sm"
        class="bg-gray-900 hover:bg-gray-800"
        @click="completeNow"
      >
        Complete Now
      </UButton>
      <UButton
        color="secondary"
        variant="ghost"
        size="sm"
        @click="remindLater"
      >
        Remind Me Later
      </UButton>
    </template>
  </UAlert>
</template>
