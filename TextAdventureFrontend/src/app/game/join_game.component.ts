import {Component, OnInit, ViewChild} from '@angular/core';
import {JoystickEvent, NgxJoystickComponent} from "ngx-joystick";
import {Router} from "@angular/router";
import {JoystickManagerOptions, JoystickOutputData} from "nipplejs";
import {Vector} from "../../helper/Vector";
import {InputSwitchChangeEvent} from "primeng/inputswitch";
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http'; // Import HttpClientModule
@Component({
  selector: 'app-game',
  templateUrl: './create_game.component.html',
  styleUrls: ['./join_game.component.scss']
})
export class Join_gameComponent implements OnInit{
  ngOnInit(): void {
      this.fetchAvailableGames();
  }
  constructor(private http: HttpClient, private router: Router) {
    this.suggestions = []; // Initialize suggestions as an empty array
  } // Inject HttpClient in the constructor

  userName!: string;
  joinGameDialog = false;
  ratingDialog = false;
  value!:string;
  gamecode!:string;
  selectedGameId!:string;
  values!:any;
  suggestions: any
  createDialog = false;
  createOpt = [{
    label: 'Review',
    icon: 'pi pi-star',
    command: () =>{
      this.review();
    }
  }, {
    label: 'Report',
    icon: 'pi pi-exclamation-triangle',
    command: ()=>{
      this.report();
    }
  }]
  create(gameId:string){
    this.createDialog = true;
    this.selectedGameId = gameId;
  }
  createGame(){
    this.createRoom(this.selectedGameId,this.userName).then((result) =>{
      this.navigateToAnotherURL(result.roomId,this.userName);
      this.createDialog = false;

    });


  }
  review(){
    this.ratingDialog = true;
  }
  report(){}
  getSeverity(tag: string){
    tag = tag.toLowerCase();
    if(tag === "new")
      return "success";
    else if(tag === "available")
      return "available";
    else if(tag ==="n.a")
      return "danger";
    return "warning";
  }
  getActiveIcon(active: boolean){
    return active ? "pi pi-check" : "pi pi-times";
  }
  join() {
    this.joinGameDialog = true;
    // Fetch chatrooms or available games and populate suggestions
    this.http.get<any[]>('http://localhost:5000/allChatrooms').subscribe(
      (response) => {
        // Format the received data to match the format of suggestions
        this.suggestions = response.map(game => game.id);
        console.log(this.suggestions);
      },
      (error) => {
        console.error('Error fetching available games:', error);
      }
    );
  }
  joinGame(){
    this.joinGameDialog = false;
    this.navigateToAnotherURL(this.gamecode,this.value);
  }
  fetchAvailableGames() {
    // Make an HTTP GET request to your backend endpoint
    this.http.get<any[]>('http://localhost:5000/availableGames').subscribe(
      (response) => {
        // Format the received data to match the format of values
        this.values = response.map(game => ({
          id: game.adventureGameModel.id,
          name: game.gameInfoDTO.displayName,
          author: game.gameInfoDTO.author ?? "n.a",
          rating: (game.adventureGameModel != null && game.adventureGameModel.amountRating != 0 ? game.adventureGameModel.ratingSummedUp / game.adventureGameModel.amountRating : 0),
          available: game.adventureGameModel.available ?? true
        }));
      },
      (error) => {
        console.error('Error fetching available games:', error);
        // Handle error
      }
    );
  }
  createRoom(gameId: string, username: string): Promise<any> {
    const createGameRoomDTO = { "gameId":Number(gameId), "username":username };
    const url = 'http://localhost:5000/newChatroom';
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    console.log("Body "+JSON.stringify(createGameRoomDTO))
    return new Promise<any>((resolve, reject) => {
      this.http.post<any>(url, JSON.stringify(createGameRoomDTO), { headers }).subscribe(
        (response) => {
          resolve(response);
        },
        (error) => {
          reject(error);
        }
      );
    });
  }
  navigateToAnotherURL(room: string,username: string) {

    this.router.navigate(['/room/'+room], { queryParams: { username: username } });
  }
}
