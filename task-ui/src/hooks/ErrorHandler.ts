import {
    notificateLoading,
    notificateAndWaitConfirmation,
    NotificationType,
    notificate,
} from '@/services/notification';
import { IErrorResponse } from '@/interfaces/IErrorResponse';

type ErrorHandler = {
    handle: (error: any) => void
}

export default () : ErrorHandler => {

    const handle = (error: any): void => {
        const errorResponse: IErrorResponse = error.response.data;
        notificateAndWaitConfirmation(
            errorResponse.message,
            NotificationType.ERROR
        );
    }

    return {
        handle
    }
}
