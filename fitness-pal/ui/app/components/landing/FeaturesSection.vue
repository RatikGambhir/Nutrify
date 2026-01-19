<script setup lang="ts">
import type { BentoCardData } from '~/components/landing/BentoGrid.vue'

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
</script>

<template>
  <section class="py-24 bg-background">
    <div class="container mx-auto px-4 sm:px-6 lg:px-8">
      <div class="text-center mb-16">
        <h2 class="text-5xl font-bold text-foreground mb-4">
          Everything You Need to Succeed
        </h2>
        <p class="text-xl text-muted-foreground max-w-2xl mx-auto">
          Powerful features designed to help you track, analyze, and optimize your nutrition and fitness journey.
        </p>
      </div>
      <BentoGrid :cards="featureCards" />
    </div>
  </section>
</template>
