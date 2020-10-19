import { GameStyle } from './gameStyle.model';

export class GameInfo {
  playersUsernames: Array<String>;
  provinceName: String;
  municipalitiesCant: number;
  styleIndex: Number;

  constructor(playersUsernames: Array<String>, municipalitiesCant:number, provinceName:String, gameStyle: Number){
    this.playersUsernames = playersUsernames;
    this.municipalitiesCant = municipalitiesCant;
    this.provinceName = provinceName;    
    this.styleIndex = gameStyle;
    console.log(this.styleIndex);
  }

}