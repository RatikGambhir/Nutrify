<script setup lang="ts">
import Card from "~/components/ui/card/Card.vue"
import CardHeader from "~/components/ui/card/CardHeader.vue"
import CardContent from "~/components/ui/card/CardContent.vue"
import CardTitle from "~/components/ui/card/CardTitle.vue"

defineProps<{
    gradient: string;
    macros: Array<{
        name: string;
        percent: number;
        color: string;
        labelColor: string;
        labelClass: string;
    }>;
}>();
</script>

<template>
    <Card class="bg-slate-700 text-slate-100 border-0 shadow-sm">
        <CardHeader class="pb-2">
            <CardTitle class="text-sm font-semibold text-slate-200">
                Macro Distribution
            </CardTitle>
        </CardHeader>
        <CardContent class="pt-4">
            <div class="flex flex-col items-center gap-6">
                <div class="relative h-52 w-52">
                    <div class="h-full w-full rounded-full" :style="{ background: gradient }"></div>
                    <span
                        v-for="macro in macros"
                        :key="macro.name"
                        class="absolute text-xs"
                        :class="[macro.labelColor, macro.labelClass]"
                    >
                        {{ macro.name }} {{ macro.percent }}%
                    </span>
                </div>
                <div class="flex flex-wrap items-center justify-center gap-6 text-xs text-slate-300">
                    <div v-for="macro in macros" :key="`legend-${macro.name}`" class="flex items-center gap-2">
                        <span class="h-2 w-2 rounded-full" :style="{ backgroundColor: macro.color }"></span>
                        <span>{{ macro.name }} ({{ macro.percent }}%)</span>
                    </div>
                </div>
            </div>
        </CardContent>
    </Card>
</template>
