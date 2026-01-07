<script setup lang="ts">
import { ref, computed } from "vue";
import type { TableColumn } from "@nuxt/ui";

const selectedTab = ref("nutrition");
const timePeriod = ref("daily");

const getWeekStart = (date: Date) => {
    const d = new Date(date);
    const day = d.getDay();
    const diff = d.getDate() - day;
    return new Date(d.setDate(diff));
};

const getWeekEnd = (date: Date) => {
    const start = getWeekStart(date);
    const end = new Date(start);
    end.setDate(start.getDate() + 6);
    return end;
};

const isInCurrentWeek = (date: Date) => {
    const now = new Date();
    const weekStart = getWeekStart(now);
    const weekEnd = getWeekEnd(now);
    weekStart.setHours(0, 0, 0, 0);
    weekEnd.setHours(23, 59, 59, 999);
    return date >= weekStart && date <= weekEnd;
};

const isInCurrentMonth = (date: Date) => {
    const now = new Date();
    return (
        date.getMonth() === now.getMonth() &&
        date.getFullYear() === now.getFullYear()
    );
};

const isToday = (date: Date) => {
    const now = new Date();
    return (
        date.getDate() === now.getDate() &&
        date.getMonth() === now.getMonth() &&
        date.getFullYear() === now.getFullYear()
    );
};

const allNutritionData = ref([
    {
        mealType: "Breakfast",
        food: ["Oatmeal", "Berries", "Protein Powder"],
        calories: 400,
        protein: 30,
        carbs: 45,
        fats: 10,
        date: new Date(),
    },
    {
        mealType: "Lunch",
        food: ["Chicken Salad", "Mixed Greens", "Olive Oil Dressing"],
        calories: 500,
        protein: 40,
        carbs: 20,
        fats: 25,
        date: new Date(),
    },
    {
        mealType: "Dinner",
        food: ["Salmon", "Asparagus", "Quinoa"],
        calories: 600,
        protein: 50,
        carbs: 30,
        fats: 30,
        date: new Date(),
    },
    {
        mealType: "Snack",
        food: ["Greek Yogurt", "Almonds"],
        calories: 200,
        protein: 20,
        carbs: 10,
        fats: 10,
        date: new Date(),
    },
    // Yesterday's meals (in current week)
    {
        mealType: "Breakfast",
        food: ["Eggs", "Toast", "Avocado"],
        calories: 450,
        protein: 25,
        carbs: 35,
        fats: 20,
        date: new Date(new Date().setDate(new Date().getDate() - 1)),
    },
    {
        mealType: "Lunch",
        food: ["Turkey Sandwich", "Apple"],
        calories: 550,
        protein: 35,
        carbs: 50,
        fats: 15,
        date: new Date(new Date().setDate(new Date().getDate() - 1)),
    },
    {
        mealType: "Dinner",
        food: ["Steak", "Sweet Potato", "Broccoli"],
        calories: 700,
        protein: 55,
        carbs: 40,
        fats: 35,
        date: new Date(new Date().setDate(new Date().getDate() - 1)),
    },
    // 2 days ago (in current week)
    {
        mealType: "Breakfast",
        food: ["Pancakes", "Maple Syrup"],
        calories: 500,
        protein: 15,
        carbs: 75,
        fats: 12,
        date: new Date(new Date().setDate(new Date().getDate() - 2)),
    },
    {
        mealType: "Lunch",
        food: ["Pasta", "Marinara Sauce"],
        calories: 600,
        protein: 20,
        carbs: 80,
        fats: 18,
        date: new Date(new Date().setDate(new Date().getDate() - 2)),
    },
    // Previous week meals (for monthly view)
    {
        mealType: "Dinner",
        food: ["Chicken", "Rice", "Vegetables"],
        calories: 650,
        protein: 45,
        carbs: 60,
        fats: 20,
        date: new Date(new Date().setDate(new Date().getDate() - 10)),
    },
]);

// Filtered nutrition data based on selected time period
const nutritionData = computed(() => {
    if (timePeriod.value === "daily") {
        return allNutritionData.value.filter((meal) => isToday(meal.date));
    } else if (timePeriod.value === "weekly") {
        return allNutritionData.value.filter((meal) =>
            isInCurrentWeek(meal.date),
        );
    } else if (timePeriod.value === "monthly") {
        return allNutritionData.value.filter((meal) =>
            isInCurrentMonth(meal.date),
        );
    }
    return allNutritionData.value;
});

const nutritionColumns: TableColumn<object>[] = [
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

const totalCalories = computed(() =>
    nutritionData.value.reduce((sum, meal) => sum + meal.calories, 0),
);

const totalProtein = computed(() =>
    nutritionData.value.reduce((sum, meal) => sum + meal.protein, 0),
);

const totalCarbs = computed(() =>
    nutritionData.value.reduce((sum, meal) => sum + meal.carbs, 0),
);

const totalFats = computed(() =>
    nutritionData.value.reduce((sum, meal) => sum + meal.fats, 0),
);

const macroChartData = computed(() => [
    { name: "Protein", value: totalProtein.value },
    { name: "Carbs", value: totalCarbs.value },
    { name: "Fats", value: totalFats.value },
]);

const periodLabel = computed(() => {
    if (timePeriod.value === "daily") return "Daily";
    if (timePeriod.value === "weekly") return "Weekly";
    if (timePeriod.value === "monthly") return "Monthly";
    return "Daily";
});
</script>

<template>
    <UDashboardPanel id="nutrition">
        <template #header>
            <UDashboardNavbar title="Nutrition" :ui="{ right: 'gap-3' }">
                <template #leading>
                    <UDashboardSidebarCollapse />
                </template>

                <template #right>
                    <UButtonGroup size="sm" orientation="horizontal">
                        <UButton
                            :color="
                                timePeriod === 'daily' ? 'primary' : 'neutral'
                            "
                            :variant="
                                timePeriod === 'daily' ? 'solid' : 'ghost'
                            "
                            @click="timePeriod = 'daily'"
                        >
                            Daily
                        </UButton>
                        <UButton
                            :color="
                                timePeriod === 'weekly' ? 'primary' : 'neutral'
                            "
                            :variant="
                                timePeriod === 'weekly' ? 'solid' : 'ghost'
                            "
                            @click="timePeriod = 'weekly'"
                        >
                            Weekly
                        </UButton>
                        <UButton
                            :color="
                                timePeriod === 'monthly' ? 'primary' : 'neutral'
                            "
                            :variant="
                                timePeriod === 'monthly' ? 'solid' : 'ghost'
                            "
                            @click="timePeriod = 'monthly'"
                        >
                            Monthly
                        </UButton>
                    </UButtonGroup>

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
            <div class="space-y-6">
                <UCard>
                    <template #header>
                        <h2 class="text-lg font-semibold">
                            {{ periodLabel }} Macronutrient Distribution
                        </h2>
                    </template>
                    <div class="flex flex-col items-center justify-center">
                        <MacroChart :data="macroChartData" />
                        <div class="mt-6 w-full">
                            <div class="text-center mb-4">
                                <p class="text-3xl font-bold text-primary">
                                    {{ totalCalories }}
                                </p>
                                <p class="text-sm text-gray-600">
                                    Total Calories
                                </p>
                            </div>
                            <div class="grid grid-cols-3 gap-4">
                                <div class="text-center">
                                    <p class="text-2xl font-bold text-blue-500">
                                        {{ totalProtein }}
                                    </p>
                                    <p class="text-sm text-gray-600">
                                        Protein (g)
                                    </p>
                                </div>
                                <div class="text-center">
                                    <p
                                        class="text-2xl font-bold text-orange-500"
                                    >
                                        {{ totalCarbs }}
                                    </p>
                                    <p class="text-sm text-gray-600">
                                        Carbs (g)
                                    </p>
                                </div>
                                <div class="text-center">
                                    <p
                                        class="text-2xl font-bold text-amber-500"
                                    >
                                        {{ totalFats }}
                                    </p>
                                    <p class="text-sm text-gray-600">
                                        Fats (g)
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </UCard>

                <UCard>
                    <template #header>
                        <h2 class="text-lg font-semibold">
                            {{ periodLabel }} Nutrition Breakdown
                        </h2>
                    </template>
                    <UTable
                        :data="nutritionData"
                        :columns="nutritionColumns"
                        class="shrink-0"
                        :ui="{
                            base: 'table-fixed border-separate border-spacing-0',
                            thead: '[&>tr]:bg-elevated/50 [&>tr]:after:content-none',
                            tbody: '[&>tr]:last:[&>td]:border-b-0',
                            th: 'first:rounded-l-lg last:rounded-r-lg border-y border-default first:border-l last:border-r',
                            td: 'border-b border-default',
                        }"
                    />
                </UCard>
            </div>
        </template>
    </UDashboardPanel>
</template>
