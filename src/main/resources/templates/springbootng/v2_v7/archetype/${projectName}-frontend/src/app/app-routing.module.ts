import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxPermissionsGuard } from 'ngx-permissions';

const routes: Routes = [
    {
        path: 'report-mngt',
        loadChildren: './view/@report/report-routing.module#ReportRoutingModule',
        canActivate: [NgxPermissionsGuard],
        data: {
            permissions: {
                only: 'REPORT_MNGT',
                redirectTo: '/'
            }
        }
    },
    { path: '', loadChildren: './view/user/user-routing.module#UserRoutingModule' },

    { path: '**', redirectTo: '' } // Rota padrão
]
@NgModule({
    imports: [
        BrowserAnimationsModule,
        RouterModule.forRoot(routes, { useHash: true })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule { }
