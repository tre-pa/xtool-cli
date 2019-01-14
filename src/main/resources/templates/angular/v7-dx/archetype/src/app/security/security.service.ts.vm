import { Injectable } from '@angular/core';

import { KeycloakService } from './keycloak/keycloak.service';
import { SecurityModule } from './security.module';
import { UserInfo } from './keycloak/user-info';

@Injectable({
  providedIn: SecurityModule
})
export class SecurityService {

  constructor(private keycloakService: KeycloakService) { }

  /**
   * Método de inicialização da segurança.
   * Inicializa o timer para atualização do token
   */
  public init(): Promise<any> {
    return this.keycloakService.init();
  }

  /**
   * Método de logout
   */
  public logout() {
    this.keycloakService.logout();
  }

  /**
   * Captura o token
   */
  public getToken(): Promise<string> {
    return this.keycloakService.getToken();
  }

  public getTokenParsed(): any{
    return this.keycloakService.getTokenParsed();
  }

  /**
   * Retorna as informações do usuário
   */
  public getUserInfo(): UserInfo {
    return this.keycloakService.getUserInfo();
  }

  /**
   * Verifica se o usuário possui a regra informada para o angular
   * @param role
   */
  public hasResourceRole(role: string): boolean {
    return this.keycloakService.hasResourceRole(role);
  }

  /**
   * Verifica se o usuário possui a regra informada
   * @param role 
   */
  public hasRealmRole(role: string): boolean {
    return this.keycloakService.hasRealmRole(role);
  }

  /**
   * Retorna todos os grupos do usuário
   */
  public getGroupsInToken(): string[] {
    return this.keycloakService.getGroupsInToken();
  }
}
