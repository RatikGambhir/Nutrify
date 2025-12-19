<script setup lang="ts">
definePageMeta({
  layout: 'landing',
})

const mounted = ref(false)

onMounted(() => {
  //TODO: Add animation trigger here
  setTimeout(() => {
    mounted.value = true
  }, 100)
})

const getStarted = () => {
  navigateTo('/login')
}

const learnMore = () => {
  // TODO: Scroll to features or navigate to about page
  console.log('Learn More clicked')
}
</script>

<template>
  <div class="relative min-h-screen w-full overflow-hidden flex items-center justify-center">
    <div class="absolute inset-0 z-0">
      <NuxtImg
        src="/assets/background-image.jpeg"
        alt="Background"
        class="w-full h-full object-cover"
      />
      <div class="absolute inset-0 bg-gradient-to-b from-black/50 via-black/40 to-black/60" />
    </div>

    <UContainer class="relative z-10 py-24 sm:py-32">
      <div class="flex flex-col items-center text-center space-y-8">
        <h1
          class="text-5xl sm:text-6xl md:text-7xl lg:text-8xl font-black text-white leading-tight tracking-tight transition-all duration-1000 ease-out"
          :class="{
            'opacity-0 translate-y-8': !mounted,
            'opacity-100 translate-y-0': mounted,
          }"
        >
          Transform Your
          <span class="bg-gradient-to-r from-green-400 via-emerald-400 to-teal-400 bg-clip-text text-transparent">
            Fitness Journey
          </span>
        </h1>

        <p
          class="text-xl sm:text-2xl md:text-3xl text-white max-w-3xl font-medium transition-all duration-1000 ease-out delay-200"
          :class="{
            'opacity-0 translate-y-8': !mounted,
            'opacity-100 translate-y-0': mounted,
          }"
        >
          Track your nutrition, achieve your goals, and become the best version of yourself
        </p>

        <div
          class="flex flex-row items-center justify-center gap-4 w-full sm:w-auto transition-all duration-1000 ease-out delay-300"
          :class="{
            'opacity-0 translate-y-8': !mounted,
            'opacity-100 translate-y-0': mounted,
          }"
        >
          <UButton
            size="xl"
            color="primary"
            variant="solid"
            class="px-8 py-4 text-lg font-semibold shadow-2xl hover:scale-105 transition-transform duration-300"
            @click="getStarted"
          >
            <span class="flex items-center gap-2">
              Get Started
              <UIcon name="i-lucide-arrow-right" class="w-5 h-5" />
            </span>
          </UButton>

          <UButton
            size="xl"
            color="neutral"
            variant="outline"
            class="px-8 py-4 text-lg font-semibold backdrop-blur-sm bg-white/10 hover:bg-white/20 border-2 border-white/30 hover:scale-105 transition-all duration-300"
            @click="learnMore"
          >
            <span class="flex items-center gap-2 text-white">
              Learn More
              <UIcon name="i-lucide-info" class="w-5 h-5" />
            </span>
          </UButton>
        </div>
        <div
            class="flex flex-wrap justify-center gap-4 mt-8 transition-all duration-1000 ease-out delay-500"
            :class="{
            'opacity-0 translate-y-8': !mounted,
            'opacity-100 translate-y-0': mounted,
          }"
        >
          <div
              v-for="feature in ['Nutrition Tracking', 'Workout Plans', 'Goal Setting']"
              :key="feature"
          >
            <UButton
                size="xl"
                color="neutral"
                variant="outline"
                class="px-4 py-2 rounded-full bg-white/10 backdrop-blur-md border border-white/20 text-white text-sm font-medium"
                @click="learnMore"
            >
            <span class="flex items-center gap-2 text-white">
              {{feature}}
            </span>
            </UButton>
          </div>
        </div>
      </div>
    </UContainer>
  </div>
</template>

<style scoped>
@keyframes float {
  0%, 100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-10px);
  }
}

.animate-float {
  animation: float 3s ease-in-out infinite;
}
</style>
