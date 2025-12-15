<script setup lang="ts">
import { ref, computed, watch } from "vue";

const props = defineProps<{
    modelValue: boolean;
    workout?: {
        id: number;
        name: string;
        date: string;
        duration: string;
        exercises: Array<{
            id: number;
            name: string;
            sets: Array<{
                set: number;
                previous: number | null;
                lbs: number;
                reps: number;
            }>;
        }>;
    };
}>();

const emit = defineEmits<{
    (e: "update:modelValue", value: boolean): void;
    (e: "save", workout: any): void;
}>();

const isAddExerciseModalOpen = ref(false);
const workoutData = ref<any>(null);

const isOpen = computed({
    get: () => props.modelValue,
    set: (value) => emit("update:modelValue", value),
});

// Initialize workout data when modal opens
watch(() => props.modelValue, (newValue) => {
    if (newValue && props.workout) {
        workoutData.value = JSON.parse(JSON.stringify(props.workout));
    } else if (newValue && !props.workout) {
        // New workout
        workoutData.value = {
            id: Date.now(),
            name: "New Workout",
            date: new Date().toLocaleDateString("en-US", {
                month: "short",
                day: "numeric",
                year: "numeric",
            }),
            duration: "0:00",
            exercises: [],
        };
    }
});

const addSet = (exerciseId: number) => {
    const exercise = workoutData.value?.exercises.find((e: any) => e.id === exerciseId);
    if (exercise) {
        const lastSet = exercise.sets[exercise.sets.length - 1];
        exercise.sets.push({
            set: exercise.sets.length + 1,
            previous: lastSet.lbs,
            lbs: lastSet.lbs,
            reps: lastSet.reps,
        });
    }
};

const deleteSet = (exerciseId: number, setIndex: number) => {
    const exercise = workoutData.value?.exercises.find((e: any) => e.id === exerciseId);
    if (exercise && exercise.sets.length > 1) {
        exercise.sets.splice(setIndex, 1);
        // Renumber sets
        exercise.sets.forEach((set: any, index: number) => {
            set.set = index + 1;
        });
    }
};

const addExercise = (exercise: { id: number; name: string }) => {
    if (workoutData.value) {
        const newExerciseId = workoutData.value.exercises.length > 0
            ? Math.max(...workoutData.value.exercises.map((e: any) => e.id)) + 1
            : 1;
        workoutData.value.exercises.push({
            id: newExerciseId,
            name: exercise.name,
            sets: [
                { set: 1, previous: null, lbs: 0, reps: 0 },
            ],
        });
    }
};

const handleSave = () => {
    if (workoutData.value) {
        emit("save", workoutData.value);
        isOpen.value = false;
    }
};

const handleCancel = () => {
    isOpen.value = false;
};
</script>

<template>
    <UModal v-model="isOpen" :ui="{ width: 'sm:max-w-4xl' }">
        <UCard>
            <template #header>
                <div class="flex items-center justify-between">
                    <h3 class="text-xl font-bold">{{ workoutData?.name || "Workout" }}</h3>
                    <UButton
                        icon="i-lucide-more-horizontal"
                        color="neutral"
                        variant="ghost"
                        square
                    />
                </div>
            </template>

            <div class="space-y-6 max-h-[70vh] overflow-y-auto">
                <!-- Workout Header Info -->
                <div class="flex items-center gap-6 text-gray-600">
                    <div class="flex items-center gap-2">
                        <UIcon name="i-lucide-calendar" class="size-5" />
                        <span>{{ workoutData?.date }}</span>
                    </div>
                    <div class="flex items-center gap-2">
                        <UIcon name="i-lucide-clock" class="size-5" />
                        <span>{{ workoutData?.duration }}</span>
                    </div>
                </div>

                <!-- Exercises List -->
                <div class="space-y-6">
                    <div
                        v-for="exercise in workoutData?.exercises"
                        :key="exercise.id"
                        class="space-y-4"
                    >
                        <div class="flex items-center justify-between">
                            <h3 class="text-xl font-semibold text-blue-600">
                                {{ exercise.name }}
                            </h3>
                            <div class="flex items-center gap-2">
                                <UButton
                                    icon="i-lucide-line-chart"
                                    color="neutral"
                                    variant="ghost"
                                    size="sm"
                                    square
                                />
                                <UButton
                                    icon="i-lucide-more-horizontal"
                                    color="neutral"
                                    variant="ghost"
                                    size="sm"
                                    square
                                />
                            </div>
                        </div>

                        <!-- Sets Table -->
                        <div class="border border-gray-200 rounded-lg overflow-hidden">
                            <div class="overflow-x-auto">
                                <table class="w-full">
                                    <thead class="bg-gray-50 border-b border-gray-200">
                                        <tr>
                                            <th class="px-4 py-3 text-left text-sm font-semibold">
                                                Set
                                            </th>
                                            <th class="px-4 py-3 text-left text-sm font-semibold">
                                                Previous
                                            </th>
                                            <th class="px-4 py-3 text-left text-sm font-semibold">
                                                lbs
                                            </th>
                                            <th class="px-4 py-3 text-left text-sm font-semibold">
                                                Reps
                                            </th>
                                            <th class="px-4 py-3 text-center text-sm font-semibold">
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr
                                            v-for="(set, index) in exercise.sets"
                                            :key="index"
                                            class="border-b border-gray-100 hover:bg-gray-50"
                                        >
                                            <td class="px-4 py-3">
                                                <div class="bg-gray-200 rounded-full w-8 h-8 flex items-center justify-center text-sm font-medium">
                                                    {{ set.set }}
                                                </div>
                                            </td>
                                            <td class="px-4 py-3 text-gray-500">
                                                {{ set.previous !== null ? set.previous : '—' }}
                                            </td>
                                            <td class="px-4 py-3">
                                                <input
                                                    v-model.number="set.lbs"
                                                    type="number"
                                                    class="w-20 px-2 py-1 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                                                />
                                            </td>
                                            <td class="px-4 py-3">
                                                <input
                                                    v-model.number="set.reps"
                                                    type="number"
                                                    class="w-20 px-2 py-1 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                                                />
                                            </td>
                                            <td class="px-4 py-3 text-center">
                                                <UButton
                                                    v-if="exercise.sets.length > 1"
                                                    icon="i-lucide-trash"
                                                    color="red"
                                                    variant="ghost"
                                                    size="sm"
                                                    square
                                                    @click="deleteSet(exercise.id, index)"
                                                />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>

                            <div class="p-4 border-t border-gray-200 bg-white">
                                <UButton
                                    color="neutral"
                                    variant="outline"
                                    block
                                    @click="addSet(exercise.id)"
                                >
                                    + Add Set (2:00)
                                </UButton>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Add Exercises Button -->
                <UButton
                    color="primary"
                    variant="outline"
                    size="lg"
                    block
                    @click="isAddExerciseModalOpen = true"
                >
                    Add Exercises
                </UButton>

                <!-- Cancel Workout Button -->
                <UButton
                    color="red"
                    variant="outline"
                    size="lg"
                    block
                    @click="handleCancel"
                >
                    Cancel Workout
                </UButton>
            </div>
        </UCard>

        <ExerciseSelectModal v-model="isAddExerciseModalOpen" @select="addExercise" />
    </UModal>
</template>
