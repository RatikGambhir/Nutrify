<script setup lang="ts">
import Input from '~/components/ui/input/Input.vue'
import Label from '~/components/ui/label/Label.vue'
import Textarea from '~/components/ui/textarea/Textarea.vue'

interface Goals {
  primaryGoal: string
  targetWeight: string
  timeline: string
  motivation: string
}

interface Props {
  formData: Goals
}

const props = defineProps<Props>()
const emit = defineEmits<{
  'update:formData': [data: Partial<Goals>]
}>()

const updateField = (field: keyof Goals, value: string) => {
  emit('update:formData', { [field]: value })
}
</script>

<template>
  <div>
    <h2 class="text-2xl font-semibold mb-2">Goals</h2>
    <p class="text-muted-foreground mb-8">Why you're here</p>

    <div class="grid grid-cols-1 gap-6">
      <div class="space-y-2">
        <Label for="primaryGoal">Primary Goal</Label>
        <Input
          id="primaryGoal"
          :model-value="formData.primaryGoal"
          @update:model-value="updateField('primaryGoal', $event)"
          placeholder="e.g., Lose weight, Build muscle, Improve endurance"
        />
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div class="space-y-2">
          <Label for="targetWeight">Target Weight</Label>
          <Input
            id="targetWeight"
            :model-value="formData.targetWeight"
            @update:model-value="updateField('targetWeight', $event)"
            placeholder="e.g., 150 lbs"
          />
        </div>

        <div class="space-y-2">
          <Label for="timeline">Timeline</Label>
          <Input
            id="timeline"
            :model-value="formData.timeline"
            @update:model-value="updateField('timeline', $event)"
            placeholder="e.g., 3 months, 6 months"
          />
        </div>
      </div>

      <div class="space-y-2">
        <Label for="motivation">What Motivates You?</Label>
        <Textarea
          id="motivation"
          :model-value="formData.motivation"
          @update:model-value="updateField('motivation', $event)"
          placeholder="Tell us about your motivation and why this is important to you..."
          class="min-h-[120px]"
        />
      </div>
    </div>
  </div>
</template>
