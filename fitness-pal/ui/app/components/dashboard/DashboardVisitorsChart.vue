<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { eachDayOfInterval, subDays, subMonths, format } from 'date-fns'
import { VisXYContainer, VisArea, VisLine, VisAxis, VisCrosshair, VisTooltip } from '@unovis/vue'
import { useElementSize } from '@vueuse/core'

type TimePeriod = 'last3months' | 'last30days' | 'last7days'

type DataRecord = {
  date: Date
  visitors: number
  sessions: number
}

const cardRef = ref<HTMLElement | null>(null)
const { width } = useElementSize(cardRef)

const selectedPeriod = ref<TimePeriod>('last3months')

const periods = [
  { value: 'last3months' as TimePeriod, label: 'Last 3 months' },
  { value: 'last30days' as TimePeriod, label: 'Last 30 days' },
  { value: 'last7days' as TimePeriod, label: 'Last 7 days' }
]

const data = ref<DataRecord[]>([])

const generateData = () => {
  const now = new Date()
  let start: Date

  switch (selectedPeriod.value) {
    case 'last7days':
      start = subDays(now, 7)
      break
    case 'last30days':
      start = subDays(now, 30)
      break
    case 'last3months':
    default:
      start = subMonths(now, 3)
      break
  }

  const dates = eachDayOfInterval({ start, end: now })

  // Generate wave-like data for the chart to match the screenshot
  data.value = dates.map((date, index) => {
    const baseVisitors = 150 + Math.sin(index * 0.3) * 80 + Math.random() * 40
    const baseSessions = 80 + Math.sin(index * 0.3 + 1) * 50 + Math.random() * 30
    return {
      date,
      visitors: Math.floor(baseVisitors),
      sessions: Math.floor(baseSessions)
    }
  })
}

watch(selectedPeriod, generateData, { immediate: true })

const x = (_: DataRecord, i: number) => i
const yVisitors = (d: DataRecord) => d.visitors
const ySessions = (d: DataRecord) => d.sessions

const xTicks = (i: number) => {
  if (!data.value[i]) return ''

  const totalPoints = data.value.length
  // Show approximately 12 ticks
  const interval = Math.max(1, Math.floor(totalPoints / 12))

  if (i % interval !== 0 && i !== totalPoints - 1) return ''

  return format(data.value[i].date, 'MMM d')
}

const template = (d: DataRecord) => {
  return `${format(d.date, 'MMM d, yyyy')}<br/>Visitors: ${d.visitors}<br/>Sessions: ${d.sessions}`
}

const periodLabel = computed(() => {
  const period = periods.find(p => p.value === selectedPeriod.value)
  return period?.label.replace('Last ', 'the last ') || 'the last 3 months'
})
</script>

<template>
  <div ref="cardRef" class="bg-muted rounded-2xl p-6">
    <!-- Header -->
    <div class="flex items-start justify-between mb-6">
      <div>
        <h3 class="text-lg font-semibold text-foreground">Total Visitors</h3>
        <p class="text-sm text-muted-foreground">Total for {{ periodLabel }}</p>
      </div>

      <!-- Period tabs -->
      <div class="flex items-center gap-1 bg-background rounded-lg p-1 border">
        <button
          v-for="period in periods"
          :key="period.value"
          class="px-3 py-1.5 text-sm font-medium rounded-md transition-colors"
          :class="[
            selectedPeriod === period.value
              ? 'bg-muted text-foreground'
              : 'text-muted-foreground hover:text-foreground'
          ]"
          @click="selectedPeriod = period.value"
        >
          {{ period.label }}
        </button>
      </div>
    </div>

    <!-- Chart -->
    <div class="h-80 relative">
      <!-- SVG Gradient Definitions -->
      <svg class="absolute w-0 h-0">
        <defs>
          <linearGradient id="greenGradient" x1="0%" y1="0%" x2="0%" y2="100%">
            <stop offset="0%" stop-color="#22c55e" stop-opacity="0.5" />
            <stop offset="100%" stop-color="#22c55e" stop-opacity="0.05" />
          </linearGradient>
          <linearGradient id="orangeGradient" x1="0%" y1="0%" x2="0%" y2="100%">
            <stop offset="0%" stop-color="#f97316" stop-opacity="0.5" />
            <stop offset="100%" stop-color="#f97316" stop-opacity="0.05" />
          </linearGradient>
        </defs>
      </svg>

      <VisXYContainer
        :data="data"
        :padding="{ top: 20, bottom: 0, left: 0, right: 0 }"
        class="h-full"
        :width="width - 48"
      >
        <!-- Sessions area (orange, behind) -->
        <VisArea
          :x="x"
          :y="ySessions"
          color="url(#orangeGradient)"
          :curve-type="'basis'"
        />
        <!-- Sessions line (orange) -->
        <VisLine
          :x="x"
          :y="ySessions"
          color="#f97316"
          :curve-type="'basis'"
          :line-width="2"
        />

        <!-- Visitors area (green, in front) -->
        <VisArea
          :x="x"
          :y="yVisitors"
          color="url(#greenGradient)"
          :curve-type="'basis'"
        />
        <!-- Visitors line (green) -->
        <VisLine
          :x="x"
          :y="yVisitors"
          color="#22c55e"
          :curve-type="'basis'"
          :line-width="2"
        />

        <VisAxis
          type="x"
          :x="x"
          :tick-format="xTicks"
          :grid-line="false"
        />

        <VisCrosshair
          color="#22c55e"
          :template="template"
        />

        <VisTooltip />
      </VisXYContainer>
    </div>
  </div>
</template>

<style scoped>
.unovis-xy-container {
  --vis-crosshair-line-stroke-color: var(--foreground);
  --vis-crosshair-circle-stroke-color: var(--background);

  --vis-axis-grid-color: var(--border);
  --vis-axis-tick-color: transparent;
  --vis-axis-tick-label-color: var(--muted-foreground);

  --vis-tooltip-background-color: var(--background);
  --vis-tooltip-border-color: var(--border);
  --vis-tooltip-text-color: var(--foreground);
}
</style>
