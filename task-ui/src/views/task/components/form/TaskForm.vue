<template>
    <teleport to="body">
        <div class="modal">
            <form id="add-form" @submit.prevent="sendRequest">
                <input class="input-field" type="text" v-model="task.title" name="title" placeholder="Title" />
                <input class="input-field" type="text" v-model="task.description" name="description"
                    placeholder="Description" />
                <select class="input-field" v-model="task.priority">
                    <option value="LOW">Low</option>
                    <option value="NORMAL">Normal</option>
                    <option value="HIGH">High</option>
                    <option value="URGENT">Urgent</option>
                </select>
                <select class="input-field" v-model="task.status">
                    <option value="TO_DO">To do</option>
                    <option value="DOING">Doing</option>
                    <option value="PENDING">Pending</option>
                    <option value="FINISHED">Finished</option>
                </select>
                <input class="input-field" type="date" v-model="task.initialDate" name="initial-date"
                    placeholder="Initial Date" />
                <input class="input-field" type="date" v-model="task.finalDate" name="final-date"
                    placeholder="Final Date" />
                <div class="form-buttons">
                    <button class="button" type="submit">Add</button>
                    <button class="button red" type="button" @click="cancelForm">Cancel</button>
                </div>
            </form>
        </div>
    </teleport>
</template>

<script lang="ts">
import './TaskForm.scss';
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
                initialDate: null,
                finalDate: null,
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
