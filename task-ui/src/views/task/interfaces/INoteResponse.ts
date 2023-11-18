export default interface INoteResponse {
    id: number,
    text: string,
    dateHour: string,
    links: Array<Record<string, string>>,
};