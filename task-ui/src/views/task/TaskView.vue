<template>
    <div>
        <nav>
            <router-link :to="{ name: 'login' }" @click="auth.logout()">Log Out</router-link>
            <h1>Tasks</h1>
        </nav>

        <div>
            <button type="button" @click="openAddForm" @cancelForm="closeForm" @add="addTask">Add</button>
            <TaskForm v-if="isFormOpen" />
        </div>

        <ul v-for="task in tasks" :key="task.id">
            <li>
                <div>
                    <h6>{{ task.title }}</h6>
                    <span>{{ task.status }}</span>
                    <span>{{ task.priority }}</span>
                </div>
            </li>

        </ul>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { linksStore } from '@/stores/links';
import { authStore } from '@/stores/auth';
import api from '@/services/api';
import { IErrorResponse } from '@/interfaces/IErrorResponse';
import ITask from '@/views/task/interfaces/ITask';
import ITaskResponse from '@/views/task/interfaces/ITaskResponse';
import { toISODatetimeFormat } from '@/utils/date_helper';
import {
    notificateLoading,
    notificateAndWaitConfirmation,
    NotificationType,
    notificate,
} from '@/services/notification';
import TaskForm from '@/views/task/components/TaskForm.vue'
import arrayToJson from '@/utils/links_helper';
import Page from '@/interfaces/Page'

export default defineComponent({
    name: 'TaskView',
    components: {
        TaskForm,
    },
    data() {
        return {
            isFormOpen: false,
        }
    },
    methods: {
        addTask(task: ITask) {
            api
                .post(this.links.getTasks, task)
                .then(() => {
                    notificate('Task added', NotificationType.SUCCESS);
                    this.closeForm();
                    this.searchTasks();
                })
                .catch((error) => {
                    const errorResponse: IErrorResponse = error.response.data;
                    notificateAndWaitConfirmation(
                        errorResponse.message,
                        NotificationType.ERROR
                    );
                });
        },
        closeForm() {
            this.isFormOpen = false;
        },
        openAddForm() {
            this.isFormOpen = true;
        }
    },
    mounted() {
        this.searchTasks();
    },
    setup() {
        const auth = authStore();
        const links = linksStore();
        const tasks = ref<Array<ITask>>()
        const page = ref<Page>();
        const searchTasks = (
            { pageNumber = 0, size = 20, filter = '', field = '' }: { pageNumber?: number, size?: number, filter?: string, field?: string } = {}
        ) => {
            api
                .get(links.getTasks, { params: { 'page': pageNumber, 'size': size, [field]: filter } })
                .then((response) => {
                    const tasksData: Array<ITaskResponse> = response.data.content;
                    tasks.value = tasksData.map((task) => ({
                        ...task,
                        initialDate: toISODatetimeFormat(task.initialDate),
                        finalDate: toISODatetimeFormat(task.finalDate),
                        links: arrayToJson(task.links),
                    }));
                    page.value = response.data.page;
                })
                .catch((error) => {
                    const errorResponse: IErrorResponse = error.response.data;
                    notificateAndWaitConfirmation(
                        errorResponse.message,
                        NotificationType.ERROR
                    );
                });
        }
        return {
            auth,
            links,
            tasks,
            page,
            searchTasks
        };
    },
});
</script>
