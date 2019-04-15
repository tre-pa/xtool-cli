import { ErrorHandler } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import notify from 'devextreme/ui/notify';


export class ErrorHandlerException implements ErrorHandler {

    type: string = "error";
    time: number = 10000;

    public handleError(error: Error | HttpErrorResponse) {
        console.warn(error);
        if (error instanceof HttpErrorResponse) {
            let statusCode: number = error.status;
            // Server or connection error happened
            if (!navigator.onLine) {
                // Handle offline error
                notify("Sem conexão com a internet", this.type, this.time);
            } else {
                this.show(error.error.message);
            }
            console.log(error, statusCode);
        } else {
            this.show(error);
        }
    }

    show(error) {
        notify(error, this.type, this.time);
    }
}
