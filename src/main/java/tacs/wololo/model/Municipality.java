package tacs.wololo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tacs.wololo.model.APIs.GeoData.Centroide;
import tacs.wololo.model.APIs.GeoData.Provincia;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "municipalities")
public class Municipality {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long table_id;

    //API DATA tiene que ser en español  ¯\_(ツ)_/¯.
    @Embedded
    public Centroide centroide;

    @JsonIgnore
    @Transient
    public String id;

    public String nombre;

    @JsonIgnore
    @Transient
    public Provincia provincia;

    //--

    private int gauchos;

    private double height;

    @JsonIgnore
    private double coefDist = 2;

    @JsonIgnore
    private double coefAlt = 2;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@PrimaryKeyJoinColumn
    @JoinColumn(name = "mode_id")
    private MunicipalityMode mode;

    private String owner;

    @Transient
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

     /**
    * Calcula la distancia entre municipios utilizando la formula de haversine, que calcula distancias mediante
    * la longitud y latitud, y el radio de la tierra
    * @param other el otro municipio al que se le calcula la distancia
     * @return la distancia entre los 2 municipios
    */

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
  /**
     * Calcula uno de los factores utilizados para determinar si un ataque es eficaz o no
     * @param anotherMunicipality la otra municipalidad a la distancia
     * @param map el mapa en donde ocurre la partida
     * @return el multiplicador de distancia como se usa en las cuentas
     */
    double multDist(Municipality anotherMunicipality, Map map){
        return 1 - (distanceToMunicipality(anotherMunicipality)- map.getDistMin())/
                (coefDist*(map.getDistMax()- map.getDistMin()));
    }
        /**
     * Calcula uno de los factores utilizados para determinar si un ataque es eficaz o no
     * @param map mapa donde ocurre la partida
     * @return el multiplicador de altura como se usa en las cuentas
     */
    double multAlt (Map map){
        return 1 + heightMultiplier(map);
    }

    /**
     * Intenta atacar a defender, en el mapa map
     * si el ataque es exitoso, cambia de dueño al municipio defender y le setea los gauchos restantes al atacante
     * si el ataque falla, se le setean los gauchos restantes al defender, y 0 al atacante
     * @param defender municipio al que se ataca
     * @param map mapa donde ocurre la partida
     * @return true si el ataque fue exitoso
     */
    public boolean attackMunicipality(Municipality defender, Map map)
    {
        boolean winTheBattleAttacker = gauchosRemainAfterAttack(defender,map);

        if(winTheBattleAttacker)
        {
            defender.setOwner(this.owner);
            setGauchos(endingAttackingGauchos(defender,map));
        }
        else{
            defender.setGauchos(defender.endingDefendersGauchos(this,map));
            setGauchos(0);
        }

        defender.addMovement(new MovementDefend(defender.getGauchos(), this.nombre, !winTheBattleAttacker));

        return winTheBattleAttacker;
    }
    /**
     * agrega un movimiento de gauchos al historial de movimientos
     * @param movement el movimiento a agregar
     */
    public void addMovement(Movement movement)
    {
        this.movements.add(movement);
    }

    /**
    *  Calcula uno de los factores utilizados para determinar si un ataque es eficaz o no
     * @param map el mapa en el que ocurre la partida
    */
    private double heightMultiplier(Map map){
       return (this.height - map.getMinHeight())/
               (coefAlt*Math.max(map.getMaxHeight()- map.getMinHeight(),1)); //
    }

        /**
     *  Calcula cuantos gauchos produce un municipio por turno en un mapa determinado
     *  @param map el mapa en el que ocurre la partida
     */
    private int gauchosToAdd(Map map){
        return  (int) (mode.getCoefProdGauchos()*(1 - this.heightMultiplier(map)));
    }
    
    /**
     * Crea un nuevo Movement con los gauchos preexistentes y los gauchos a agregar, y los agrega al municipio
     * @param map el mapa en el que ocurre la partida
     */
    public void produceGauchos(Map map){
        this.addMovement(new MovementProduce(this.gauchos, gauchosToAdd(map)));
        this.addGauchos(gauchosToAdd(map));

    }

    /**
     * Agrega gauchos al municipio
     * @param gauchos los gauchos a agregar
     */    
    public void addGauchos(int gauchos)
    {
        setGauchos(this.gauchos+gauchos);
    }
    
    /**
     * elimina gauchos del municipio
     * @param gauchos los gauchos a remover
     */
    public void removeGauchos(int gauchos)
    {
        setGauchos(this.gauchos-gauchos);
    }


    /**
     * booleano de si quedan o no gauchos luego de un ataque
     * @param map el mapa en el que ocurre la partida
     * @param anotherMunicipality  la municipalidad a la que se ataco
     */
    public boolean gauchosRemainAfterAttack(Municipality anotherMunicipality, Map map){
        return endingAttackingGauchos(anotherMunicipality, map)>0;
    }

    /**
     * calculo de cuantos gauchos quedan luego de un ataque
     * @param map el mapa en el que ocurre la partida
     * @param defenderMunicipality la municipalidad que se ataco
     */
    public int endingAttackingGauchos(Municipality defenderMunicipality, Map map){

       return (int) (this.gauchos*this.multDist(defenderMunicipality, map)
               - defenderMunicipality.getGauchos()*defenderMunicipality.multAlt(map)*defenderMunicipality.getMode().getMultDef());
    }
    
    /**
     * calculo de cuantos gauchos quedan luego de defenderse de un ataque
     * @param map el mapa en el que ocurre la partida
     * @param attackingMunicipality la municipalidad que ataco
     */
    int endingDefendersGauchos(Municipality attackingMunicipality, Map map){

        return (int) (Math.ceil(gauchos*multAlt(map)* this.getMode().getMultDef()             //TODO: redondear para arriba
                - attackingMunicipality.gauchos* multDist(attackingMunicipality, map))/
                (multAlt(map)* getMode().getMultDef()));
    }

      /**
     * Llama al modo a cambiarse a la otra opcion (defensa -> produccion, produccion ->defensa)
     */
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
