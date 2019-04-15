import {
  Component,
  ViewChild,
  OnInit
} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { DxDataGridComponent } from 'devextreme-angular/ui/data-grid';
import { Subscription } from 'rxjs';

import { JiiAggregation } from 'src/app/@core/jii/jii-aggregation';
import { ReportService } from 'src/app/service/report.service';
import { JiiStore } from 'src/app/@core/jii/jii-store';
import { Report } from 'src/app/domain/report';

@Component({
  selector: 'app-report-detail',
  templateUrl: './report-detail.component.html'
})
export class ReportDetailComponent implements OnInit {

  @ViewChild(DxDataGridComponent)
  dataGrid: DxDataGridComponent;

  idReport: number;
  report: Report;
  aggregations: JiiAggregation[];

  reportStore: JiiStore<Report>;

  downloadSub: Subscription;

  constructor(
    private activatedRoute: ActivatedRoute,
    private reportService: ReportService,
    private httpClient: HttpClient
  ) { }

  ngOnInit() {
    this.idReport = +this.activatedRoute.snapshot.params['idReport'];
    if (this.idReport) this.loadData();
  }

  private loadData() {
    this.reportService.findById(this.idReport)
      .subscribe(report => {
        for (const key in report.grid.properties) 
          this.dataGrid[key] = report.grid.properties[key];
        this.report = report;
      });
    this.reportStore = new JiiStore<Report>(this.httpClient, '/jreport', {
      key: null,
      endpoints: {
        load: `/jreport/${this.idReport}/datasource`
      }
    });
    this.reportStore.onLoad
      .subscribe(() => this.aggregations = this.reportStore.getSummary());
  }

  onToolbarPreparing(toolbar) {
    toolbar.items.unshift({
      location: 'after',
      widget: 'dxButton',
      options: {
        icon: 'revert',
        onClick: () => {
          this.dataGrid.instance.clearFilter();
        }
      }
    });
    toolbar.items.unshift({
      location: 'after',
      widget: 'dxButton',
      options: {
        icon: 'export',
        onClick: () => {
          this.downloadSub = this.reportService.export(this.idReport, this.report.title, this.reportStore.sortParams, this.reportStore.filter);
        }
      }
    });
  }

}
