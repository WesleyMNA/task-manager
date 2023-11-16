import INote from './INote';

export default interface ITask {
    id: number,
    title: string,
    priority: string,
    status: string,
    initialDate: string | null,
    finalDate: string | null,
    description: string | null,
    links: Record<string, string>,
    notes: INote | null,
};
