import { Component, OnInit } from '@angular/core';
import {PrimeNGConfig} from "primeng/api";
import {ThemeToggleService} from "../theme-toggle.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  items: any;
  constructor(private primengConfig: PrimeNGConfig, private themeService: ThemeToggleService) { }
  currentTheme = "dark";
  ngOnInit() {
    // Define your menu items here
    this.primengConfig.ripple = true;

    this.items = [
      {label: 'Home', icon: 'pi pi-home', routerLink: '/home' },
      {label: 'Game', routerLink: '/create'}

      // Add more menu items as needed
    ];
    // this.switchThemes();
  }
  switchThemes(){
    if(this.currentTheme === "light"){
      this.currentTheme = "dark";
    }
    else if( this.currentTheme === "dark"){
      this.currentTheme = "light";
    }
    console.log("try to switch to "+this.currentTheme+" ...")
    this.themeService.switchTheme(this.currentTheme);
  }
}
