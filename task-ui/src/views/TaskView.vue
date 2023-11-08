<template>
    <div>
        <h1>Tasks</h1>
        <router-link :to="{ name: 'login' }" @click="auth.logout()">Log Out</router-link>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { authStore } from '@/stores/auth';
import api from '@/services/api';
import { IErrorResponse } from '@/interfaces/IErrorResponse';
import {
    notificateLoading,
    notificateAndWaitConfirmation,
    NotificationType,
    notificate,
} from '@/services/notification';

export default defineComponent({
    name: 'TaskView',
    setup() {
        const auth = authStore();
        return {
            auth,
        };
    },
    methods: {
    },
});
</script>