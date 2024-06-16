import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomePageComponent} from "./home-page/home-page.component";
import {Join_gameComponent} from "./game/join_game.component";
import {ChatroomComponent} from "./chatroom/chatroom.component";

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {path: 'create', component: Join_gameComponent},
  {path: 'home', component: HomePageComponent},
  {path : 'room/:id', component: ChatroomComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
