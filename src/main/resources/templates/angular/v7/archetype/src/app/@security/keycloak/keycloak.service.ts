import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

import { SecurityModule } from '../security.module';
import { KeycloakEnum } from './keycloak.enum';
import { environment } from 'src/environments/environment';
import { UserInfo } from './user-info';
import * as  jwtDecode from 'jwt-decode';

declare var Keycloak: any;

declare var KeycloakAuthorization: any;

@Injectable({ providedIn: SecurityModule })
export class KeycloakService {

  auth: any = {};

  userInfo: UserInfo = new UserInfo();
  
  authorization: any = {};

  private eventObservable = new Subject<KeycloakEnum>();

  constructor() { }

  /**
   * Método de inicialização da segurança.
   * Inicializa o timer para atualização do token
   */
  init(): Promise<any> {
    console.log('Keycloak init');
    let keycloak = Keycloak(environment.keycloak_installation);

    this.auth.loggedIn = false;

    keycloak.onReady = () => { this.eventObservable.next(KeycloakEnum.READY) }
    keycloak.onAuthSuccess = () => { this.eventObservable.next(KeycloakEnum.AUTH_SUCCESS) }
    keycloak.onAuthError = () => { this.eventObservable.next(KeycloakEnum.AUTH_ERROR) }
    keycloak.onAuthRefreshSuccess = () => { this.eventObservable.next(KeycloakEnum.AUTH_REFRESH_SUCCESS) }
    keycloak.onAuthRefreshError = () => { this.eventObservable.next(KeycloakEnum.AUTH_REFRESH_ERROR) }
    keycloak.onAuthLogout = () => { this.eventObservable.next(KeycloakEnum.AUTH_LOGOUT) }
    keycloak.onTokenExpired = () => {
      keycloak.updateToken(70).success(function (refreshed) {
      }).error(function () {
        console.error('Failed to refresh token');
      });
      this.eventObservable.next(KeycloakEnum.TOKEN_EXPIRED);
    }

    return new Promise((resolve, reject) => {
      keycloak.init({ onLoad: 'login-required', checkLoginIframe: false })
        .success(() => {
          this.auth.loggedIn = true;
          this.auth.authz = keycloak;
          this.auth.logoutUrl = keycloak.authServerUrl + `/realms`
            + `/${environment.keycloak_installation.realm}/protocol/openid-connect/logout`
            + `?redirect_uri=${window.location.origin}${environment.keycloak_redirect_uri}`;
          
          this.auth.authz.loadUserInfo().success((userInfo) => this.userInfo = userInfo);
          
		  this.authorization = new KeycloakAuthorization(keycloak);

          this.getEvent();

          resolve();
        })
        .error((err) => {
          console.log(err)
          reject();
        });
    });
  }

  /**
   * Método de logout
   */
  logout() {
    this.auth.loggedIn = false;
    this.auth.authz = null;

    window.location.href = this.auth.logoutUrl;
  }

  /**
   * Captura o token
   */
  getToken(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (this.auth.authz.token) {
        this.auth.authz.updateToken()
          .success(() => {
            resolve(<string>this.auth.authz.token);
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
  getUserInfo(): UserInfo {
    return this.userInfo;
  }

  /**
   * Verifica se o usuário possui a regra informada para o angular
   * @param role 
   */
  public hasResourceRole(role: string): boolean {
    return this.auth.authz.hasResourceRole(role, environment.keycloak_installation.clientId);
  }

  /**
   * Verifica se o usuário possui a regra informada
   * @param role 
   */
  public hasRealmRole(role: string): boolean {
    return this.auth.authz.hasRealmRole(role);
  }

  public isTokenExpired() {
    return this.auth.authz.isTokenExpired();
  }

  public getGroupsInToken(): string[] {
    return this.auth.authz.tokenParsed.groups;
  }

  public getEvent(): Observable<KeycloakEnum> {
    return this.eventObservable;
  }

  public getTokenParsed(): any{
    return this.auth.authz.tokenParsed
  }
  
  public getPermisionScopes():Promise<string[]>{
    let permisionScopes: string [] = []; 

    if(this.getTokenParsed().realm_access){
      this.getTokenParsed().realm_access.roles.forEach(role => {
        permisionScopes.push(role);
      });
    }

    if(this.getTokenParsed().resource_access[environment.keycloak_clientId_sboot].roles){
      this.getTokenParsed().resource_access[environment.keycloak_clientId_sboot].roles.forEach(role => {
        permisionScopes.push(role);
      });
    }

    return new Promise((resolve)=>{
      this.authorization.entitlement(environment.keycloak_clientId_sboot).then(rpt=>{
        
        
        jwtDecode(rpt).authorization.permissions.forEach(permission => {
          if(permission.scopes){
            permission.scopes.forEach(scope => {
              permisionScopes.push(permission.rsname + ":" + scope);
            });
          }
        });

        resolve(permisionScopes);
      });
    });
  }
  
}