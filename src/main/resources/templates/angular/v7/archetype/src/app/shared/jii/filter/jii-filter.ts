import * as _ from 'lodash';

import { JiiDisjunction } from './jii-disjunction';
import { JiiConjunction } from './jii-conjunction';
import { JiiFilterable } from './jii-filterable';
import { JiiPredicate } from './jii-predicate';

/**
 * Objeto de filtro
 * 
 * ---
 * Atributos
 * 
 * - dxFilter: any[] (filtro dx original)
 * - jiiFilterable: JiiFilterable (filtro de uso em endpoints do pacote Jii)
 * 
 * ---
 * Métodos
 * 
 * - of(dxFilter: any[]): JiiFilter (conversão de um filtro dx em um JiiFilter
 * contendo o filtro original e o filterable parseado, pronto para uso em um
 * payload de um endpoint do pacote Jii)
 * 
 */
export class JiiFilter {
  jiiFilterable: JiiFilterable;
  dxFilter: any[];

  /**
   * Constrói de maneira estática um JiiFilter a partir de um array de filtro do DevExtreme
   * 
   * @param dxFilter
   */
  static of(dxFilter: any[]): JiiFilter {
    let jiiFilter = new JiiFilter();
    jiiFilter.dxFilter = dxFilter;
    jiiFilter.jiiFilterable = parser4(parser3(parser2(parser1(dxFilter || []))));
    return jiiFilter;
  }

  /**
   * Adiciona à um filtro já existente um ou mais novos predicados
   * 
   * @param filterAttr atributo ou lista de atributos
   * @param filterOperation operação de comparação
   * @param filterValue valor para comparação
   * @param logicalOperator (default = 'and') operador lógico para união do filtro existente ao que está sendo adicionado
   */
  add(
    filterAttr: string | string[],
    filterOperation: '=' | '<>' | '>' | '>=' | '<' | '<=' | 'startswith' | 'endswith' | 'contains' | 'notcontains' | 'isblank' | 'isnotblank',
    filterValue: any,
    logicalOperator: 'and' | 'or' = 'and'
  ) {
    if (!filterValue) return this;
    if (this.dxFilter) {
      this.dxFilter = [dxFilterBuilder(filterAttr, filterOperation, filterValue), logicalOperator, this.dxFilter];
      this.jiiFilterable = parser4(parser3(parser2(parser1(this.dxFilter))));
    } else {
      this.dxFilter = dxFilterBuilder(filterAttr, filterOperation, filterValue);
      this.jiiFilterable = parser4(parser3(parser2(parser1(this.dxFilter))));
    }
    return this;
  }
}

let comparisonOperators = [['=', '<>', '>', '<', '>=', '<=', 'contains', 'notcontains', '@null', '@notnull'], ['<>', '=', '<=', '>=', '<', '>', 'notcontains', 'contains', '@notnull', '@null']];
let logicalOperators = [['and', 'or'], ['or', 'and']];
let isSimplePredicate = (p: JiiPredicate | any[]): boolean => { return p instanceof JiiPredicate; };
let hasAndOperator = (arr: any[]): boolean => { return _.includes(arr, 'and'); };
let isComparisonOperator = (op: string): boolean => { return !_.isEmpty(_.intersection([op], comparisonOperators[0])); };
let isLogicalOperator = (op: string): boolean => { return !_.isEmpty(_.intersection([op], logicalOperators[0])); };
let getComparisonOperator = (op: string, inverse: boolean = false): string => { return inverse ? comparisonOperators[1][comparisonOperators[0].indexOf(op)] : op; };
let getLogicalOperator = (op: string, inverse: boolean = false): string => { return inverse ? logicalOperators[1][logicalOperators[0].indexOf(op)] : op; };

/**
 * Constrói um vetor de filtro à partir de um atributo ou uma lista de atributos, um operador de comparação e um valor.
 * 
 * @param filterAttr 
 * @param filterOperation 
 * @param filterValue 
 */
let dxFilterBuilder = (
  filterAttr: string | string[],
  filterOperation: '=' | '<>' | '>' | '>=' | '<' | '<=' | 'startswith' | 'endswith' | 'contains' | 'notcontains' | 'isblank' | 'isnotblank',
  filterValue: any
): any[] => {
  if (filterAttr instanceof Array) {
    let newFilter: any[] = [];
    filterAttr.map(expr => [expr, filterOperation, filterValue])
      .forEach(expr => {
        newFilter.push(expr);
        newFilter.push('or');
      });
    newFilter.pop();
    return newFilter;
  }
  return [filterAttr, filterOperation, filterValue];
}

let parser1 = (v: any[], inverse: boolean = false): any[] => {
  return v.map((ele) => {
    if (Array.isArray(ele)) return parser1(ele);
    if (_.isNull(ele)) return "@null";
    return ele;
  });
};

/**
 * Aplica o teorema de 'de morgan'.
 * 
 * @param v 
 * @param inverse 
 */
let parser2 = (v: any[], inverse: boolean = false): any[] => {
  return v.map((ele) => {
    if (Array.isArray(ele)) return ele[0] === '!' ? _.compact(parser2(ele, inverse)) : parser2(ele, inverse);
    if (isComparisonOperator(ele)) return getComparisonOperator(ele, inverse);
    if (isLogicalOperator(ele)) return getLogicalOperator(ele, inverse);
    if (ele === '!') {
      inverse = true
      return null;
    };
    return ele;
  });
};

/**
 * Trasnforma os predicatos de array para JiiPredicate
 * 
 * @param v 
 */
let parser3 = (v: any[]): any[] => {
  if (Array.isArray(v) && isComparisonOperator(v[1])) return [new JiiPredicate(v[0], v[1], v[2])];
  return v.map(ele => {
    if (Array.isArray(ele)) {
      if (isComparisonOperator(ele[1])) {
        return new JiiPredicate(ele[0], ele[1], ele[2]);
      }
      return parser3(ele);
    }
    return ele;
  });
}

/**
 * Une os predicados em lógicas booleanas OR (JiiDisjunction) e AND (JiiConjunction) gerando um JiiFilter.
 * 
 * @param v 
 * @param jiiFilterable 
 */
let parser4 = (v: any[], jiiFilterable: JiiFilterable = new JiiConjunction()): JiiFilterable => {
  jiiFilterable = hasAndOperator(v) ? new JiiConjunction() : new JiiDisjunction();
  v.forEach(ele => {
    if (Array.isArray(ele)) {
      if (hasAndOperator(ele)) {
        jiiFilterable.predicates.push(parser4(ele, new JiiConjunction()));
        return;
      }
      jiiFilterable.predicates.push(parser4(ele, new JiiDisjunction()));
    }
    if (isSimplePredicate(ele)) {
      jiiFilterable.predicates.push(ele);
    }
  });
  return jiiFilterable;
}