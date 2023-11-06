import Swal, { SweetAlertIcon } from "sweetalert2";

export enum NotificationType {
  SUCCESS = "success",
  ERROR = "error",
  WARNING = "warning",
  INFO = "info",
  QUESTION = "question",
}

export function notificate(title: string, icone: SweetAlertIcon): void {
  Swal.fire({
    title: title,
    icon: icone,
    showConfirmButton: false,
  });
}

export function notificateLoading(title: string): void {
  Swal.fire({
    title: title,
    showConfirmButton: false,
    allowOutsideClick: false,
  });
  Swal.showLoading();
}

export function notificateAndWaitConfirmation(
  title: string,
  icone: SweetAlertIcon
): void {
  Swal.fire({
    title: title,
    icon: icone,
  });
}

export function closeDialog(): void {
  Swal.close();
}
