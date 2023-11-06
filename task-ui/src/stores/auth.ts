import { ref, Ref } from 'vue';
import { defineStore } from 'pinia';
import { IJwtData } from '@/interfaces/IJwtData';
import { jwtDecode } from 'jwt-decode';

interface AuthState {
  jwt: Ref<string | null>;
  jwtData: Ref<IJwtData | null>;
}

export const authStore = defineStore('auth', {
  state: (): AuthState => {
    const jwt = ref(localStorage.getItem('jwt'));
    const localStorageJwtData = localStorage.getItem('jwtData');
    const jwtData =
      localStorageJwtData != null ? JSON.parse(localStorageJwtData) : null;
    return {
      jwt,
      jwtData,
    };
  },
  actions: {
    login(data: any): void {
      this.jwt = `${data['type']} ${data['jwt']}`;
      this.jwtData = jwtDecode(data['jwt']);
      localStorage.setItem('jwt', this.jwt);
      localStorage.setItem('jwtData', JSON.stringify(jwtDecode(this.jwt)));
    },
    logout(): void {
      this.jwt = null;
      this.jwtData = null;
      localStorage.clear();
    },
  },
  getters: {
    getJwt(): string | null {
      return this.jwt;
    },
    shouldLogin(): boolean {
      return this.jwt == null;
    },
    userIsLogged(): boolean {
      return this.jwt != null;
    },
  },
});
