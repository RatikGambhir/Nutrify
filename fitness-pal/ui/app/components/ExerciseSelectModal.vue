<script setup lang="ts">
import { ref, computed } from "vue";

const props = defineProps<{
    modelValue: boolean;
}>();

const emit = defineEmits<{
    (e: "update:modelValue", value: boolean): void;
    (e: "select", exercise: { id: number; name: string }): void;
}>();

const searchQuery = ref("");

// Mock exercise library
const exercises = ref([
    { id: 1, name: "Bicep Curl (Machine)", category: "Arms" },
    { id: 2, name: "Bicep Curl (Dumbbell)", category: "Arms" },
    { id: 3, name: "Hammer Curl", category: "Arms" },
    { id: 4, name: "Tricep Extension", category: "Arms" },
    { id: 5, name: "Bench Press", category: "Chest" },
    { id: 6, name: "Incline Bench Press", category: "Chest" },
    { id: 7, name: "Dumbbell Fly", category: "Chest" },
    { id: 8, name: "Push Ups", category: "Chest" },
    { id: 9, name: "Shoulder Press", category: "Shoulders" },
    { id: 10, name: "Lateral Raise", category: "Shoulders" },
    { id: 11, name: "Front Raise", category: "Shoulders" },
    { id: 12, name: "Squat", category: "Legs" },
    { id: 13, name: "Leg Press", category: "Legs" },
    { id: 14, name: "Leg Extension", category: "Legs" },
    { id: 15, name: "Leg Curl", category: "Legs" },
    { id: 16, name: "Deadlift", category: "Back" },
    { id: 17, name: "Pull Ups", category: "Back" },
    { id: 18, name: "Barbell Row", category: "Back" },
    { id: 19, name: "Lat Pulldown", category: "Back" },
]);

const isOpen = computed({
    get: () => props.modelValue,
    set: (value) => emit("update:modelValue", value),
});

const filteredExercises = computed(() => {
    if (!searchQuery.value) {
        return exercises.value;
    }
    const query = searchQuery.value.toLowerCase();
    return exercises.value.filter((exercise) =>
        exercise.name.toLowerCase().includes(query) ||
        exercise.category.toLowerCase().includes(query)
    );
});

const handleSelect = (exercise: { id: number; name: string }) => {
    emit("select", exercise);
    searchQuery.value = "";
    isOpen.value = false;
};
</script>

<template>
    <UModal v-model="isOpen" :ui="{ width: 'sm:max-w-2xl' }">
        <UCard>
            <template #header>
                <div class="flex items-center justify-between">
                    <h3 class="text-lg font-semibold">Select Exercise</h3>
                    <UButton
                        icon="i-lucide-x"
                        color="neutral"
                        variant="ghost"
                        size="sm"
                        square
                        @click="isOpen = false"
                    />
                </div>
            </template>

            <div class="space-y-4">
                <UInput
                    v-model="searchQuery"
                    icon="i-lucide-search"
                    placeholder="Search exercises..."
                    size="lg"
                />

                <div class="max-h-96 overflow-y-auto space-y-2">
                    <div
                        v-for="exercise in filteredExercises"
                        :key="exercise.id"
                        class="p-4 border border-gray-200 rounded-lg hover:bg-gray-50 cursor-pointer transition-colors"
                        @click="handleSelect(exercise)"
                    >
                        <div class="flex items-center justify-between">
                            <div>
                                <p class="font-medium">{{ exercise.name }}</p>
                                <p class="text-sm text-gray-500">{{ exercise.category }}</p>
                            </div>
                            <UIcon name="i-lucide-chevron-right" class="size-5 text-gray-400" />
                        </div>
                    </div>

                    <div v-if="filteredExercises.length === 0" class="text-center py-8 text-gray-500">
                        No exercises found
                    </div>
                </div>
            </div>
        </UCard>
    </UModal>
</template>
