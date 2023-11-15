import { ref, Ref } from 'vue';
import { defineStore } from 'pinia';
import { IJwtData } from '@/interfaces/IJwtData';
import { jwtDecode } from 'jwt-decode';

interface AuthState {
    jwt: Ref<string | null>;
    jwtData: Ref<IJwtData | null>;
    refreshToken: Ref<string | null>;
}

export const authStore = defineStore('auth', {
    state: (): AuthState => {
        const jwt = ref(localStorage.getItem('jwt'));
        const localStorageJwtData = localStorage.getItem('jwtData');
        const jwtData =
            localStorageJwtData != null ? JSON.parse(localStorageJwtData) : null;
        const refreshToken = ref(localStorage.getItem('refreshToken'));
        return {
            jwt,
            jwtData,
            refreshToken
        };
    },
    actions: {
        login(data: any): void {
            this.jwt = `${data['type']} ${data['jwt']}`;
            this.jwtData = jwtDecode(data['jwt']);
            this.refreshToken = data['refreshToken'];
            localStorage.setItem('jwt', this.jwt);
            localStorage.setItem('jwtData', JSON.stringify(jwtDecode(this.jwt)));

            if (this.refreshToken !== null)
                localStorage.setItem('refreshToken', this.refreshToken);
        },
        logout(): void {
            this.jwt = null;
            this.jwtData = null;
            localStorage.clear();
        },
        shouldLogin(): boolean {
            return this.jwt == null;
        },
        userIsLogged(): boolean {
            return this.jwt != null;
        },
    },
    getters: {
        getJwt(): string | null {
            return this.jwt;
        },
        getJwtData(): IJwtData | null {
            return this.jwtData;
        },
        getRefreshToken(): string | null {
            return this.refreshToken;
        }
    },
});
