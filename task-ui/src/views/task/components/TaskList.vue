<template>
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
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import ITask from '@/views/task/interfaces/ITask';
import { TaskStatus } from '../enums';

export default defineComponent({
    name: 'TaskList',
    emits: [],
    data() {
        const tasks = ref<Array<ITask>>([])
        return {
            tasks,
        }
    },
    mounted() {
        console.log(this.tasksAll)
        this.tasks = this.tasksAll.filter(task => task.status == this.taskStatus)
    },
    props: {
        tasksAll: {
            type: Array as () => Array<ITask>,
            required: true,
        },
        taskStatus: {
            type: String,
            required: true,
            validator: (status: string) => {
                return Object.values(TaskStatus).includes(status);
            }
        }
    },
});
</script>