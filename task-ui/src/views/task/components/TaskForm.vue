<template>
    <teleport to="body">
        <div class="modal">
            <form @submit.prevent="sendRequest">
                <input type="text" v-model="task.title" name="title" placeholder="Title" />
                <input type="text" v-model="task.description" name="description" placeholder="Description" />
                <select v-model="task.priority">
                    <option value="LOW">Low</option>
                    <option value="NORMAL">Normal</option>
                    <option value="HIGH">High</option>
                    <option value="URGENT">Urgent</option>
                </select>
                <select v-model="task.status">
                    <option value="TO_DO">To do</option>
                    <option value="DOING">Doing</option>
                    <option value="PENDING">Pending</option>
                    <option value="FINISHED">Finished</option>
                </select>
                <input type="date" v-model="task.initialDate" name="initial-date" placeholder="Initial Date" />
                <input type="date" v-model="task.finalDate" name="final-date" placeholder="Final Date" />
                <button type="submit">Add</button>
            </form>
            <button @click="cancelForm">Cancel</button>
        </div>
    </teleport>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { toISODateFormat } from '@/utils/DateHelper';

const ADD_EVENT = 'add';
const CANCEL_FORM_EVENT = 'cancelForm';

export default defineComponent({
    name: 'TaskForm',
    emits: [
        ADD_EVENT,
        CANCEL_FORM_EVENT
    ],
    data() {
        return {
            task: {
                title: '',
                description: null,
                priority: 'LOW',
                status: 'TO_DO',
                initialDate: toISODateFormat(new Date()),
                finalDate: toISODateFormat(new Date()),
            },
        }
    },
    methods: {
        cancelForm() {
            this.$emit(CANCEL_FORM_EVENT);
        },
        sendRequest() {
            this.$emit(ADD_EVENT, this.task);
        },
    },
});
</script>

<style scoped>
.modal {
    position: absolute;
    top: 0;
    left: 0;
    background-color: grey;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal>form {
    background-color: white;
    padding: 50px;
    border-radius: 10px;
}
</style>
