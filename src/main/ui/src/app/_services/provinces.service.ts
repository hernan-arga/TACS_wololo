import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProvinceInfo } from '../shared/models/provinceInfo.model';

const API_URL = 'http://localhost:8080/api/test/';

@Injectable({
  providedIn: 'root'
})
export class ProvincesService {

  constructor(private http: HttpClient) { 

  }

  provinces: Array<ProvinceInfo> = [
    new ProvinceInfo ('Buenos Aires', 9), 
    new ProvinceInfo ('La Pampa', 2), 
    new ProvinceInfo ('Formosa', 3), 
    new ProvinceInfo ('Chaco', 4), 
];

  getProvincesList(): Array<ProvinceInfo> {

    //fixme: pasar por http?
    //return this.http.get(API_URL + 'all', { responseType: 'text' }); Observable<any>
    return this.provinces;
  }
}
