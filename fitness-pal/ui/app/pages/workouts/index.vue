<script setup lang="ts">
import { ref, computed } from "vue";
import Button from '~/components/ui/button/Button.vue'
import Input from '~/components/ui/input/Input.vue'
import Card from '~/components/ui/card/Card.vue'
import CardHeader from '~/components/ui/card/CardHeader.vue'
import CardContent from '~/components/ui/card/CardContent.vue'
import Tooltip from '~/components/ui/tooltip/Tooltip.vue'
import TooltipContent from '~/components/ui/tooltip/TooltipContent.vue'
import TooltipProvider from '~/components/ui/tooltip/TooltipProvider.vue'
import TooltipTrigger from '~/components/ui/tooltip/TooltipTrigger.vue'
import Badge from '~/components/ui/badge/Badge.vue'
import { Plus, Bell, MoreHorizontal, Circle, Search } from 'lucide-vue-next'

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
    <div class="flex-1 flex flex-col h-full">
        <!-- Header -->
        <header class="flex h-16 items-center justify-between border-b px-6 shrink-0">
            <h1 class="text-xl font-semibold">Workouts</h1>
            <div class="flex items-center gap-3">
                <Button @click="isAddWorkoutModalOpen = true">
                    <Plus class="h-4 w-4 mr-2" />
                    Add Workout
                </Button>

                <TooltipProvider>
                    <Tooltip>
                        <TooltipTrigger as-child>
                            <Button variant="ghost" size="icon" class="relative">
                                <Bell class="h-5 w-5" />
                                <Badge class="absolute -top-1 -right-1 h-4 w-4 p-0 flex items-center justify-center text-[10px]" variant="destructive">
                                    3
                                </Badge>
                            </Button>
                        </TooltipTrigger>
                        <TooltipContent>
                            <p>Notifications (N)</p>
                        </TooltipContent>
                    </Tooltip>
                </TooltipProvider>
            </div>
        </header>

        <!-- Body -->
        <div class="flex-1 overflow-auto p-6 space-y-6">
            <div class="relative">
                <Search class="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                <Input
                    v-model="searchQuery"
                    placeholder="Search workouts..."
                    class="pl-10 h-11"
                />
            </div>

            <div
                v-if="filteredWorkouts.length === 0"
                class="text-center py-12 text-gray-500"
            >
                No workouts found
            </div>

            <div
                class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
            >
                <Card
                    v-for="workout in filteredWorkouts"
                    :key="workout.id"
                    class="cursor-pointer hover:shadow-lg transition-shadow"
                    @click="navigateTo(`/workouts/${workout.id}`)"
                >
                    <CardHeader class="pb-2">
                        <div class="flex items-center justify-between">
                            <h3 class="text-lg font-semibold">
                                {{ workout.name }}
                            </h3>
                            <Button
                                variant="ghost"
                                size="icon"
                                class="h-8 w-8"
                                @click.stop
                            >
                                <MoreHorizontal class="h-4 w-4" />
                            </Button>
                        </div>
                    </CardHeader>

                    <CardContent>
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
                                    <Circle class="h-2 w-2 text-gray-400 fill-current" />
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
                    </CardContent>
                </Card>
            </div>
        </div>

        <WorkoutAddModal v-model="isAddWorkoutModalOpen" @create="createWorkout" />
    </div>
</template>
