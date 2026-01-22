<script setup lang="ts">
import { ref } from 'vue'
import { User, Target, Apple, Dumbbell, FileCheck, ChevronLeft, ChevronRight } from 'lucide-vue-next'
import Button from '~/components/ui/button/Button.vue'
import Badge from '~/components/ui/badge/Badge.vue'
import PersonalInfoStep from '~/components/setup/PersonalInfoStep.vue'
import GoalsStep from '~/components/setup/GoalsStep.vue'
import NutritionStep from '~/components/setup/NutritionStep.vue'
import TrainingStep from '~/components/setup/TrainingStep.vue'
import ReviewStep from '~/components/setup/ReviewStep.vue'
import { cn } from '~/lib/utils'

definePageMeta({
  layout: false,
})

const steps = [
  {
    id: 1,
    title: 'Personal Info',
    description: 'Basic details',
    icon: User,
  },
  {
    id: 2,
    title: 'Goals',
    description: "Why you're here",
    icon: Target,
  },
  {
    id: 3,
    title: 'Nutrition',
    description: 'Historical records',
    icon: Apple,
  },
  {
    id: 4,
    title: 'Training',
    description: 'Overview of your training',
    icon: Dumbbell,
  },
  {
    id: 5,
    title: 'Review',
    description: 'Final check',
    icon: FileCheck,
  },
]

const currentStep = ref(1)
const personalInfo = ref({
  firstName: 'John',
  lastName: 'Doe',
  email: 'john@example.com',
  phone: '+1 (555) 000-0000',
})
const goals = ref({
  primaryGoal: '',
  targetWeight: '',
  timeline: '',
  motivation: '',
})
const nutrition = ref({
  dietType: '',
  allergies: '',
  dailyCalories: '',
  mealsPerDay: '',
})
const training = ref({
  experienceLevel: '',
  workoutFrequency: '',
  preferredActivities: '',
  injuries: '',
})

const handleNext = () => {
  if (currentStep.value < steps.length) {
    currentStep.value++
  }
}

const handleBack = () => {
  if (currentStep.value > 1) {
    currentStep.value--
  }
}

const handleSubmit = () => {
  console.log('Form submitted:', {
    personalInfo: personalInfo.value,
    goals: goals.value,
    nutrition: nutrition.value,
    training: training.value
  })
  alert('Account setup completed!')
}
</script>

<template>
  <div class="min-h-screen border bg-background py-12 px-4">
    <div class="max-w-7xl mx-auto ">
      <!-- Header -->
      <div class="text-center mb-12">
        <Badge variant="outline" class="mb-4 px-4 py-1 bg-primary">
          Nutrify
        </Badge>
        <h1 class="text-4xl font-bold mb-3">Account Setup</h1>
        <p class="text-muted-foreground">
          Complete the steps below to verify your profile
        </p>
      </div>

      <!-- Main Content -->
      <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
        <!-- Sidebar Navigation -->
        <div class="lg:col-span-1">
          <div class="bg-card rounded-lg border shadow-sm p-6 space-y-4">
            <button
              v-for="step in steps"
              :key="step.id"
              @click="currentStep = step.id"
              :class="cn(
                'w-full flex items-start gap-3 p-3 rounded-lg text-left transition-colors',
                currentStep === step.id && 'bg-accent',
                currentStep !== step.id && 'hover:bg-accent/50'
              )"
            >
              <div
                :class="cn(
                  'flex items-center justify-center w-10 h-10 rounded-full flex-shrink-0',
                  currentStep === step.id && 'bg-primary text-primary-foreground',
                  currentStep !== step.id && 'bg-muted text-muted-foreground'
                )"
              >
                <component :is="step.icon" class="w-5 h-5" />
              </div>
              <div class="flex-1 min-w-0">
                <p
                  :class="cn(
                    'font-medium',
                    currentStep === step.id && 'text-foreground',
                    currentStep !== step.id && 'text-muted-foreground'
                  )"
                >
                  {{ step.title }}
                </p>
                <p class="text-sm text-muted-foreground">
                  {{ step.description }}
                </p>
              </div>
            </button>
          </div>
        </div>

        <!-- Form Content -->
        <div class="lg:col-span-3">
          <div class="bg-card rounded-lg border shadow-sm p-8 min-h-[500px]">
            <PersonalInfoStep
              v-if="currentStep === 1"
              :form-data="personalInfo"
              @update:form-data="(data) => personalInfo = { ...personalInfo, ...data }"
            />
            <GoalsStep
              v-if="currentStep === 2"
              :form-data="goals"
              @update:form-data="(data) => goals = { ...goals, ...data }"
            />
            <NutritionStep
              v-if="currentStep === 3"
              :form-data="nutrition"
              @update:form-data="(data) => nutrition = { ...nutrition, ...data }"
            />
            <TrainingStep
              v-if="currentStep === 4"
              :form-data="training"
              @update:form-data="(data) => training = { ...training, ...data }"
            />
            <ReviewStep
              v-if="currentStep === 5"
              :personal-info="personalInfo"
              :goals="goals"
              :nutrition="nutrition"
              :training="training"
            />

            <!-- Navigation Buttons -->
            <div class="flex items-center justify-between mt-12 pt-8 border-t border-border">
              <Button
                variant="ghost"
                @click="handleBack"
                :disabled="currentStep === 1"
                class="gap-2"
              >
                <ChevronLeft class="w-4 h-4" />
                Back
              </Button>

              <Button
                v-if="currentStep < steps.length"
                @click="handleNext"
                class="gap-2"
              >
                Next Step
                <ChevronRight class="w-4 h-4" />
              </Button>
              <Button
                v-else
                @click="handleSubmit"
                class="gap-2"
              >
                Complete Setup
                <ChevronRight class="w-4 h-4" />
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
