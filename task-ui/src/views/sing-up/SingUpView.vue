<template>
    <div class="card">
        <header>
            <h1>Create an account</h1>
            <img src="@/assets/logo.png" />
        </header>
        <form class="form" @submit.prevent="singUp">
            <input class="input-field" type="text" name="name" v-model="name" placeholder="Name" />
            <input class="input-field" type="text" name="email" v-model="email" placeholder="Email" />
            <input class="input-field" type="password" name="password" v-model="password" placeholder="Password" />
            <input class="input-field" type="password" name="confirm-password" v-model="confirmPassword" placeholder="Confirm Password" />
            <div>
                <button class="button green" type="submit">Sing Up</button>
                <button class="button blue" type="button" @click="goToLogin">Already have an account?</button>
            </div>
        </form>
    </div>
</template>

<script lang="ts">
import './SingUpView.scss';
import { defineComponent, ref } from 'vue';
import api from '@/services/api';
import {
    notificateLoading,
    NotificationType,
    notificate,
} from '@/services/notification';
import useErrorHandler from '@/hooks/ErrorHandler';

export default defineComponent({
    name: 'SingUpView',
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
            .catch((error) => errorHandler.handle(error));
        const errorHandler = useErrorHandler();
        return {
            singUpUrl,
            errorHandler
        };
    },
    data() {
        return {
            name: '',
            email: '',
            password: '',
            confirmPassword: '',
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
                .catch((error) => this.errorHandler.handle(error));
        },
        goToLogin() {
            this.$router.push('/login');
        }
    },
});
</script>
