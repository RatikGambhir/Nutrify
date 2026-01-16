<script setup lang="ts">
import Button from '~/components/ui/button/Button.vue'
import Card from '~/components/ui/card/Card.vue'
import CardContent from '~/components/ui/card/CardContent.vue'
import Input from '~/components/ui/input/Input.vue'
import Textarea from '~/components/ui/textarea/Textarea.vue'
import Select from '~/components/ui/select/Select.vue'
import SelectContent from '~/components/ui/select/SelectContent.vue'
import SelectItem from '~/components/ui/select/SelectItem.vue'
import SelectTrigger from '~/components/ui/select/SelectTrigger.vue'
import SelectValue from '~/components/ui/select/SelectValue.vue'
import Separator from '~/components/ui/separator/Separator.vue'
import Label from '~/components/ui/label/Label.vue'
import type { PlaceholderFor, SettingsFormDefaults, SettingsFormState } from '~/components/settings/types'

defineProps<{
    formState: SettingsFormState;
    formDefaults: SettingsFormDefaults;
    placeholderFor: PlaceholderFor;
}>();
</script>

<template>
    <div class="space-y-6">
        <div class="space-y-1">
            <h2 class="text-lg font-semibold">Profile</h2>
            <p class="text-sm text-muted-foreground">
                Configure your personal and body metrics information.
            </p>
        </div>

        <Card>
            <CardContent class="p-6 space-y-5">
                <div class="grid gap-4 md:grid-cols-[220px,1fr]">
                    <div>
                        <Label>Age</Label>
                        <p class="text-xs text-muted-foreground">Your current age.</p>
                    </div>
                    <Input
                        v-model="formState.profile.age"
                        :placeholder="placeholderFor(formDefaults.profile.age, 'e.g., 28')"
                    />
                </div>

                <Separator />

                <div class="grid gap-4 md:grid-cols-[220px,1fr]">
                    <div>
                        <Label>Sex</Label>
                        <p class="text-xs text-muted-foreground">Biological sex assigned at birth.</p>
                    </div>
                    <Select v-model="formState.profile.sex">
                        <SelectTrigger>
                            <SelectValue :placeholder="placeholderFor(formDefaults.profile.sex, 'Select sex')" />
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="female">Female</SelectItem>
                            <SelectItem value="male">Male</SelectItem>
                            <SelectItem value="intersex">Intersex</SelectItem>
                        </SelectContent>
                    </Select>
                </div>

                <Separator />

                <div class="grid gap-4 md:grid-cols-[220px,1fr]">
                    <div>
                        <Label>Gender</Label>
                        <p class="text-xs text-muted-foreground">Your gender identity.</p>
                    </div>
                    <Input
                        v-model="formState.profile.gender"
                        :placeholder="placeholderFor(formDefaults.profile.gender, 'e.g., Woman, Man, Non-binary, etc.')"
                    />
                </div>

                <Separator />

                <div class="grid gap-4 md:grid-cols-[220px,1fr]">
                    <div>
                        <Label>Height</Label>
                        <p class="text-xs text-muted-foreground">Your height in centimeters.</p>
                    </div>
                    <Input
                        v-model="formState.profile.height"
                        :placeholder="placeholderFor(formDefaults.profile.height, 'e.g., 175')"
                    />
                </div>

                <Separator />

                <div class="grid gap-4 md:grid-cols-[220px,1fr]">
                    <div>
                        <Label>Weight</Label>
                        <p class="text-xs text-muted-foreground">Your weight in kilograms.</p>
                    </div>
                    <Input
                        v-model="formState.profile.weight"
                        :placeholder="placeholderFor(formDefaults.profile.weight, 'e.g., 70')"
                    />
                </div>

                <Separator />

                <div class="grid gap-4 md:grid-cols-[220px,1fr]">
                    <div>
                        <Label>Body Fat Percentage</Label>
                        <p class="text-xs text-muted-foreground">Your body fat percentage (optional).</p>
                    </div>
                    <Input
                        v-model="formState.profile.bodyFat"
                        :placeholder="placeholderFor(formDefaults.profile.bodyFat, 'e.g., 18.5')"
                    />
                </div>

                <Separator />

                <div class="grid gap-4 md:grid-cols-[220px,1fr]">
                    <div>
                        <Label>Lean Mass</Label>
                        <p class="text-xs text-muted-foreground">Your lean body mass in kg (optional).</p>
                    </div>
                    <Input
                        v-model="formState.profile.leanMass"
                        :placeholder="placeholderFor(formDefaults.profile.leanMass, 'e.g., 57.0')"
                    />
                </div>

                <Separator />

                <div class="grid gap-4 md:grid-cols-[220px,1fr] items-start">
                    <div>
                        <Label>Biological Considerations</Label>
                        <p class="text-xs text-muted-foreground">Any factors that may affect training or nutrition.</p>
                    </div>
                    <Textarea
                        v-model="formState.profile.biological"
                        :rows="3"
                        :placeholder="placeholderFor(formDefaults.profile.biological, 'e.g., Post-pregnancy, recovering from injury, thyroid condition...')"
                    />
                </div>

                <Separator />

                <div class="grid gap-4 md:grid-cols-[220px,1fr] items-start">
                    <div>
                        <Label>Menstrual Cycle Info</Label>
                        <p class="text-xs text-muted-foreground">Information about your menstrual cycle if applicable.</p>
                    </div>
                    <Textarea
                        v-model="formState.profile.menstrual"
                        :rows="3"
                        :placeholder="placeholderFor(formDefaults.profile.menstrual, 'e.g., Regular 28-day cycle, tracking symptoms for training adjustments...')"
                    />
                </div>
            </CardContent>
        </Card>

        <div class="flex justify-end gap-3">
            <Button variant="ghost">Cancel</Button>
            <Button>Save changes</Button>
        </div>
    </div>
</template>
