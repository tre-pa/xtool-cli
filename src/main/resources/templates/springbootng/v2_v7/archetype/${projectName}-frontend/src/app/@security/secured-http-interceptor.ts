import { Injectable } from '@angular/core';
import {
    HttpInterceptor,
    HttpHandler,
    HttpRequest,
    HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';

import { KeycloakService } from './keycloak.service';
import { environment } from 'src/environments/environment';

@Injectable()
export class SecuredHttpInterceptor implements HttpInterceptor {

    constructor(private keycloakService: KeycloakService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.isNotEnvUrl(request)) {
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

    private isNotEnvUrl(request: HttpRequest<any>): boolean {
        return !(request.url == `${environment.contextPath}/env`);
    }

}