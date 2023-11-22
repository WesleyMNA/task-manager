<template>
    <li id="card">
        <div>
            <h3>{{ task.title }}</h3>
            <p>{{ task.priority }}</p>
            <p>{{ task.initialDate }}</p>
        </div>
    </li>
</template>

<script lang="ts">
import './TaskCard.scss';
import { defineComponent } from 'vue';
import ITask from '@/views/task/interfaces/ITask';

export default defineComponent({
    name: 'TaskCard',
    props: {
        task: {
            type: Object as () => ITask,
            required: true
        }
    }
})
</script>