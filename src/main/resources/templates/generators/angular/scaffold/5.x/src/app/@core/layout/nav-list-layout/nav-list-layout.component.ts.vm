import { TdMediaService } from '@covalent/core';
import { Component, OnInit, AfterViewInit, Input, ChangeDetectorRef } from '@angular/core';


@Component({
  selector: 'nav-list-layout',
  templateUrl: './nav-list-layout.component.html',
  styleUrls: ['./nav-list-layout.component.css']
})
export class NavListLayoutComponent implements OnInit, AfterViewInit {

  @Input() isToolbarSidNav: boolean = false;
  @Input() toolbarSidNavTitle: string = '';
  @Input() isSubFooter: boolean = false;
  @Input() isMainFooter: boolean = false;
  @Input() toolbarContentTitle: string;
  @Input() formTitle: string;
  @Input() sidenavWidth: string = '257';

  /**
   *
   * @param media
   * @param _changeDetectorRef
   */
  constructor(
    public media: TdMediaService,
    private _changeDetectorRef: ChangeDetectorRef
  ) { }

  ngOnInit() {

  }

  /**
   *
   */
  ngAfterViewInit(): void {
    this.media.broadcast();
    this._changeDetectorRef.detectChanges();
    // broadcast to all listener observables when loading the page
  }

}
