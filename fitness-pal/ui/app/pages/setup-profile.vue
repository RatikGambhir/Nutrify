<script setup lang="ts">
import * as z from 'zod'
import { ActivityLevel, PrimaryGoal, type UserProfile } from '~/types'
import { userApi } from '~/api/user'
import Card from '~/components/ui/card/Card.vue'
import CardContent from '~/components/ui/card/CardContent.vue'
import Button from '~/components/ui/button/Button.vue'
import Label from '~/components/ui/label/Label.vue'
import RadioGroup from '~/components/ui/radio-group/RadioGroup.vue'
import RadioGroupItem from '~/components/ui/radio-group/RadioGroupItem.vue'
import Select from '~/components/ui/select/Select.vue'
import SelectContent from '~/components/ui/select/SelectContent.vue'
import SelectItem from '~/components/ui/select/SelectItem.vue'
import SelectTrigger from '~/components/ui/select/SelectTrigger.vue'
import SelectValue from '~/components/ui/select/SelectValue.vue'
import {
  Flame,
  Dumbbell,
  Repeat,
  Zap,
  HeartPulse,
  ShieldCheck,
  Loader2
} from 'lucide-vue-next'

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

const goalIconMap: Record<string, any> = {
  [PrimaryGoal.FAT_LOSS]: Flame,
  [PrimaryGoal.MUSCLE_GAIN]: Dumbbell,
  [PrimaryGoal.RECOMPOSITION]: Repeat,
  [PrimaryGoal.STRENGTH]: Zap,
  [PrimaryGoal.ENDURANCE]: HeartPulse,
  [PrimaryGoal.GENERAL_HEALTH]: ShieldCheck
}

const goals = [
  { value: PrimaryGoal.FAT_LOSS, label: 'Fat Loss', description: 'Reduce body fat percentage' },
  { value: PrimaryGoal.MUSCLE_GAIN, label: 'Muscle Gain', description: 'Build muscle mass and size' },
  { value: PrimaryGoal.RECOMPOSITION, label: 'Recomposition', description: 'Lose fat while gaining muscle' },
  { value: PrimaryGoal.STRENGTH, label: 'Strength', description: 'Increase overall strength' },
  { value: PrimaryGoal.ENDURANCE, label: 'Endurance', description: 'Improve stamina and endurance' },
  { value: PrimaryGoal.GENERAL_HEALTH, label: 'General Health', description: 'Maintain overall wellness' }
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

const onSubmit = async () => {
  loading.value = true

  try {
    const profileData: Omit<UserProfile, 'userId'> = {
      age: state.age,
      sex: state.sex,
      gender: state.gender,
      height: state.height,
      weight: state.weight,
      ethnicity: state.ethnicity,
      activityLevel: state.activity_level,
      primaryGoal: state.goal
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
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-muted via-background to-muted p-8">
    <div class="w-full max-w-2xl">
      <div class="text-center mb-8">
        <NuxtLink to="/" class="inline-flex items-center gap-2 mb-8">
          <div class="w-8 h-8 bg-primary rounded" />
          <span class="text-xl font-bold text-foreground">Nutrify</span>
        </NuxtLink>

        <h1 class="text-4xl font-bold text-foreground mb-2">
          Complete Your Profile
        </h1>
        <p class="text-muted-foreground">
          Help us personalize your fitness experience
        </p>
      </div>

      <div class="mb-8">
        <div class="flex justify-between items-center mb-2">
          <span class="text-sm font-medium text-muted-foreground">Step {{ currentStep }} of {{ totalSteps }}</span>
          <span class="text-sm text-muted-foreground">{{ Math.round((currentStep / totalSteps) * 100) }}% complete</span>
        </div>
        <div class="h-2 bg-muted rounded-full overflow-hidden">
          <div
            class="h-full bg-primary transition-all duration-300 ease-out"
            :style="{ width: `${(currentStep / totalSteps) * 100}%` }"
          />
        </div>
      </div>

      <Card class="shadow-xl">
        <CardContent class="p-6">
          <form @submit.prevent="onSubmit">
            <div v-if="currentStep === 1" class="space-y-6">
              <div class="text-center mb-6">
                <h2 class="text-2xl font-bold text-foreground mb-2">Basic Information</h2>
                <p class="text-muted-foreground">Tell us about yourself</p>
              </div>

              <div class="space-y-2">
                <Label for="age">Age</Label>
                <input
                  id="age"
                  v-model.number="state.age"
                  type="number"
                  min="13"
                  max="120"
                  class="w-24 px-3 py-2 text-center border border-border rounded-md shadow-sm focus:ring-2 focus:ring-primary focus:border-primary"
                >
              </div>

              <div class="grid grid-cols-2 gap-4">
                <div class="space-y-2">
                  <Label for="height">Height (inches)</Label>
                  <input
                    id="height"
                    v-model.number="state.height"
                    type="number"
                    min="36"
                    max="96"
                    class="w-24 px-3 py-2 text-center border border-border rounded-md shadow-sm focus:ring-2 focus:ring-primary focus:border-primary"
                  >
                </div>

                <div class="space-y-2">
                  <Label for="weight">Weight (lbs)</Label>
                  <input
                    id="weight"
                    v-model.number="state.weight"
                    type="number"
                    min="50"
                    max="500"
                    class="w-24 px-3 py-2 text-center border border-border rounded-md shadow-sm focus:ring-2 focus:ring-primary focus:border-primary"
                  >
                </div>
              </div>

              <div class="space-y-2">
                <Label>Sex</Label>
                <div class="flex gap-3">
                  <Button
                    v-for="option in sexOptions"
                    :key="option.value"
                    type="button"
                    :variant="state.sex === option.value ? 'default' : 'outline'"
                    class="flex-1"
                    @click="state.sex = option.value"
                  >
                    {{ option.label }}
                  </Button>
                </div>
              </div>

              <div class="space-y-2">
                <Label>Gender Identity</Label>
                <div class="flex gap-3">
                  <Button
                    v-for="option in sexOptions"
                    :key="option.value"
                    type="button"
                    :variant="state.gender === option.value ? 'default' : 'outline'"
                    class="flex-1"
                    @click="state.gender = option.value"
                  >
                    {{ option.label }}
                  </Button>
                </div>
              </div>

              <div class="space-y-2">
                <Label>Ethnicity</Label>
                <Select v-model="state.ethnicity">
                  <SelectTrigger class="h-11">
                    <SelectValue placeholder="Select your ethnicity" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem v-for="option in ethnicityOptions" :key="option.value" :value="option.value">
                      {{ option.label }}
                    </SelectItem>
                  </SelectContent>
                </Select>
              </div>

              <div class="flex gap-3 pt-4">
                <Button
                  type="button"
                  size="lg"
                  variant="outline"
                  class="flex-1"
                  @click="skipSetup"
                >
                  Skip for Now
                </Button>
                <Button
                  type="button"
                  size="lg"
                  class="flex-1"
                  :disabled="!canProceed"
                  @click="nextStep"
                >
                  Next
                </Button>
              </div>
            </div>

            <!-- Step 2: Activity Level -->
            <div v-if="currentStep === 2" class="space-y-6">
              <div class="text-center mb-6">
                <h2 class="text-2xl font-bold text-foreground mb-2">Activity Level</h2>
                <p class="text-muted-foreground">How active are you?</p>
              </div>

              <RadioGroup v-model="state.activity_level" class="space-y-3">
                <div v-for="level in activityLevels" :key="level.value" class="flex items-start space-x-3">
                  <RadioGroupItem :id="level.value" :value="level.value" class="mt-1" />
                  <Label :for="level.value" class="cursor-pointer flex-1">
                    <p class="font-medium text-foreground">{{ level.label }}</p>
                    <p class="text-sm text-muted-foreground">{{ level.description }}</p>
                  </Label>
                </div>
              </RadioGroup>

              <div class="flex gap-3 pt-4">
                <Button
                  type="button"
                  size="lg"
                  variant="outline"
                  @click="prevStep"
                >
                  Back
                </Button>
                <Button
                  type="button"
                  size="lg"
                  class="flex-1"
                  @click="nextStep"
                >
                  Next
                </Button>
              </div>
            </div>

            <div v-if="currentStep === 3" class="space-y-6">
              <div class="text-center mb-6">
                <h2 class="text-2xl font-bold text-foreground mb-2">Your Goal</h2>
                <p class="text-muted-foreground">What do you want to achieve?</p>
              </div>

              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <button
                  v-for="goal in goals"
                  :key="goal.value"
                  type="button"
                  class="p-6 rounded-lg border-2 transition-all text-left"
                  :class="state.goal === goal.value
                    ? 'border-primary bg-primary/5'
                    : 'border-border hover:border-primary/50'"
                  @click="state.goal = goal.value"
                >
                  <component :is="goalIconMap[goal.value]" class="w-8 h-8 mb-3" />
                  <h3 class="font-bold text-foreground mb-1">{{ goal.label }}</h3>
                  <p class="text-sm text-muted-foreground">{{ goal.description }}</p>
                </button>
              </div>

              <div class="flex gap-3 pt-4">
                <Button
                  type="button"
                  size="lg"
                  variant="outline"
                  @click="prevStep"
                >
                  Back
                </Button>
                <Button
                  type="submit"
                  size="lg"
                  class="flex-1"
                  :disabled="loading"
                >
                  <Loader2 v-if="loading" class="mr-2 h-4 w-4 animate-spin" />
                  Complete Setup
                </Button>
              </div>
            </div>
          </form>
        </CardContent>
      </Card>

      <div class="text-center mt-6">
        <p class="text-sm text-muted-foreground">
          You can always update your profile in settings later
        </p>
      </div>
    </div>
  </div>
</template>
