import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Game } from '../shared/models/Game.model';
import { GamesService } from '../_services/games.service';

@Component({
  selector: 'app-game-play',
  templateUrl: './game-play.component.html',
  styleUrls: ['./game-play.component.css']
})
export class GamePlayComponent implements OnInit {

  constructor(private gamesService: GamesService) { }

  ngOnInit(): void {
    this.gamesService.getGame(3);
  }

}
