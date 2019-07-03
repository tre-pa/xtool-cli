import { KeycloakService } from './../../../@security/keycloak.service';
import {
  EventEmitter,
  Component,
  OnDestroy,
  ViewChild,
  NgModule,
  Output,
  OnInit,
  Input
} from '@angular/core';
import { Router } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DxTemplateModule } from 'devextreme-angular/core/template';
import {
  DxTreeViewComponent,
  DxTreeViewModule
} from 'devextreme-angular/ui/tree-view';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';


library.add(fas);

@Component({
  selector: 'app-side-navigation-menu',
  templateUrl: './side-navigation-menu.component.html',
  styleUrls: ['./side-navigation-menu.component.scss']
})
export class SideNavigationMenuComponent implements OnInit, OnDestroy {

  @ViewChild(DxTreeViewComponent)
  menu: DxTreeViewComponent;

  @Output()
  selectedItemChanged = new EventEmitter<string>();

  @Input()
  items: any[];

  @Input()
  set selectedItem(value: String) {
    if (this.menu.instance) this.menu.instance.selectItem(value);
  }

  private _compactMode = false;

  @Input()
  get compactMode() {
    return this._compactMode;
  }
  set compactMode(val) {
    this._compactMode = val;
    if (val && this.menu.instance) this.menu.instance.collapseAll();
  }

  constructor(
    private keycloakService: KeycloakService,
    private router: Router
  ) { }

  ngOnInit(): void {
    let matchItem = this.getPathMatchItem(this.items, this.router.url.split('?')[0]);
    if (matchItem) matchItem['selected'] = true;

    this.filterItemsByRolePermission();
  }

  ngOnDestroy(): void {
    this.deselectAll(this.menu.items);
  }

  private filterItemsByRolePermission() {
    this.items = this.items.filter(item => this.userIsAllowed(item.require));
  }

  private userIsAllowed(require: string | string[]): boolean {
    if (!require) return true;
    if (require instanceof Array) {
      for (const r of require)
        if (this.userHasRole(r))
          return true;
      return false;
    } else {
      return this.userHasRole(require);
    }
  }

  private userHasRole(role: string): boolean {
    return this.keycloakService.hasResourceRole(role) || this.keycloakService.hasRealmRole(role)
  }

  private getPathMatchItem(items: any[], url: string): any {
    let matchItem = undefined;

    matchItem = items.find(item => url === item.path);
    if (matchItem) return matchItem;
    matchItem = items.find(item => url.startsWith(item.path));
    if (matchItem) return matchItem;
    items.forEach(item => {
      if (item.items) {
        let childMatchItem = this.getPathMatchItem(item.items, url);
        if (childMatchItem) matchItem = childMatchItem;
      }
    });

    return matchItem;
  }

  deselectAll(items: any[]) {
    items.forEach(item => {
      if (item.selected) item.selected = false;
      if (item.items) this.deselectAll(item.items);
    });
  }

  updateSelection(event) {
    const nodeClass = 'dx-treeview-node';
    const selectedClass = 'dx-state-selected';
    const leafNodeClass = 'dx-treeview-node-is-leaf';
    const element: HTMLElement = event.element;

    const rootNodes = element.querySelectorAll(`.${nodeClass}:not(.${leafNodeClass}`);
    Array.from(rootNodes)
      .forEach(node => node.classList.remove(selectedClass));

    let selectedNode = element.querySelector(`.${nodeClass}.${selectedClass}`);
    while (selectedNode && selectedNode.parentElement) {
      if (selectedNode.classList.contains(nodeClass)) selectedNode.classList.add(selectedClass);
      selectedNode = selectedNode.parentElement;
    }
  }

  onItemClick(event) {
    this.selectedItemChanged.emit(event);
  }

  onMenuInitialized(event) {
    event.component.option('deferRendering', false);
  }

}

@NgModule({
  imports: [
    FontAwesomeModule,
    FlexLayoutModule,
    DxTemplateModule,
    DxTreeViewModule
  ],
  declarations: [SideNavigationMenuComponent],
  exports: [SideNavigationMenuComponent]
})
export class SideNavigationMenuModule { }
