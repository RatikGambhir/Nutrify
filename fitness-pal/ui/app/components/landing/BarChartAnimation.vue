<script setup lang="ts">
const mounted = ref(false)

onMounted(() => {
  setTimeout(() => {
    mounted.value = true
  }, 100)
})

const barHeights = [40, 65, 50, 80, 60, 90, 70, 85, 55, 75, 95, 65]
</script>

<template>
  <div class="absolute top-0 right-0 z-0 flex items-end justify-end pr-8 pointer-events-none overflow-hidden">
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
            rgba(249, 115, 22, 0.9),
            rgba(251, 146, 60, 0.7),
            rgba(253, 186, 116, 0.4))`,
          borderRadius: '8px 8px 0 0',
          boxShadow: '0 4px 20px rgba(249, 115, 22, 0.2)',
        }"
      />
    </div>
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
</style>
