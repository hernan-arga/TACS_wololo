import { Centroide } from './centroide.model';
import { MunicipalityMode } from "./municipalityMode.model";

export class Municipality {
  //id: number;
  gauchos: number;
  nombre: string;
  height: number;
  coefDist: number;
  coefAlt: number;
  mode: MunicipalityMode;
  centroide: Centroide;  
  posX: Number = 0;
  posY: Number = 0;
}