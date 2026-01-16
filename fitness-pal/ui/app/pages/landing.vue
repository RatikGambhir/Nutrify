<script setup lang="ts">
import type { BentoCardData } from '~/components/landing/BentoGrid.vue'
import Button from '~/components/ui/button/Button.vue'
import { ArrowRight } from 'lucide-vue-next'

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

const featureCards: BentoCardData[] = [
  {
    title: 'Smart Calorie Tracking',
    description: 'Effortlessly log your meals with our intuitive interface. Get instant nutritional breakdowns and stay within your daily calorie goals with intelligent recommendations.',
    icon: 'utensils',
    gradient: 'bg-gradient-to-br from-green-500 to-emerald-600',
    span: 'md:col-span-2 lg:col-span-2 lg:row-span-2'
  },
  {
    title: 'Macro Breakdown',
    description: 'Track proteins, carbs, and fats with precision. Visualize your macro distribution to optimize your nutrition.',
    icon: 'pie-chart',
    gradient: 'bg-gradient-to-br from-blue-500 to-cyan-600',
    span: 'md:col-span-1 lg:col-span-1'
  },
  {
    title: 'Workout Logging',
    description: 'Record your exercises, sets, and reps. Track your strength progress and stay consistent with your fitness routine.',
    icon: 'dumbbell',
    gradient: 'bg-gradient-to-br from-purple-500 to-pink-600',
    span: 'md:col-span-1 lg:col-span-1'
  },
  {
    title: 'Progress Analytics',
    description: 'Visualize your journey with beautiful charts and insights. Monitor trends, celebrate milestones, and stay motivated with data-driven progress tracking.',
    icon: 'trending-up',
    gradient: 'bg-gradient-to-br from-orange-500 to-red-600',
    span: 'md:col-span-2 lg:col-span-2'
  }
]

const benefitCards: BentoCardData[] = [
  {
    title: 'Smart Insights',
    description: 'Leverage AI-powered recommendations tailored to your goals. Get personalized suggestions for meals, workouts, and lifestyle adjustments that help you succeed faster.',
    icon: 'brain',
    gradient: 'bg-gradient-to-br from-indigo-500 to-purple-600',
    span: 'md:col-span-2 lg:col-span-2 lg:row-span-2'
  },
  {
    title: 'Effortless Tracking',
    description: 'Spend less time logging and more time living. Our streamlined interface makes tracking your nutrition and fitness second nature.',
    icon: 'zap',
    gradient: 'bg-gradient-to-br from-yellow-500 to-orange-600',
    span: 'md:col-span-1 lg:col-span-1 lg:row-span-2'
  },
  {
    title: 'Achieve Your Goals',
    description: 'Set custom goals and watch as we help you crush them. Whether it\'s weight loss, muscle gain, or maintenance, Nutrify adapts to your journey.',
    icon: 'target',
    gradient: 'bg-gradient-to-br from-green-500 to-teal-600',
    span: 'md:col-span-2 lg:col-span-2'
  },
  {
    title: 'Community Support',
    description: 'Join a community of health-conscious individuals. Share tips, celebrate wins, and stay motivated together.',
    icon: 'users',
    gradient: 'bg-gradient-to-br from-pink-500 to-rose-600',
    span: 'md:col-span-1 lg:col-span-1'
  },
  {
    title: 'Privacy & Security',
    description: 'Your data is encrypted and secure. We respect your privacy and never share your personal information.',
    icon: 'shield',
    gradient: 'bg-gradient-to-br from-red-500 to-pink-600',
    span: 'md:col-span-1 lg:col-span-1'
  },
  {
    title: 'Cross-Platform',
    description: 'Access your data anywhere, anytime. Seamlessly sync across all your devices for a consistent experience.',
    icon: 'smartphone',
    gradient: 'bg-gradient-to-br from-teal-500 to-cyan-600',
    span: 'md:col-span-1 lg:col-span-1'
  }
]
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
    <div class="container mx-auto px-4 sm:px-6 lg:px-8 relative z-10 py-24 sm:py-32">
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
          <Button
            size="lg"
            class="px-8 py-4 h-auto text-lg font-semibold bg-gray-900 hover:bg-gray-800 text-white shadow-xl hover:shadow-2xl hover:scale-105 transition-all duration-300 rounded-2xl"
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
            class="px-8 py-4 h-auto text-lg font-semibold border-2 border-gray-300 hover:border-gray-400 text-gray-700 hover:bg-gray-50 hover:scale-105 transition-all duration-300 rounded-2xl"
            @click="learnMore"
          >
            <span class="flex items-center gap-2">
              Learn More
            </span>
          </Button>
        </div>
      </div>

    </div>

    <!-- Features Bento Section -->
    <section class="py-24 bg-white">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-5xl font-bold text-gray-900 mb-4">
            Everything You Need to Succeed
          </h2>
          <p class="text-xl text-gray-600 max-w-2xl mx-auto">
            Powerful features designed to help you track, analyze, and optimize your nutrition and fitness journey.
          </p>
        </div>
        <LandingBentoGrid :cards="featureCards" />
      </div>
    </section>

    <!-- Benefits Bento Section -->
    <section class="py-24 bg-gray-50">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-5xl font-bold text-gray-900 mb-4">
            Why Choose Nutrify?
          </h2>
          <p class="text-xl text-gray-600 max-w-2xl mx-auto">
            More than just tracking—experience a complete ecosystem built for your success.
          </p>
        </div>
        <LandingBentoGrid :cards="benefitCards" :columns="3" />
      </div>
    </section>

    <!-- Feedback Form Section -->
    <section class="py-24 pb-32 bg-white">
      <div class="container mx-auto px-4 sm:px-6 lg:px-8">
        <LandingFeedbackForm />
      </div>
    </section>
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
