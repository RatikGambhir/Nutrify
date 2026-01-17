<script setup lang="ts">
import Input from '~/components/ui/input/Input.vue'
import Label from '~/components/ui/label/Label.vue'
import Textarea from '~/components/ui/textarea/Textarea.vue'

interface Training {
  experienceLevel: string
  workoutFrequency: string
  preferredActivities: string
  injuries: string
}

interface Props {
  formData: Training
}

const props = defineProps<Props>()
const emit = defineEmits<{
  'update:formData': [data: Partial<Training>]
}>()

const updateField = (field: keyof Training, value: string) => {
  emit('update:formData', { [field]: value })
}
</script>

<template>
  <div>
    <h2 class="text-2xl font-semibold mb-2">Training</h2>
    <p class="text-muted-foreground mb-8">Overview of your training</p>

    <div class="grid grid-cols-1 gap-6">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div class="space-y-2">
          <Label for="experienceLevel">Experience Level</Label>
          <Input
            id="experienceLevel"
            :model-value="formData.experienceLevel"
            @update:model-value="updateField('experienceLevel', $event)"
            placeholder="e.g., Beginner, Intermediate, Advanced"
          />
        </div>

        <div class="space-y-2">
          <Label for="workoutFrequency">Workout Frequency</Label>
          <Input
            id="workoutFrequency"
            :model-value="formData.workoutFrequency"
            @update:model-value="updateField('workoutFrequency', $event)"
            placeholder="e.g., 3-4 times per week"
          />
        </div>
      </div>

      <div class="space-y-2">
        <Label for="preferredActivities">Preferred Activities</Label>
        <Textarea
          id="preferredActivities"
          :model-value="formData.preferredActivities"
          @update:model-value="updateField('preferredActivities', $event)"
          placeholder="e.g., Running, Weightlifting, Yoga, Swimming, Cycling..."
          class="min-h-[100px]"
        />
      </div>

      <div class="space-y-2">
        <Label for="injuries">Past Injuries or Limitations</Label>
        <Textarea
          id="injuries"
          :model-value="formData.injuries"
          @update:model-value="updateField('injuries', $event)"
          placeholder="List any past injuries or physical limitations we should know about..."
          class="min-h-[100px]"
        />
      </div>
    </div>
  </div>
</template>
