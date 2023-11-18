<template>
    <div>
        <header>
            <button type="button" @click="goToTasks">Go to tasks</button>
        </header>
        <form @submit.prevent="editTask">
            <button v-if="!editTaskMode" type="button" @click="editTaskMode = !editTaskMode">Edit</button>
            <input v-if="editTaskMode" type="text" v-model="title" />
            <h3 v-else>{{ title }}</h3>
            <input v-if="editTaskMode" type="text" v-model="description" />
            <p v-else>{{ description }}</p>
            <input v-if="editTaskMode" type="text" v-model="status" />
            <p v-else>{{ status }}</p>
            <input v-if="editTaskMode" type="text" v-model="priority" />
            <p v-else>{{ priority }}</p>
            <input v-if="editTaskMode" type="text" v-model="initialDate" />
            <p v-else>{{ initialDate }}</p>
            <input v-if="editTaskMode" type="text" v-model="finalDate" />
            <p v-else>{{ finalDate }}</p>
            <button v-if="editTaskMode" type="submit">Edit</button>
        </form>

        <h3>Notes</h3>
        <div v-for="note in notes" :key="note.id">
            <p>{{ toHumanDatetimeFormat(note.dateHour) }}</p>
            <p>{{ note.text }}</p>
        </div>
        <form  @submit.prevent="addNote()">
            <input type="text" v-model="noteText" />
            <button type="submit">Add note</button>
        </form>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { linksStore } from '@/stores/links';
import api from '@/services/api';
import ITask from '@/views/task/interfaces/ITask';
import ITaskResponse from '@/views/task/interfaces/ITaskResponse';
import { toISODateFormat, toISODatetimeFormat, toHumanDatetimeFormat } from '@/utils/DateHelper';
import useErrorHandler from '@/hooks/ErrorHandler';
import arrayToJson from '@/utils/LinksHelper';
import INote from './interfaces/INote';
import INoteRequest from './interfaces/INoteRequest';
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
            get(): INote[] {
                return this.task?.notes || [];
            },
            set(value: INote): void {
                if (this.task)
                    this.task.notes?.push(value);
            },
        },
    },
    data() {
        return {
            editTaskMode: false,
            noteText: ''
        }
    },
    methods: {
        addNote() {
            console.log(this.noteText);
            if (this.task === undefined)
                throw new Error();
            
            const note: INoteRequest = {
                taskId: this.task?.id,
                text: this.noteText
            }
            api
                .post(this.links.notes, note)
                .then(() => {
                    notificate('Note added', NotificationType.SUCCESS);
                    this.searchTask();
                })
                .catch((error) => this.errorHandler.handle(error));
        },
        editTask() {
            const link = this.task?.links['update-task']

            if (link === undefined)
                throw new Error();

            api
                .put(link, this.task)
                .then(() => {
                    notificate('Task updated', NotificationType.SUCCESS);
                    this.searchTask();
                    this.editTaskMode = false;
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
            const links = linksStore();
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
                        notes: taskData.notes?.map(note => ({
                            id: note.id,
                            text: note.text,
                            dateHour: toISODatetimeFormat(note.dateHour) || '',
                            links: arrayToJson(note.links),
                        })) || []
                    };
                })
                .catch((error) => errorHandler.handle(error));
        };
        return {
            links,
            errorHandler,
            task,
            searchTask,
            toHumanDatetimeFormat
        };
    }
})
</script>