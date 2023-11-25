import { authStore } from "@/stores/auth";
import axios from "axios";
import { linksStore } from '@/stores/links';
import {
    notificate,
    NotificationType,
} from '@/services/notification';
const auth = authStore()
const links = linksStore();
const api = axios.create({
    baseURL: 'http://localhost:8080/api/task-manager',
    headers: {
        'Content-Type': 'application/json'
    }
})

const useRefreshTokenIfNeeded = () => {
    const exp = auth.jwtData?.exp

    if (exp !== undefined) {
        const now = new Date();

        if (new Date(exp * 1000) < now) {
            api
                .post(links.refresh)
                .then((response) => auth.login(response.data))
                .catch((error) => {
                    if (error.response.status === 401) {
                        auth.logout()
                        notificate('session expired', NotificationType.WARNING)
                    }
                });
        }
    }
};

api.interceptors.request.use(config => {
    if (auth.shouldLogin())
        return config;

    if (config.url?.endsWith('/refresh')) {
        config.headers['X-Refresh'] = auth.getRefreshToken;
        return config
    }

    useRefreshTokenIfNeeded();
    config.headers['Authorization'] = auth.getJwt
    return config
})

export default api;

