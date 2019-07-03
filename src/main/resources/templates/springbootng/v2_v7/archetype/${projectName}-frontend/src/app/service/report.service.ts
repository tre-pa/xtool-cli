import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as _ from 'lodash';

import { environment } from 'src/environments/environment';
import { CrudService } from 'src/app/@core/crud.service';
import { JiiFilterable } from 'src/app/@core/jii/filter/jii-filterable';
import { Report } from 'src/app/domain/report';
import { Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportService extends CrudService<Report> {

  private url: string = `${environment.urlbase}/jreport`;

  constructor(private http: HttpClient) {
    super(http, '/jreport');
    this.findById = (id: number): Observable<Report> => {
      return this.http.get<Report>(`${this.url}/${id}`).pipe(
        map(report => {
          report.grid.columns.forEach(column => column['width'] = undefined);
          return report;
        })
      );
    };
  }

  export(id: number, title: string, params?: string, filter?: JiiFilterable): Subscription {
    let url = `${this.url}/${id}/pdf`;
    return this.http.post(`${url}${params ? `?${params}` : ''}`, filter, {
      responseType: 'blob',
      headers: new HttpHeaders({
        'Accept': 'application/octet-stream'
      })
    })
      .subscribe(data => {
        this.downloadFromRequest(title, 'PDF', data);
      }, (err) => {
        console.log(err);
        this.errorMensage();
      }, () => {
        console.log("this._loadingService.resolve();");
      });
  }

  /**
   * 
   * @param data 
   */
  private downloadFromRequest(title: string, type: string, data: any) {
    type = type.toLowerCase();
    let options = { type: `application/${type};charset=utf-8;` };
    let filename = `${_.kebabCase(title.toLowerCase())}.${type}`;
    var blob = new Blob([data], options);
    if (navigator.msSaveBlob) {
      // IE 10+
      navigator.msSaveBlob(blob, filename);
    }
    else {
      var link = document.createElement('a');
      // Browsers that support HTML5 download attribute
      if (link.download !== undefined) {
        var url = URL.createObjectURL(blob);
        link.setAttribute('href', url);
        link.setAttribute('download', filename);
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    }
  }

  private errorMensage() {
    console.error('Um erro inesperado aconteceu!')
  }

}