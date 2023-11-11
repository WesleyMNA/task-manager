import moment from 'moment'

export function toISODateFormat(date: Array<any> | Date | string): string {
    if (date instanceof Date || date instanceof String)
        return moment(date).format('YYYY-MM-DD');

    if (date.length === 3) {
        const year = date[0];
        const month = parseFloat(date[1]) - 1;
        const day = date[2];
        return moment(new Date(year, month, day)).format('YYYY-MM-DD');
    }

    const year = date[0];
    const month = parseFloat(date[1]) - 1;
    const day = date[2];
    const hour = date[3];
    const minute = date[4];
    const second = date[5];
    const millisecond = date[6];
    return moment(new Date(
        year,
        month,
        day,
        hour,
        minute,
        second,
        millisecond
    )).format('YYYY-MM-DDTHH:mm:ssZ');
}

export function toBrDateFormat(date: string | Date) {
    return moment(date).format('DD/MM/YYYY');
}

export function toISODatetimeFormat(date: Array<any> | Date | string | null): string | null{
    if (date === null)
        return null;

    if (date instanceof Date || date instanceof String)
        return moment(date).format('YYYY-MM-DD');

    const year = date[0];
    const month = parseFloat(date[1]) - 1;
    const day = date[2];
    const hour = date[3];
    const minute = date[4];
    const second = date[5];
    return moment(new Date(
        year,
        month,
        day,
        hour,
        minute,
        second
    )).format('YYYY-MM-DDTHH:mm:ss');
}

export function toBrDatetimeFormat(date: string | Date | null): string {
    return moment(date).format('DD/MM/YYYY HH:mm:ss');
}