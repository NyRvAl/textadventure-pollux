import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'TextAdventureFrontend';

  items: any;

  ngOnInit(): void {
    this.items = [
      {label: 'Home', icon: 'pi pi-home', routerLink: '/mboot' },
      {label: 'Mboot', routerLink: '/mboot'},
      {label: 'Controller', routerLink: '/controller'},
      {label: 'Settings',routerLink: '/settings'}

      // Add more menu items as needed
    ];
  }
}
