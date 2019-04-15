import {
  Component,
  OnInit
} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

import { JiiStore } from 'src/app/@core/jii/jii-store';
import { Report } from 'src/app/domain/report';

@Component({
  selector: 'app-report-mngt-list',
  templateUrl: './report-mngt-list.component.html'
})
export class ReporMngttListComponent implements OnInit {

  defaultVisible: boolean = false;

  pageSize: number = 20;

  reportStore: JiiStore<Report>;

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private title: Title
  ) { }

  ngOnInit() {
    this.title.setTitle('Gerência de Relatórios');
    this.reportStore = new JiiStore(this.httpClient, '/mngt/jreport');
  }

  create() {
    this.router.navigate(['report-mngt', 'edit']);
  }

  edit(id: number) {
    this.router.navigate(['report-mngt', 'edit', id]);
  }

  toggleDefault() {
    this.defaultVisible = !this.defaultVisible;
  }

  onToolbarPreparing(toolbar) {
    toolbar.items.unshift({
      location: 'before',
      widget: 'dxButton',
      options: {
        text: 'Novo',
        stylingMode: 'contained',
        type: 'success',
        onClick: () => this.create()
      }
    });
  }

}
