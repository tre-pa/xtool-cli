import {
  Component,
  OnInit
} from '@angular/core';
import {
  ActivatedRoute,
  Router
} from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Title } from '@angular/platform-browser';
import DataSource from 'devextreme/data/data_source';

import { MngtReportService } from 'src/app/view/@report/mngt-report.service';
import { JiiStore } from 'src/app/@core/jii/jii-store';
import { Report } from 'src/app/domain/report';

@Component({
  selector: 'app-report-mngt-edit',
  templateUrl: './report-mngt-edit.component.html'
})
export class ReportMngtEditComponent implements OnInit {

  idReport: number;
  report: Report;

  categoryDataSource: DataSource;

  constructor(
    private mngtReportService: MngtReportService,
    private activatedRoute: ActivatedRoute,
    private httpClient: HttpClient,
    private router: Router,
    private title: Title
  ) { }

  ngOnInit() {
    this.processReport();
    this.categoryDataSource = new DataSource({
      store: new JiiStore(this.httpClient, '/mngt/jreport', {
        key: null,
        endpoints: {
          load: `/mngt/jreport/categories`
        },
        loadByGet: true
      }),
      paginate: true,
      pageSize: 10
    });
  }

  processReport() {
    this.idReport = +this.activatedRoute.snapshot.children[0].params['idReport'];
    if (this.idReport && !isNaN(this.idReport)) this.edit();
    else this.create();
  }

  private create() {
    this.title.setTitle('Novo Relatório');
    this.report = new Report();
  }

  private edit() {
    this.title.setTitle('Edição de Relatório');
    this.mngtReportService.findById(this.idReport)
      .subscribe(report => this.report = report);
  }

  back() {
    this.router.navigate(['report-mngt']);
  }

  save(event) {
    this.mngtReportService.save(this.report)
      .subscribe(() => this.back());
    event.preventDefault();
  }

}
