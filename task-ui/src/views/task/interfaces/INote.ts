export default interface INote {
    id: number,
    text: string,
    dateHour: string,
    editMode: boolean,
    links: Record<string, string>,
};