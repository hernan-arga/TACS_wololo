export class GameInfo {
  //id: number;
  playersUsernames: Array<String>;
  provinceName: String;
  municipalitiesCant: number;

  constructor(playersUsernames: Array<String>, municipalitiesCant:number, provinceName:String){
    this.playersUsernames = playersUsernames;
    this.municipalitiesCant = municipalitiesCant;
    this.provinceName = provinceName;
  }

}