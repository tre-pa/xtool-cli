import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EnvService {

  private url: string = `${environment.contextPath}/env`;

  constructor(private http: HttpClient) { }

  public async init(): Promise<Map<string, any>> {
    return await this.load();
  }

  private load(): Promise<Map<string, any>> {
    console.info("Loading environment...");
    return new Promise((resolve, reject) => {
      this.http.get<Map<string, any>>(`${this.url}`)
        .subscribe(env => {
          if (env['keycloak_url']) environment.keycloak_installation['url'] = env['keycloak_url'];
          resolve();
        }, error => {
          console.error("Error loading the environment: ", error);
          reject();
        });
    });
  }

}
