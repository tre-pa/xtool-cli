import {
  Component,
  OnInit
} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { Subscription } from 'rxjs';

import { ReportService } from 'src/app/service/report.service';
import { JiiStore } from 'src/app/@core/jii/jii-store';
import { Report } from 'src/app/domain/report';

@Component({
  selector: 'app-report-list',
  templateUrl: './report-list.component.html'
})
export class ReportListComponent implements OnInit {

  reportStore: JiiStore<Report>;

  downloadSub: Subscription;

  constructor(
    private reportService: ReportService,
    private httpClient: HttpClient,
    private router: Router,
    private title: Title
  ) { }

  ngOnInit() {
    this.title.setTitle('Relatórios');
    this.reportStore = new JiiStore(this.httpClient, '/jreport');
  }

  navigate(route: string) {
    this.router.navigate([route]);
  }

  download(report: Report) {
    this.downloadSub = this.reportService.export(report.id, report.title);
  }

}
