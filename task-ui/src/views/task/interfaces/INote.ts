export default interface INote {
    id: number,
    text: string,
    dateHour: string,
    links: Record<string, string>,
};