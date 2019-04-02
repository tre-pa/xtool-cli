import { QyExpression } from './qy-expression';

/**
 * Expressão do filtro usada para fazer o request à um endpoint
 * findAll() do pacote Qy, podendo ela ser simples ou complexa
 */
export class QyFilterable extends QyExpression {
  predicates: QyExpression[] = [];
}
