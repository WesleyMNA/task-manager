export default class Page {

    number: number;
    size: number;
    totalElements: number;
    totalPages: number;

    constructor(
        number: number,
        size: number,
        totalElements: number,
        totalPages: number,
    ) {
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}