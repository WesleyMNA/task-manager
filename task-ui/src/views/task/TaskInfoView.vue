<template>
    <div>
        <header>
            <button type="button" @click="goToTasks">Go to tasks</button>
            <button v-if="!editTaskMode" type="button" @click="editTaskMode = !editTaskMode">Edit</button>
        </header>

        <div id="task-info" class="border">
            <div>
                <form id="task-form" class="border" @submit.prevent="editTask">
                    <div class="input-group" v-if="editTaskMode">
                        <label for="title">Title</label>
                        <input class="input-field" type="text" v-model="title" name="title" />
                    </div>
                    <h3 v-else>{{ title }}</h3>
                    <div class="input-group" v-if="editTaskMode">
                        <label for="description">Description</label>
                        <input class="input-field" type="text" v-model="description" name="description" />
                    </div>
                    <p v-else>{{ description }}</p>
                    <div class="input-group" v-if="editTaskMode">
                        <label for="priority">Priority</label>
                        <select class="input-field" v-model="priority">
                            <option value="LOW">Low</option>
                            <option value="NORMAL">Normal</option>
                            <option value="HIGH">High</option>
                            <option value="URGENT">Urgent</option>
                        </select>
                    </div>
                    <p v-else>Priority: {{ priority }}</p>
                    <div class="input-group" v-if="editTaskMode">
                        <label for="status">Status</label>
                        <select class="input-field" v-model="status">
                            <option value="TO_DO">To do</option>
                            <option value="DOING">Doing</option>
                            <option value="PENDING">Pending</option>
                            <option value="FINISHED">Finished</option>
                        </select>
                    </div>
                    <p v-else>Status: {{ status }}</p>
                    <div class="input-group" v-if="editTaskMode">
                        <label for="initialDate">Initial Date</label>
                        <input class="input-field" type="text" v-model="initialDate" name="initialDate" />
                    </div>
                    <p v-else>Initial date: {{ initialDate }}</p>
                    <div class="input-group" v-if="editTaskMode">
                        <label for="finalDate">Final Date</label>
                        <input class="input-field" type="text" v-model="finalDate" name="finalDate" />
                    </div>
                    <p v-else>Final date: {{ finalDate }}</p>
                    <div class="form-buttons" v-if="editTaskMode">
                        <button type="submit">Edit</button>
                        <button type="button" @click="editTaskMode = !editTaskMode">Cancel</button>
                    </div>
                </form>

                <div id="notes" class="border">
                    <h3>Notes</h3>
                    <div class="border" v-for="note in notes" :key="note.id">
                        <p id="date-hour">{{ toHumanDatetimeFormat(note.dateHour) }}</p>
                        <p>{{ note.text }}</p>
                    </div>

                    <form class="border" @submit.prevent="addNote()">
                        <textarea type="text" v-model="noteText" />
                        <button type="submit">Add note</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import './TaskInfoView.scss';
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
                    this.noteText = '';
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