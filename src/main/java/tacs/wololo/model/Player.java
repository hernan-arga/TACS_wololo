package tacs.wololo.model;

import java.util.List;

public class Player {
    private String username;
    private List<Municipality> municipalities;




    private void moveGauchos(int amount,Municipality origin, Municipality destination){
        origin.removeGauchos(amount);                   //TODO ver donde poner el chequeo de si alcanzan
        destination.addGauchos(amount);
    }


}
