<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from "vue";
import Button from '~/components/ui/button/Button.vue'
import Input from '~/components/ui/input/Input.vue'
import Card from '~/components/ui/card/Card.vue'
import CardHeader from '~/components/ui/card/CardHeader.vue'
import CardContent from '~/components/ui/card/CardContent.vue'
import Expandable from '~/components/ui/expandable/Expandable.vue'
import ExpandableCard from '~/components/ui/expandable/ExpandableCard.vue'
import ExpandableContent from '~/components/ui/expandable/ExpandableContent.vue'
import ExpandableTrigger from '~/components/ui/expandable/ExpandableTrigger.vue'
import Tooltip from '~/components/ui/tooltip/Tooltip.vue'
import TooltipContent from '~/components/ui/tooltip/TooltipContent.vue'
import TooltipProvider from '~/components/ui/tooltip/TooltipProvider.vue'
import TooltipTrigger from '~/components/ui/tooltip/TooltipTrigger.vue'
import Badge from '~/components/ui/badge/Badge.vue'
import { Plus, Bell, MoreHorizontal, Circle, Search, ChevronDown, Clock } from 'lucide-vue-next'

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

const viewportWidth = ref(0);

const updateViewport = () => {
    viewportWidth.value = window.innerWidth;
};

onMounted(() => {
    updateViewport();
    window.addEventListener("resize", updateViewport);
});

onBeforeUnmount(() => {
    window.removeEventListener("resize", updateViewport);
});

const columnsCount = computed(() => {
    if (viewportWidth.value < 768) {
        return 1;
    }
    if (viewportWidth.value < 1024) {
        return 2;
    }
    if (viewportWidth.value < 1280) {
        return 3;
    }
    return 4;
});

const columnedWorkouts = computed(() => {
    const columns = Array.from({ length: columnsCount.value }, () => [] as typeof workouts.value);
    filteredWorkouts.value.forEach((workout, index) => {
        columns[index % columnsCount.value].push(workout);
    });
    return columns;
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

            <div class="flex flex-col md:flex-row gap-6">
                <div
                    v-for="(column, columnIndex) in columnedWorkouts"
                    :key="`column-${columnIndex}`"
                    class="flex flex-col gap-6 flex-1"
                >
                    <Expandable v-for="workout in column" :key="workout.id" class="h-full max-h-4">
                        <template #default="isExpanded">
                            <ExpandableTrigger>
                                <ExpandableCard
                                    class="rounded-lg border bg-card text-card-foreground shadow-sm transition-[height] duration-300"
                                    :class="isExpanded ? 'h-full' : 'h-[260px]'"
                                >
                                    <Card class="border-0 shadow-none">
                                        <CardHeader class="pb-2">
                                            <div class="flex items-center justify-between">
                                                <div>
                                                    <h3 class="text-lg font-semibold">
                                                        {{ workout.name }}
                                                    </h3>
                                                    <div class="flex items-center gap-2 text-sm text-muted-foreground mt-1">
                                                        <Badge variant="secondary">{{ workout.date }}</Badge>
                                                        <span class="flex items-center gap-1">
                                                            <Clock class="h-3 w-3" />
                                                            {{ workout.duration }}
                                                        </span>
                                                    </div>
                                                </div>
                                                <div class="flex items-center gap-2">
                                                    <Button
                                                        variant="ghost"
                                                        size="icon"
                                                        class="h-8 w-8"
                                                        @click.stop
                                                    >
                                                        <MoreHorizontal class="h-4 w-4" />
                                                    </Button>
                                                    <ChevronDown
                                                        class="h-4 w-4 text-muted-foreground transition-transform duration-200"
                                                        :class="isExpanded ? 'rotate-180' : ''"
                                                    />
                                                </div>
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
                                                        <span class="font-medium">{{ exercise.name }}</span>
                                                        <span class="text-gray-500">
                                                            - {{ exercise.sets.length }} set{{ exercise.sets.length !== 1 ? "s" : "" }}
                                                        </span>
                                                    </li>
                                                </ul>
                                            </div>

                                            <ExpandableContent preset="slide-up" class="mt-4">
                                                <div class="border-t pt-4 space-y-3">
                                                    <div
                                                        v-for="exercise in workout.exercises"
                                                        :key="`detail-${exercise.id}`"
                                                        class="text-sm text-muted-foreground"
                                                    >
                                                        <span class="font-medium text-foreground">
                                                            {{ exercise.name }}
                                                        </span>
                                                        <span>
                                                            - {{ exercise.sets.map((set) => `${set.lbs}lb x ${set.reps}`).join(", ") }}
                                                        </span>
                                                    </div>
                                                    <div class="flex justify-end">
                                                        <Button size="sm" @click.stop="navigateTo(`/workouts/${workout.id}`)">
                                                            Open workout
                                                        </Button>
                                                    </div>
                                                </div>
                                            </ExpandableContent>
                                        </CardContent>
                                    </Card>
                                </ExpandableCard>
                            </ExpandableTrigger>
                        </template>
                    </Expandable>
                </div>
            </div>
        </div>

        <WorkoutAddModal v-model="isAddWorkoutModalOpen" @create="createWorkout" />
    </div>
</template>
