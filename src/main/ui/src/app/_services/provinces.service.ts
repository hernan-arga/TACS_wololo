import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api/test/';

@Injectable({
  providedIn: 'root'
})
export class ProvincesService {

  constructor(private http: HttpClient) { 

  }

  provinces: Array<string> = ['Buenos Aires', 'La Pampa', 'Formosa', 'Chaco'];

  getProvincesList(): Array<string> {

    //fixme: pasar por http?
    //return this.http.get(API_URL + 'all', { responseType: 'text' }); Observable<any>
    return this.provinces;
  }
}
