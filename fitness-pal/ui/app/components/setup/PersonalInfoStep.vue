<script setup lang="ts">
import Input from '~/components/ui/input/Input.vue'
import Label from '~/components/ui/label/Label.vue'

interface PersonalInfo {
  firstName: string
  lastName: string
  email: string
  phone: string
}

interface Props {
  formData: PersonalInfo
}

const props = defineProps<Props>()
const emit = defineEmits<{
  'update:formData': [data: Partial<PersonalInfo>]
}>()

const updateField = (field: keyof PersonalInfo, value: string) => {
  emit('update:formData', { [field]: value })
}
</script>

<template>
  <div>
    <h2 class="text-2xl font-semibold mb-2">Personal Info</h2>
    <p class="text-muted-foreground mb-8">Basic details</p>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <div class="space-y-2">
        <Label for="firstName">
          First Name <span class="text-destructive">*</span>
        </Label>
        <Input
          id="firstName"
          :model-value="formData.firstName"
          @update:model-value="updateField('firstName', $event)"
          placeholder="John"
        />
      </div>

      <div class="space-y-2">
        <Label for="lastName">
          Last Name <span class="text-destructive">*</span>
        </Label>
        <Input
          id="lastName"
          :model-value="formData.lastName"
          @update:model-value="updateField('lastName', $event)"
          placeholder="Doe"
        />
      </div>

      <div class="space-y-2">
        <Label for="email">
          Email <span class="text-destructive">*</span>
        </Label>
        <Input
          id="email"
          type="email"
          :model-value="formData.email"
          @update:model-value="updateField('email', $event)"
          placeholder="john@example.com"
        />
      </div>

      <div class="space-y-2">
        <Label for="phone">
          Phone <span class="text-destructive">*</span>
        </Label>
        <Input
          id="phone"
          type="tel"
          :model-value="formData.phone"
          @update:model-value="updateField('phone', $event)"
          placeholder="+1 (555) 000-0000"
        />
      </div>
    </div>
  </div>
</template>
