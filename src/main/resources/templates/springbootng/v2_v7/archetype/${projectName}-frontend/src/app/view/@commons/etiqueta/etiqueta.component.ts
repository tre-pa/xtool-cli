import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-etiqueta',
  templateUrl: './etiqueta.component.html',
  styleUrls: ['./etiqueta.component.scss']
})
export class EtiquetaComponent implements OnInit {

  @Input()
  public corTexto = '#000';
  @Input()
  public corFundo = '#FFF';
  @Input()
  public texto = '';
  @Input()
  public fontSize = "15px";

  constructor() { }

  ngOnInit() {}

}
