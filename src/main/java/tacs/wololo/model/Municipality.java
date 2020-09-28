package tacs.wololo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tacs.wololo.model.APIs.GeoData.Centroide;
import tacs.wololo.model.APIs.GeoData.Provincia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Municipality {

    //API DATA tiene que ser en español  ¯\_(ツ)_/¯.
    public Centroide centroide;

    @JsonIgnore
    public String id;

    public String nombre;

    @JsonIgnore
    public Provincia provincia;

    //--

    private int gauchos;

    private double height;

    @JsonIgnore
    private double coefDist;

    @JsonIgnore
    private double coefAlt;

    private MunicipalityMode mode;

    private String owner;

    private List<Movement> movements = new ArrayList<>();

    public Municipality() { }

    public Municipality(String owner, int gauchos, double height, MunicipalityMode mode, Centroide centroide)
    {
        this.owner = owner;
        this.gauchos = gauchos;
        this.height=height;
        this.mode = mode;
        this.centroide = centroide;
    }

    /*
    * This uses the ‘haversine’ formula to calculate the great-circle distance between two points
    * – that is, the shortest distance over the earth’s surface – giving an ‘as-the-crow-flies’
    * distance between the points (ignoring any hills they fly over, of course!).
    * */

    public double distanceToMunicipality(Municipality other)
    {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(other.getCentroide().getLat() - this.getCentroide().getLat());
        double dLon = Math.toRadians(other.getCentroide().getLon() - this.getCentroide().getLon());

        // convert to radians
        double lat1 = Math.toRadians(this.getCentroide().getLat());
        double lat2 = Math.toRadians(other.getCentroide().getLat());

        // apply formulae - Haversine
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;                          //Radio en kilometros de la tierra
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

    double multDist (Municipality anotherMunicipality, Map map){
        return 1 - (distanceToMunicipality(anotherMunicipality)- map.getDistMin())/
                (coefDist*(map.getDistMax()- map.getDistMin()));
    }

    double multAlt (Map map){
        return 1 + heightMultiplier(map);
    }

    public boolean attackMunicipality(Municipality defender, Map map)
    {
        boolean winTheBattleAttacker = gauchosRemainAfterAttack(defender,map);

        if(winTheBattleAttacker)
        {
            defender.setOwner(this.owner);
            setGauchos(endingAttackingGauchos(defender,map));
        }
        else{
            setGauchos(0);
            defender.setGauchos(defender.endingDefendersGauchos(this,map));
        }

        defender.addMovement(new MovementDefend(defender.getGauchos(), this.nombre, !winTheBattleAttacker));

        return winTheBattleAttacker;
    }

    public void addMovement(Movement movement)
    {
        this.movements.add(movement);
    }

    /*
    * Returns the necessary multiplication factor to calculate the remaining gauchos
    * */
    private double heightMultiplier(Map map){
       return  (this.height - map.getMinHeight())/
               (coefAlt*Math.max(map.getMaxHeight()- map.getMinHeight(),1)); //
    }

    public void produceGauchos(Map map){
        int gauchosToAdd = (int) (mode.getCoefProdGauchos()*(this.heightMultiplier(map)));
        this.addMovement(new MovementProduce(this.gauchos, gauchosToAdd));
        this.addGauchos(gauchosToAdd);
    }

    public void addGauchos(int gauchos)
    {
        setGauchos(this.gauchos+gauchos);
    }

    public void removeGauchos(int gauchos)
    {
        setGauchos(this.gauchos-gauchos);
    }



    public boolean gauchosRemainAfterAttack(Municipality anotherMunicipality, Map map){
        return endingAttackingGauchos(anotherMunicipality, map)>0;
    }


    public int endingAttackingGauchos(Municipality defenderMunicipality, Map map){
       return (int) (this.gauchos*this.multDist(defenderMunicipality, map)
               - defenderMunicipality.gauchos*defenderMunicipality.multAlt(map)*defenderMunicipality.getMode().getMultDef());
    }

    int endingDefendersGauchos(Municipality attackingMunicipality, Map map){

        System.out.println(gauchos);
        System.out.println(multAlt(map));
        System.out.println(mode.getMultDef());
        System.out.println(attackingMunicipality.gauchos);
        System.out.println(multDist(attackingMunicipality, map));
        System.out.println(multAlt(map));

        int test = (int) (Math.ceil(gauchos*multAlt(map)* mode.getMultDef()             //TODO: redondear para arriba
                - attackingMunicipality.gauchos*multDist(attackingMunicipality, map))/
                (multAlt(map)* mode.getMultDef()));

        System.out.println(test);
        return test;
    }

    public void changeMode() throws IOException {
       mode = mode.changeMode();
    }


    // --------------- Getters y Setters --------------
    public void setMode(MunicipalityMode mode) {
        this.mode = mode;
    }

    public int getGauchos()
    {
        return this.gauchos;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner()
    {
        return this.owner;
    }

    public void setGauchos(int gauchos) {
        this.gauchos = gauchos;
    }

    public MunicipalityMode getMode() {
        return mode;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public Centroide getCentroide() {
        return centroide;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public double getHeight() {
        return height;
    }

    public double getCoefDist() {
        return coefDist;
    }

    public void setCoefDist(double coefDist) {
        this.coefDist = coefDist;
    }

    public double getCoefAlt() {
        return coefAlt;
    }

    public void setCoefAlt(double coefAlt) {
        this.coefAlt = coefAlt;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }


}
