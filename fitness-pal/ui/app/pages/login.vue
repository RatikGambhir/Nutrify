<script setup lang="ts">
import * as z from "zod";
import { supabase } from "~/utils";
import { useUserStore } from "~/stores/useUserStore";
import type { FormSubmitEvent } from "@nuxt/ui";
import type {AuthError} from "@supabase/supabase-js";

definePageMeta({
    layout: false,
});

export interface UserSession {
  error: AuthError | null,
  user: object | null,
  session: object | null
}

const userStore = useUserStore();

const toast = useToast();

const schema = z.object({
    email: z.string().email("Invalid email"),
});

type Schema = z.output<typeof schema>;

const state = reactive({
    email: undefined,
});

const router = useRouter();

async function login(email: string): Promise<UserSession> {
    const { data, error } = await supabase.auth.signInWithOtp({
        email: email,
    });
    if (error) {
        return {
          error: error,
          user: null,
          session: null
        }
    }
        toast.add({
            title: "Success",
            description: "Continuing with email login...",
            color: "primary",
        });
        return {
          user: data.user,
          session: data.session,
          error: error
        }

}

async function onSubmit(event: FormSubmitEvent<Schema>) {
    const { email } = event.data;
    const {user, session, error} = await login(email)
    if(error) {
      toast.add({
        title: "Uh oh, something went wrong with your email, please try again",
        description: error.message,
        color: "error",
      });
    } else {
      userStore.addUserSession(user ?? {}, session ?? {});
      await router.push({
        path: "/verify-email",
        query: {email: event.data.email},
      });
    }




}
</script>

<template>
    <div class="min-h-screen flex">
        <div
            class="hidden lg:flex lg:w-1/2 relative overflow-hidden bg-gradient-to-br from-[#e5989b] via-[#d47b7e] to-[#c25d60]"
        >
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
                        />
                        <span
                            class="text-xl font-bold text-gray-900 dark:text-white"
                            >Nutrify</span
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
                            Sign In or Join Now!
                        </h1>
                        <p class="text-gray-600 dark:text-gray-400">
                            login or create your efferd account.
                        </p>
                    </div>

                    <div class="relative">
                        <div class="absolute inset-0 flex items-center">
                            <div
                                class="w-full border-t border-gray-200 dark:border-gray-800"
                            />
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
                            Enter your email address to sign in or create an
                            account
                        </p>

                        <UForm
                            :schema="schema"
                            :state="state"
                            class="space-y-4"
                            @submit="onSubmit"
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
                                color="primary"
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
                </div>
            </div>
        </div>
    </div>
</template>
