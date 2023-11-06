import { createRouter, createWebHistory } from 'vue-router'
import GalleryView from '../pages/gallery.vue'
import LoginView from '../pages/login.vue'
import RegisterView from '../pages/register.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: GalleryView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
  ]
})

export default router
