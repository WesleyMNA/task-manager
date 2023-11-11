export default interface ITaskResponse {
    id: number,
    title: string,
    priority: string,
    status: string,
    initialDate: string | null,
    finalDate: string | null,
    description: string | null,
    links: Array<Record<string, string>>,
}