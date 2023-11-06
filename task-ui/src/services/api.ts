import { authStore } from "@/stores/auth";
import axios from "axios";

const auth = authStore()
const api = axios.create({
    baseURL: 'http://localhost:8080/api/task-manager',
    headers: {
        'Content-Type': 'application/json'
    }
})

api.interceptors.request.use(config => {
    if (auth.getJwt)
        config.headers['Authorization'] = auth.getJwt

    return config
})

export default api;

