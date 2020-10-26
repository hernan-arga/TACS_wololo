import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Game } from "../shared/models/Game.model";
import { Sort } from '@angular/material/sort';
import { GameState } from "../shared/models/GameState.model";
import { GamesService } from '../_services/games.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { GamePassTurnComponent } from '../game-pass-turn/game-pass-turn.component';
import { GameTurnChangedComponent } from '../game-turn-changed/game-turn-changed.component';
import { GameSurrenderComponent } from '../game-surrender/game-surrender.component';
import { GameSurrenderedComponent } from '../game-surrendered/game-surrendered.component';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {
  content = '';
  games: Array<Game>;
  sortedData: Game[];


  constructor(private userService: UserService, private gamesService: GamesService,
    private router: Router, public dialog: MatDialog, private tokenStorageService: TokenStorageService) { 
  }

  ngOnInit(): void {

    this.gamesService.getGames().subscribe(data =>
      {
        this.games = data;
        this.sortedData = this.games.slice();
      }
    );

  }

  sortData(sort: Sort) {
    const data = this.games.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'date': return compare(a.date, b.date, isAsc);
        case 'state': return compare(a.state, b.state, isAsc);
        default: return 0;
      }
    });
  }

  isGameFinished(game: Game): boolean{
    return game.state === GameState.FINISHED;
  }

  goToGame(id: number){
    this.router.navigate(['/game', id]);
  }

  public changeTurn(id: number){
    const dialogRef = this.dialog.open(GamePassTurnComponent, {
      width: '500px',
    });

    dialogRef.afterClosed().subscribe(changeTurn => {
      if(changeTurn){
        this.gamesService.passTurn(id).subscribe(data => {
          this.turnChanged();
        });
      }
    });
  }

  turnChanged(){
    const dialogRef = this.dialog.open(GameTurnChangedComponent, {
      width: '400px',
    });
  }

  public surrender(id: number) {
    const dialogRef = this.dialog.open(GameSurrenderComponent, {
      width: '500px',
    });

    dialogRef.afterClosed().subscribe(changeTurn => {
      if(changeTurn){
        this.gamesService.surrender(id).subscribe(data => {
          this.surrendered();
        });
      }
    });
  }

  surrendered(){
    const dialogRef = this.dialog.open(GameSurrenderedComponent, {
      width: '400px',
    });
  }

  public isCancelAvailable(game: Game): boolean{
    const user = this.tokenStorageService.getUser();
    return game.players[0] === user.username;   
    //return false; // TODO
  }

  public isSurrenderAvailable(game: Game): boolean{
    return !this.isGameFinished(game);
  }

}

function compare(a: number | string | Date, b: number | string | Date, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}