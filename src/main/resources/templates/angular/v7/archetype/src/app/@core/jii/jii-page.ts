import { JiiAggregation } from './jii-aggregation';
import { Page } from 'src/app/@core/page';

/**
 * Objeto de retorno de um request à um endpoint findAll() do pacote Jii, contendo: 
 * 
 * - pagination: Page<T> (página requisitada)
 * - aggregations?: JiiAggregation[] (resultado das agregações requisitadas)
 * 
 */
export class JiiPage<T> {
  /** Objeto padrão de paginação */
  pagination: Page<T>;
  /** Objeto com o resultado das agregações requisitadas à um endpoint findAll() do pacote Jii */
  aggregations?: JiiAggregation[];
}
