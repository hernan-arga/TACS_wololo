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

  municipalitiesTest = [{
    nombre: 'Municipalidad 1',
    posX: 0,
    posY: 0,
    centroide: { lat: -32.546237695526, lon: -62.9807310033667 },
    gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
    mode: { multDef: 4, coefProdGauchos: 6 }
  }]
  //XXX: Esto es para testear borrarlo despues
  gamesTest: Array<Game> = [
    {
      municipalityLimit: 3,
      municipalities: this.municipalitiesTest,
      province: 'Chaco', date: new Date("2019-03-16"), state: GameState.IN_PROGRESS,
      map: {
        latMax: 3, lonMax: 2, latMin: 1, lonMin: 1,
        maxHeight: 9, minHeight: 2, distMax: 4,
        province: {
          name: 'Chaco',
          municipalities: [{
            nombre: 'Municipalidad 1',
            posX: 0,
            posY: 0,
            centroide: { lat: -32.546237695526, lon: -62.9807310033667 },
            gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
            mode: { multDef: 4, coefProdGauchos: 6 }
          }]
        }
      },
      players: [{
        username: "testUser", municipalities: [{
          nombre: 'Municipalidad 1',
          posX: 0,
          posY: 0,
          centroide: { lat: -32.546237695526, lon: -62.9807310033667 },
          gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
          mode: { multDef: 4, coefProdGauchos: 6 }
        }]
      },
      {
        username: "testUser2", municipalities: [{
          nombre: 'Municipalidad 1',
          posX: 0,
          posY: 0,
          centroide: { lat: -32.546237695526, lon: -62.9807310033667 },
          gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
          mode: { multDef: 4, coefProdGauchos: 6 }
        }]
      }]
    },

    {
      municipalityLimit: 3,
      municipalities: this.municipalitiesTest,
      province: 'Formosa', date: new Date("2018-03-16"), state: GameState.CANCELLED,
      map: {
        latMax: 3, lonMax: 2, latMin: 1, lonMin: 1,
        maxHeight: 9, minHeight: 2, distMax: 4,
        province: {
          name: 'Formosa',
          municipalities: [{
            nombre: 'Municipalidad 1',
            posX: 0,
            posY: 0,
            centroide: { lat: -32.546237695526, lon: -62.9807310033667 },
            gauchos: 3, height: 4, coefDist: 5, coefAlt: 2,
            mode: { multDef: 4, coefProdGauchos: 6 }
          }]
        }
      },
      players: [{
        username: "testUser", municipalities: [{
          nombre: 'Municipalidad 1',
          posX: 0,
          posY: 0,
          centroide: { lat: -32.546237695526, lon: -62.9807310033667 },
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