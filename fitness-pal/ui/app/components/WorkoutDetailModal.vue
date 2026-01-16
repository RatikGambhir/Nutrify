<script setup lang="ts">
import { ref, computed, watch } from "vue";
import Dialog from '~/components/ui/dialog/Dialog.vue'
import DialogContent from '~/components/ui/dialog/DialogContent.vue'
import DialogHeader from '~/components/ui/dialog/DialogHeader.vue'
import DialogTitle from '~/components/ui/dialog/DialogTitle.vue'
import Button from '~/components/ui/button/Button.vue'
import { MoreHorizontal, Calendar, Clock, LineChart, Trash } from 'lucide-vue-next'

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
    <Dialog v-model:open="isOpen">
        <DialogContent class="sm:max-w-4xl max-h-[90vh] overflow-hidden flex flex-col">
            <DialogHeader class="flex-shrink-0">
                <div class="flex items-center justify-between">
                    <DialogTitle>{{ workoutData?.name || "Workout" }}</DialogTitle>
                    <Button variant="ghost" size="icon">
                        <MoreHorizontal class="h-5 w-5" />
                    </Button>
                </div>
            </DialogHeader>

            <div class="flex-1 overflow-y-auto space-y-6 py-4">
                <!-- Workout Header Info -->
                <div class="flex items-center gap-6 text-gray-600 dark:text-gray-400">
                    <div class="flex items-center gap-2">
                        <Calendar class="h-5 w-5" />
                        <span>{{ workoutData?.date }}</span>
                    </div>
                    <div class="flex items-center gap-2">
                        <Clock class="h-5 w-5" />
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
                                <Button variant="ghost" size="icon" class="h-8 w-8">
                                    <LineChart class="h-4 w-4" />
                                </Button>
                                <Button variant="ghost" size="icon" class="h-8 w-8">
                                    <MoreHorizontal class="h-4 w-4" />
                                </Button>
                            </div>
                        </div>

                        <!-- Sets Table -->
                        <div class="border border-gray-200 dark:border-gray-700 rounded-lg overflow-hidden">
                            <div class="overflow-x-auto">
                                <table class="w-full">
                                    <thead class="bg-gray-50 dark:bg-gray-800 border-b border-gray-200 dark:border-gray-700">
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
                                            class="border-b border-gray-100 dark:border-gray-800 hover:bg-gray-50 dark:hover:bg-gray-800/50"
                                        >
                                            <td class="px-4 py-3">
                                                <div class="bg-gray-200 dark:bg-gray-700 rounded-full w-8 h-8 flex items-center justify-center text-sm font-medium">
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
                                                    class="w-20 px-2 py-1 border border-gray-300 dark:border-gray-600 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 bg-background"
                                                />
                                            </td>
                                            <td class="px-4 py-3">
                                                <input
                                                    v-model.number="set.reps"
                                                    type="number"
                                                    class="w-20 px-2 py-1 border border-gray-300 dark:border-gray-600 rounded focus:outline-none focus:ring-2 focus:ring-blue-500 bg-background"
                                                />
                                            </td>
                                            <td class="px-4 py-3 text-center">
                                                <Button
                                                    v-if="exercise.sets.length > 1"
                                                    variant="ghost"
                                                    size="icon"
                                                    class="h-8 w-8 text-red-500 hover:text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20"
                                                    @click="deleteSet(exercise.id, index)"
                                                >
                                                    <Trash class="h-4 w-4" />
                                                </Button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>

                            <div class="p-4 border-t border-gray-200 dark:border-gray-700 bg-background">
                                <Button
                                    variant="outline"
                                    class="w-full"
                                    @click="addSet(exercise.id)"
                                >
                                    + Add Set (2:00)
                                </Button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Add Exercises Button -->
                <Button
                    variant="outline"
                    size="lg"
                    class="w-full"
                    @click="isAddExerciseModalOpen = true"
                >
                    Add Exercises
                </Button>

                <!-- Cancel Workout Button -->
                <Button
                    variant="outline"
                    size="lg"
                    class="w-full text-red-500 border-red-300 hover:bg-red-50 dark:hover:bg-red-900/20"
                    @click="handleCancel"
                >
                    Cancel Workout
                </Button>
            </div>
        </DialogContent>

        <ExerciseSelectModal v-model="isAddExerciseModalOpen" @select="addExercise" />
    </Dialog>
</template>
