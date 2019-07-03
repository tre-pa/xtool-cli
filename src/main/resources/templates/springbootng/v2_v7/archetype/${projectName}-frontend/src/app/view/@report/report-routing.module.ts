import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DxDataGridModule } from 'devextreme-angular/ui/data-grid';
import { DxTextAreaModule } from 'devextreme-angular/ui/text-area';
import { DxButtonModule } from 'devextreme-angular/ui/button';
import { DxFormModule } from 'devextreme-angular/ui/form';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';

import { SideNavOuterToolbarModule } from 'src/app/shared/layouts/side-nav-outer-toolbar/side-nav-outer-toolbar.component';

import { ReporMngttListComponent } from 'src/app/view/@report/report/report-list/report-mngt-list.component';
import { ReportMngtEditComponent } from 'src/app/view/@report/report/report-edit/report-mngt-edit.component';
import { ReportPageComponent } from 'src/app/view/@report/report-page.component';

library.add(fas);

const DEVEXTREME_MODULES: any[] = [
  DxDataGridModule,
  DxTextAreaModule,
  DxButtonModule,
  DxFormModule
];

const ANGULAR_MODULES: any[] = [
  FlexLayoutModule,
  CommonModule
];

const routes: Routes = [
  { path: '', component: ReportPageComponent, children: [
      { path: '', children: [
          { path: '', component: ReporMngttListComponent },
          { path: 'edit', component: ReportMngtEditComponent, children: [
              { path: '' },
              { path: ':idReport' }
            ]
          }
        ]
      }
    ]
  }
];
@NgModule({
  imports: [
    DEVEXTREME_MODULES,
    ANGULAR_MODULES,
    SideNavOuterToolbarModule,
    FontAwesomeModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule],
  declarations: [
    ReportMngtEditComponent,
    ReporMngttListComponent,
    ReportPageComponent
  ]
})
export class ReportRoutingModule { };