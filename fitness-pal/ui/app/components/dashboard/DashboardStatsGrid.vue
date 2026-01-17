<script setup lang="ts">
import DashboardStatCard from './DashboardStatCard.vue'

const stats = [
  {
    title: 'Total Revenue',
    value: '$1,250.00',
    change: 12.5,
    trend: 'Trending up this month',
    description: 'Visitors for the last 6 months'
  },
  {
    title: 'New Customers',
    value: '1,234',
    change: -20,
    trend: 'Down 20% this period',
    description: 'Acquisition needs attention'
  },
  {
    title: 'Active Accounts',
    value: '45,678',
    change: 12.5,
    trend: 'Strong user retention',
    description: 'Engagement exceed targets'
  },
  {
    title: 'Growth Rate',
    value: '4.5%',
    change: 4.5,
    trend: 'Steady performance',
    description: 'Meets growth projections'
  }
]
</script>

<template>
  <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 mb-6">
    <DashboardStatCard
      v-for="stat in stats"
      :key="stat.title"
      :title="stat.title"
      :value="stat.value"
      :change="stat.change"
      :trend="stat.trend"
      :description="stat.description"
    />
  </div>
</template>
