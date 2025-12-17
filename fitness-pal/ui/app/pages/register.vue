<script setup lang="ts">
import * as z from "zod";
import type { FormSubmitEvent } from "@nuxt/ui";

definePageMeta({
    layout: false,
});

const toast = useToast();

const schema = z.object({
    email: z.string().email("Invalid email"),
});

type Schema = z.output<typeof schema>;

const state = reactive({
    email: undefined,
});

const router = useRouter();

function onSubmit(event: FormSubmitEvent<Schema>) {
    console.log("Register submitted", event.data);
    toast.add({
        title: "Success",
        description: "Creating your account...",
        color: "primary",
    });

    router.push({
        path: "/verify-email",
        query: { email: event.data.email },
    });
}
</script>

<template>
    <div class="min-h-screen flex">
        <div
            class="hidden lg:flex lg:w-1/2 relative overflow-hidden bg-gradient-to-br from-[#e5989b] via-[#d47b7e] to-[#c25d60]"
        >
            -->

            <div class="absolute bottom-12 left-12 max-w-lg z-10">
                <p class="text-white text-2xl font-medium mb-4">
                    "This platform has helped me to save time and serve my
                    clients faster than ever before."
                </p>
                <p class="text-white/80 text-sm">~ Ali Hassan</p>
            </div>
        </div>

        <div
            class="w-full lg:w-1/2 flex items-center justify-center p-8 bg-white dark:bg-gray-900"
        >
            <div class="w-full max-w-md">
                <div class="mb-16">
                    <NuxtLink to="/" class="flex items-center gap-2">
                        <div
                            class="w-8 h-8 bg-gray-900 dark:bg-white rounded"
                        ></div>
                        <span
                            class="text-xl font-bold text-gray-900 dark:text-white"
                            >efferd</span
                        >
                    </NuxtLink>
                </div>

                <NuxtLink
                    to="/"
                    class="inline-flex items-center gap-2 text-sm text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-white mb-12"
                >
                    <UIcon name="i-lucide-chevron-left" class="w-4 h-4" />
                    Home
                </NuxtLink>

                <div class="space-y-8">
                    <div>
                        <h1
                            class="text-3xl font-bold text-gray-900 dark:text-white mb-2"
                        >
                            Create Your Account
                        </h1>
                        <p class="text-gray-600 dark:text-gray-400">
                            Start your journey with efferd today.
                        </p>
                    </div>

                    <div class="relative">
                        <div class="absolute inset-0 flex items-center">
                            <div
                                class="w-full border-t border-gray-200 dark:border-gray-800"
                            ></div>
                        </div>
                        <div class="relative flex justify-center text-sm">
                            <span
                                class="px-2 bg-white dark:bg-gray-900 text-gray-500"
                                >OR</span
                            >
                        </div>
                    </div>

                    <div class="space-y-4">
                        <p class="text-sm text-gray-600 dark:text-gray-400">
                            Enter your email address to create an account
                        </p>

                        <UForm
                            :schema="schema"
                            :state="state"
                            @submit="onSubmit"
                            class="space-y-4"
                        >
                            <UFormField name="email">
                                <UInput
                                    v-model="state.email"
                                    type="email"
                                    placeholder="your.email@example.com"
                                    size="lg"
                                    icon="i-lucide-mail"
                                    class="w-full"
                                />
                            </UFormField>

                            <UButton
                                type="submit"
                                size="lg"
                                color="black"
                                block
                                class="font-medium"
                            >
                                Continue With Email
                            </UButton>
                        </UForm>

                        <p
                            class="text-xs text-gray-500 dark:text-gray-400 text-center"
                        >
                            By clicking continue, you agree to our
                            <NuxtLink
                                to="/terms"
                                class="underline hover:text-gray-900 dark:hover:text-white"
                                >Terms of Service</NuxtLink
                            >
                            and
                            <NuxtLink
                                to="/privacy"
                                class="underline hover:text-gray-900 dark:hover:text-white"
                                >Privacy Policy</NuxtLink
                            >.
                        </p>
                    </div>

                    <div class="text-center">
                        <p class="text-sm text-gray-600 dark:text-gray-400">
                            Already have an account?
                            <NuxtLink
                                to="/login"
                                class="font-medium text-gray-900 dark:text-white hover:underline"
                            >
                                Sign in
                            </NuxtLink>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
