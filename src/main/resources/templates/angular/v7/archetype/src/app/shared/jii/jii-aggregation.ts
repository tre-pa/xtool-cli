/**
 * Agregação resultado de um request à um endpoint do pacote Jii
 * 
 * - dataField: string (coluna de agregação)
 * - operation: 'count' | 'sum' | 'max' | 'min' | 'avg' (operação de agregação)
 * - result: number (resultado da agregação)
 * 
 */
export class JiiAggregation {
  /** Coluna de agregação da Entidade de trabalho */
  dataField: string;
  /** Operação de agregação desempenhada na coluna da Entidade de trabalho  */
  operation: 'count' | 'sum' | 'max' | 'min' | 'avg';
  /** Resultado de uma operação de agregação desempenhada em uma coluna da Entidade de trabalho */
  result: number;
}