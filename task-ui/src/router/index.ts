import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import { authStore } from "@/stores/auth";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'app',
        component: () => import('@/App.vue'),
        meta: {
            auth: true,
        },
    },
    {
        path: '/tasks',
        name: 'tasks',
        component: () => import('@/views/TaskView.vue'),
        meta: {
            auth: true,
        },
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/LoginView.vue')
    },
    {
        path: '/sing-up',
        name: 'sing-up',
        component: () => import('@/views/SingUpView.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const auth = authStore();
  
    if (to.meta?.auth && auth.shouldLogin)
      next({ name: 'login' });
  
    next();
  });
  

export default router
