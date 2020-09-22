import { Injectable } from '@angular/core';
import { Municipality } from '../shared/models/municipality.model';

@Injectable({
  providedIn: 'root'
})
export class CoordinatesService {

  constructor() { }

  public convertCoordinates(municipality: Municipality, imgSizeX: number, imgSizeY: number){
    let lat = municipality.centroide.lat;
    let lon = municipality.centroide.lon;
    municipality.posY =  ((lat* 5000) * -1) / (imgSizeY);
    municipality.posX = (lon + 5000) / (imgSizeX);
  }
}
