<template>
    <div>
        <h3>{{ task?.title }}</h3>
        <p>{{ task?.description }}</p>
        <p>{{ task?.status }}</p>
        <p>{{ task?.priority }}</p>
        <p>{{ task?.initialDate }}</p>
        <p>{{ task?.finalDate }}</p>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import api from '@/services/api';
import ITask from '@/views/task/interfaces/ITask';
import ITaskResponse from '@/views/task/interfaces/ITaskResponse';
import { toISODateFormat } from '@/utils/DateHelper';
import useErrorHandler from '@/hooks/ErrorHandler';
import arrayToJson from '@/utils/LinksHelper';

export default defineComponent({
    name: 'TaskInfoView',
    props: {
        url: {
            type: String,
            required: true
        }
    },
    setup(props) {
        const errorHandler = useErrorHandler();
        const task = ref<ITask>();

        onMounted(() => {
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
        });

        return {
            errorHandler,
            task
        };
    }
})
</script>