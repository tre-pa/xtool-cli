import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DxLoadPanelModule } from 'devextreme-angular/ui/load-panel';
import { DxDataGridModule } from 'devextreme-angular/ui/data-grid';
import { DxButtonModule } from 'devextreme-angular/ui/button';
import { DxFormModule } from 'devextreme-angular/ui/form';
import { FormsModule } from '@angular/forms';
import { DxTextBoxModule } from 'devextreme-angular/ui/text-box';
import { DxSelectBoxModule } from 'devextreme-angular/ui/select-box';
import { DxValidatorModule, } from 'devextreme-angular/ui/validator';
import { DxValidationSummaryModule } from 'devextreme-angular/ui/validation-summary';
import { DxNumberBoxModule } from 'devextreme-angular/ui/number-box';
import { DxCheckBoxModule } from 'devextreme-angular/ui/check-box';
import { DxDateBoxModule } from 'devextreme-angular/ui/date-box';
import {
  NgxPermissionsService,
  NgxPermissionsModule
} from 'ngx-permissions';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';

import { SideNavOuterToolbarModule } from '../../shared/layouts/side-nav-outer-toolbar/side-nav-outer-toolbar.component';
import { ReportDetailComponent } from './report/report-detail/report-detail.component';
import { ReportListComponent } from './report/report-list/report-list.component';
import { UserPageComponent } from './user-page.component';
import { HomeComponent } from './home/home.component';

library.add(fas);

const DEVEXTREME_MODULES: any[] = [
  DxLoadPanelModule,
  DxDataGridModule,
  DxButtonModule,
  DxFormModule,
  DxTextBoxModule,
  DxSelectBoxModule,
  DxButtonModule,
  DxValidatorModule,
  DxValidatorModule,
  DxValidationSummaryModule,
  DxNumberBoxModule,
  DxCheckBoxModule,
  DxDateBoxModule
];

const ANGULAR_MODULES: any[] = [
  FlexLayoutModule,
  CommonModule,
  FormsModule
];

const routes: Routes = [
  { path: '', component: UserPageComponent, children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent },
      { path: 'relatorios', children: [
          { path: '', component: ReportListComponent },
          { path: ':idReport', component: ReportDetailComponent }
        ]
      }
    ]
  },

  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [
    DEVEXTREME_MODULES,
    ANGULAR_MODULES,
    SideNavOuterToolbarModule,
    FontAwesomeModule,
    RouterModule.forChild(routes),
    NgxPermissionsModule.forChild()
  ],
  exports: [RouterModule],
  declarations: [
    ReportDetailComponent,
    ReportListComponent,
    UserPageComponent,
    HomeComponent
  ]
})
export class UserRoutingModule {}
