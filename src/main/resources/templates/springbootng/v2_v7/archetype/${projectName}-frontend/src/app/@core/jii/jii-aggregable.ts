/**
 * Informação das agregações à serem feitas, oriundo do LoadOptions (parâmetro do load de um JiiStore)
 * 
 * - selector: string (coluna de agregação)
 * - operation: 'count' | 'sum' | 'max' | 'min' | 'avg' (operador de agregação)
 * 
 */
export class JiiAggregable {
  /** Coluna de agregação da Entidade de trabalho */
  selector: string;
  /** Operação de agregação à ser desempenhada na coluna da Entidade de trabalho  */
  operation: 'count' | 'sum' | 'max' | 'min' | 'avg';
}
