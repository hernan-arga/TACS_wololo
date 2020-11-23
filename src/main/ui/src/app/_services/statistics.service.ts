import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_URL = 'http://EC2Co-EcsEl-1RR0TL52DGMVI-1769319833.us-east-1.elb.amazonaws.com:8080/api/statistics/';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  constructor(private http: HttpClient) { }

  getIndividualStatistics(userName: string): Observable<Map<string, number>>
  {
    return this.http.get<Map<string, number>>(API_URL + "users/" + userName, { responseType: 'json' });
  }
  
  getStatisticsByDate(from: Date, to: Date): Observable<Map<string, number>>
  { 
    return this.http.get<Map<string, number>>(API_URL + "games?" +
      "from=" + this.convertDateToISOString(from) + "&to="
      + this.convertDateToISOString(to), { responseType: 'json' });
  }

  convertDateToISOString(date: Date): string
  {
    return date.getFullYear().toString() + "-" + (date.getMonth() + 1).toString() + "-" + date.getDate().toString();
  }
}
