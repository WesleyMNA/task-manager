<template>
    <li>
        <div>
            <h3>{{ task.title }}</h3>
            <p>{{ task.priority }}</p>
            <p>{{ task.initialDate }}</p>
        </div>
    </li>
</template>

<script lang="ts">
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