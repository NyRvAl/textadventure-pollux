import { Component, OnDestroy, OnInit, ViewChild, ElementRef, AfterViewChecked } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompatClient, IMessage, Stomp } from '@stomp/stompjs';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.scss']
})
export class ChatroomComponent implements OnInit, OnDestroy, AfterViewChecked {
  room!: string | null;
  messages: string[] = [];
  userName!: string | null;
  private connection: CompatClient | undefined;
  userInput: string = '';
  @ViewChild('messagesContainer') private messagesContainer!: ElementRef;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.room = params.get('id');
      this.userName = this.route.snapshot.queryParamMap.get('username');

      if (this.room) {
        this.connect(this.room);
      }
    });
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    this.messagesContainer.nativeElement.scrollTop = this.messagesContainer.nativeElement.scrollHeight;
  }

  connect(room: string): void {
    this.connection = Stomp.client('ws://localhost:5000/ws');
    this.connection.connect({}, () => {
      console.log('Connected');
      console.log('STOMP connection established');
      this.connection?.subscribe(`/topic/${room}`, (message: IMessage) => {
        const parsedMessage = JSON.parse(message.body);
        if (parsedMessage.output) {
          this.handleActions(parsedMessage.output);
        }
      });
      console.log("username: " + this.userName);
      this.connection?.send(`/app/textadventure.send/${room}`, {}, JSON.stringify({
        user: this.userName,
        type: 'JOIN',
        message: this.userName + ' joined'
      }));
    });
  }

  handleActions(actions: any[]): void {
    let currentIndex = 0;

    const processNextAction = () => {
      if (currentIndex < actions.length) {
        const action = actions[currentIndex];
        currentIndex++;

        if (action.action === 'WRITE') {
          this.messages.push(action.text);
          console.log(action.text);
          processNextAction(); // Move to the next action immediately
        } else if (action.action === 'SLEEP') {
          setTimeout(() => {
            processNextAction(); // Move to the next action after the specified time
          }, action.time);
        }
      }
    };

    processNextAction();
  }

  sendMessage(message: string): void {
    if (this.connection?.connected && this.room) {
      this.connection.send(`/app/textadventure.send/${this.room}`, {}, JSON.stringify({
        user: this.userName || 'User',
        type: 'RESPONSE',
        message: message
      }));
      this.userInput = ''; // Clear the input field after sending the message
    } else {
      console.error('STOMP connection not established');
    }
  }
  copyRoomCode(){
    navigator.clipboard.writeText(this.room ?? "").then(() => {
      console.log('Text copied to clipboard');
    }).catch(err => {
      console.error('Could not copy text: ', err);
    });
  }
  ngOnDestroy() {

    if (this.connection) {
      this.connection.disconnect(() => {
        console.log('Disconnected');
      });
    }
  }
}
