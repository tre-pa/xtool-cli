import { Injectable } from '@angular/core';

import { KeycloakService } from './../@security/keycloak.service';
import { EnvService } from './env.service';

@Injectable({
  providedIn: 'root'
})
export class AppInitService {

  constructor(
    private keycloakService: KeycloakService,
    private appConfigService: EnvService
  ) { }

  public async init() {
    await this.appConfigService.init();
    await this.keycloakService.init();
  }

}
