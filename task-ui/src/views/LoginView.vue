<template>
    <form @submit.prevent="login">
        <input type="text" name="email" v-model="email" placeholder="Email" />
        <input type="password" name="password" v-model="password" placeholder="Password" />
        <button type="submit">Sing In</button>
    </form>
    <button>Sing Up</button>
</template>

<script lang="ts">
import { authStore } from "@/stores/auth";
import { defineComponent, ref } from "vue";
import api from "@/services/api";
import { IErrorResponse } from "@/interfaces/IErrorResponse";
import {
    notificateLoading,
    notificateAndWaitConfirmation,
    NotificationType,
    notificate,
} from "@/services/notification";

export default defineComponent({
    name: "LoginView",
    setup() {
        const auth = authStore();
        const loginUrl = ref();
        const refreshUrl = ref();
        api
            .get('/')
            .then((response) => {
                const links: Array<Record<string, string>> = response.data.links;
                loginUrl.value = links.find((l) => l['rel'] == 'login')!.href;
                refreshUrl.value = links.find(
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
            loginUrl,
            refreshUrl,
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
                .post(this.loginUrl, {
                    email: this.email,
                    password: this.password,
                })
                .then((response) => {
                    this.auth.login(response.data);
                    notificate('Login successfully', NotificationType.SUCCESS);
                    // this.$router.push('/');
                })
                .catch((error) => {
                    const errorResponse: IErrorResponse = error.response.data;
                    notificateAndWaitConfirmation(
                        errorResponse.message,
                        NotificationType.ERROR
                    );
                });
        },
    },
});
</script>