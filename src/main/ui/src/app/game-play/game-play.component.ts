import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/internal/Subscription';
import { Game } from '../shared/models/Game.model';
import { GameInfo } from '../shared/models/gameInfo.model';
import { GamesService } from '../_services/games.service';

import { Map } from "../shared/models/Map.model";
import { Player } from '../shared/models/Player.model';
import { GameState } from "../shared/models/GameState.model";

@Component({
  selector: 'app-game-play',
  templateUrl: './game-play.component.html',
  styleUrls: ['./game-play.component.css']
})
export class GamePlayComponent implements OnInit {
  private routeSub: Subscription;
  game: GameInfo; //TODO: cambiar a Game
  gameId: Number;


  mapMock: Map = {
    maxHeight: 9, minHeight: 2, distMax: 4,
    province: {
      name: 'Córdoba',
      municipalities: [{
        gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
        mode: { multDef: 4, coefProdGauchos: 6 }
      }]
    }
  }

  playersMock: Array<Player> = [{
    username: "testUser", municipalities: [{
      gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
      mode: { multDef: 4, coefProdGauchos: 6 }
    }]
  },
  {
    username: "testUser2", municipalities: [{
      gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
      mode: { multDef: 4, coefProdGauchos: 6 }
    }]
  }

  ];

  gameMock: Game = {
    province: "Cordoba", date: new Date("2018-03-16"), map: this.mapMock,
    state: GameState.FINISHED, players: this.playersMock
  };

  constructor(private gamesService: GamesService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      this.gameId = params['id']
    });
    this.gamesService.getGame(this.gameId).subscribe(data => this.game = data);
  }

  

}
