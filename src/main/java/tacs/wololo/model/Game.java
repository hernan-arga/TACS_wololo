package tacs.wololo.model;

import java.util.Date;
import java.util.List;

public class Game {
    Map map;
    String province; //TODO: averiguar de que tipo es esto
    Date date;
    List<Player> players;
    GameState state;
}
