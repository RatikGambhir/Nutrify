<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'
import { ActivityLevel, PrimaryGoal, type UserProfile } from '~/types'
import { userApi } from '~/api/user'

definePageMeta({
  layout: false,
})

const router = useRouter()
const toast = useToast()

const currentStep = ref(1)
const totalSteps = 3
const loading = ref(false)

const schema = z.object({
  age: z.number().min(13, 'You must be at least 13 years old').max(120, 'Age must be less than 120'),
  height: z.number().min(36, 'Height must be at least 36 inches').max(96, 'Height must be at most 96 inches'),
  weight: z.number().min(50, 'Weight must be at least 50 lbs').max(500, 'Weight must be at most 500 lbs'),
  sex: z.string().min(1, 'Sex is required'),
  gender: z.string().min(1, 'Gender is required'),
  ethnicity: z.string().min(1, 'Ethnicity is required'),
  activity_level: z.nativeEnum(ActivityLevel),
  goal: z.nativeEnum(PrimaryGoal)
})

type Schema = z.output<typeof schema>

const state = reactive<Schema>({
  age: 25,
  height: 66,
  weight: 150,
  sex: 'male',
  gender: 'male',
  ethnicity: 'prefer_not_say',
  activity_level: ActivityLevel.MODERATELY_ACTIVE,
  goal: PrimaryGoal.GENERAL_HEALTH
})

const sexOptions = [
  { value: 'male', label: 'Male' },
  { value: 'female', label: 'Female' },
  { value: 'other', label: 'Other' }
]

const ethnicityOptions = [
  { value: 'asian', label: 'Asian' },
  { value: 'black', label: 'Black or African American' },
  { value: 'hispanic', label: 'Hispanic or Latino' },
  { value: 'white', label: 'White or Caucasian' },
  { value: 'native_american', label: 'Native American' },
  { value: 'pacific_islander', label: 'Pacific Islander' },
  { value: 'mixed', label: 'Mixed/Multiple' },
  { value: 'other', label: 'Other' },
  { value: 'prefer_not_say', label: 'Prefer not to say' }
]

const activityLevels = [
  { value: ActivityLevel.SEDENTARY, label: 'Sedentary', description: 'Little to no exercise' },
  { value: ActivityLevel.LIGHTLY_ACTIVE, label: 'Lightly Active', description: 'Exercise 1-3 days/week' },
  { value: ActivityLevel.MODERATELY_ACTIVE, label: 'Moderately Active', description: 'Exercise 3-5 days/week' },
  { value: ActivityLevel.VERY_ACTIVE, label: 'Very Active', description: 'Exercise 6-7 days/week' },
  { value: ActivityLevel.EXTREMELY_ACTIVE, label: 'Extremely Active', description: 'Physical job or training twice/day' }
]

const goals = [
  { value: PrimaryGoal.FAT_LOSS, label: 'Fat Loss', icon: 'i-lucide-flame', description: 'Reduce body fat percentage' },
  { value: PrimaryGoal.MUSCLE_GAIN, label: 'Muscle Gain', icon: 'i-lucide-dumbbell', description: 'Build muscle mass and size' },
  { value: PrimaryGoal.RECOMPOSITION, label: 'Recomposition', icon: 'i-lucide-repeat', description: 'Lose fat while gaining muscle' },
  { value: PrimaryGoal.STRENGTH, label: 'Strength', icon: 'i-lucide-zap', description: 'Increase overall strength' },
  { value: PrimaryGoal.ENDURANCE, label: 'Endurance', icon: 'i-lucide-heart-pulse', description: 'Improve stamina and endurance' },
  { value: PrimaryGoal.GENERAL_HEALTH, label: 'General Health', icon: 'i-lucide-shield-check', description: 'Maintain overall wellness' }
]

const nextStep = () => {
  if (currentStep.value < totalSteps) {
    currentStep.value++
  }
}

const prevStep = () => {
  if (currentStep.value > 1) {
    currentStep.value--
  }
}

const skipSetup = async () => {
  await router.push({ path: '/' })
}

const onSubmit = async (event: FormSubmitEvent<Schema>) => {
  loading.value = true

  try {
    const profileData: Omit<UserProfile, 'userId'> = {
      age: event.data.age,
      sex: event.data.sex,
      gender: event.data.gender,
      height: event.data.height,
      weight: event.data.weight,
      ethnicity: event.data.ethnicity,
      activityLevel: event.data.activity_level,
      primaryGoal: event.data.goal
    }

    const response = await userApi.createUserProfile(profileData)

    if (response) {
      toast.add({
        title: 'Profile Created!',
        description: 'Your fitness profile has been set up successfully.',
        color: 'primary'
      })

      await router.push({ path: '/' })
    }
  } catch (error) {
    console.error('Error submitting profile:', error)
    toast.add({
      title: 'Error',
      description: 'An unexpected error occurred. Please try again.',
      color: 'error'
    })
  } finally {
    loading.value = false
  }
}

const canProceed = computed(() => {
  if (currentStep.value === 1) {
    return state.age >= 13 && state.age <= 120 &&
           state.height >= 36 && state.height <= 96 &&
           state.weight >= 50 && state.weight <= 500 &&
           state.sex.length > 0 &&
           state.gender.length > 0 &&
           state.ethnicity.length > 0
  }
  return true
})
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-50 via-white to-gray-100 p-8">
    <div class="w-full max-w-2xl">
      <div class="text-center mb-8">
        <NuxtLink to="/" class="inline-flex items-center gap-2 mb-8">
          <div class="w-8 h-8 bg-gray-900 rounded" />
          <span class="text-xl font-bold text-gray-900">Nutrify</span>
        </NuxtLink>

        <h1 class="text-4xl font-bold text-gray-900 mb-2">
          Complete Your Profile
        </h1>
        <p class="text-gray-600">
          Help us personalize your fitness experience
        </p>
      </div>

      <div class="mb-8">
        <div class="flex justify-between items-center mb-2">
          <span class="text-sm font-medium text-gray-600">Step {{ currentStep }} of {{ totalSteps }}</span>
          <span class="text-sm text-gray-500">{{ Math.round((currentStep / totalSteps) * 100) }}% complete</span>
        </div>
        <div class="h-2 bg-gray-200 rounded-full overflow-hidden">
          <div
            class="h-full bg-gray-900 transition-all duration-300 ease-out"
            :style="{ width: `${(currentStep / totalSteps) * 100}%` }"
          />
        </div>
      </div>

      <UCard class="shadow-xl">
        <UForm
          :schema="schema"
          :state="state"
          @submit="onSubmit"
        >
          <div v-if="currentStep === 1" class="space-y-6">
            <div class="text-center mb-6">
              <h2 class="text-2xl font-bold text-gray-900 mb-2">Basic Information</h2>
              <p class="text-gray-600">Tell us about yourself</p>
            </div>

            <UFormField name="age" label="Age">
              <input
                v-model.number="state.age"
                type="number"
                min="13"
                max="120"
                class="w-24 px-3 py-2 text-center border border-gray-300 rounded-md shadow-sm focus:ring-2 focus:ring-gray-900 focus:border-gray-900"
              >
            </UFormField>

            <div class="grid grid-cols-2 gap-4">
              <UFormField name="height" label="Height (inches)">
                <input
                  v-model.number="state.height"
                  type="number"
                  min="36"
                  max="96"
                  class="w-24 px-3 py-2 text-center border border-gray-300 rounded-md shadow-sm focus:ring-2 focus:ring-gray-900 focus:border-gray-900"
                >
              </UFormField>

              <UFormField name="weight" label="Weight (lbs)">
                <input
                  v-model.number="state.weight"
                  type="number"
                  min="50"
                  max="500"
                  class="w-24 px-3 py-2 text-center border border-gray-300 rounded-md shadow-sm focus:ring-2 focus:ring-gray-900 focus:border-gray-900"
                >
              </UFormField>
            </div>

            <UFormField name="sex" label="Sex">
              <div class="flex gap-3">
                <UButton
                  v-for="option in sexOptions"
                  :key="option.value"
                  :variant="state.sex === option.value ? 'solid' : 'outline'"
                  :color="state.sex === option.value ? 'primary' : 'secondary'"
                  size="md"
                  class="flex-1"
                  @click="state.sex = option.value"
                >
                  {{ option.label }}
                </UButton>
              </div>
            </UFormField>

            <UFormField name="gender" label="Gender Identity">
              <div class="flex gap-3">
                <UButton
                  v-for="option in sexOptions"
                  :key="option.value"
                  :variant="state.gender === option.value ? 'solid' : 'outline'"
                  :color="state.gender === option.value ? 'primary' : 'secondary'"
                  size="md"
                  class="flex-1"
                  @click="state.gender = option.value"
                >
                  {{ option.label }}
                </UButton>
              </div>
            </UFormField>

            <UFormField name="ethnicity" label="Ethnicity">
              <USelect
                v-model="state.ethnicity"
                :options="ethnicityOptions"
                placeholder="Select your ethnicity"
                size="lg"
              />
            </UFormField>

            <div class="flex gap-3 pt-4">
              <UButton
                size="lg"
                variant="outline"
                color="secondary"
                block
                @click="skipSetup"
              >
                Skip for Now
              </UButton>
              <UButton
                size="lg"
                color="primary"
                block
                :disabled="!canProceed"
                class="bg-gray-900 hover:bg-gray-800"
                @click="nextStep"
              >
                Next
              </UButton>
            </div>
          </div>

          <!-- Step 2: Activity Level -->
          <div v-if="currentStep === 2" class="space-y-6">
            <div class="text-center mb-6">
              <h2 class="text-2xl font-bold text-gray-900 mb-2">Activity Level</h2>
              <p class="text-gray-600">How active are you?</p>
            </div>

            <UFormField name="activity_level">
              <URadioGroup
                v-model="state.activity_level"
                :options="activityLevels"
                value-attribute="value"
              >
                <template #label="{ option }">
                  <div>
                    <p class="font-medium text-gray-900">{{ option.label }}</p>
                    <p class="text-sm text-gray-500">{{ option.description }}</p>
                  </div>
                </template>
              </URadioGroup>
            </UFormField>

            <div class="flex gap-3 pt-4">
              <UButton
                size="lg"
                variant="outline"
                color="secondary"
                @click="prevStep"
              >
                Back
              </UButton>
              <UButton
                size="lg"
                color="primary"
                block
                class="bg-gray-900 hover:bg-gray-800"
                @click="nextStep"
              >
                Next
              </UButton>
            </div>
          </div>

          <div v-if="currentStep === 3" class="space-y-6">
            <div class="text-center mb-6">
              <h2 class="text-2xl font-bold text-gray-900 mb-2">Your Goal</h2>
              <p class="text-gray-600">What do you want to achieve?</p>
            </div>

            <UFormField name="goal">
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <button
                  v-for="goal in goals"
                  :key="goal.value"
                  type="button"
                  class="p-6 rounded-lg border-2 transition-all text-left"
                  :class="state.goal === goal.value
                    ? 'border-gray-900 bg-gray-50'
                    : 'border-gray-200 hover:border-gray-300'"
                  @click="state.goal = goal.value"
                >
                  <UIcon :name="goal.icon" class="w-8 h-8 mb-3" />
                  <h3 class="font-bold text-gray-900 mb-1">{{ goal.label }}</h3>
                  <p class="text-sm text-gray-600">{{ goal.description }}</p>
                </button>
              </div>
            </UFormField>

            <div class="flex gap-3 pt-4">
              <UButton
                size="lg"
                variant="outline"
                color="secondary"
                @click="prevStep"
              >
                Back
              </UButton>
              <UButton
                type="submit"
                size="lg"
                color="primary"
                block
                :loading="loading"
                class="bg-gray-900 hover:bg-gray-800"
              >
                Complete Setup
              </UButton>
            </div>
          </div>
        </UForm>
      </UCard>

      <div class="text-center mt-6">
        <p class="text-sm text-gray-500">
          You can always update your profile in settings later
        </p>
      </div>
    </div>
  </div>
</template>
