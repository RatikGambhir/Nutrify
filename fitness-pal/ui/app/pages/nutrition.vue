<script setup lang="ts">
import { computed, ref } from "vue";
import Input from "~/components/ui/input/Input.vue"
import NutritionSummaryCard from "~/components/nutrition/NutritionSummaryCard.vue"
import MacroDistributionCard from "~/components/nutrition/MacroDistributionCard.vue"
import MealCard from "~/components/nutrition/MealCard.vue"
import NutritionResourcesCard from "~/components/nutrition/NutritionResourcesCard.vue"
import { CalendarDays, FileText, Lightbulb, Plus, Sun } from "lucide-vue-next"

const selectedDate = ref("01/13/2026");
const displayDate = "Monday, January 12, 2026";

const summaryCards = [
    { label: "Total Calories", value: "2450", unit: "kcal", valueClass: "text-purple-400", dotClass: "bg-purple-400" },
    { label: "Carbs", value: "271", unit: "g", valueClass: "text-blue-400", dotClass: "bg-blue-400" },
    { label: "Protein", value: "165", unit: "g", valueClass: "text-emerald-400", dotClass: "bg-emerald-400" },
    { label: "Fat", value: "85", unit: "g", valueClass: "text-orange-400", dotClass: "bg-orange-400" },
];

    const macros = [
        {
            name: "Carbs",
            percent: 43,
            color: "#60A5FA",
            labelColor: "text-blue-300",
            labelClass: "-top-6 left-1/2 -translate-x-1/2",
        },
        {
            name: "Protein",
            percent: 26,
            color: "#34D399",
            labelColor: "text-emerald-300",
            labelClass: "left-0 bottom-4 -translate-x-6",
        },
        {
            name: "Fat",
            percent: 30,
            color: "#F97316",
            labelColor: "text-orange-300",
            labelClass: "right-0 bottom-6 translate-x-6",
        },
    ];

const pieGradient = computed(() => {
    const [carb, protein, fat] = macros;
    const carbStop = carb.percent;
    const proteinStop = carbStop + protein.percent;
    return `conic-gradient(${carb.color} 0 ${carbStop}%, ${protein.color} ${carbStop}% ${proteinStop}%, ${fat.color} ${proteinStop}% 100%)`;
});

const meals = [
    {
        name: "Breakfast",
        icon: Sun,
        totals: { calories: 470, carbs: 66, protein: 32, fat: 12 },
        items: [
            { name: "Oatmeal with Berries", calories: 320, carbs: 54, protein: 12, fat: 8 },
            { name: "Greek Yogurt", calories: 150, carbs: 12, protein: 20, fat: 4 },
        ],
    },
    {
        name: "Lunch",
        icon: Sun,
        totals: { calories: 830, carbs: 80, protein: 56, fat: 30 },
        items: [
            { name: "Grilled Chicken Salad", calories: 450, carbs: 28, protein: 42, fat: 18 },
            { name: "Quinoa Bowl", calories: 380, carbs: 52, protein: 14, fat: 12 },
        ],
    },
];

const resources = [
            { label: "Meal Plans", value: 12, icon: FileText, iconBg: "bg-blue-100", iconColor: "text-blue-600" },
            { label: "Add Specific Food", value: 156, icon: Plus, iconBg: "bg-rose-100", iconColor: "text-rose-600" },
            { label: "Recipes", value: 48, icon: FileText, iconBg: "bg-emerald-100", iconColor: "text-emerald-600" },
            { label: "Nutrition Recommendations", value: 8, icon: Lightbulb, iconBg: "bg-violet-100", iconColor: "text-violet-600" },
    ];
</script>

<template>
    <div class="flex-1 flex flex-col h-full bg-slate-200">
        <div class="flex-1 overflow-auto p-6">
            <div class="max-w-6xl mx-auto space-y-6">
                <div class="space-y-2">
                    <h1 class="text-2xl font-semibold text-slate-900">Nutrition Tracker</h1>
                    <div class="flex flex-wrap items-center gap-4 text-sm text-slate-600">
                        <span>{{ displayDate }}</span>
                        <div class="relative">
                            <Input v-model="selectedDate" class="h-8 w-[120px] pr-8" />
                            <CalendarDays class="absolute right-2 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-500" />
                        </div>
                    </div>
                </div>

                <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
                    <NutritionSummaryCard
                        v-for="card in summaryCards"
                        :key="card.label"
                        :label="card.label"
                        :value="card.value"
                        :unit="card.unit"
                        :value-class="card.valueClass"
                        :dot-class="card.dotClass"
                    />
                </div>

                <MacroDistributionCard :gradient="pieGradient" :macros="macros" />

                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <MealCard v-for="meal in meals" :key="meal.name" :meal="meal" />
                </div>

                <NutritionResourcesCard :resources="resources" />
            </div>
        </div>
    </div>
</template>
