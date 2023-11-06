import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/LoginView.vue')
    },
    {
        path: '/sing-up',
        name: 'Sing Up',
        component: () => import('../views/SingUp.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
