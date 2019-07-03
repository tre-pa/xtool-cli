import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from 'src/environments/environment';
import { CrudService } from 'src/app/@core/crud.service';
import { Report, ReportGrid } from 'src/app/domain/report';

@Injectable({
  providedIn: 'root'
})
export class MngtReportService extends CrudService<Report> {

  private url: string = `${environment.urlbase}/mngt/jreport`;

  constructor(private http: HttpClient) {
    super(http, '/mngt/jreport');
    this.findById = (id: number): Observable<Report> => {
      return this.http.get<Report>(`${this.url}/${id}`).pipe(
        map(report => {
          if (report.grid) report['_grid'] = JSON.stringify(report.grid, null, 2);
          return report;
        })
      );
    };
  }

  save(report: Report): Observable<Report> {
    if (report['_grid']) report.grid = JSON.parse(report['_grid']);
    return report.id ?
      this.update(report.id, report) :
      this.insert(report);
  }

  previewSQL(report: Report): Observable<any> {
    return this.http.post<any>(`${this.url}/preview/datasource`, report);
  }

  previewPDF(report: Report): Observable<any> {
    return this.http.post<any>(`${this.url}/preview/pdf`, report);
  }

  genDefaultGridTemplate(report: Report): Observable<ReportGrid> {
    return this.http.post<ReportGrid>(`${this.url}/grid/template`, report);
  }

  genDefaultGPDFTemplate(): Observable<any> {
    return this.http.get<any>(`${this.url}/gpdf/template`);
  }

}
