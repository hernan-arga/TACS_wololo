package tacs.wololo.model;

import tacs.wololo.model.APIs.GeoData.Centroide;

import tacs.wololo.model.APIs.GeoData.Provincia;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

public class Municipality {

    //API DATA tiene que ser en español  ¯\_(ツ)_/¯.
    public Centroide centroide;
    public String id;
    public String nombre;
    public Provincia provincia;

    //--

    private int gauchos;

    private double height;

    private double coefDist = 2;

    private double coefAlt = 2;

    private MunicipalityMode mode;

    private Player owner;

    private List<Movement> movements = new ArrayList<>();

    public Municipality() { }

    public Municipality(Player owner, int gauchos, double height, MunicipalityMode mode, Centroide centroide)
    {
        this.owner = owner;
        this.gauchos = gauchos;
        this.height=height;
        this.mode = mode;
        this.centroide = centroide;
                                    //Nunca deberia instanciarse un Municipio por el constructor, siempre por GAME
    }                                //GAME los crea mediante lo que levanta de la API, pero el constructor queda
                                     //Porque es util para testear



    public double distanceToMunicipality(Municipality other)
    {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(other.centroide.lat - this.centroide.lat);
        double dLon = Math.toRadians(other.centroide.lon - this.centroide.lon);

        // convert to radians
        double lat1 = Math.toRadians(this.centroide.lat);
        double lat2 = Math.toRadians(other.centroide.lat);

        // apply formulae
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

    public boolean attackMunicipality(Municipality defender, Map map){
        boolean winTheBattleAttacker = gauchosRemainAfterAttack(defender,map);

        if(winTheBattleAttacker){
            defender.setOwner(this.owner);
            setGauchos(endingAttackingGauchos(defender,map));
        }
        else{
            setGauchos(0);
            defender.setGauchos(defender.endingDefendersGauchos(this,map));
        }

        defender.addMovement(new MovementDefend(defender.getGauchos(), this.nombre, winTheBattleAttacker));

        return winTheBattleAttacker;
    }

    public void addMovement(Movement movement)
    {
        this.movements.add(movement);
    }

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

    public void setOwner(Player owner) {
        this.owner = owner;
        owner.addMunicipality(this);
    }

    public Player getOwner()
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

    public double getCoefAlt() {
        return coefAlt;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }
}
