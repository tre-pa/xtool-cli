import { QyExpression } from './qy-expression';

export class QyPredicate extends QyExpression {
  constructor(
    private dataField: string,
    op: string,
    private value: any) {
    super()
    this.type = op;
  }
}
