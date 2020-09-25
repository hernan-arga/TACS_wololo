import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserInfo } from '../shared/models/userInfo.model';
import { GameInfo } from '../shared/models/gameInfo.model';
import { Game } from '../shared/models/Game.model';

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
    console.log(gameInfo);
    return this.http.post(API_URL + 'games', {
      playersUsernames: gameInfo.playersUsernames,
      provinceName: gameInfo.provinceName,
      municipalitiesCant: gameInfo.municipalitiesCant
    }, httpOptions);
  }

  //TODO: Observable<Game>
  getGame(id: Number): Observable<Game> {
    //let searchParam = new HttpParams().set('gameID', id.toString());
    return this.http.get<Game>(API_URL + 'game/'+id.toString(), { responseType: 'json' });  //{ params: searchParam }
  }

}
