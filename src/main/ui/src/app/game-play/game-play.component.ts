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
import { CoordinatesService } from '../_services/coordinates.service';
import { Municipality } from '../shared/models/municipality.model';
import { MapPosition } from '../shared/models/mapPosition.model';

@Component({
  selector: 'app-game-play',
  templateUrl: './game-play.component.html',
  styleUrls: ['./game-play.component.css']
})
export class GamePlayComponent implements OnInit {
  private routeSub: Subscription;
  game: GameInfo; //TODO: cambiar a Game
  gameId: Number;
  imgSizeX: number = 366;
  imgSizeY: number = 557;

  matrizPositions: MapPosition[][] = new Array<Array<MapPosition>>();

  mapMock: Map = {
    maxHeight: 9, minHeight: 2, distMax: 4,
    province: {
      name: 'Córdoba',
      municipalities: [{
        nombre: 'Municipalidad 1',
        posX: 0,
        posY: 0,
        centroide: { lat: -32.546237695526, lon: -62.9807310033667 },
        gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
        mode: { multDef: 4, coefProdGauchos: 6 }
      }]
    }
  }

  playersMock: Array<Player> = [{
    username: "testUser", municipalities: [{
      nombre: 'Municipalidad 2',
      posX: 0,
      posY: 0,
      centroide: { lat: -34.0601407054622, lon: -63.7340063162336 },
      gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
      mode: { multDef: 4, coefProdGauchos: 6 }
    },
    {
      nombre: 'Municipalidad 3',
      posX: 0,
      posY: 0,
      centroide: { lat: -31.1729826637303, lon: -64.3191044525332 },
      gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
      mode: { multDef: 4, coefProdGauchos: 6 }
    }
    ]
  },
  {
    username: "testUser2", municipalities: [{
      nombre: 'Municipalidad 4',
      posX: 0,
      posY: 0,
      centroide: { lat: -31.3121237322311, lon: -64.5025780020643 },
      gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
      mode: { multDef: 4, coefProdGauchos: 6 }
    },
    {
      nombre: 'Municipalidad 5',
      posX: 0,
      posY: 0,
      centroide: { lat: -30.9830362325549, lon: -64.1282788962145 },
      gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
      mode: { multDef: 4, coefProdGauchos: 6 }
    }
    ]
  }

  ];

  gameMock: Game = {
    province: "Cordoba", date: new Date("2018-03-16"), map: this.mapMock,
    state: GameState.FINISHED, players: this.playersMock
  };

  constructor(private gamesService: GamesService, private route: ActivatedRoute,
    private coordinateService: CoordinatesService) { }

  ngOnInit(): void {

    this.initializeMatrizPosition();

    this.routeSub = this.route.params.subscribe(params => {
      this.gameId = params['id']
    });
    this.gamesService.getGame(this.gameId).subscribe(data => this.game = data);

    this.gameMock.players.forEach(p => p.municipalities
      .forEach(m => {this.convertCoordinates(m); this.assignPositionToMunicipality(m);}
    ));
    
    
  }

  private initializeMatrizPosition() {
    for (let y = 0; y <= 10; y++) {
      let row: MapPosition[] = new Array<MapPosition>();
      for (let x = 0; x <= 10; x++) {
        row.push(new MapPosition(this.imgSizeX / 10 * (x+1), this.imgSizeY / 10 * (y+1)));
      }
      this.matrizPositions.push(row);
    }

  }

  private assignPositionToMunicipality(municipality: Municipality) {
    var i = 1, j = 1;

    while(municipality.posX > this.matrizPositions[i][j].posX){
      j++;
    }
    
    while(municipality.posY > this.matrizPositions[i][j].posY){
      i++;
    }

    if(this.matrizPositions[i][j].free){
      municipality.posX = this.matrizPositions[i][j].posX;
      municipality.posY = this.matrizPositions[i][j].posY;
      this.matrizPositions[i][j].free = false;
    }
    
    else{
      while(!this.matrizPositions[i][j].free){
        i++;
        while(!this.matrizPositions[i][j].free){
          j++;
        }
      }
      municipality.posX = this.matrizPositions[i][j].posX;
      municipality.posY = this.matrizPositions[i][j].posY;
      this.matrizPositions[i][j].free = false;
    }
  }

  private convertCoordinates(municipalitiy: Municipality) {
    this.coordinateService.convertCoordinates(municipalitiy, this.imgSizeX, this.imgSizeY);
  }

  public calculatePositionAndColor(i: number, municipality: Municipality) {

    let colors = ['Green', 'Red', 'Purple', 'Black'];

    let color = colors[i - 1];

    return {
      "top": municipality.posY.toString() + 'px',
      "left": municipality.posX.toString() + 'px',
      "background": color
    }
  }

}
