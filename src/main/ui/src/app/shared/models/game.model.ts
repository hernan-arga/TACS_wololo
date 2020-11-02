import {Map} from "./map.model";
import {GameState} from "./gameState.model";
import {Municipality} from './municipality.model';
import {GameStyle} from './gameStyle.model';

export class Game {
  id: number;
  province: string;
  date: Date;
  map: Map;
  state: GameState;
  players: Array<string>;
  municipalityLimit: Number;
  municipalities: Array<Municipality>;
  style: GameStyle;

}
