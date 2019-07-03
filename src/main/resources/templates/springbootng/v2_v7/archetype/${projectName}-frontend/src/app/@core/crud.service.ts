import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { JiiPage } from './jii/jii-page';
import { JiiPayload } from './jii/jii-payload';
import { Page } from './page';



/**
 * Classe abstrata com a implementação base das operações de crud;
 */
export abstract class CrudService<T> {

  constructor(
    private _http: HttpClient,
    private apiContext: string
  ) { }

  public findAll(params: string = ""): Observable<Page<T>> {
    return this._http.get<Page<T>>(`${environment.urlbase}${this.apiContext}${params}`);
  }

  /**
   * Retorna um recurso pelo id.
   *
   * @param id Id do recurso
   */
  public findById(id: number): Observable<T> {
    return this._http.get<T>(`${environment.urlbase}${this.apiContext}/${id}`);
  }

  /**
   * Retorna um recurso detalhado pelo id.
   *
   * @param id Id do recurso
   */
  public findDetailById(id: number): Observable<T> {
    return this._http.get<T>(`${environment.urlbase}${this.apiContext}/${id}/detail`);
  }

  /**
   * Insere um novo recurso.
   *
   * @param resource
   */
  public insert(resource: T): Observable<T> {
    return this._http.post<T>(`${environment.urlbase}${this.apiContext}`, resource);
  }

  /**
   * Atualiza um recurso.
   *
   * @param id
   * @param resource
   */
  public update(id: number, resource: T): Observable<T> {
    return this._http.put<T>(`${environment.urlbase}${this.apiContext}/${id}`, resource);
  }

  /**
   * Deleta um recurso pelo id.
   *
   * @param id Id do recurso.
   */
  public delete(id: number): Observable<void> {
    return this._http.delete<void>(`${environment.urlbase}${this.apiContext}/${id}`);
  }

  /**
   * Retorna a quantidade total de recursos.
   */
  public count(): Observable<number> {
    return this._http.get<number>(`${environment.urlbase}${this.apiContext}/_count`);
  }
  
  /**
   * Endpoint padrão para filtragem de dados.
   */
  public filter(sort: string, page: number = 0, size: number = 20, jiiPayload?: JiiPayload): Observable<JiiPage<T>> {
    return this._http.post<JiiPage<T>>(`${environment.urlbase}${this.apiContext}/filter?sort=${sort}&page=${page}&size=${size}`, jiiPayload);
  }

}
