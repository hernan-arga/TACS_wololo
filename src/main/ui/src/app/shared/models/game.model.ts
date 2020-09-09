import { Map } from "./Map.model";
import { GameState } from "./GameState.model";
import { Player } from "./Player.model";

export class Game {
  //id: number;
  province: string;
  date: Date;
  map: Map;
  state: GameState;
  players: Array<Player>;
}