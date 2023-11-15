import {
    notificateAndWaitConfirmation,
    NotificationType,
} from '@/services/notification';
import { IErrorResponse } from '@/interfaces/IErrorResponse';

type ErrorHandler = {
    handle: (error: any) => void
}

export default () : ErrorHandler => {

    const handle = (error: any): void => {
        const response: IErrorResponse = error.response.data;

        if (response.status === 400) {
            let message;
            let title = 'Erro';

            if (response.errors) {
                message = Object.entries(response.errors)
                    .map(([key, value]) => `<br/>- <strong>${key}:</strong> ${value}`)
                    .join('\n');
                title = response.message;
            } else
                message = response.message;

            notificateAndWaitConfirmation(
                title,
                message,
                NotificationType.ERROR
            );
        }
    }

    return {
        handle
    }
}
