<script setup lang="ts">
// No props needed for now
</script>

<template>
  <div class="relative w-full h-[800px] md:h-[700px]">
    <!-- Mountain background -->
    <div
      class="absolute inset-0 bg-cover bg-center bg-no-repeat"
      style="background-image: url('/assets/mountain-hero.jpg');"
    />

    <!-- Hero text content -->
    <div class="relative z-10 flex flex-col items-center h-full">
      <!-- "Nutrify" at top -->
      <h1
        class="absolute top-16 md:top-20 text-5xl md:text-7xl lg:text-8xl font-black text-white tracking-tight"
        style="text-shadow: 0 2px 12px rgba(0, 0, 0, 0.4);"
      >
        Nutrify
      </h1>

      <!-- "Find your peak" positioned above mountain peak -->
      <div
        class="absolute text-3xl md:text-5xl lg:text-6xl font-bold text-white text-center px-4"
        style="top: 60%; text-shadow: 0 2px 12px rgba(0, 0, 0, 0.4);"
      >
        Find your
        <span class="bg-gradient-to-r from-orange-500 to-red-600 bg-clip-text text-transparent">
          peak
        </span>
      </div>
    </div>

    <!-- Gradient blend overlay -->
    <div
      class="absolute bottom-0 left-0 right-0 h-[300px] pointer-events-none z-20"
      style="background: linear-gradient(to bottom, transparent 0%, rgba(51, 51, 54, 0.4) 40%, rgba(51, 51, 54, 0.8) 70%, oklch(32% 0.002 260) 100%);"
    />
  </div>
</template>
