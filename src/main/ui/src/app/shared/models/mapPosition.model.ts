
export class MapPosition {
  free: boolean;
  posX: number;
  posY: number;

  constructor(posX: number, posY: number){
    this.posX = posX;
    this.posY = posY;
    this.free = true;
  }

}