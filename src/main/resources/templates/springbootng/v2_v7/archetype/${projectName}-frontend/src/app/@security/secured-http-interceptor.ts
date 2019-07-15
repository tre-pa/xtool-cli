import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
    HttpInterceptor,
    HttpHandler,
    HttpRequest,
    HttpEvent
} from '@angular/common/http';

import { KeycloakService } from './keycloak.service';

@Injectable()
export class SecuredHttpInterceptor implements HttpInterceptor {

    constructor(private keycloakService: KeycloakService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (!this.keycloakService.auth) {
            this.keycloakService.getToken();
            let kcToken = this.keycloakService.auth.authz.token;

            if (this.keycloakService.auth.loggedIn && this.keycloakService.auth.authz.authenticated) {
                request = request.clone({
                    setHeaders: {
                        Authorization: 'Bearer ' + kcToken
                    }
                });
            }
        }
        return next.handle(request);
    }

}