import { KeycloakService } from './@security/keycloak.service';
import { NgxPermissionsModule, NgxPermissionsService } from 'ngx-permissions';
import { NgModule, APP_INITIALIZER, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { SecuredHttpInterceptor } from './@security/secured-http-interceptor';
import { ErrorHandlerException } from './shared/handlers/error-handling/error-handler-exception';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';


import "devextreme/localization/globalize/number";
import "devextreme/localization/globalize/date";
import "devextreme/localization/globalize/currency";
import "devextreme/localization/globalize/message";

import 'devextreme-intl';

import * as Globalize from 'globalize';
import { DxButtonModule } from 'devextreme-angular/ui/button';

Globalize.load(require('cldr-data/supplemental/likelySubtags.json'))
Globalize.load(require('cldr-data/supplemental/numberingSystems.json'));
Globalize.load(require('cldr-data/main/pt/ca-gregorian.json'))
Globalize.load(require('cldr-data/main/pt/numbers.json'))
Globalize.load(require('cldr-data/main/pt/currencies.json'))
Globalize.load(require('cldr-data/supplemental/timeData.json'))
Globalize.load(require('cldr-data/supplemental/weekData.json'))
Globalize.load(require('cldr-data/supplemental/currencyData.json'))

Globalize.loadMessages(require('devextreme/localization/messages/pt.json'));

Globalize.locale('pt-BR')

export function kcFactory(keycloakService: KeycloakService) {
  return () => keycloakService.init();
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    AppRoutingModule,
    HttpClientModule,
    BrowserModule,
    DxButtonModule,
    NgxPermissionsModule.forRoot()
  ],
  providers: [
  	KeycloakService,
    KeycloakService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: SecuredHttpInterceptor,
      multi: true
    },
    {
      provide: ErrorHandler,
      useClass: ErrorHandlerException,
    },
    {
      provide: APP_INITIALIZER,
      useFactory: kcFactory,
      deps: [KeycloakService, NgxPermissionsService],
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
