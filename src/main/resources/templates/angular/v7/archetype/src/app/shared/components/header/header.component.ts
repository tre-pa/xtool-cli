import {
  EventEmitter,
  Component,
  NgModule,
  Output,
  Input,
  OnInit
} from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DxContextMenuModule } from 'devextreme-angular/ui/context-menu';
import { DxTemplateModule } from 'devextreme-angular/core/template';
import { DxToolbarModule } from 'devextreme-angular/ui/toolbar';
import { DxButtonModule } from 'devextreme-angular/ui/button';
import { DxPopupModule } from 'devextreme-angular/ui/popup';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';

import { SecurityService } from 'src/app/@security/security.service';
import { UserInfo } from 'src/app/@security/keycloak/user-info';

library.add(fas);

@Component({
  selector: 'app-header',
  templateUrl: 'header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Output()
  menuToggle = new EventEmitter<boolean>();

  @Input()
  menuToggleEnabled = false;

  @Input()
  toolbarTitle: string;

  isUserAuthorized = true;

  userInfo: UserInfo;

  colors = ['#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'];

  userMenuItems: any = [{
    text: 'userinfo',
    closeMenuOnClick: false
  }, {
    text: 'Gerência de Relatórios',
    icon: 'file-alt',
    isHidden: false
  }, {
    text: 'Logout',
    icon: 'running'
  }];

  constructor(
    private router: Router,
    private securityService: SecurityService
  ) { }

  ngOnInit(): void {
    this.userInfo = this.securityService.getUserInfo();
    if (!this.securityService.hasResourceRole('REPORT_MANAGER')) {
      this.userMenuItems[1].isHidden = true;
    }
  }

  toggleMenu = () => {
    this.menuToggle.emit();
  }

  onLoginClick(args) {
    this.isUserAuthorized = true;
  }

  onUserMenuItemClick(item) {
    if (item === this.userMenuItems[1]) this.router.navigate(['/report-mngt']);
    if (item === this.userMenuItems[2]) this.securityService.logout();
  }

  getInitials(): string {
    let aaa: string[] = []
    if (this.userInfo.name) aaa = this.userInfo.name.split(' ');
    else aaa = [this.userInfo.preferred_username];
    return aaa[0].charAt(0) + aaa[aaa.length - 1].charAt(0);
  }

  randonColorStyle() {
    var hash = 0;
    for (var i = 0; i < this.getInitials().length; i++)
      hash = 31 * hash + this.getInitials().charCodeAt(i);
    var index = Math.abs(hash % this.colors.length);
    return { 'background-color': this.colors[index] };
  }

}

@NgModule({
  imports: [
    DxContextMenuModule,
    FontAwesomeModule,
    FlexLayoutModule,
    DxTemplateModule,
    DxToolbarModule,
    DxButtonModule,
    DxPopupModule,
    CommonModule
  ],
  declarations: [HeaderComponent],
  exports: [HeaderComponent]
})
export class HeaderModule { }
