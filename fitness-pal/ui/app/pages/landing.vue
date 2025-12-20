<script setup lang="ts">
definePageMeta({
  layout: 'landing',
})

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

const barHeights = [40, 65, 50, 80, 60, 90, 70, 85, 55, 75, 95, 65]
</script>

<template>
  <div class="relative min-h-screen w-full overflow-hidden bg-gradient-to-br from-gray-50 via-white to-gray-100">
    <div class="absolute top-[175px] right-0 z-0 flex items-end justify-end pr-8 pointer-events-none overflow-hidden">
      <div class="flex items-end gap-3 h-[600px] opacity-80">
        <div
          v-for="(height, index) in barHeights"
          :key="index"
          class="bar-graph transition-all duration-1000 ease-out"
          :class="{
            'opacity-0 translate-y-8': !mounted,
            'opacity-100 translate-y-0': mounted,
          }"
          :style="{
            height: mounted ? `${height}%` : '0%',
            transitionDelay: `${600 + index * 80}ms`,
            width: '40px',
            background: `linear-gradient(to top,
              rgba(34, 197, 94, 0.9),
              rgba(74, 222, 128, 0.7),
              rgba(134, 239, 172, 0.4))`,
            borderRadius: '8px 8px 0 0',
            boxShadow: '0 4px 20px rgba(34, 197, 94, 0.2)',
          }"
        />
      </div>
    </div>
    <UContainer class="relative z-10 py-24 sm:py-32">
      <div class="flex flex-col items-start max-w-3xl space-y-8">
        <div
          class="transition-all duration-800 ease-out"
          :class="{
            'opacity-0 -translate-x-4': !mounted,
            'opacity-100 translate-x-0': mounted,
          }"
        >
          <div class="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-green-100 border border-green-200">
            <div class="w-2 h-2 bg-green-500 rounded-full animate-pulse" />
            <span class="text-sm font-medium text-gray-700">Track smarter, achieve faster</span>
          </div>
        </div>
        <h1
          class="text-6xl sm:text-7xl md:text-8xl font-black text-gray-900 leading-tight tracking-tight transition-all duration-1000 ease-out delay-100"
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

        <p
          class="text-xl sm:text-2xl text-gray-600 max-w-2xl font-medium transition-all duration-1000 ease-out delay-200"
          :class="{
            'opacity-0 -translate-x-8': !mounted,
            'opacity-100 translate-x-0': mounted,
          }"
        >
          Nutrify transforms how you track calories, macros, and workouts with intelligent insights that keep you on track to crush your goals.
        </p>

        <div
          class="flex flex-row items-center gap-4 w-full sm:w-auto transition-all duration-1000 ease-out delay-300"
          :class="{
            'opacity-0 -translate-x-8': !mounted,
            'opacity-100 translate-x-0': mounted,
          }"
        >
          <UButton
            size="xl"
            color="primary"
            variant="solid"
            class="px-8 py-4 text-lg font-semibold bg-gray-900 hover:bg-gray-800 text-white shadow-xl hover:shadow-2xl hover:scale-105 transition-all duration-300 rounded-2xl"
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
            class="px-8 py-4 text-lg font-semibold border-2 border-gray-300 hover:border-gray-400 text-gray-700 hover:bg-gray-50 hover:scale-105 transition-all duration-300 rounded-2xl"
            @click="learnMore"
          >
            <span class="flex items-center gap-2">
              Learn More
            </span>
          </UButton>
        </div>
      </div>

<!--      <div class="w-full h-px bg-gradient-to-r from-transparent via-gray-900 to-transparent mt-0 mb-8" />-->

<!--      <div class="max-w-3xl">-->
<!--        <div-->
<!--          class="flex flex-wrap gap-3 transition-all duration-1000 ease-out delay-400"-->
<!--          :class="{-->
<!--            'opacity-0 -translate-x-8': !mounted,-->
<!--            'opacity-100 translate-x-0': mounted,-->
<!--          }"-->
<!--        >-->
<!--          <div-->
<!--            v-for="(feature, index) in ['Calorie Tracking', 'Macro Analysis', 'Workout Logs', 'Progress Charts']"-->
<!--            :key="feature"-->
<!--            class="transition-all duration-700 ease-out"-->
<!--            :style="{ transitionDelay: `${500 + index * 100}ms` }"-->
<!--            :class="{-->
<!--              'opacity-0 translate-y-4': !mounted,-->
<!--              'opacity-100 translate-y-0': mounted,-->
<!--            }"-->
<!--          >-->
<!--            <UButton-->
<!--              size="xl"-->
<!--              color="neutral"-->
<!--              variant="outline"-->
<!--              class="px-4 py-2 rounded-full bg-white border border-gray-200 text-gray-700 text-sm font-medium shadow-sm hover:shadow-md hover:border-gray-300 transition-all duration-300"-->
<!--              @click="learnMore"-->
<!--            >-->
<!--              <span class="flex items-center gap-2">-->
<!--                {{ feature }}-->
<!--              </span>-->
<!--            </UButton>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
    </UContainer>
  </div>
</template>

<style scoped>
@keyframes growHeight {
  from {
    height: 0%;
  }
}

.bar-graph {
  animation: growHeight 1.2s ease-out;
}

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
