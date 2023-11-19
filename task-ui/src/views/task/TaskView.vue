<template>
    <div>
        <div id="add-button">
            <button class="button green" type="button" @click="openAddForm">Add</button>
            <TaskForm v-if="isFormOpen" @cancelForm="closeForm" @add="addTask" />
        </div>

        <div id="tasks-card">
            <div class="tasks-list">
                <h2>To Do</h2>
                <ul v-for="task in tasksToDo" :key="task.id">
                    <TaskCard @click="openInfoViewer(task)" :task="task" />
                </ul>
            </div>
            <div class="tasks-list">
                <h2>Doing</h2>
                <ul v-for="task in tasksDoing" :key="task.id">
                    <TaskCard @click="openInfoViewer(task)" :task="task" />
                </ul>
            </div>
            <div class="tasks-list">
                <h2>Pending</h2>
                <ul v-for="task in tasksPending" :key="task.id">
                    <TaskCard @click="openInfoViewer(task)" :task="task" />
                </ul>
            </div>
            <div class="tasks-list">
                <h2>Finished</h2>
                <ul v-for="task in tasksFinished" :key="task.id">
                    <TaskCard @click="openInfoViewer(task)" :task="task" />
                </ul>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import './TaskView.scss';
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
import { TaskStatus } from './enums';
import TaskCard from './components/TaskCard.vue';


export default defineComponent({
    name: 'TaskView',
    components: {
        TaskForm,
        TaskCard,

    },
    computed: {
        tasksToDo(): Array<ITask> | undefined {
            return this.filterByStatus(TaskStatus.TO_DO);
        },
        tasksDoing(): Array<ITask> | undefined {
            return this.filterByStatus(TaskStatus.DOING);
        },
        tasksPending(): Array<ITask> | undefined {
            return this.filterByStatus(TaskStatus.PENDING);
        },
        tasksFinished(): Array<ITask> | undefined {
            return this.filterByStatus(TaskStatus.FINISHED);
        }
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
        filterByStatus(status: string): Array<ITask> | undefined {
            return this.tasks?.filter((task: ITask) => task.status == status);
        },
        openAddForm() {
            this.isFormOpen = true;
        },
        openInfoViewer(task: ITask) {
            this.$router.push({ name: 'task-info', params: { url: task.links['detail-task'] } });
        },
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
                        notes: []
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
