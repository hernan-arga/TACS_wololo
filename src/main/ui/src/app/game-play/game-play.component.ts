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
import { FormControl } from '@angular/forms';
import { TokenStorageService } from '../_services/token-storage.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-game-play',
  templateUrl: './game-play.component.html',
  styleUrls: ['./game-play.component.css']
})
export class GamePlayComponent implements OnInit {
  private routeSub: Subscription;
  selectControl: FormControl = new FormControl();
  currentUserUsername: String;

  municipalityInAction: Municipality;
  attackMode: boolean = false;
  moveMode: boolean = false;

  game: GameInfo; //TODO: cambiar a Game
  gameId: Number;
  imgSizeX: number = 366;
  imgSizeY: number = 557;

  matrizPositions: MapPosition[][] = new Array<Array<MapPosition>>();

  mapMock: Map = {
    latMax: -30.9830362325549, lonMax: -63.7340063162336, latMin: -34.0601407054622, lonMin: -64.5025780020643,
    maxHeight: 9, minHeight: 2, distMax: 4,
    province: {
      name: 'Córdoba',
      municipalities: [
        {
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
        },
        {
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
    private tokenStorageService: TokenStorageService,
    private coordinateService: CoordinatesService,
    private cdRef:ChangeDetectorRef) { }

  ngOnInit(): void {

    const user = this.tokenStorageService.getUser();
    this.currentUserUsername = user.username;

    this.routeSub = this.route.params.subscribe(params => {
      this.gameId = params['id']
    });
    this.gamesService.getGame(this.gameId).subscribe(data => this.game = data);

    this.gameMock.players.forEach(p => p.municipalities
      .forEach(m => { this.convertCoordinates(m); this.assignPositionToMunicipality(m); }
      ));
  }

  //Ecuaciones de interpolacion
  private assignPositionToMunicipality(municipality: Municipality) {
    let lonMun = municipality.centroide.lon, latMun = municipality.centroide.lat,
      latMin = this.gameMock.map.latMin, lonMin = this.gameMock.map.lonMin,
      latMax = this.gameMock.map.latMax, lonMax = this.gameMock.map.lonMax;

    municipality.posX = this.imgSizeX * 0.15 + (lonMun - lonMin) * (this.imgSizeX * 0.70 - this.imgSizeX * 0.15) / (lonMax - lonMin);
    municipality.posY = this.imgSizeY * 0.15 + (latMun - latMax) * (this.imgSizeY * 0.80 - this.imgSizeY * 0.15) / (latMin - latMax);
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

  public realizeAction(municipalitiy: Municipality) {
    switch (this.selectControl.value) {
      case 'prepareAttack': 
        this.attackMode = true;
        this.municipalityInAction = municipalitiy;
        this.cdRef.detectChanges(); // Esto es porque tira Expression has changed after it was checked        
        break;
      case 'prepareMove':
        this.moveMode = true;
        this.municipalityInAction = municipalitiy;
        this.cdRef.detectChanges();
        break;
      case 'modify': 
        this.modifySpecialization(municipalitiy);
        break;
      case 'attack':
        this.attack(municipalitiy);
        break;
      case 'move':
        this.moveGauchos(municipalitiy);
        break;
      default: 
        throw("Se pidio una accion invalida");
    }
  }

  public attack(municipality: Municipality) {
    console.log("Atacar desde: "+this.municipalityInAction.nombre+" a "+municipality.nombre);
  }

  public moveGauchos(municipality: Municipality) {
    console.log("Mover gauchos desde: "+this.municipalityInAction.nombre+" a "+municipality.nombre);
  }

  public modifySpecialization(municipality: Municipality) {
    console.log(municipality.mode);
  }

  public playerIsCurrentUser(player: Player): boolean {
    return player.username == this.currentUserUsername;
  }

  public isCurrentPlayerOption(player: Player): boolean{
    return this.playerIsCurrentUser(player);
  }

  public isSelectDisabled(player: Player): boolean{
    return (!this.playerIsCurrentUser(player) && !this.attackMode) ||
          (this.playerIsCurrentUser(player) && this.attackMode) ||
          (this.playerIsCurrentUser(player) && this.moveMode ); //que desactive solo el que se selecciono para mover
  }

}
