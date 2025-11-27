<script setup lang="ts">
import { ref, computed } from "vue";
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

const totalProtein = computed(() =>
    nutritionData.value.reduce((sum, meal) => sum + meal.protein, 0)
);

const totalCarbs = computed(() =>
    nutritionData.value.reduce((sum, meal) => sum + meal.carbs, 0)
);

const totalFats = computed(() =>
    nutritionData.value.reduce((sum, meal) => sum + meal.fats, 0)
);

const macroChartData = computed(() => [
    { name: "Protein", value: totalProtein.value },
    { name: "Carbs", value: totalCarbs.value },
    { name: "Fats", value: totalFats.value },
]);
</script>

<template>
    <UDashboardPanel id="nutrition">
        <template #header>
            <UDashboardNavbar title="Nutrition" :ui="{ right: 'gap-3' }">
                <template #leading>
                    <UDashboardSidebarCollapse />
                </template>

                <template #right>
                    <UTooltip text="Notifications" :shortcuts="['N']">
                        <UButton
                            color="neutral"
                            variant="ghost"
                            square
                        >
                            <UChip color="error" inset>
                                <UIcon name="i-lucide-bell" class="size-5 shrink-0" />
                            </UChip>
                        </UButton>
                    </UTooltip>
                </template>
            </UDashboardNavbar>
        </template>

        <template #body>
            <div class="space-y-6">
                <!-- Macronutrient Pie Chart -->
                <UCard>
                    <template #header>
                        <h2 class="text-lg font-semibold">Macronutrient Distribution</h2>
                    </template>
                    <div class="flex flex-col items-center justify-center">
                        <MacroChart :data="macroChartData" />
                        <div class="mt-6 grid grid-cols-3 gap-4 w-full">
                            <div class="text-center">
                                <p class="text-2xl font-bold text-blue-500">{{ totalProtein }}</p>
                                <p class="text-sm text-gray-600">Protein (g)</p>
                            </div>
                            <div class="text-center">
                                <p class="text-2xl font-bold text-orange-500">{{ totalCarbs }}</p>
                                <p class="text-sm text-gray-600">Carbs (g)</p>
                            </div>
                            <div class="text-center">
                                <p class="text-2xl font-bold text-amber-500">{{ totalFats }}</p>
                                <p class="text-sm text-gray-600">Fats (g)</p>
                            </div>
                        </div>
                    </div>
                </UCard>

                <!-- Nutrition Table -->
                <UCard>
                    <template #header>
                        <h2 class="text-lg font-semibold">Daily Nutrition Breakdown</h2>
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
