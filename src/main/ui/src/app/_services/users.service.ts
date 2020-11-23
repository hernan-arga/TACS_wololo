import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserInfo } from '../shared/models/userInfo.model';

const API_URL = 'http://ec2co-ecsel-v938kjf340ap-1893651420.us-east-1.elb.amazonaws.com:8080/api/';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: HttpClient) { 

  }

  getUsersList(): Observable<UserInfo[]> {
    
    return this.http.get<UserInfo[]>(API_URL + 'users', { responseType: 'json' });

  }
}
