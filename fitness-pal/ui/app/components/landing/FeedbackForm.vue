<script setup lang="ts">
import * as z from 'zod'
import { toTypedSchema } from '@vee-validate/zod'
import { useForm, Field as FormField } from 'vee-validate'
import { useIntersectionObserver } from '@vueuse/core'
import Card from '~/components/ui/card/Card.vue'
import CardContent from '~/components/ui/card/CardContent.vue'
import Button from '~/components/ui/button/Button.vue'
import Input from '~/components/ui/input/Input.vue'
import Textarea from '~/components/ui/textarea/Textarea.vue'
import FormItem from '~/components/ui/form/FormItem.vue'
import FormLabel from '~/components/ui/form/FormLabel.vue'
import FormControl from '~/components/ui/form/FormControl.vue'
import FormMessage from '~/components/ui/form/FormMessage.vue'
import { User, Mail, Loader2 } from 'lucide-vue-next'

// Toast functionality removed - will need to implement a custom solution
// const toast = useToast()

const schema = toTypedSchema(z.object({
  name: z.string().min(2, 'Name must be at least 2 characters'),
  email: z.string().email('Invalid email address'),
  message: z.string().min(10, 'Message must be at least 10 characters')
}))

const { handleSubmit, resetForm } = useForm({
  validationSchema: schema,
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

const onSubmit = handleSubmit((values) => {
  loading.value = true

  setTimeout(() => {
    // TODO: Implement toast notification
    console.log('Feedback submitted:', values)
    alert('Feedback Received! Thank you for your feedback.')

    resetForm()
    loading.value = false
  }, 500)
})
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

    <Card class="shadow-xl">
      <CardContent class="p-6">
        <form class="space-y-6" @submit="onSubmit">
          <FormField v-slot="{ componentField }" name="name">
            <FormItem>
              <FormLabel>Name</FormLabel>
              <FormControl>
                <div class="relative">
                  <User class="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                  <Input
                    v-bind="componentField"
                    type="text"
                    placeholder="Your name"
                    class="h-11 pl-10"
                  />
                </div>
              </FormControl>
              <FormMessage />
            </FormItem>
          </FormField>

          <FormField v-slot="{ componentField }" name="email">
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <div class="relative">
                  <Mail class="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                  <Input
                    v-bind="componentField"
                    type="email"
                    placeholder="your.email@example.com"
                    class="h-11 pl-10"
                  />
                </div>
              </FormControl>
              <FormMessage />
            </FormItem>
          </FormField>

          <FormField v-slot="{ componentField }" name="message">
            <FormItem>
              <FormLabel>Message</FormLabel>
              <FormControl>
                <Textarea
                  v-bind="componentField"
                  placeholder="Tell us what you think..."
                  :rows="5"
                  class="resize-none"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          </FormField>

          <Button
            type="submit"
            size="lg"
            class="w-full font-semibold bg-gray-900 hover:bg-gray-800"
            :disabled="loading"
          >
            <Loader2 v-if="loading" class="mr-2 h-4 w-4 animate-spin" />
            Send Feedback
          </Button>
        </form>
      </CardContent>
    </Card>
  </div>
</template>
