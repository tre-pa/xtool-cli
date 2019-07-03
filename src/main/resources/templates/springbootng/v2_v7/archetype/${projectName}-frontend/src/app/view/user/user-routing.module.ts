import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DxLoadPanelModule } from 'devextreme-angular/ui/load-panel';
import { DxDataGridModule } from 'devextreme-angular/ui/data-grid';
import { DxButtonModule } from 'devextreme-angular/ui/button';
import { DxColorBoxModule } from 'devextreme-angular/ui/color-box';
import { DxTextAreaModule } from 'devextreme-angular/ui/text-area';
import { DxFormModule } from 'devextreme-angular/ui/form';
import {
  NgxPermissionsService,
  NgxPermissionsModule
} from 'ngx-permissions';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';

import { EventoListComponent } from './evento/evento-list/evento-list.component';
import { EventoEditComponent } from './evento/evento-edit/evento-edit.component';
import { LocalTypeListComponent } from './local-type/local-type-list/local-type-list.component';
import { LocalTypeEditComponent } from './local-type/local-type-edit/local-type-edit.component';
import { LocalListComponent } from './local/local-list/local-list.component';
import { LocalEditComponent } from './local/local-edit/local-edit.component';
import { OrdemServicoListComponent } from './ordem-servico/ordem-servico-list/ordem-servico-list.component';
import { OrdemServicoDetailComponent } from './ordem-servico/ordem-servico-detail/ordem-servico-detail.component';
import { OrdemServicoEditComponent } from './ordem-servico/ordem-servico-edit/ordem-servico-edit.component';
import { MotivoOrdemServicoListComponent } from './motivo-ordem-servico/motivo-ordem-servico-list/motivo-ordem-servico-list.component';
import { MotivoOrdemServicoDetailComponent } from './motivo-ordem-servico/motivo-ordem-servico-detail/motivo-ordem-servico-detail.component';
import { MotivoOrdemServicoEditComponent } from './motivo-ordem-servico/motivo-ordem-servico-edit/motivo-ordem-servico-edit.component';
import { MaterialTypeListComponent } from './material-type/material-type-list/material-type-list.component';
import { MaterialTypeEditComponent } from './material-type/material-type-edit/material-type-edit.component';
import { MaterialModeloListComponent } from './material-modelo/material-modelo-list/material-modelo-list.component';
import { MaterialModeloDetailComponent } from './material-modelo/material-modelo-detail/material-modelo-detail.component';
import { MaterialModeloEditComponent } from './material-modelo/material-modelo-edit/material-modelo-edit.component';
import { MaterialListComponent } from './material/material-list/material-list.component';
import { MaterialDetailComponent } from './material/material-detail/material-detail.component';
import { MaterialEditComponent } from './material/material-edit/material-edit.component';
import { SideNavOuterToolbarModule } from '../../shared/layouts/side-nav-outer-toolbar/side-nav-outer-toolbar.component';
import { MovimentacaoListComponent } from './movimentacao/movimentacao-list/movimentacao-list.component';
import { MovimentacaoDetailComponent } from './movimentacao/movimentacao-detail/movimentacao-detail.component';
import { MovimentacaoEditComponent } from './movimentacao/movimentacao-edit/movimentacao-edit.component';
import { PendenciaListComponent } from './pendencia/pendencia-list/pendencia-list.component';
import { OrdemServicoExternaListComponent } from './ordem-servico-externa/ordem-servico-externa-list/ordem-servico-externa-list.component';
import { OrdemServicoExternaDetailComponent } from './ordem-servico-externa/ordem-servico-externa-detail/ordem-servico-externa-detail.component';
import { OrdemServicoExternaEditComponent } from './ordem-servico-externa/ordem-servico-externa-edit/ordem-servico-externa-edit.component';
import { ReportDetailComponent } from './report/report-detail/report-detail.component';
import { ReportListComponent } from './report/report-list/report-list.component';
import { UserPageComponent } from './user-page.component';
import { HomeComponent } from './home/home.component';
import { EtiquetaComponent } from '../@commons/etiqueta/etiqueta.component';
import { EtiquetaMaterialComponent } from '../@commons/etiqueta-material/etiqueta-material.component';

library.add(fas);

const DEVEXTREME_MODULES: any[] = [
  DxLoadPanelModule,
  DxDataGridModule,
  DxButtonModule,
  DxColorBoxModule,
  DxTextAreaModule,
  DxFormModule
];

const ANGULAR_MODULES: any[] = [
  FlexLayoutModule,
  CommonModule
];

const routes: Routes = [
  { path : '', component : UserPageComponent, children : [
      { path : '', redirectTo : 'home', pathMatch : 'full' },
      { path : 'home', component : HomeComponent },
      { path : 'relatorios', children : [
          { path : '', component : ReportListComponent },
          { path : ':idReport', component : ReportDetailComponent }
        ] },
      { path : 'eventos', children : [
          { path : '', component : EventoListComponent },
          { path : 'edit', component : EventoEditComponent, children : [
              { path : '' },
              { path : ':idEvento' }
            ] }
        ] },
      { path : 'local-types', children : [
          { path : '', component : LocalTypeListComponent },
          { path : 'edit', component : LocalTypeEditComponent, children : [
              { path : '' },
              { path : ':idLocalType' }
            ] }
        ] },
      { path : 'locais', children : [
          { path : '', component : LocalListComponent },
          { path : 'edit', component : LocalEditComponent, children : [
              { path : '' },
              { path : ':idLocal' }
            ] }
        ] },
      { path : 'ordem-servicos', children : [
          { path : '', component : OrdemServicoListComponent },
          { path : 'edit', component : OrdemServicoEditComponent, children : [
              { path : '' },
              { path : ':idOrdemServico' }
            ] },
          { path : ':idOrdemServico', component : OrdemServicoDetailComponent }
        ] },
      { path : 'motivo-ordem-servicos', children : [
          { path : '', component : MotivoOrdemServicoListComponent },
          { path : 'edit', component : MotivoOrdemServicoEditComponent, children : [
              { path : '' },
              { path : ':idMotivoOrdemServico' }
            ] },
          { path : ':idMotivoOrdemServico', component : MotivoOrdemServicoDetailComponent }
        ] },
      { path : 'material-types', children : [
          { path : '', component : MaterialTypeListComponent },
          { path : 'edit', component : MaterialTypeEditComponent, children : [
              { path : '' },
              { path : ':idMaterialType' }
            ] },
          { path : ':idMotivoOrdemServico', component : MotivoOrdemServicoDetailComponent }
        ] },
      { path : 'material-modelos', children : [
          { path : '', component : MaterialModeloListComponent },
          { path : 'edit', component : MaterialModeloEditComponent, children : [
              { path : '' },
              { path : ':idMaterialModelo' }
            ] },
          { path : ':idMaterialModelo', component : MaterialModeloDetailComponent }
        ] },
      { path : 'materiais', children : [
          { path : '', component : MaterialListComponent },
          { path : 'edit', component : MaterialEditComponent, children : [
              { path : '' },
              { path : ':idMaterial' }
            ] },
          { path : ':idMaterial', component : MaterialDetailComponent }
        ] },
      { path : 'movimentacoes', children : [
          { path : '', component : MovimentacaoListComponent },
          { path : 'edit', component : MovimentacaoEditComponent, children : [
              { path : '' },
              { path : ':idMovimentacao' }
            ] },
          { path : ':idMovimentacao', component : MovimentacaoDetailComponent }
        ] },
      { path : 'pendencias', children : [
          { path : '', component : PendenciaListComponent }
        ] },
     { path : 'ordem-servico-externas', children : [
          { path : '', component : OrdemServicoExternaListComponent },
          { path : 'edit', component : OrdemServicoExternaEditComponent, children : [
              { path : '' },
              { path : ':idOrdemServicoExterna' }
            ] },
          { path : ':idOrdemServicoExterna', component : OrdemServicoExternaDetailComponent }
        ] }
    ] },
  { path : '**', redirectTo : 'home' }
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
    HomeComponent,
    EventoListComponent,
    EventoEditComponent,
    LocalTypeListComponent,
    LocalTypeEditComponent,
    LocalListComponent,
    LocalEditComponent,
    OrdemServicoListComponent,
    OrdemServicoDetailComponent,
    OrdemServicoEditComponent,
    MotivoOrdemServicoListComponent,
    MotivoOrdemServicoDetailComponent,
    MotivoOrdemServicoEditComponent,
    MaterialTypeListComponent,
    MaterialTypeEditComponent,
    MaterialModeloListComponent,
    MaterialModeloDetailComponent,
    MaterialModeloEditComponent,
    MaterialListComponent,
    MaterialDetailComponent,
    MaterialEditComponent,
    EtiquetaComponent,
    MovimentacaoListComponent,
    MovimentacaoDetailComponent,
    MovimentacaoEditComponent,
    PendenciaListComponent,
    EtiquetaMaterialComponent,
    OrdemServicoExternaListComponent,
    OrdemServicoExternaDetailComponent,
    OrdemServicoExternaEditComponent
  ]
})
export class UserRoutingModule {}
