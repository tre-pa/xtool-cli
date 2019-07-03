import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-report-page',
  template: `
    <app-side-nav-outer-toolbar toolbarTitle="Controle, Organização e Velocidade - Gerência de Relatórios"
      [menuItems]="navigation">
      <router-outlet></router-outlet>
    </app-side-nav-outer-toolbar>
  `
})
export class ReportPageComponent implements OnInit {

  navigation = [{
    text: 'Voltar',
    path: '/home',
    icon: 'arrow-left'
  }, {
    text: 'Gerência de Relatórios',
    path: '/report-mngt',
    icon: 'file-alt'
  }];

  constructor() { }

  ngOnInit() {
  }

}
