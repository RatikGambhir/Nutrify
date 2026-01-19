<script setup lang="ts">
import type { BentoCardData } from '~/components/landing/BentoGrid.vue'

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
  <section class="py-24 bg-background">
    <div class="container mx-auto px-4 sm:px-6 lg:px-8">
      <div class="text-center mb-16">
        <h2 class="text-5xl font-bold text-foreground mb-4">
          Why Choose Nutrify?
        </h2>
        <p class="text-xl text-muted-foreground max-w-2xl mx-auto">
          More than just tracking—experience a complete ecosystem built for your success.
        </p>
      </div>
      <BentoGrid :cards="benefitCards" :columns="3" />
    </div>
  </section>
</template>
