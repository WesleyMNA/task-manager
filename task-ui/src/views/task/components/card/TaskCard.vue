<template>
    <li id="card">
        <div>
            <h3>{{ task.title }}</h3>
            <div id="info">
                <p>{{ task.initialDate }}</p>
                <p id="priority" :class="getPriorityColor">{{ task.priority }}</p>
            </div>
        </div>
    </li>
</template>

<script lang="ts">
import './TaskCard.scss';
import { defineComponent } from 'vue';
import ITask from '@/views/task/interfaces/ITask';
import { TaskPriority } from '@/views/task/enums';

export default defineComponent({
    name: 'TaskCard',
    computed: {
        getPriorityColor(): string {
            if (this.task.priority === TaskPriority.LOW)
                return 'green'

            if (this.task.priority === TaskPriority.NORMAL)
                return 'yellow'

            if (this.task.priority === TaskPriority.HIGH)
                return 'orange'

            return 'red'
        }
    },
    props: {
        task: {
            type: Object as () => ITask,
            required: true
        }
    }
})
</script>