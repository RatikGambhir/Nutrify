<script setup lang="ts">
import Input from '~/components/ui/input/Input.vue'
import Label from '~/components/ui/label/Label.vue'
import Textarea from '~/components/ui/textarea/Textarea.vue'

interface Nutrition {
  dietType: string
  allergies: string
  dailyCalories: string
  mealsPerDay: string
}

interface Props {
  formData: Nutrition
}

const props = defineProps<Props>()
const emit = defineEmits<{
  'update:formData': [data: Partial<Nutrition>]
}>()

const updateField = (field: keyof Nutrition, value: string) => {
  emit('update:formData', { [field]: value })
}
</script>

<template>
  <div>
    <h2 class="text-2xl font-semibold mb-2">Nutrition</h2>
    <p class="text-muted-foreground mb-8">Historical records</p>

    <div class="grid grid-cols-1 gap-6">
      <div class="space-y-2">
        <Label for="dietType">Diet Type</Label>
        <Input
          id="dietType"
          :model-value="formData.dietType"
          @update:model-value="updateField('dietType', $event)"
          placeholder="e.g., Vegetarian, Vegan, Keto, Paleo"
        />
      </div>

      <div class="space-y-2">
        <Label for="allergies">Allergies & Restrictions</Label>
        <Textarea
          id="allergies"
          :model-value="formData.allergies"
          @update:model-value="updateField('allergies', $event)"
          placeholder="List any food allergies or dietary restrictions..."
          class="min-h-[100px]"
        />
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div class="space-y-2">
          <Label for="dailyCalories">Daily Calorie Target</Label>
          <Input
            id="dailyCalories"
            :model-value="formData.dailyCalories"
            @update:model-value="updateField('dailyCalories', $event)"
            placeholder="e.g., 2000 calories"
          />
        </div>

        <div class="space-y-2">
          <Label for="mealsPerDay">Meals Per Day</Label>
          <Input
            id="mealsPerDay"
            :model-value="formData.mealsPerDay"
            @update:model-value="updateField('mealsPerDay', $event)"
            placeholder="e.g., 3-4 meals"
          />
        </div>
      </div>
    </div>
  </div>
</template>
