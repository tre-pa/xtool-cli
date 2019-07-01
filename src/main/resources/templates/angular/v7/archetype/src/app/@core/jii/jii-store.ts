import { EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoadOptions } from 'devextreme/data/load_options';
import CustomStore from 'devextreme/data/custom_store';

import { JiiStoreOptions } from './jii-store-options';
import { JiiAggregation } from './jii-aggregation';
import { JiiFilterable } from './filter/jii-filterable';
import { environment } from '../../../environments/environment';
import { JiiPayload } from './jii-payload';
import { JiiFilter } from './filter/jii-filter';
import { JiiPage } from './jii-page';
import { Page } from '../page';

/**
 * Store para ser usado nos DataSouces que deve receber no seu construtor:
 * 
 * - http: HttpClient (usado para fazer as requisições ao servidor)
 * - basePath: string (raíz dos endpoints rest onde os requests serão feitos)
 * - options: JiiStoreOptions (configurações específicas dos requests)
 * 
 * Apenas options é opcional
 * 
 * ---
 * Atributos
 * 
 * - onLoad: EventEmitter (é emitido ao final do load, quando as variáveis do Store são atualizados)
 * - sortParams: string (parâmetros de ordenação usados no request do último load)
 * - filter: JiiFilterable (filtro usado no request do último load)
 * - aggregations: JiiAggregation[] (agregações resultadas do último load)
 * 
 * ---
 * Métodos
 * 
 * - getSummary(dataField?, operation?): JiiAggregation[] (retorna as agregações resultadas do último load com filtros opcionais)
 * 
 */
export class JiiStore<T> extends CustomStore {

  /** EventEmitter emitido ao final do load, quando as variáveis do Store são atualizados */
  onLoad: EventEmitter<any> = new EventEmitter<any>();

  /** Parâmetros de ordenação usados no request do último load do Store */
  sortParams: string;
  /** Filtro usado no request do último load do Store */
  filter: JiiFilterable;
  /** Agregações resultadas do último load do Store */
  aggregations: JiiAggregation[];
  /** Quantidade total de elementos obtida no ultimo load */
  totalElements: number;

  constructor(
    /** Usado para fazer as requisições ao servidor, e deve ser uma depenência injetada do Componente que instanciar o Store */
    http: HttpClient,
    /** Caminho raíz dos endpoints rest onde os requests serão feitos pelo Store */
    basePath: string,
    /** JiiStoreOptions contendo configurações específicas dos requests que serão feitos pelo Store */
    options: JiiStoreOptions = new JiiStoreOptions()
  ) {
    super({
      key: options.key === undefined ? 'id' : options.key,
      byKey: (key) => {
        if (options.key === null) return key;
        return http.get<T>(this.byKeyUrl(options, basePath, key)).toPromise();
      },
      load: (loadOptions: LoadOptions) => {
        if (options.loadByGet) return http.get<Page<T>>(this.loadUrl(loadOptions, options, basePath))
          .toPromise()
          .then((page: Page<T>) => {
            this.updateStoreVariables(loadOptions, page);
            return page;
          })
          .then((page: Page<T>) => ({
            data: page.content,
            totalCount: page.totalElements
          }));

        return http.post<JiiPage<T>>(this.loadUrl(loadOptions, options, basePath), this.payloadFromLoadOptions(loadOptions))
          .toPromise()
          .then((jiiPage: JiiPage<T>) => {
            this.updateStoreVariables(loadOptions, jiiPage);
            return jiiPage;
          })
          .then((jiiPage: JiiPage<T>) => ({
            data: jiiPage.pagination ? jiiPage.pagination.content : undefined,
            totalCount: jiiPage.pagination ? jiiPage.pagination.totalElements : undefined,
            summary: jiiPage.aggregations ? jiiPage.aggregations.map(agg => agg.result) : undefined
          }));
      }
    });
  }

  /**
   * Retorna a string referente a URL que deve ser usada para o request do byKey (findOne)
   * 
   * @param options
   * @param basePath
   * @param key
   */
  private byKeyUrl(options: JiiStoreOptions, basePath: string, key: any): string {
    return `${environment.urlbase}${options.endpoints.byKey || basePath}/${key}`;
  }

  /**
   * Retorna a string referente a URL que deve ser usada para o request do load (findAll)
   * 
   * @param loadOptions 
   * @param options 
   * @param basePath 
   */
  private loadUrl(loadOptions: LoadOptions, options: JiiStoreOptions, basePath: string): string {
    let params = this.paramsFromLoadOptions(loadOptions);
    if (options.loadByGet) return `${environment.urlbase}${options.endpoints.load || basePath}${params}`;
    return `${environment.urlbase}${options.endpoints.load || `${basePath}/filter`}${params}`;
  }

  /**
   * Retorna a string referente aos parâmetros que devem ser usado no request do load (findAll)
   * 
   * @param loadOptions 
   */
  private paramsFromLoadOptions(loadOptions: LoadOptions): string {
    let skip = loadOptions.skip,
      take = loadOptions.take,
      page = skip ? (skip / take) : 0,
      params = `?page=${page}&size=${take}`;
    if (loadOptions.sort) params += `&${this.paramFromSort(loadOptions.sort)}`;
    if (loadOptions.select) params += `&fields=${loadOptions.select.join(',')}`;
    return params;
  }

  /**
   * Retorna o payload referente ao body que deve ser usado no request do load (findAll)
   * 
   * @param loadOptions 
   */
  private payloadFromLoadOptions(loadOptions: LoadOptions): JiiPayload {
    return {
      filterable: this.filterableFromLoadOptions(loadOptions),
      aggregables: loadOptions.totalSummary
    };
  }

  /**
   * Retorna um filterable referente ao filtro que deve ser usado na formação de um payload
   * 
   * @param loadOptions
   */
  private filterableFromLoadOptions(loadOptions: LoadOptions): JiiFilterable {
    let searchExpr = loadOptions.searchExpr as string | string[],
      searchOperation = loadOptions.searchOperation,
      searchValue = loadOptions.searchValue,
      filter = loadOptions.filter,
      newFilter: JiiFilter = new JiiFilter();
    if (filter) newFilter.add(filter);
    if (searchValue) newFilter.add(JiiFilter.build(searchExpr, searchOperation, searchValue));
    return newFilter.jiiFilterable;
  }

  /**
   * Retorna a string referente ao parâmetro de ordenação
   * 
   * @param sort
   */
  private paramFromSort(sort: { selector: string, desc: boolean }[]): string {
    return sort.map(order => `sort=${order.selector},${order.desc ? 'desc' : 'asc'}`).join('&');
  }

  /**
   * Atualiza as variáveis do Store
   * 
   * @param loadOptions 
   * @param aggregations
   */
  private updateStoreVariables(loadOptions: LoadOptions, page: JiiPage<T> | Page<T>): void {
    this.sortParams = loadOptions.sort ? this.paramFromSort(loadOptions.sort) : null;
    this.filter = loadOptions.filter ? JiiFilter.of(loadOptions.filter).jiiFilterable : null;
    if (page['pagination']) {
      this.totalElements = page['pagination'].totalElements;
      if (loadOptions.totalSummary) this.aggregations = page['aggregations'];
    } else {
      this.totalElements = page['totalElements'];
    }
    this.onLoad.emit();
  }

  /**
   * Método que retorna as agregações resultadas do último load do Store com filtros opcionais
   * 
   * @param dataField
   * @param operation
   */
  getSummary(dataField?: string, operation?: 'count' | 'sum' | 'max' | 'min' | 'avg'): JiiAggregation[] {
    let filteredSummary: JiiAggregation[];
    if (!dataField) return this.aggregations;
    if (operation) filteredSummary = this.aggregations
      .filter(agg => agg.dataField === dataField && agg.operation === operation);
    else filteredSummary = this.aggregations
      .filter(agg => agg.dataField === dataField);
    if (filteredSummary.length === 0) return null;
    return filteredSummary;
  }

}