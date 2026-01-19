<script setup lang="ts">
import type { BentoCardData } from '~/components/landing/BentoGrid.vue'
import HeroSection from '~/components/landing/HeroSection.vue'
import BarChartAnimation from '~/components/landing/BarChartAnimation.vue'
import ContentHero from '~/components/landing/ContentHero.vue'

definePageMeta({
  layout: 'landing',
})

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
  <div class="relative w-full">
    <!-- Mountain Hero Section -->
    <HeroSection />

    <!-- Main Content Section -->
    <div class="relative min-h-screen w-full bg-[oklch(32%_0.002_260)]">
      <!-- Bar Chart Animation -->
      <BarChartAnimation />

      <!-- Content Hero (tagline, heading, description, CTA buttons) -->
      <ContentHero />

      <!-- Features Bento Section -->
      <section class="py-24">
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

      <!-- Benefits Bento Section -->
      <section class="py-24">
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

      <!-- Feedback Form Section -->
      <section class="py-24 pb-32">
        <div class="container mx-auto px-4 sm:px-6 lg:px-8">
          <FeedbackForm />
        </div>
      </section>
    </div>
  </div>
</template>

