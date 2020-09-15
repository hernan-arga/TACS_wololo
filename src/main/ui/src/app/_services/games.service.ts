import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserInfo } from '../shared/models/userInfo.model';
import { GameInfo } from '../shared/models/gameInfo.model';

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

  createGame(gameInfo: GameInfo): Observable<any>  {
    console.log(gameInfo);
    return this.http.post(API_URL + 'games', {
      gameInfo: gameInfo
    }, httpOptions);
  }

}
