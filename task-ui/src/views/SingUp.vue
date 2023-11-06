<template>
    <form @submit.prevent="singUp">
        <input type="text" name="name" v-model="name" placeholder="Name" />
        <input type="text" name="email" v-model="email" placeholder="Email" />
        <input type="password" name="password" v-model="password" placeholder="Password" />
        <input type="password" name="confirm-password" v-model="confirmPassword" placeholder="Confirm Password" />
        <button type="submit">Sing Up</button>
    </form>
    <button @click="goToLogin">Already have an account?</button>
</template>

<script lang="ts">
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
        const singUpUrl = ref();
        api
            .get('/')
            .then((response) => {
                const links: Array<Record<string, string>> = response.data.links;
                singUpUrl.value = links.find(
                    (l) => l['rel'] == 'register'
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
            singUpUrl
        };
    },
    data() {
        return {
            name: "Test",
            email: "test@email.com",
            password: "12345",
            confirmPassword: "12345",
        };
    },
    methods: {
        singUp() {
            notificateLoading('loding...');
            api
                .post(this.singUpUrl, {
                    name: this.name,
                    email: this.email,
                    password: this.password,
                    confirmPassword: this.confirmPassword,
                })
                .then(() => {
                    notificate('Account registered successfully', NotificationType.SUCCESS);
                    this.goToLogin();
                })
                .catch((error) => {
                    const errorResponse: IErrorResponse = error.response.data;
                    notificateAndWaitConfirmation(
                        errorResponse.message,
                        NotificationType.ERROR
                    );
                });
        },
        goToLogin() {
            this.$router.push('/login');
        }
    },
});
</script>
