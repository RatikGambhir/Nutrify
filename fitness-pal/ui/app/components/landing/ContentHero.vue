<script setup lang="ts">
import Button from '~/components/ui/button/Button.vue'
import { ArrowRight } from 'lucide-vue-next'

const mounted = ref(false)

onMounted(() => {
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
  <div class="container mx-auto px-4 sm:px-6 lg:px-8 relative z-10 py-24 sm:py-32">
    <div class="flex flex-col items-start max-w-3xl space-y-8">
      <!-- Tagline badge -->
      <div
        class="transition-all duration-800 ease-out"
        :class="{
          'opacity-0 -translate-x-4': !mounted,
          'opacity-100 translate-x-0': mounted,
        }"
      >
        <div class="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-primary/10 border border-primary/20">
          <div class="w-2 h-2 bg-primary rounded-full animate-pulse" />
          <span class="text-sm font-medium text-foreground/80">Track smarter, achieve faster</span>
        </div>
      </div>

      <!-- Main heading -->
      <h1
        class="text-6xl sm:text-7xl md:text-8xl font-black text-foreground leading-tight tracking-tight transition-all duration-1000 ease-out delay-100"
        :class="{
          'opacity-0 -translate-x-8': !mounted,
          'opacity-100 translate-x-0': mounted,
        }"
      >
        Your Nutrition & Fitness,
        <br>
        <span class="bg-gradient-to-r from-green-600 via-emerald-500 to-green-400 bg-clip-text text-transparent">
          Simplified
        </span>
      </h1>

      <!-- Description -->
      <p
        class="text-xl sm:text-2xl text-muted-foreground max-w-2xl font-medium transition-all duration-1000 ease-out delay-200"
        :class="{
          'opacity-0 -translate-x-8': !mounted,
          'opacity-100 translate-x-0': mounted,
        }"
      >
        Nutrify transforms how you track calories, macros, and workouts with intelligent insights that keep you on track to crush your goals.
      </p>

      <!-- CTA buttons -->
      <div
        class="flex flex-row items-center gap-4 w-full sm:w-auto transition-all duration-1000 ease-out delay-300"
        :class="{
          'opacity-0 -translate-x-8': !mounted,
          'opacity-100 translate-x-0': mounted,
        }"
      >
        <Button
          size="lg"
          class="px-8 py-4 h-auto text-lg font-semibold bg-primary hover:bg-primary/90 text-primary-foreground shadow-xl hover:shadow-2xl hover:scale-105 transition-all duration-300 rounded-2xl"
          @click="getStarted"
        >
          <span class="flex items-center gap-2">
            Get Started
            <ArrowRight class="w-5 h-5" />
          </span>
        </Button>

        <Button
          size="lg"
          variant="outline"
          class="px-8 py-4 h-auto text-lg font-semibold border-2 border-border hover:border-primary/50 text-foreground hover:bg-accent hover:scale-105 transition-all duration-300 rounded-2xl"
          @click="learnMore"
        >
          <span class="flex items-center gap-2">
            Learn More
          </span>
        </Button>
      </div>
    </div>
  </div>
</template>
