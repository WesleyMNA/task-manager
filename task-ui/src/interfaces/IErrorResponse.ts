export interface IErrorResponse {
    error: string,
    message: string,
    path: string,
    status: number,
    timestamp: string,
    errors: { [key: string]: string } | null
}