<script setup lang="ts">
import { ref, computed, Transition } from "vue";
import type { TableColumn } from "@nuxt/ui";

const selectedTab = ref("nutrition");

const nutritionData = ref([
    {
        mealType: "Breakfast",
        food: ["Oatmeal", "Berries", "Protein Powder"],
        calories: 400,
        protein: 30,
        carbs: 45,
        fats: 10,
    },
    {
        mealType: "Lunch",
        food: ["Chicken Salad", "Mixed Greens", "Olive Oil Dressing"],
        calories: 500,
        protein: 40,
        carbs: 20,
        fats: 25,
    },
    {
        mealType: "Dinner",
        food: ["Salmon", "Asparagus", "Quinoa"],
        calories: 600,
        protein: 50,
        carbs: 30,
        fats: 30,
    },
    {
        mealType: "Snack",
        food: ["Greek Yogurt", "Almonds"],
        calories: 200,
        protein: 20,
        carbs: 10,
        fats: 10,
    },
]);

const nutritionColumns: TableColumn[] = [
    {
        accessorKey: "mealType",
        header: "Meal Type",
        cell: ({ row }) => row.getValue("mealType"),
    },
    {
        accessorKey: "food",
        header: "Food",
        cell: ({ row }) => (row.getValue("food") as string[]).join(", "),
    },
    {
        accessorKey: "calories",
        header: "Calories",
        cell: ({ row }) => row.getValue("calories"),
    },
    {
        accessorKey: "protein",
        header: "Protein (g)",
        cell: ({ row }) => row.getValue("protein"),
    },
    {
        accessorKey: "carbs",
        header: "Carbs (g)",
        cell: ({ row }) => row.getValue("carbs"),
    },
    {
        accessorKey: "fats",
        header: "Fats (g)",
        cell: ({ row }) => row.getValue("fats"),
    },
];

const workoutData = ref([
    {
        workoutType: "Strength Training",
        duration: "60 min",
        caloriesBurned: 350,
        workouts: ["Bench Press", "Squats", "Deadlifts"],
    },
    {
        workoutType: "Cardio",
        duration: "45 min",
        caloriesBurned: 400,
        workouts: ["Running", "Elliptical"],
    },
    {
        workoutType: "Yoga",
        duration: "30 min",
        caloriesBurned: 150,
        workouts: ["Vinyasa Flow", "Warrior Pose"],
    },
]);

const workoutColumns: TableColumn[] = [
    {
        accessorKey: "workoutType",
        header: "Workout Type",
        cell: ({ row }) => row.getValue("workoutType"),
    },
    {
        accessorKey: "duration",
        header: "Duration",
        cell: ({ row }) => row.getValue("duration"),
    },
    {
        accessorKey: "caloriesBurned",
        header: "Calories Burned",
        cell: ({ row }) => row.getValue("caloriesBurned"),
    },
    {
        accessorKey: "workouts",
        header: "Workouts",
        cell: ({ row }) => (row.getValue("workouts") as string[]).join(", "),
    },
];

const currentColumns = computed(() => {
    return selectedTab.value === "nutrition"
        ? nutritionColumns
        : workoutColumns;
});

const currentData = computed(() => {
    return selectedTab.value === "nutrition"
        ? nutritionData.value
        : workoutData.value;
});
</script>

<template>
    <div>
        <UButtonGroup class="mb-4">
            <UButton
                label="Nutrition"
                :variant="selectedTab === 'nutrition' ? 'solid' : 'outline'"
                @click="selectedTab = 'nutrition'"
            />
            <UButton
                label="Workout"
                :variant="selectedTab === 'workout' ? 'solid' : 'outline'"
                @click="selectedTab = 'workout'"
            />
        </UButtonGroup>

        <Transition name="fade-slide" mode="out-in">
            <UTable
                :key="selectedTab"
                :data="currentData"
                :columns="currentColumns"
                class="shrink-0"
                :ui="{
                    base: 'table-fixed border-separate border-spacing-0',
                    thead: '[&>tr]:bg-elevated/50 [&>tr]:after:content-none',
                    tbody: '[&>tr]:last:[&>td]:border-b-0',
                    th: 'first:rounded-l-lg last:rounded-r-lg border-y border-default first:border-l last:border-r',
                    td: 'border-b border-default',
                }"
            />
        </Transition>
    </div>
</template>

<style scoped>
.fade-slide-enter-active,
.fade-slide-leave-active {
    transition: all 0.3s ease;
}

.fade-slide-enter-from {
    opacity: 0;
    transform: translateY(10px);
}

.fade-slide-leave-to {
    opacity: 0;
    transform: translateY(-10px);
}
</style>
