<template>
    <div>
        <header>
            <button type="button" @click="goToTasks">Go to tasks</button>
            <div>
                <button v-if="!editTaskMode" type="button" class="button"
                    @click="editTaskMode = true">Edit</button>
                <button type="button" class="button red" @click="deleteTask">Delete</button>
            </div>
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
                    <p v-else>Initial date: {{ toHumanDateFormat(initialDate) }}</p>
                    <div class="input-group" v-if="editTaskMode">
                        <label for="finalDate">Final Date</label>
                        <input class="input-field" type="text" v-model="finalDate" name="finalDate" />
                    </div>
                    <p v-else>Final date: {{ toHumanDateFormat(finalDate) }}</p>
                    <div class="form-buttons" v-if="editTaskMode">
                        <button type="submit">Edit</button>
                        <button type="button" @click="editTaskMode = false">Cancel</button>
                    </div>
                </form>

                <div id="notes" class="border">
                    <h3>Notes</h3>
                    <div class="note-card border" v-for="note in notes" :key="note.id">
                        <form @submit.prevent="editNote(note)">
                            <p class="date-hour">{{ toHumanDatetimeFormat(note.dateHour) }}</p>
                            <textarea v-if="note.editMode" type="text" v-model="note.text" />
                            <p v-else>{{ note.text }}</p>

                            <div class="form-buttons" v-if="note.editMode">
                                <button type="submit">Edit</button>
                                <button type="button" @click="note.editMode = false">Cancel</button>
                            </div>
                        </form>
                        <div>
                            <button v-if="!note.editMode" type="button" class="button" @click="note.editMode = true">Edit</button>
                            <button type="button" class="button red" @click="deleteNote(note)">Delete</button>
                        </div>
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
import { defineComponent, ref, Ref } from 'vue';
import { linksStore } from '@/stores/links';
import api from '@/services/api';
import ITask from '@/views/task/interfaces/ITask';
import ITaskResponse from '@/views/task/interfaces/ITaskResponse';
import { toISODateFormat, toISODatetimeFormat, toHumanDatetimeFormat, toHumanDateFormat } from '@/utils/DateHelper';
import useErrorHandler from '@/hooks/ErrorHandler';
import arrayToJson from '@/utils/LinksHelper';
import INote from '@/views/task/interfaces/INote';
import INoteResponse from '@/views/task/interfaces/INoteResponse';
import INoteRequest from '@/views/task/interfaces/INoteRequest';
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
        }
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
        deleteNote(note: INote) {
            const link = note.links['delete-note']
            api
                .delete(link)
                .then(() => {
                    notificate('Note deleted', NotificationType.SUCCESS);
                    this.searchTask();
                })
                .catch((error) => this.errorHandler.handle(error));
        },
        deleteTask() {
            const link = this.task?.links['delete-task'];

            if (link === undefined)
                throw new Error();

            api
                .delete(link)
                .then(() => {
                    notificate('Task deleted', NotificationType.SUCCESS);
                    this.goToTasks();
                })
                .catch((error) => this.errorHandler.handle(error));
        },
        editNote(note: INote) {
            const link = note.links['update-note']
            api
                .put(link, note)
                .then(() => {
                    notificate('Note updated', NotificationType.SUCCESS);
                    this.searchTask();
                    note.editMode = false;
                })
                .catch((error) => this.errorHandler.handle(error));
        },
        editTask() {
            const link = this.task?.links['update-task'];

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
        const notes = ref<INote[]>();
        const links = linksStore();
        const searchTask = () => {
            api
                .get(props.url)
                .then((response) => {
                    const taskData: ITaskResponse = response.data;
                    const links = arrayToJson(taskData.links);
                    const noteUrl = links['task-notes']
                    api
                        .get(noteUrl, { params: { sort: 'dateHour,asc' } })
                        .then((response) => {
                            var notesResponse: Array<INoteResponse> = response.data.content;
                            notes.value = notesResponse.map(note => ({
                                id: note.id,
                                text: note.text,
                                dateHour: toISODatetimeFormat(note.dateHour) || '',
                                editMode: false,
                                links: arrayToJson(note.links),
                            }))
                        })
                        .catch((error) => errorHandler.handle(error));
                    task.value = {
                        ...taskData,
                        initialDate: toISODateFormat(taskData.initialDate),
                        finalDate: toISODateFormat(taskData.finalDate),
                        links: links
                    };
                })
                .catch((error) => errorHandler.handle(error));
        };
        return {
            links,
            errorHandler,
            task,
            notes,
            searchTask,
            toHumanDatetimeFormat,
            toHumanDateFormat
        };
    }
})
</script>