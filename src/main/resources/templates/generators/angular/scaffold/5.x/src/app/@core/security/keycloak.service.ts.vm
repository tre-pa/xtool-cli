import { Observable } from 'rxjs/Rx';
import { Injectable } from "@angular/core";

import { environment } from '../../../environments/environment';

declare var Keycloak: any;

@Injectable()
export class KeycloakService {
  static auth: any = {};

  /**
   * Método de inicialização da segurança.
   * Inicializa o timer para atualização do token
   */
  static init(): Promise<any> {
    console.log('Keycloak init');
    let keycloak = Keycloak(environment.keycloak_installation);

    KeycloakService.auth.loggedIn = false;

    return new Promise((resolve, reject) => {
      keycloak.init({ onLoad: 'login-required', checkLoginIframe: false })
        .success(() => {
          KeycloakService.auth.loggedIn = true;
          KeycloakService.auth.authz = keycloak;
          KeycloakService.auth.logoutUrl = keycloak.authServerUrl + `/realms/${environment.keycloak_installation.realm}/protocol/openid-connect/logout?redirect_uri=${environment.keycloak_redirect_uri}`;

          //Evento disparado quando token é expirado.
          keycloak.onTokenExpired = () => {
            console.info('auth token expired');

            keycloak.updateToken()
              .success(() => {
                console.info('auth token updated');
              })
              .error(() => {
                throw new Error('failed to update token on expired');
              });
          };

          console.log("Loading...");

          resolve();
        })
        .error(() => {
          reject();
        });
    });
  }

  /**
   * Método de logout
   */
  logout() {
    console.log('*** LOGOUT');
    KeycloakService.auth.loggedIn = false;
    KeycloakService.auth.authz = null;

    window.location.href = KeycloakService.auth.logoutUrl;
  }

  /**
   * Captura o token
   */
  getToken(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (KeycloakService.auth.authz.token) {
        KeycloakService.auth.authz.updateToken()
          .success(() => {
            resolve(<string>KeycloakService.auth.authz.token);
          })
          .error(() => {
            reject('Failed to refresh token');
          });
      } else {
        reject('Not loggen in');
      }
    });
  }

  /**
   * Retorna as informações do usuário
   */
  getLoadUserInfo(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      KeycloakService.auth.authz.loadUserInfo().success((userInfo) => {
        resolve(userInfo);
      });

    });
  }

  /**
   * Verifica se o usuário possui a regra informada.
   * @param role Regra a pesquisar
   */
  static hasResourceRole(role: string): boolean {
    return KeycloakService.auth.authz.hasResourceRole(role, environment.keycloak_clientId_sboot);
  }

  isTokenExpired() {
    return KeycloakService.auth.authz.isTokenExpired();
  }
}
