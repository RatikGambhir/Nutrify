export default defineNuxtRouteMiddleware((to) => {
  console.log('Global auth middleware', to)
})