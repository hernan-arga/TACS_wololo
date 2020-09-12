package tacs.wololo.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String username;
    private List<Municipality> municipalities = new ArrayList<>();


    public void moveGauchos(int amount,Municipality origin, Municipality destination) throws Exception {
        this.validateMunicipalitiesBelongToMe(origin, destination);
        this.validateEnoughGauchos(amount, origin);
        origin.removeGauchos(amount);
        destination.addGauchos(amount);
    }

    private void validateMunicipalitiesBelongToMe(Municipality origin, Municipality destination) throws Exception {
        if(!(municipalities.contains(origin) && municipalities.contains(destination)))
            throw new Exception("Solo se pueden mover gauchos entre municipios que le pertenecen");
    }

    private void validateEnoughGauchos(int amount, Municipality origin) throws Exception {
        if(origin.getGauchos() < amount)
            throw new Exception("No se tienen suficientes gauchos para mover");
    }

    public void addMunicipality(Municipality municipality)
    {
        this.municipalities.add(municipality);
    }
}
