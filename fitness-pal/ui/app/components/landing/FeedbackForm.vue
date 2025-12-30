<script setup lang="ts">
import * as z from 'zod'
import type { FormSubmitEvent } from '@nuxt/ui'
import { useIntersectionObserver } from '@vueuse/core'

const toast = useToast()

const schema = z.object({
  name: z.string().min(2, 'Name must be at least 2 characters'),
  email: z.string().email('Invalid email address'),
  message: z.string().min(10, 'Message must be at least 10 characters')
})

type Schema = z.output<typeof schema>

const state = reactive({
  name: undefined,
  email: undefined,
  message: undefined
})

const loading = ref(false)
const formRef = ref<HTMLElement | null>(null)
const isVisible = ref(false)

useIntersectionObserver(
  formRef,
  ([{ isIntersecting }]) => {
    if (isIntersecting && !isVisible.value) {
      isVisible.value = true
    }
  },
  { threshold: 0.1, rootMargin: '0px 0px -100px 0px' }
)

async function onSubmit(event: FormSubmitEvent<Schema>) {
  loading.value = true

  setTimeout(() => {
    toast.add({
      title: 'Feedback Received!',
      description: 'Thank you for your feedback. We\'ll get back to you soon.',
      color: 'primary',
      timeout: 5000
    })

    state.name = undefined
    state.email = undefined
    state.message = undefined

    loading.value = false
  }, 500)
}
</script>

<template>
  <div
    ref="formRef"
    class="w-full max-w-2xl mx-auto transition-all duration-1000 ease-out"
    :class="{
      'opacity-0 translate-y-8': !isVisible,
      'opacity-100 translate-y-0': isVisible
    }"
  >
    <div class="text-center mb-8">
      <h2 class="text-4xl font-bold text-gray-900 mb-4">
        Share Your Feedback
      </h2>
      <p class="text-lg text-gray-600">
        Have questions or suggestions? We'd love to hear from you.
      </p>
    </div>

    <UCard class="shadow-xl">
      <UForm
        :schema="schema"
        :state="state"
        class="space-y-6"
        @submit="onSubmit"
      >
        <UFormField name="name" label="Name">
          <UInput
            v-model="state.name"
            type="text"
            placeholder="Your name"
            size="lg"
            icon="i-lucide-user"
          />
        </UFormField>

        <UFormField name="email" label="Email">
          <UInput
            v-model="state.email"
            type="email"
            placeholder="your.email@example.com"
            size="lg"
            icon="i-lucide-mail"
          />
        </UFormField>

        <UFormField name="message" label="Message">
          <UTextarea
            v-model="state.message"
            placeholder="Tell us what you think..."
            :rows="5"
            size="lg"
          />
        </UFormField>

        <UButton
          type="submit"
          size="lg"
          color="primary"
          block
          :loading="loading"
          class="font-semibold bg-gray-900 hover:bg-gray-800"
        >
          Send Feedback
        </UButton>
      </UForm>
    </UCard>
  </div>
</template>
