import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Game } from "../shared/models/Game.model";
import { Sort } from '@angular/material/sort';
import { GameState } from "../shared/models/GameState.model";


@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {
  content = '';
  games: Array<Game>;
  sortedData: Game[];

  //XXX: Esto es para testear borrarlo despues
  gamesTest: Array<Game> = [
    {
      province: 'Chaco', date: new Date("2019-03-16"), state: GameState.IN_PROGRESS,
      map: {
        maxHeight: 9, minHeight: 2, distMax: 4,
        province: {
          name: 'Chaco',
          municipalities: [{
            gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
            mode: { multDef: 4, coefProdGauchos: 6 }
          }]
        }
      },
      players: [{
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
      }]
    },

    {
      province: 'Formosa', date: new Date("2018-03-16"), state: GameState.CANCELLED,
      map: {
        maxHeight: 9, minHeight: 2, distMax: 4,
        province: {
          name: 'Formosa',
          municipalities: [{
            gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
            mode: { multDef: 4, coefProdGauchos: 6 }
          }]
        }
      },
      players: [{
        username: "testUser", municipalities: [{
          gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
          mode: { multDef: 4, coefProdGauchos: 6 }
        }]
      }]
    }
  ];


  constructor(private userService: UserService) {
    //Fixme: borrar esto y pedirle a la api los juegos del usuario
    this.games = this.gamesTest;
    this.sortedData = this.games.slice();
  }

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

  createGame() {

  }

}

function compare(a: number | string | Date, b: number | string | Date, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}