import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})

export class ImagesService {

  FUNCIONA = "https://cors-anywhere.herokuapp.com/https://pixabay.com/api/?key=18376709-a188ab6baf8fe12654f8c3700&q=Rojas&image_type=photo"

  CORS_HEROKU = "https://cors-anywhere.herokuapp.com/"

  // No subirlo en prod
  API_KEY = '18376709-a188ab6baf8fe12654f8c3700';

  API_URL = 'https://pixabay.com/api/?key=';
  API_NAME_PARAMETER = '&q=';
  API_IMG_TYPE = '&image_type=photo';

  constructor(private http: HttpClient) {
  }
  //this.CORS_HEROKU+this.API_URL+this.API_KEY+this.API_NAME_PARAMETER+imageSearch+this.API_IMG_TYPE

  getImages(imageSearch: string): Observable<any> {
    let imageSearchFormatted = imageSearch.split(" ").join('%20');

    return this.http.get(
      this.CORS_HEROKU+this.API_URL+this.API_KEY+this.API_NAME_PARAMETER+imageSearch+this.API_IMG_TYPE, 
      { responseType: 'json' });
  }
  

}
