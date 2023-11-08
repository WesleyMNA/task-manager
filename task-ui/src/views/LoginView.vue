<template>
    <form @submit.prevent="login">
        <input type="text" name="email" v-model="email" placeholder="Email" />
        <input type="password" name="password" v-model="password" placeholder="Password" />
        <button type="submit">Sing In</button>
    </form>
    <button @click="goToSingUp">Sing Up</button>
</template>

<script lang="ts">
import { authStore } from '@/stores/auth';
import { linksStore } from '@/stores/links';
import { defineComponent, ref } from 'vue';
import api from '@/services/api';
import { IErrorResponse } from '@/interfaces/IErrorResponse';
import {
    notificateLoading,
    notificateAndWaitConfirmation,
    NotificationType,
    notificate,
} from '@/services/notification';

export default defineComponent({
    name: 'LoginView',
    setup() {
        const auth = authStore();
        const links = linksStore();
        const loginLink = ref();
        const refreshLink = ref();
        api
            .get('/')
            .then((response) => {
                const links: Array<Record<string, string>> = response.data.links;
                loginLink.value = links.find((l) => l['rel'] == 'login')!.href;
                refreshLink.value = links.find(
                    (l) => l['rel'] == 'refresh-token'
                )!.href;
            })
            .catch((error) => {
                const errorResponse: IErrorResponse = error.response.data;
                notificateAndWaitConfirmation(
                    errorResponse.message,
                    NotificationType.ERROR
                );
            });
        return {
            auth,
            links,
            loginLink,
            refreshLink,
        };
    },
    data() {
        return {
            email: 'user@email.com',
            password: '12345',
        };
    },
    methods: {
        login() {
            notificateLoading('loding...');
            api
                .post(this.loginLink, {
                    email: this.email,
                    password: this.password,
                })
                .then((response) => {
                    this.auth.login(response.data);
                    this.links.setLoginLinks(response.data.links)
                    notificate('Login successfully', NotificationType.SUCCESS);
                    this.$router.push('/tasks');
                })
                .catch((error) => {
                    const errorResponse: IErrorResponse = error.response.data;
                    notificateAndWaitConfirmation(
                        errorResponse.message,
                        NotificationType.ERROR
                    );
                });
        },
        goToSingUp() {
            this.$router.push('/sing-up');
        }
    },
});
</script>