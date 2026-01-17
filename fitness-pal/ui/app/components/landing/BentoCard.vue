<script setup lang="ts">
import Card from '~/components/ui/card/Card.vue'
import CardContent from '~/components/ui/card/CardContent.vue'
import {
  Utensils,
  PieChart,
  Dumbbell,
  TrendingUp,
  Brain,
  Zap,
  Target,
  Users,
  Shield,
  Smartphone,
  HeartPulse,
  Flame,
  Repeat,
  ShieldCheck
} from 'lucide-vue-next'

interface Props {
  title: string
  description: string
  icon: string
  gradient: string
  span: string
  isVisible: boolean
  index: number
}

const props = defineProps<Props>()

const iconMap: Record<string, any> = {
  'utensils': Utensils,
  'pie-chart': PieChart,
  'dumbbell': Dumbbell,
  'trending-up': TrendingUp,
  'brain': Brain,
  'zap': Zap,
  'target': Target,
  'users': Users,
  'shield': Shield,
  'smartphone': Smartphone,
  'heart-pulse': HeartPulse,
  'flame': Flame,
  'repeat': Repeat,
  'shield-check': ShieldCheck
}

const iconComponent = computed(() => iconMap[props.icon] || Shield)
</script>

<template>
  <Card
    :class="[
      'relative overflow-hidden h-full min-h-[200px]',
      'transition-all duration-1000 ease-out',
      'hover:scale-[1.02] hover:-translate-y-1 hover:shadow-2xl',
      'cursor-pointer will-change-transform',
      span,
      {
        'opacity-0 translate-y-8': !isVisible,
        'opacity-100 translate-y-0': isVisible,
      }
    ]"
    :style="{
      transitionDelay: isVisible ? `${index * 100}ms` : '0ms'
    }"
  >
    <CardContent class="p-6">
      <div :class="['absolute inset-0 opacity-10 group-hover:opacity-20 transition-opacity', gradient]" />

      <div class="relative z-10 h-full flex flex-col gap-4">
        <div :class="['w-14 h-14 rounded-2xl flex items-center justify-center', gradient]">
          <component :is="iconComponent" class="w-7 h-7 text-white" />
        </div>

        <div class="flex-1">
          <h3 class="text-xl font-bold text-foreground mb-2">
            {{ title }}
          </h3>
          <p class="text-muted-foreground leading-relaxed">
            {{ description }}
          </p>
        </div>
      </div>
    </CardContent>
  </Card>
</template>
