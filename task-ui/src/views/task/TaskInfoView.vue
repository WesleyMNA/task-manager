<template>
    <div>
        <header>
            <button type="button" @click="goToTasks">Go to tasks</button>
        </header>
        <form @submit.prevent="editTask">
            <button v-if="!editMode" type="button" @click="editMode = !editMode">Edit</button>
            <input v-if="editMode" type="text" v-model="title" />
            <h3 v-else>{{ title }}</h3>
            <input v-if="editMode" type="text" v-model="description" />
            <p v-else>{{ description }}</p>
            <input v-if="editMode" type="text" v-model="status" />
            <p v-else>{{ status }}</p>
            <input v-if="editMode" type="text" v-model="priority" />
            <p v-else>{{ priority }}</p>
            <input v-if="editMode" type="text" v-model="initialDate" />
            <p v-else>{{ initialDate }}</p>
            <input v-if="editMode" type="text" v-model="finalDate" />
            <p v-else>{{ finalDate }}</p>
            <button type="submit">Edit</button>
        </form>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import api from '@/services/api';
import ITask from '@/views/task/interfaces/ITask';
import ITaskResponse from '@/views/task/interfaces/ITaskResponse';
import { toISODateFormat } from '@/utils/DateHelper';
import useErrorHandler from '@/hooks/ErrorHandler';
import arrayToJson from '@/utils/LinksHelper';
import INote from './interfaces/INote';
import {
    NotificationType,
    notificate,
} from '@/services/notification';

export default defineComponent({
    name: 'TaskInfoView',
    computed: {
        title: {
            get(): string {
                return this.task?.title || '';
            },
            set(value: string): void {
                if (this.task) {
                    this.task.title = value;
                }
            },
        },
        description: {
            get(): string {
                return this.task?.description || '';
            },
            set(value: string): void {
                if (this.task) {
                    this.task.description = value;
                }
            },
        },
        status: {
            get(): string {
                return this.task?.status || '';
            },
            set(value: string): void {
                if (this.task) {
                    this.task.status = value;
                }
            },
        },
        priority: {
            get(): string {
                return this.task?.priority || '';
            },
            set(value: string): void {
                if (this.task) {
                    this.task.priority = value;
                }
            },
        },
        initialDate: {
            get(): string {
                return this.task?.initialDate || '';
            },
            set(value: string): void {
                if (this.task) {
                    this.task.initialDate = value;
                }
            },
        },
        finalDate: {
            get(): string {
                return this.task?.finalDate || '';
            },
            set(value: string): void {
                if (this.task) {
                    this.task.finalDate = value;
                }
            },
        },
        notes: {
            get(): INote[] | null {
                return this.task?.notes || null;
            },
            set(value: INote): void {
                if (this.task)
                    this.task.notes?.push(value);
            },
        },
    },
    data() {
        return {
            editMode: false
        }
    },
    methods: {
        editTask() {
            const link = this.task?.links['update-task']

            if (link === undefined)
                throw new Error();

            api
                .put(link, this.task)
                .then(() => {
                    notificate('Task updated', NotificationType.SUCCESS);
                    this.searchTask();
                    this.editMode = false;
                })
                .catch((error) => this.errorHandler.handle(error));
        },
        goToTasks() {
            this.$router.push('/tasks');
        }
    },
    props: {
        url: {
            type: String,
            required: true
        }
    },
    mounted() {
        this.searchTask();
    },
    setup(props) {
        const errorHandler = useErrorHandler();
        const task = ref<ITask>();
        const searchTask = () => {
            api
                .get(props.url)
                .then((response) => {
                    const taskData: ITaskResponse = response.data;
                    task.value = {
                        ...taskData,
                        initialDate: toISODateFormat(taskData.initialDate),
                        finalDate: toISODateFormat(taskData.finalDate),
                        links: arrayToJson(taskData.links),
                    };
                })
                .catch((error) => errorHandler.handle(error));
        };
        return {
            errorHandler,
            task,
            searchTask
        };
    }
})
</script>