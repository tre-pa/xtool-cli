import { SecurityModule } from './security.module';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

import { environment } from 'src/environments/environment';
import { UserInfo } from './user-info';
import * as  jwtDecode from 'jwt-decode';
import { NgxPermissionsService } from 'ngx-permissions';

declare var Keycloak: any;

declare var KeycloakAuthorization: any;

@Injectable({ providedIn: SecurityModule })
export class KeycloakService {

  auth: any = {};

  userInfo: UserInfo = new UserInfo();
  
  authorization: any = {};

  constructor(private permissionsService: NgxPermissionsService) { }

  public async init(): Promise<any>{
    //Inicializa o keycloak
    const init = await this.start();
    //seta permissões de permission do client
    const initAuthz = await this.initKeycloakAuthorization();
    const permissionsScopes = await this.getPermisionScopes();
    permissionsScopes.forEach(ps => {
      this.permissionsService.addPermission(ps);
    });
    this.getRealmRoles().forEach(role => {this.permissionsService.addPermission(role)});
    this.getResourceRoles().forEach(role => {this.permissionsService.addPermission(role)});
    return init;
  }

  /**
   * Método de inicialização da segurança.
   * Inicializa o timer para atualização do token
   */
  private start(): Promise<any> {
    console.log('Keycloak init');
    let keycloak = Keycloak(environment.keycloak_installation);

    this.auth.loggedIn = false;

    keycloak.onTokenExpired = () => {
      keycloak.updateToken(70).success(function (refreshed) {
      }).error(function () {
        console.error('Failed to refresh token');
      });
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
          
          resolve();
        })
        .error((err) => {
          console.log(err)
          reject();
        });
    });
  }

  private initKeycloakAuthorization(){
    this.authorization = new KeycloakAuthorization(this.auth.authz);
    console.log(this.authorization)
    return new Promise((resolve)=>{
      this.authorization.init().then(conf=>{
        resolve()
      });
    })
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
    return this.auth.authz.hasResourceRole(role, environment.keycloak_clientId_sboot);
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

  public getTokenParsed(): any{
    return this.auth.authz.tokenParsed
  }
  
  public getPermisionScopes(): Promise<string[]> {
    let permisionScopes: string[] = [];

    return new Promise((resolve) => {
      this.authorization.entitlement(environment.keycloak_clientId_sboot).then(rpt => {

        jwtDecode(rpt).authorization.permissions.forEach(permission => {
          if (permission.scopes) {
            permission.scopes.forEach(scope => {
              permisionScopes.push(permission.rsname + ":" + scope);
            });
          }
        });

        resolve(permisionScopes);
      });
    });
  }

  public getRealmRoles() {
    if (this.getTokenParsed().realm_access) {
      return this.getTokenParsed().realm_access.roles;
    }
    return [];
  }

  public getResourceRoles() {
    if (this.getTokenParsed().resource_access && this.getTokenParsed().resource_access[environment.keycloak_clientId_sboot]) {
      return this.getTokenParsed().resource_access[environment.keycloak_clientId_sboot].roles;
    }
    return [];
  }

  public getPer
  
}