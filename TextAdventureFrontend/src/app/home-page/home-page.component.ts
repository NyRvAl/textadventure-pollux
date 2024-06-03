import { Component } from '@angular/core';
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent {
  constructor(private router : Router) {
  }
  exploreGame(){
    this.navigateToAnotherURL("/create")
  }
  join(){
    this.navigateToAnotherURL("/create")
  }
  navigateToAnotherURL(url: string) {

    this.router.navigate([url]);
  }
}
