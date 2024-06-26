import {Inject, Injectable} from '@angular/core';
import {DOCUMENT} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class ThemeToggleService {

  constructor(@Inject(DOCUMENT) private document: Document) { }

  switchTheme(theme: string) {
    let themeLink = this.document.getElementById('app-theme') as HTMLLinkElement;
    console.log(themeLink);
    if (themeLink) {
      themeLink.href = "assets/"+theme + '-theme.css'; // bundle name
      console.log(themeLink.href)
    }
  }}
