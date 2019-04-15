import { JiiExpression } from './jii-expression';

export class JiiPredicate extends JiiExpression {
  constructor(
    private dataField: string,
    op: string,
    private value: any) {
    super()
    this.type = op;
  }
}
