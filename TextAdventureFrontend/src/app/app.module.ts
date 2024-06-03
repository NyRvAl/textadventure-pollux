import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http'; // Import HttpClientModule

import {AppComponent} from './app.component';
import {ButtonModule} from 'primeng/button';
import {NgxJoystickModule} from "ngx-joystick";
import {HomePageComponent} from './home-page/home-page.component';
import {Join_gameComponent} from './game/join_game.component';
import {MenuModule} from "primeng/menu";
import {MenubarModule} from "primeng/menubar";
import {RippleModule} from "primeng/ripple";
import {TerminalModule, TerminalService} from "primeng/terminal";
import {InputSwitchModule} from "primeng/inputswitch";
import {SettingsComponent} from './settings/settings.component';
import {CardModule} from "primeng/card";
import {PanelModule} from "primeng/panel";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RatingModule} from "primeng/rating";
import {TableModule} from "primeng/table";
import {FormsModule} from '@angular/forms';
import {TagModule} from "primeng/tag";
import {SplitButtonModule} from "primeng/splitbutton";
import {FloatLabelModule} from "primeng/floatlabel";
import {DialogModule} from "primeng/dialog";
import {InputTextModule} from "primeng/inputtext";
import {AutoCompleteModule} from "primeng/autocomplete";
import {ChatroomComponent} from './chatroom/chatroom.component';
import { IngameComponent } from './ingame/ingame.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    Join_gameComponent,
    SettingsComponent,
    ChatroomComponent,
    IngameComponent,

  ],
  imports: [
    FormsModule, BrowserAnimationsModule, BrowserModule, ButtonModule, NgxJoystickModule, AppRoutingModule, MenuModule, MenubarModule, RippleModule, TerminalModule, InputSwitchModule, CardModule, PanelModule, RatingModule, TableModule, TagModule, SplitButtonModule, FloatLabelModule, DialogModule, InputTextModule, AutoCompleteModule,    HttpClientModule // Add HttpClientModule to the imports array

  ],
  providers: [TerminalService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
