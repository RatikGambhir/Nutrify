<script setup lang="ts">
import { useIntersectionObserver } from '@vueuse/core'

export interface BentoCardData {
  title: string
  description: string
  icon: string
  gradient: string
  span: string
}

interface Props {
  cards: BentoCardData[]
  columns?: number
}

const props = withDefaults(defineProps<Props>(), {
  columns: 4
})

const gridRef = ref<HTMLElement | null>(null)
const isVisible = ref(false)

useIntersectionObserver(
  gridRef,
  ([{ isIntersecting }]) => {
    if (isIntersecting && !isVisible.value) {
      isVisible.value = true
    }
  },
  { threshold: 0.1, rootMargin: '0px 0px -100px 0px' }
)

const gridClasses = computed(() => {
  const baseClasses = 'grid grid-cols-1 md:grid-cols-2 gap-6 w-full'
  const lgColClass = props.columns === 3 ? 'lg:grid-cols-3' : 'lg:grid-cols-4'
  return `${baseClasses} ${lgColClass}`
})
</script>

<template>
  <div
    ref="gridRef"
    :class="gridClasses"
  >
    <LandingBentoCard
      v-for="(card, index) in cards"
      :key="card.title"
      :title="card.title"
      :description="card.description"
      :icon="card.icon"
      :gradient="card.gradient"
      :span="card.span"
      :is-visible="isVisible"
      :index="index"
    />
  </div>
</template>
