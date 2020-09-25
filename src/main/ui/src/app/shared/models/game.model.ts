import { Map } from "./Map.model";
import { GameState } from "./GameState.model";
import { Player } from "./Player.model";
import { Municipality } from './municipality.model';

export class Game {
  //id: number;
  province: string;
  date: Date;
  map: Map;
  state: GameState;
  players: Array<Player>;
  municipalityLimit: Number;
  municipalities: Array<Municipality>;
}