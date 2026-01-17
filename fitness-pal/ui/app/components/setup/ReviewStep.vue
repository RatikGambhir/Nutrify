<script setup lang="ts">
import Card from '~/components/ui/card/Card.vue'
import CardContent from '~/components/ui/card/CardContent.vue'

interface PersonalInfo {
  firstName: string
  lastName: string
  email: string
  phone: string
}

interface Goals {
  primaryGoal: string
  targetWeight: string
  timeline: string
  motivation: string
}

interface Nutrition {
  dietType: string
  allergies: string
  dailyCalories: string
  mealsPerDay: string
}

interface Training {
  experienceLevel: string
  workoutFrequency: string
  preferredActivities: string
  injuries: string
}

interface Props {
  personalInfo: PersonalInfo
  goals: Goals
  nutrition: Nutrition
  training: Training
}

const props = defineProps<Props>()
</script>

<template>
  <div>
    <h2 class="text-2xl font-semibold mb-2">Review</h2>
    <p class="text-muted-foreground mb-8">Final check</p>

    <div class="space-y-6">
      <Card>
        <CardContent class="pt-6">
          <h3 class="text-lg font-semibold mb-4">Personal Information</h3>
          <div class="grid grid-cols-2 gap-4 text-sm">
            <div>
              <p class="text-muted-foreground">First Name</p>
              <p class="text-foreground">{{ personalInfo.firstName || '—' }}</p>
            </div>
            <div>
              <p class="text-muted-foreground">Last Name</p>
              <p class="text-foreground">{{ personalInfo.lastName || '—' }}</p>
            </div>
            <div>
              <p class="text-muted-foreground">Email</p>
              <p class="text-foreground">{{ personalInfo.email || '—' }}</p>
            </div>
            <div>
              <p class="text-muted-foreground">Phone</p>
              <p class="text-foreground">{{ personalInfo.phone || '—' }}</p>
            </div>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardContent class="pt-6">
          <h3 class="text-lg font-semibold mb-4">Goals</h3>
          <div class="grid grid-cols-2 gap-4 text-sm">
            <div>
              <p class="text-muted-foreground">Primary Goal</p>
              <p class="text-foreground">{{ goals.primaryGoal || '—' }}</p>
            </div>
            <div>
              <p class="text-muted-foreground">Target Weight</p>
              <p class="text-foreground">{{ goals.targetWeight || '—' }}</p>
            </div>
            <div>
              <p class="text-muted-foreground">Timeline</p>
              <p class="text-foreground">{{ goals.timeline || '—' }}</p>
            </div>
            <div class="col-span-2">
              <p class="text-muted-foreground">Motivation</p>
              <p class="text-foreground">{{ goals.motivation || '—' }}</p>
            </div>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardContent class="pt-6">
          <h3 class="text-lg font-semibold mb-4">Nutrition</h3>
          <div class="grid grid-cols-2 gap-4 text-sm">
            <div>
              <p class="text-muted-foreground">Diet Type</p>
              <p class="text-foreground">{{ nutrition.dietType || '—' }}</p>
            </div>
            <div>
              <p class="text-muted-foreground">Daily Calorie Target</p>
              <p class="text-foreground">{{ nutrition.dailyCalories || '—' }}</p>
            </div>
            <div>
              <p class="text-muted-foreground">Meals Per Day</p>
              <p class="text-foreground">{{ nutrition.mealsPerDay || '—' }}</p>
            </div>
            <div class="col-span-2">
              <p class="text-muted-foreground">Allergies & Restrictions</p>
              <p class="text-foreground">{{ nutrition.allergies || '—' }}</p>
            </div>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardContent class="pt-6">
          <h3 class="text-lg font-semibold mb-4">Training</h3>
          <div class="grid grid-cols-2 gap-4 text-sm">
            <div>
              <p class="text-muted-foreground">Experience Level</p>
              <p class="text-foreground">{{ training.experienceLevel || '—' }}</p>
            </div>
            <div>
              <p class="text-muted-foreground">Workout Frequency</p>
              <p class="text-foreground">{{ training.workoutFrequency || '—' }}</p>
            </div>
            <div class="col-span-2">
              <p class="text-muted-foreground">Preferred Activities</p>
              <p class="text-foreground">{{ training.preferredActivities || '—' }}</p>
            </div>
            <div class="col-span-2">
              <p class="text-muted-foreground">Past Injuries or Limitations</p>
              <p class="text-foreground">{{ training.injuries || '—' }}</p>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  </div>
</template>
