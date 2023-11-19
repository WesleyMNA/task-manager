<template>
    <div>
        <TopBar v-if="auth.userIsLogged()" />
        <main>
            <router-view />
        </main>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { authStore } from '@/stores/auth';
import TopBar from '@/components/top-bar/TopBar.vue';

export default defineComponent({
    name: 'App',
    setup() {
        const auth = authStore();
        return {
            auth,
        };
    },
    components: {
        TopBar
    },
});
</script>
