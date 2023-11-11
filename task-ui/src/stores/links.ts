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
            this.links.clients = links.find((l) => l['rel'] == 'clients')!.href;
            this.links.tasks = links.find((l) => l['rel'] == 'tasks')!.href;
            this.links.notes = links.find((l) => l['rel'] == 'notes')!.href;
            localStorage.setItem('links', JSON.stringify(this.links));
        },
    },
    getters: {
        getClients(): string {
            return this.links.clients;
        },
        getTasks(): string {
            return this.links.tasks;
        },
        gettNotes(): string {
            return this.links.notes;
        },
    },
});
