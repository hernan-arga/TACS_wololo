import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ElementScoreBoard } from '../shared/models/elementScoreBoard.model';

const API_URL = 'http://ec2co-ecsel-v938kjf340ap-1893651420.us-east-1.elb.amazonaws.com:8080/api/scoreboard/';

@Injectable({
  providedIn: 'root'
})
export class ScoreBoardShowService {

constructor(private http: HttpClient) { }

getScoreBoard(id: Number): Observable<Map<string, number>> {
  return this.http.get<Map<string, number>>(API_URL + id.toString(), { responseType: 'json' });
}

}
