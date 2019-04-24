import { KeycloakService } from './keycloak.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest, HttpEvent, HttpResponse } from '@angular/common/http';
 
 
@Injectable()
export class SecuredHttpInterceptor implements HttpInterceptor {

    constructor(private keycloakService: KeycloakService){

    }
 
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.keycloakService.getToken();
        let kcToken = this.keycloakService.auth.authz.token;
 
        if (this.keycloakService.auth.loggedIn && this.keycloakService.auth.authz.authenticated) {
            request = request.clone({
                setHeaders: {
                    Authorization: 'Bearer ' + kcToken
                }
            });
        }
 
        return next.handle(request);
    }
}