import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-etiqueta-material',
  templateUrl: './etiqueta-material.component.html',
  styleUrls: ['./etiqueta-material.component.scss']
})
export class EtiquetaMaterialComponent implements OnInit {

  @Input()
  public corTexto = '#000';
  @Input()
  public corFundo = '#FFF';
  @Input()
  public texto = '';
  @Input()
  public fontSize = "9px";

  constructor() { }

  ngOnInit() {
  }

}
