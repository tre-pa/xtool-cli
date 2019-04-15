import { JiiExpression } from './jii-expression';

/**
 * Expressão do filtro usada para fazer o request à um endpoint
 * findAll() do pacote Jii, podendo ela ser simples ou complexa
 */
export class JiiFilterable extends JiiExpression {
  predicates: JiiExpression[] = [];
}
