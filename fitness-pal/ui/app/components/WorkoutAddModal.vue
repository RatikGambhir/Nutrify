<script setup lang="ts">
import { ref } from "vue";

const props = defineProps<{
    modelValue: boolean;
}>();

const emit = defineEmits<{
    (e: "update:modelValue", value: boolean): void;
    (e: "create", name: string): void;
}>();

const workoutName = ref("");

const isOpen = computed({
    get: () => props.modelValue,
    set: (value) => {
        if (!value) {
            // Clear input when modal is closed
            workoutName.value = "";
        }
        emit("update:modelValue", value);
    },
});

const handleCreate = () => {
    console.log("Creating workout", isOpen.value);
    if (workoutName.value.trim()) {
        emit("create", workoutName.value);
        workoutName.value = "";
        isOpen.value = false;
    }
};

const handleCancel = () => {
    workoutName.value = "";
    isOpen.value = false;
};
</script>

<template>
    <UModal v-model="isOpen">
        <UCard>
            <template #header>
                <div class="flex items-center justify-between">
                    <h3 class="text-lg font-semibold">New Workout</h3>
                    <UButton
                        icon="i-lucide-x"
                        color="neutral"
                        variant="ghost"
                        size="sm"
                        square
                        @click="handleCancel"
                    />
                </div>
            </template>

            <div class="space-y-4">
                <div>
                    <label class="block text-sm font-medium mb-2"
                        >Workout Name</label
                    >
                    <UInput
                        v-model="workoutName"
                        placeholder="Enter workout name"
                        size="lg"
                        @keyup.enter="handleCreate"
                    />
                </div>
            </div>

            <template #footer>
                <div class="flex gap-3 justify-end">
                    <UButton
                        color="neutral"
                        variant="outline"
                        @click="handleCancel"
                    >
                        Cancel
                    </UButton>
                    <UButton color="primary" @click="handleCreate">
                        Create Workout
                    </UButton>
                </div>
            </template>
        </UCard>
    </UModal>
</template>
