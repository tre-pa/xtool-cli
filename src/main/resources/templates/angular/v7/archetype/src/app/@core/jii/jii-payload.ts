import { JiiAggregable } from './jii-aggregable';
import { JiiFilterable } from './filter/jii-filterable';

/**
 * Payload usado como body no request de load do JiiStore, ou na
 * chamada de um endpoint findAll() do pacore Jii, contendo:
 * 
 * - filterable?: JiiFilterable (filtro que será usado)
 * - aggregables?: JiiAggregable[] (agregações que devem ser feitas)
 * 
 */
export class JiiPayload {
  /** Informação do filtro que será usado em uma consulta */
  filterable?: JiiFilterable;
  /** Informação das agregações que devem ser feitas em uma consulta */
  aggregables?: JiiAggregable[];
}
