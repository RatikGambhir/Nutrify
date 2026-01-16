<script setup lang="ts">
import { ref, computed } from "vue";
import Button from '~/components/ui/button/Button.vue'
import Card from '~/components/ui/card/Card.vue'
import CardHeader from '~/components/ui/card/CardHeader.vue'
import CardContent from '~/components/ui/card/CardContent.vue'
import CardTitle from '~/components/ui/card/CardTitle.vue'
import Tooltip from '~/components/ui/tooltip/Tooltip.vue'
import TooltipContent from '~/components/ui/tooltip/TooltipContent.vue'
import TooltipProvider from '~/components/ui/tooltip/TooltipProvider.vue'
import TooltipTrigger from '~/components/ui/tooltip/TooltipTrigger.vue'
import Badge from '~/components/ui/badge/Badge.vue'
import Table from '~/components/ui/table/Table.vue'
import TableBody from '~/components/ui/table/TableBody.vue'
import TableCell from '~/components/ui/table/TableCell.vue'
import TableHead from '~/components/ui/table/TableHead.vue'
import TableHeader from '~/components/ui/table/TableHeader.vue'
import TableRow from '~/components/ui/table/TableRow.vue'
import { Bell } from 'lucide-vue-next'

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
    <div class="flex-1 flex flex-col h-full">
        <!-- Header -->
        <header class="flex h-16 items-center justify-between border-b px-6 shrink-0">
            <h1 class="text-xl font-semibold">Nutrition</h1>
            <div class="flex items-center gap-3">
                <div class="inline-flex rounded-md shadow-sm" role="group">
                    <Button
                        :variant="timePeriod === 'daily' ? 'default' : 'outline'"
                        class="rounded-r-none"
                        size="sm"
                        @click="timePeriod = 'daily'"
                    >
                        Daily
                    </Button>
                    <Button
                        :variant="timePeriod === 'weekly' ? 'default' : 'outline'"
                        class="rounded-none border-x-0"
                        size="sm"
                        @click="timePeriod = 'weekly'"
                    >
                        Weekly
                    </Button>
                    <Button
                        :variant="timePeriod === 'monthly' ? 'default' : 'outline'"
                        class="rounded-l-none"
                        size="sm"
                        @click="timePeriod = 'monthly'"
                    >
                        Monthly
                    </Button>
                </div>

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
            <Card>
                <CardHeader>
                    <CardTitle>{{ periodLabel }} Macronutrient Distribution</CardTitle>
                </CardHeader>
                <CardContent>
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
                                    <p class="text-2xl font-bold text-orange-500">
                                        {{ totalCarbs }}
                                    </p>
                                    <p class="text-sm text-gray-600">
                                        Carbs (g)
                                    </p>
                                </div>
                                <div class="text-center">
                                    <p class="text-2xl font-bold text-amber-500">
                                        {{ totalFats }}
                                    </p>
                                    <p class="text-sm text-gray-600">
                                        Fats (g)
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </CardContent>
            </Card>

            <Card>
                <CardHeader>
                    <CardTitle>{{ periodLabel }} Nutrition Breakdown</CardTitle>
                </CardHeader>
                <CardContent>
                    <div class="rounded-lg border">
                        <Table>
                            <TableHeader>
                                <TableRow class="bg-muted/50">
                                    <TableHead>Meal Type</TableHead>
                                    <TableHead>Food</TableHead>
                                    <TableHead>Calories</TableHead>
                                    <TableHead>Protein (g)</TableHead>
                                    <TableHead>Carbs (g)</TableHead>
                                    <TableHead>Fats (g)</TableHead>
                                </TableRow>
                            </TableHeader>
                            <TableBody>
                                <TableRow v-for="meal in nutritionData" :key="meal.mealType + meal.date.toString()">
                                    <TableCell>{{ meal.mealType }}</TableCell>
                                    <TableCell>{{ meal.food.join(", ") }}</TableCell>
                                    <TableCell>{{ meal.calories }}</TableCell>
                                    <TableCell>{{ meal.protein }}</TableCell>
                                    <TableCell>{{ meal.carbs }}</TableCell>
                                    <TableCell>{{ meal.fats }}</TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </div>
                </CardContent>
            </Card>
        </div>
    </div>
</template>
