import { CovalentMarkdownModule } from '@covalent/markdown';
import { CovalentHighlightModule } from '@covalent/highlight';
import { CovalentHttpModule } from '@covalent/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { SharedModule } from './@core/shared/shared.module';
import { AppRoutingModule } from './app-routing.module';
import { MainLayoutComponent } from './@core/layout/main-layout/main-layout.component';

@NgModule({
  declarations: [
    AppComponent,
    MainLayoutComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    SharedModule,
    CovalentHttpModule.forRoot(),
    CovalentHighlightModule,
    CovalentMarkdownModule,
    AppRoutingModule
  ],
  providers: [
  ],
  entryComponents: [ ],
  bootstrap: [ AppComponent ],
})
export class AppModule {}