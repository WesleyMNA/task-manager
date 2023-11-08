import { Ref, ref } from 'vue';
import { defineStore } from 'pinia';
import { ILinks } from '@/interfaces/ILinks';

interface LinksState {
    links: Ref<ILinks>;
}

export const linksStore = defineStore('links', {
    state: (): LinksState => {
        const linksData = localStorage.getItem('links');
        const links = linksData != null ? JSON.parse(linksData) : {};
        return {
            links,
        };
    },
    actions: {
        setLoginLinks(links: Array<Record<string, string>>): void {
            this.links.findClients = links.find((l) => l['rel'] == 'find-clients')!.href;
            this.links.createClients = links.find((l) => l['rel'] == 'create-client')!.href;
            this.links.findTasks = links.find((l) => l['rel'] == 'find-tasks')!.href;
            this.links.createTasks = links.find((l) => l['rel'] == 'create-task')!.href;
            this.links.createNotes = links.find((l) => l['rel'] == 'create-note')!.href;
            localStorage.setItem('links', JSON.stringify(this.links));
        },
    },
    getters: {
        getFindClients(): string {
            return this.links.findClients;
        },
        getCreateClients(): string {
            return this.links.createClients;
        },
        getFindTasks(): string {
            return this.links.findTasks;
        },
        getCreateTasks(): string {
            return this.links.createTasks;
        },
        getCreateNotes(): string {
            return this.links.createNotes;
        },
    },
});
