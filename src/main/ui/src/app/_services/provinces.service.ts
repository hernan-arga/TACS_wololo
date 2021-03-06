import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProvinceInfo } from '../shared/models/provinceInfo.model';
import { AuthService } from './auth.service';
import { TokenStorageService } from './token-storage.service';
import { AuthInterceptor } from '../_helpers/auth.interceptor';
import { ProvinceLimits } from '../shared/models/ProvinceLimits.model';

const API_URL = 'http://localhost:8080/api/';

@Injectable({
  providedIn: 'root'
})
export class ProvincesService {

  constructor(private http: HttpClient) { 

  }

  getProvincesList(): Observable<ProvinceInfo[]> {
    
    //var request = 
    return this.http.get<ProvinceInfo[]>(API_URL + 'provinces', { responseType: 'json' });

  }

  getLimits(): Observable<ProvinceLimits[]> {
    return this.http.get<ProvinceLimits[]>("../../assets/provinces/Limites.json", { responseType: 'json' });
  }
    
}
