<template>
    <div class="card">
        <header>
            <h1>Welcome to Tasks Manager</h1>
            <img src="@/assets/logo.png" />
        </header>
        <form class="form" @submit.prevent="login">
            <input class="input-field" type="text" name="email" v-model="email" placeholder="Email" />
            <input class="input-field" type="password" name="password" v-model="password" placeholder="Password" />

            <div>
                <button class="button green" type="submit">Sing In</button>
                <button class="button" type="button" @click="goToSingUp">Sing Up</button>
            </div>
        </form>
    </div>
</template>

<script lang="ts">
import './LoginView.scss';
import { authStore } from '@/stores/auth';
import { linksStore } from '@/stores/links';
import { defineComponent, ref } from 'vue';
import api from '@/services/api';
import {
    notificateLoading,
    NotificationType,
    notificate,
} from '@/services/notification';
import useErrorHandler from '@/hooks/ErrorHandler';

export default defineComponent({
    name: 'LoginView',
    setup() {
        const auth = authStore();
        const links = linksStore();
        const loginLink = ref();
        api
            .get('/')
            .then((response) => {
                const links: Array<Record<string, string>> = response.data.links;
                loginLink.value = links.find((l) => l['rel'] == 'login')!.href;
            })
            .catch((error) => errorHandler.handle(error));
        const errorHandler = useErrorHandler();
        return {
            auth,
            links,
            loginLink,
            errorHandler
        };
    },
    data() {
        return {
            email: '',
            password: '',
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
                .catch((error) => this.errorHandler.handle(error));
        },
        goToSingUp() {
            this.$router.push('/sing-up');
        }
    },
});
</script>