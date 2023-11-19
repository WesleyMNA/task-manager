<template>
    <nav id="top-bar">
        <div>
            <img src="@/assets/logo.png" />
            <h1>Tasks Manager</h1>
        </div>
        <router-link :to="{ name: 'login' }" @click="auth.logout()">
            <button class="button red" type="button">Log Out</button>
        </router-link>
    </nav>
</template>

<script setup lang="ts">
import './TopBar.scss'
import { authStore } from '@/stores/auth';

const auth = authStore();
</script>