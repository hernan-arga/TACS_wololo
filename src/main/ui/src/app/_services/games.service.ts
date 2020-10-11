import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserInfo } from '../shared/models/userInfo.model';
import { GameInfo } from '../shared/models/gameInfo.model';
import { Game } from '../shared/models/Game.model';
import { Municipality } from '../shared/models/municipality.model';
import { Action } from '../shared/models/action.model';

const API_URL = 'http://localhost:8080/api/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class GamesService {

  constructor(private http: HttpClient) {

  }

  createGame(gameInfo: GameInfo): Observable<any> {
    return this.http.post(API_URL + 'games', {
      playersUsernames: gameInfo.playersUsernames,
      provinceName: gameInfo.provinceName,
      municipalitiesCant: gameInfo.municipalitiesCant
    }, httpOptions);
  }

  getGame(id: Number): Observable<Game> {
    return this.http.get<Game>(API_URL + 'games/' + id.toString(), { responseType: 'json' });
  }

  getGames(): Observable<Array<Game>> {
    return this.http.get<Array<Game>>(API_URL + 'games', { responseType: 'json' });
  }

  getGamesByDate(from: Date, to: Date):Observable<Array<Game>>
  {
    return this.http.get<Array<Game>>(API_URL + "games/by?" +
      "from=" + this.convertDateToISOString(from) + "&to="
      + this.convertDateToISOString(to), { responseType: 'json' });
  }

  convertDateToISOString(date: Date): string
  {
    return date.getFullYear().toString() + "-" + (date.getMonth() + 1).toString() + "-" + date.getDate().toString();
  }

  moveGauchos(action: Action, id: Number): Observable<any> {
    return this.http.post(API_URL + 'games/' + id.toString() + "/moves", {
      attackMun: action.attackMun,
      defenceMun: action.defenceMun,
      ammount: action.ammount
    }, httpOptions);
  }

  attackGauchos(action: Action, id: Number): Observable<any> {
    return this.http.post(API_URL + 'games/' + id.toString() + "/attacks", {
      attackMun: action.attackMun,
      defenceMun: action.defenceMun,
      ammount: action.ammount
    }, httpOptions);
  }

  changeMode(action: Action, id: Number):Observable<any>{
    return this.http.post(API_URL + 'games/' + id.toString() + "/municipalities/mode", {
      attackMun: action.attackMun,
      defenceMun: action.defenceMun,
      ammount: action.ammount
    }, httpOptions);
  }

  passTurn(id: Number):Observable<any>{
    return this.http.post(API_URL + 'games/' + id.toString() + "/passTurn", httpOptions);
  }

}
