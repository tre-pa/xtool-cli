import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.css']
})
export class MainLayoutComponent implements OnInit{

  routes: Object[] = [
    {
      title: 'dashboard',
      route: '/',
      icon: 'dashboard',
    }
  ];

  userInfo: any = { email: '', family_name: '', given_name: '', name: '', preferred_username: '', sub: '' };

  constructor(private _router: Router) { }

  ngOnInit(): void {
  }

  logout(): void {
    this._router.navigate(['/']);
  }

}
