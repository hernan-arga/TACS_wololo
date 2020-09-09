import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Game } from "../shared/models/Game.model";

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {
  content = '';
  games: Array<Game>;

  constructor(private userService: UserService) { }

  ngOnInit(): void {

    this.userService.getAdminBoard().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
    
  }

}
