<script setup lang="ts">
import { ref, computed } from "vue";

const isAddWorkoutModalOpen = ref(false);
const searchQuery = ref("");

// Mock workout data
const workouts = ref([
    {
        id: 1,
        name: "Morning Workout",
        date: "Dec 15, 2025",
        duration: "0:50",
        exercises: [
            {
                id: 1,
                name: "Bicep Curl (Machine)",
                sets: [{ set: 1, previous: null, lbs: 50, reps: 12 }],
            },
        ],
    },
    {
        id: 2,
        name: "Evening Push Day",
        date: "Dec 14, 2025",
        duration: "1:15",
        exercises: [
            {
                id: 1,
                name: "Bench Press",
                sets: [
                    { set: 1, previous: null, lbs: 135, reps: 10 },
                    { set: 2, previous: 135, lbs: 155, reps: 8 },
                    { set: 3, previous: 155, lbs: 155, reps: 8 },
                ],
            },
            {
                id: 2,
                name: "Shoulder Press",
                sets: [
                    { set: 1, previous: null, lbs: 65, reps: 10 },
                    { set: 2, previous: 65, lbs: 65, reps: 10 },
                ],
            },
        ],
    },
    {
        id: 3,
        name: "Leg Day",
        date: "Dec 13, 2025",
        duration: "1:30",
        exercises: [
            {
                id: 1,
                name: "Squat",
                sets: [
                    { set: 1, previous: null, lbs: 185, reps: 10 },
                    { set: 2, previous: 185, lbs: 205, reps: 8 },
                    { set: 3, previous: 205, lbs: 225, reps: 6 },
                ],
            },
            {
                id: 2,
                name: "Leg Press",
                sets: [
                    { set: 1, previous: null, lbs: 270, reps: 12 },
                    { set: 2, previous: 270, lbs: 270, reps: 12 },
                ],
            },
        ],
    },
    {
        id: 4,
        name: "Back & Biceps",
        date: "Dec 12, 2025",
        duration: "1:20",
        exercises: [
            {
                id: 1,
                name: "Deadlift",
                sets: [
                    { set: 1, previous: null, lbs: 225, reps: 8 },
                    { set: 2, previous: 225, lbs: 245, reps: 6 },
                ],
            },
            {
                id: 2,
                name: "Pull Ups",
                sets: [
                    { set: 1, previous: null, lbs: 0, reps: 10 },
                    { set: 2, previous: 0, lbs: 0, reps: 8 },
                    { set: 3, previous: 0, lbs: 0, reps: 6 },
                ],
            },
        ],
    },
]);

const createWorkout = (name: string) => {
    const newId =
        workouts.value.length > 0
            ? Math.max(...workouts.value.map((w) => w.id)) + 1
            : 1;
    const newWorkout = {
        id: newId,
        name,
        date: new Date().toLocaleDateString("en-US", {
            month: "short",
            day: "numeric",
            year: "numeric",
        }),
        duration: "0:00",
        exercises: [],
    };
    workouts.value.unshift(newWorkout);
    navigateTo(`/workouts/${newId}`);
};

const filteredWorkouts = computed(() => {
    if (!searchQuery.value) {
        return workouts.value;
    }
    const query = searchQuery.value.toLowerCase();
    return workouts.value.filter(
        (workout) =>
            workout.name.toLowerCase().includes(query) ||
            workout.date.toLowerCase().includes(query),
    );
});
</script>

<template>
    <UDashboardPanel id="workouts">
        <template #header>
            <UDashboardNavbar title="Workouts" :ui="{ right: 'gap-3' }">
                <template #leading>
                    <UDashboardSidebarCollapse />
                </template>

                <template #right>
                    <UButton
                        icon="i-lucide-plus"
                        size="md"
                        @click="isAddWorkoutModalOpen = true"
                    >
                        Add Workout
                    </UButton>

                    <UTooltip text="Notifications" :shortcuts="['N']">
                        <UButton color="neutral" variant="ghost" square>
                            <UChip color="error" inset>
                                <UIcon
                                    name="i-lucide-bell"
                                    class="size-5 shrink-0"
                                />
                            </UChip>
                        </UButton>
                    </UTooltip>
                </template>
            </UDashboardNavbar>
        </template>

        <template #body>
            <div class="p-6 space-y-6">
                <UInput
                    v-model="searchQuery"
                    icon="i-lucide-search"
                    placeholder="Search workouts..."
                    size="lg"
                />

                <div
                    v-if="filteredWorkouts.length === 0"
                    class="text-center py-12 text-gray-500"
                >
                    No workouts found
                </div>

                <div
                    class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
                >
                    <UCard
                        v-for="workout in filteredWorkouts"
                        :key="workout.id"
                        class="cursor-pointer hover:shadow-lg transition-shadow"
                        @click="navigateTo(`/workouts/${workout.id}`)"
                    >
                        <template #header>
                            <div class="flex items-center justify-between">
                                <h3 class="text-lg font-semibold">
                                    {{ workout.name }}
                                </h3>
                                <UButton
                                    icon="i-lucide-more-horizontal"
                                    color="neutral"
                                    variant="ghost"
                                    size="sm"
                                    square
                                />
                            </div>
                        </template>

                        <div class="space-y-2">
                            <div
                                v-if="workout.exercises.length === 0"
                                class="text-sm text-gray-500 italic py-4"
                            >
                                No exercises added yet
                            </div>
                            <ul v-else class="space-y-2">
                                <li
                                    v-for="exercise in workout.exercises"
                                    :key="exercise.id"
                                    class="flex items-center gap-2 text-sm"
                                >
                                    <UIcon
                                        name="i-lucide-circle"
                                        class="size-2 text-gray-400"
                                    />
                                    <span class="font-medium">{{
                                        exercise.name
                                    }}</span>
                                    <span class="text-gray-500"
                                        >- {{ exercise.sets.length }} set{{
                                            exercise.sets.length !== 1
                                                ? "s"
                                                : ""
                                        }}</span
                                    >
                                </li>
                            </ul>
                        </div>
                    </UCard>
                </div>
            </div>
        </template>

        <!-- <WorkoutAddModal v-model="isAddWorkoutModalOpen" @create="createWorkout" /> -->
    </UDashboardPanel>
</template>
