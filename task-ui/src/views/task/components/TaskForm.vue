<template>
    <teleport to="body">
        <div class="modal">
            <form @submit.prevent="sendRequest">
                <input type="text" v-model="task.title" name="title" placeholder="Title" />
                <button type="submit">Add</button>
            </form>
            <button @click="cancelForm">Cancel</button>
        </div>
    </teleport>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

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
                title: ''
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
