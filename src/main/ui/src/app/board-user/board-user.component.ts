import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Game } from "../shared/models/Game.model";
import { Sort } from '@angular/material/sort';
import { GameState } from "../shared/models/GameState.model";
import { GamesService } from '../_services/games.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {
  content = '';
  games: Array<Game>;
  sortedData: Game[];


  constructor(private userService: UserService, private gamesService: GamesService, private router: Router) { 
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

}

function compare(a: number | string | Date, b: number | string | Date, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}