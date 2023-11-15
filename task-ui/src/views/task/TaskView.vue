<template>
    <div>
        <nav>
            <router-link :to="{ name: 'login' }" @click="auth.logout()">Log Out</router-link>
            <h1>Tasks</h1>
        </nav>

        <div>
            <button type="button" @click="openAddForm">Add</button>
            <TaskForm v-if="isFormOpen" @cancelForm="closeForm" @add="addTask" />
        </div>

        <ul v-for="task in tasks" :key="task.id">
            <li>
                <div>
                    <h3>{{ task.title }}</h3>
                    <p>{{ task.status }}</p>
                    <p>{{ task.priority }}</p>
                    <p>{{ task.initialDate }}</p>
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
import useErrorHandler from '@/hooks/ErrorHandler';
import ITask from '@/views/task/interfaces/ITask';
import ITaskResponse from '@/views/task/interfaces/ITaskResponse';
import { toISODateFormat } from '@/utils/DateHelper';
import {
    NotificationType,
    notificate,
} from '@/services/notification';
import TaskForm from '@/views/task/components/TaskForm.vue'
import arrayToJson from '@/utils/LinksHelper';
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
                .post(this.links.tasks, task)
                .then(() => {
                    notificate('Task added', NotificationType.SUCCESS);
                    this.closeForm();
                    this.searchTasks();
                })
                .catch((error) => this.errorHandler.handle(error));
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
        const errorHandler = useErrorHandler();
        const tasks = ref<Array<ITask>>()
        const page = ref<Page>();
        const searchTasks = ({
            pageNumber = 0,
            size = 20,
            filter = '',
            field = ''
        }: {
            pageNumber?: number,
            size?: number,
            filter?: string,
            field?: string
        } = {}
        ) => {
            api
                .get(links.tasks, { params: { 'page': pageNumber, 'size': size, [field]: filter } })
                .then((response) => {
                    const tasksData: Array<ITaskResponse> = response.data.content;
                    tasks.value = tasksData.map((task) => ({
                        ...task,
                        initialDate: toISODateFormat(task.initialDate),
                        links: arrayToJson(task.links),
                    }));
                    page.value = response.data.page;
                })
                .catch((error) => errorHandler.handle(error));
        }
        return {
            auth,
            links,
            errorHandler,
            tasks,
            page,
            searchTasks
        };
    },
});
</script>
