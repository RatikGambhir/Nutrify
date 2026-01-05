<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'
import type { ProfileSetupFormData, ActivityLevel, FitnessGoal } from '~/types'

definePageMeta({
  layout: false,
})

const router = useRouter()
const toast = useToast()
const { createProfile } = useUserProfile()

const currentStep = ref(1)
const totalSteps = 3
const loading = ref(false)

const schema = z.object({
  height: z.number().min(36, 'Height must be at least 36 inches').max(96, 'Height must be at most 96 inches'),
  weight: z.number().min(50, 'Weight must be at least 50 lbs').max(500, 'Weight must be at most 500 lbs'),
  gender: z.boolean(),
  activity_level: z.enum(['sedentary', 'lightly_active', 'moderately_active', 'very_active', 'extremely_active']),
  goal: z.enum(['get_fit', 'build_strength', 'improve_endurance', 'stay_healthy'])
})

type Schema = z.output<typeof schema>

const state = reactive<Schema>({
  height: 66,
  weight: 150,
  gender: true,
  activity_level: 'moderately_active',
  goal: 'get_fit'
})

const activityLevels = [
  { value: 'sedentary', label: 'Sedentary', description: 'Little to no exercise' },
  { value: 'lightly_active', label: 'Lightly Active', description: 'Exercise 1-3 days/week' },
  { value: 'moderately_active', label: 'Moderately Active', description: 'Exercise 3-5 days/week' },
  { value: 'very_active', label: 'Very Active', description: 'Exercise 6-7 days/week' },
  { value: 'extremely_active', label: 'Extremely Active', description: 'Physical job or training twice/day' }
]

const goals = [
  { value: 'get_fit', label: 'Get Fit', icon: 'i-lucide-heart-pulse', description: 'Improve overall fitness and health' },
  { value: 'build_strength', label: 'Build Strength', icon: 'i-lucide-dumbbell', description: 'Gain muscle and increase strength' },
  { value: 'improve_endurance', label: 'Improve Endurance', icon: 'i-lucide-zap', description: 'Boost stamina and energy levels' },
  { value: 'stay_healthy', label: 'Stay Healthy', icon: 'i-lucide-shield-check', description: 'Maintain current fitness level' }
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
    const profileData: ProfileSetupFormData = {
      height: event.data.height,
      weight: event.data.weight,
      gender: event.data.gender,
      activity_level: event.data.activity_level,
      goal: event.data.goal
    }

    const profile = await createProfile(profileData)

    if (profile) {
      toast.add({
        title: 'Profile Created!',
        description: 'Your fitness profile has been set up successfully.',
        color: 'primary'
      })

      await router.push({ path: '/' })
    } else {
      toast.add({
        title: 'Error',
        description: 'Failed to create profile. Please try again.',
        color: 'error'
      })
    }
  } catch (error) {
    console.error('Error submitting profile:', error)
    toast.add({
      title: 'Error',
      description: 'An unexpected error occurred.',
      color: 'error'
    })
  } finally {
    loading.value = false
  }
}

const canProceed = computed(() => {
  if (currentStep.value === 1) {
    return state.height >= 36 && state.height <= 96 && state.weight >= 50 && state.weight <= 500
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

            <UFormField name="height" label="Height (inches)">
              <UInput
                v-model.number="state.height"
                type="number"
                size="lg"
                placeholder="66"
                icon="i-lucide-ruler"
              />
            </UFormField>

            <UFormField name="weight" label="Weight (lbs)">
              <UInput
                v-model.number="state.weight"
                type="number"
                size="lg"
                placeholder="150"
                icon="i-lucide-weight"
              />
            </UFormField>

            <UFormField name="gender" label="Gender">
              <div class="flex gap-4">
                <UButton
                  :variant="state.gender === true ? 'solid' : 'outline'"
                  :color="state.gender === true ? 'primary' : 'secondary'"
                  size="lg"
                  block
                  @click="state.gender = true"
                >
                  Male
                </UButton>
                <UButton
                  :variant="state.gender === false ? 'solid' : 'outline'"
                  :color="state.gender === false ? 'primary' : 'secondary'"
                  size="lg"
                  block
                  @click="state.gender = false"
                >
                  Female
                </UButton>
              </div>
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
                  @click="state.goal = goal.value as FitnessGoal"
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
