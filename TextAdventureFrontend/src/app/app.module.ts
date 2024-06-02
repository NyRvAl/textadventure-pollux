import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app.routes';

import { AppComponent } from './app.component';
import { ButtonModule } from 'primeng/button';
import { MenubarModule } from 'primeng/menubar'; // Corrected import

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    ButtonModule,
    AppRoutingModule,
    MenubarModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
