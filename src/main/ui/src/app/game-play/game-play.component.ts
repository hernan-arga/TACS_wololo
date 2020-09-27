import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/internal/Subscription';
import { Game } from '../shared/models/Game.model';
import { GameInfo } from '../shared/models/gameInfo.model';
import { GamesService } from '../_services/games.service';

import { Player } from '../shared/models/Player.model';
import { GameState } from "../shared/models/GameState.model";
import { CoordinatesService } from '../_services/coordinates.service';
import { Municipality } from '../shared/models/municipality.model';
import { MapPosition } from '../shared/models/mapPosition.model';
import { FormControl, FormGroup, Validator } from '@angular/forms';
import { TokenStorageService } from '../_services/token-storage.service';
import { ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { GameMoveGauchosComponent } from '../game-move-gauchos/game-move-gauchos.component';
import { GameNotTurnToPlayComponent } from '../game-not-turn-to-play/game-not-turn-to-play.component';
import { delay } from 'rxjs/operators';
import { Action } from '../shared/models/action.model';
import { GameMovementSuccessfulComponent } from '../game-movement-successful/game-movement-successful.component';
import { GameShowMunicipalityStatisticsComponent } from '../game-show-municipality-statistics/game-show-municipality-statistics.component';

@Component({
  selector: 'app-game-play',
  templateUrl: './game-play.component.html',
  styleUrls: ['./game-play.component.css']
})
export class GamePlayComponent implements OnInit {
  private routeSub: Subscription;

  isLoading = true;

  selectForm: FormGroup;
  currentUserUsername: String;

  municipalityInAction: Municipality;
  attackMode: boolean = false;
  moveMode: boolean = false;
  alreadyPlayed: boolean = false;
  isCurrentlyUserTurn: boolean;
  cantGauchosToMove: number = 0;

  colorsOfMunicipalities: Array<string> = ['Green', 'Red', 'Purple', 'Black'];

  game: Game; //TODO: cambiar a Game
  gameId: Number;
  imgSizeX: number = 366;
  imgSizeY: number = 557;

  matrizPositions: MapPosition[][] = new Array<Array<MapPosition>>();

  municipalitiesByUser: any;
  
  constructor(private gamesService: GamesService, private route: ActivatedRoute,
    private tokenStorageService: TokenStorageService,
    private coordinateService: CoordinatesService,
    private cdRef: ChangeDetectorRef,
    public dialog: MatDialog) { }

  ngOnInit(): void {

    this.routeSub = this.route.params.subscribe(params => {
      this.gameId = params['id']
    });
    this.gamesService.getGame(this.gameId).subscribe(
      data => {
        this.game = data;
        this.municipalitiesByUser = this.groupByKey(this.game.municipalities, 'owner');

        this.initializeVariables();

        this.game.municipalities.forEach(m => 
          { this.convertCoordinates(m); this.assignPositionToMunicipality(m); }
        );

        if (!this.isCurrentlyUserTurn) {
          this.openNotYourTurnDialog();
        }

        this.isLoading = false;
      }
    );
  }

  private groupByKey(array, key) {
    return array
      .reduce((hash, obj) => {
        if(obj[key] === undefined) return hash; 
        return Object.assign(hash, { [obj[key]]:( hash[obj[key]] || [] ).concat(obj)})
      }, {})
 }
 

  private initializeVariables() {
    const user = this.tokenStorageService.getUser();
    this.currentUserUsername = user.username;
    this.isCurrentlyUserTurn = this.game.players[0] === this.currentUserUsername;    

    let group = {}
    
    this.game.municipalities.forEach(m => {
      group[m.nombre] = new FormControl('');
    });
        
    this.selectForm = new FormGroup(group);    
  }

  //Ecuaciones de interpolacion
  private assignPositionToMunicipality(municipality: Municipality) {
    let lonMun = municipality.centroide.lon, latMun = municipality.centroide.lat,
      latMin = this.game.map.latMin, lonMin = this.game.map.lonMin,
      latMax = this.game.map.latMax, lonMax = this.game.map.lonMax;

    municipality.posX = this.imgSizeX * 0.15 + (lonMun - lonMin) * (this.imgSizeX * 0.70 - this.imgSizeX * 0.15) / (lonMax - lonMin);
    municipality.posY = this.imgSizeY * 0.15 + (latMun - latMax) * (this.imgSizeY * 0.80 - this.imgSizeY * 0.15) / (latMin - latMax);
  }

  private convertCoordinates(municipalitiy: Municipality) {
    this.coordinateService.convertCoordinates(municipalitiy, this.imgSizeX, this.imgSizeY);
  }

  public calculatePositionAndColor(municipality: Municipality) {

    let hashAssigned = this.hashNumberFromOwner(municipality.owner);
    let color = this.colorsOfMunicipalities[hashAssigned];

    return {
      "top": municipality.posY.toString() + 'px',
      "left": municipality.posX.toString() + 'px',
      "background": color
    }
  }

  private hashNumberFromOwner(owner: string): number{
    return this.game.players.indexOf(owner);
  }

  openNotYourTurnDialog(): void {
    const dialogRef = this.dialog.open(GameNotTurnToPlayComponent, {
      width: '400px',
      disableClose: true
    });
  }

  openMoveGauchosDialog(municipalitiy: Municipality): void {
    const dialogRef = this.dialog.open(GameMoveGauchosComponent, {
      width: '250px',
      data: municipalitiy,
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      this.cantGauchosToMove = result;
    });
  }

  openShowStatisticsDialog(municipalitiy: Municipality): void {
    const dialogRef = this.dialog.open(GameShowMunicipalityStatisticsComponent, {
      width: '400px',
      data: municipalitiy,
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      this.cantGauchosToMove = result;
    });
  }

  public realizeAction(event: any, municipality: Municipality) {
    switch (this.selectForm.value[municipality.nombre]) {
      case 'prepareAttack':
        this.attackMode = true;
        this.municipalityInAction = municipality;
        this.cdRef.detectChanges(); // Esto es porque tira Expression has changed after it was checked        
        break;
      case 'prepareMove':
        this.moveMode = true;
        this.municipalityInAction = municipality;
        this.openMoveGauchosDialog(municipality);
        this.cdRef.detectChanges();
        break;
      case 'modify':
        this.municipalityInAction = municipality;
        this.modifySpecialization(municipality);
        this.alreadyPlayed = true;
        this.cdRef.detectChanges();
        break;
      case 'attack':
        this.attack(municipality);
        this.alreadyPlayed = true;
        this.cdRef.detectChanges();
        break;
      case 'move':
        this.moveGauchos(municipality);
        this.alreadyPlayed = true;
        this.cdRef.detectChanges();
        break;
      case 'statistics':
        this.openShowStatisticsDialog(municipality);
        break;
      default:
        throw ("Se pidio una accion invalida");
    }
  }

  public attack(municipalityTarget: Municipality) {

    let action = new Action(this.municipalityInAction.nombre, municipalityTarget.nombre, 0);
    this.gamesService.attackGauchos(action, this.gameId).subscribe(
      data => {
        this.game = data;
        this.openDialogMovementSuccessful(
          "Has atacado desde " + action.attackMun+ " a "+ action.defenceMun);        
      },
      err => {
        console.log("Hubo un error al atacar");
      }
    );
    
  }

  public moveGauchos(municipalityTarget: Municipality) {
    let action = new Action(this.municipalityInAction.nombre, municipalityTarget.nombre, this.cantGauchosToMove);
    this.gamesService.moveGauchos(action, this.gameId).subscribe(
      data => {
        this.game = data;
        this.openDialogMovementSuccessful(
          "Has movido "+action.ammount+" gauchos desde " + action.attackMun+ " a "+ action.defenceMun);
      },
      err => {
        console.log("Hubo un error al mover los gauchos");
      }
    );
  }

  public modifySpecialization(municipalityTarget: Municipality) {
    let action = new Action(this.municipalityInAction.nombre, municipalityTarget.nombre, this.cantGauchosToMove);
    this.gamesService.changeMode(action, this.gameId).subscribe(
      data => {
        this.game = data;
        this.openDialogMovementSuccessful(
          "Has modificado la especializacion de " + action.attackMun);
      },
      err => {
        console.log("Hubo un error al cambiar la especialidad");
      }
    );
  }

  openDialogMovementSuccessful(msg: string): void {
    const dialogRef = this.dialog.open(GameMovementSuccessfulComponent, {
      width: '400px',
      data: msg
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  public playerIsCurrentUser(player: string): boolean {
    return player == this.currentUserUsername;
  }

  public isCurrentPlayerOption(player: string): boolean {
    return this.playerIsCurrentUser(player);
  }

  public isSelectActionsDisabled(municipalitiy: Municipality): boolean {
    let municipalityWantsToMove = this.selectForm.value[municipalitiy.nombre] === 'prepareMove';
    return (!this.playerIsCurrentUser(municipalitiy.owner) && !this.attackMode) ||
      (this.playerIsCurrentUser(municipalitiy.owner) && this.attackMode) ||
      (this.playerIsCurrentUser(municipalitiy.owner) && this.moveMode && municipalityWantsToMove) ||
      this.alreadyPlayed || !this.isCurrentlyUserTurn;
  }

}
