package tacs.wololo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tacs.wololo.model.APIs.AsterAPI;
import tacs.wololo.model.APIs.GeoRef;

import javax.persistence.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "games")
public class Game
{
    @Id
    @GeneratedValue
    Long id;

    @Embedded
    Map map;

    @Column(name = "start_date")
    Date date;

    @ElementCollection//(fetch = FetchType.EAGER)
    List<String> players;

    @Enumerated(value = EnumType.STRING)
    GameState state;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    List<Municipality> municipalities;

    int municipalityLimit;

    @Transient
    GeoRef geoRef;


    @Enumerated(value = EnumType.STRING)
    GameStyle style;


    public Game() {
    }

     /**
     * Crea un game y prepara el ambiente para su funcionamiento
     * Pide los datos de las municipalidades de la provincia a georef
     * Setea los valores maximos y minimos de longitud y latitud basado en los municipios a utilizar
     * Limita los municipios a los que el usuario haya pedido para la partida
     * Setea las distancias maximas y minimas al mapa basado en los municipios activos en la partida
     * setea las alturas, pidiendolas a asterAPI
     * setea coeficientes configurables que afectan los calculos de ataque y gauchos
     * distribuye los municipios entre los jugadores
     * setea el numero de gauchos iniciales a los municipios medianto un random
     */
    public Game(Map map, Date date, List<String> players, GameState state, int municipalityLimit,
                GeoRef geoRef, AsterAPI asterAPI, GameStyle gameStyle) throws IOException
    {
        this.municipalityLimit = municipalityLimit;
        this.geoRef = geoRef;      //TODO hacerlo singleton que no instancie
        this.map = map;
        this.date = date;
        this.players = players;
        this.state = state;
        this.style = gameStyle;
        this.municipalities = this.geoRef.municipioPorProvincia(map.getProvince());
        this.setMapLatAndLon();
        this.municipalities = this.municipalities.stream().limit(this.municipalityLimit).collect(Collectors.toList());
        this.setDists(this.municipalities);
        this.setHeights(asterAPI);
        this.setCoefs();
        this.sortMunicipalities(this.municipalities);

        Random random = new Random();

        for (Municipality municipality : this.municipalities)  //TODO:Parametrizar por config file
        {
            municipality.setGauchos(random.nextInt(15));

            if (random.nextInt(100) < 25)
                municipality.setMode(new ProducerMunicipality(this.style));
            else
                municipality.setMode(new DefendingMunicipality(this.style));
        }

    }

    @JsonIgnore
    // HashMap<player, cantMunicipalitiesHavePlayer>
        /**
     * obtiene el scoreboard de un game
     */
    public HashMap<String, Integer> getScoreBoard() {
        HashMap<String, Integer> scoreBoard = new HashMap <String, Integer> ();

        this.players.forEach(p ->
        {
            List<Municipality> municipalitiesOwner = this.municipalities.stream().filter(m -> m.getOwner().equals(p)).collect(Collectors.toList());
            scoreBoard.put(p, municipalitiesOwner.size());
        });

        return scoreBoard;
    }

    /**
     * obtiene las latitudes y longitudes maximas y minimas entre todos los municipios de una provincia
     */    
    private void setMapLatAndLon() {
        List<Double> latitudes = this.municipalities.stream().map(m -> m.getCentroide().getLat()).collect(Collectors.toList());
        List<Double> longitudes = this.municipalities.stream().map(m -> m.getCentroide().getLon()).collect(Collectors.toList());
        this.map.setLatMax(Collections.max(latitudes));
        this.map.setLonMax(Collections.max(longitudes));
        this.map.setLatMin(Collections.min(latitudes));
        this.map.setLonMin(Collections.min(longitudes));
    }

     /**
     * obtiene la distancia mayor y menor entre los municipios que participan de la partida
     */
    private void setDists(List<Municipality> municipalities) {
        List<List<Double>> distances = municipalities.stream().map(z -> this.allDistsTo(z, municipalities)).collect(Collectors.toList());
        List<Double> flattenDistances = distances.stream().flatMap(List::stream).filter(z -> z > 0).collect(Collectors.toList());
        map.setDistMax(Collections.max(flattenDistances));
        map.setDistMin(Collections.min(flattenDistances));
    }

    /**
     * obtiene los coeficientes configurables de un archivo property que afectaran los calculos durante el game
     */
    private void setCoefs() throws IOException {

        // Se lee las properties del municipality.properties
        Properties prop = new Properties();
        String propFileName = "properties/municipality.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

        double coefDist = Double.parseDouble(prop.getProperty("coefDist"));
        double coefAlt = Double.parseDouble(prop.getProperty("coefAlt"));

        inputStream.close();

        // Asigno los coeficientes a todos los municipios
        this.municipalities.forEach(m -> {
            m.setCoefDist(coefDist);
            m.setCoefAlt(coefAlt);
        });
    }

    public GameStyle getStyle() {
        return style;
    }

    public void setStyle(GameStyle style) {
        this.style = style;
    }

    /**
     * calcula las distancias a un municipio en particular para obtener la minima y maxima
     */
    private List<Double> allDistsTo(Municipality municipality, List<Municipality> municipalities) {
        return municipalities.stream().map(x -> x.distanceToMunicipality(municipality)).collect(Collectors.toList());
    }

    /**
     * setea las alturas maximas y minimas al mapa, para futuros calculos y asigna alturas a municipios
     */
    private void setHeights(AsterAPI asterAPI) {
        //TODO hacerlo singleton
        List<Double> heights = asterAPI.multipleHeights(this.municipalities.stream().map(z -> z.getCentroide()).collect(Collectors.toList()));
        this.map.setMaxHeight(Collections.max(heights));
        this.map.setMinHeight(Collections.min(heights));
        this.municipalities.stream().forEach(z -> this.setHeight(z, heights));
    }


    /**
     * setea la altura a cada municipio individual
     */
    private void setHeight(Municipality municipality, List<Double> heights) {
        municipality.setHeight(heights.get(0));
        heights.remove(0);
    }

    /**
     * distribuye municipios entre jugadores al iniciar la partida
     * si municipios/jugador no da un numero entero, se asignara un municipio de mas a los primeros jugadores
     */
    private void sortMunicipalities(List<Municipality> municipalities) {
        int municipalitiesPerPlayer = 0;
        if (municipalities.size() / players.size() * players.size() == municipalities.size()) {
            municipalitiesPerPlayer = municipalities.size() / players.size();
        } else {
            municipalitiesPerPlayer = municipalities.size() / players.size() + 1;
        }
        List<List<Municipality>> municipalitiesYetToBeAdded = this.chopped(municipalities, municipalitiesPerPlayer);
        players.stream().forEach(z -> assignMunicipalities(z, municipalitiesYetToBeAdded));
    }

    /**
     * un jugador se rinde, se lo elimina de la partida, se distribuyen sus municipios a los restantes
     */
    public void surrender(String player){
        this.players.remove(player);
        List<Municipality> municipalitiesOwnerSurrendered = this.municipalities.stream()
                .filter(m -> m.getOwner().equals(player)).collect(Collectors.toList());
        sortMunicipalities(municipalitiesOwnerSurrendered);
    }


    /**
     * assigna una lista de municipios a un jugador
    */
    private void assignMunicipalities(String player, List<List<Municipality>> municipalities) {
        municipalities.get(0).stream().forEach(z -> z.setOwner(player));
        municipalities.remove(0);
    }

    /**
     * genera una lista de listas, distribuyendo equitativamente los municipios entre los jugadores
     * En caso de que la division no sea exacta, se repartira los restantes municipios a los primeros jugadores
     * @param L cuantos municipios asignar a cada jugador
     */
    private List<List<Municipality>> chopped(List<Municipality> list, final int L) {
        List<List<Municipality>> parts = new ArrayList<List<Municipality>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<Municipality>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

    /**
     * consulta si un jugador todavia posee municipios
     */
    private boolean hasMunicipalities(String player) {
        return municipalities.stream().anyMatch(
                m -> m.getOwner().equals(player));
    }

    /**
     * elimina un jugador de la partida si este no tiene municipios
     */
    private void removePlayerIfHasNotMunicipalities(){
        List<String> cleanPlayers = this.players;
        this.players = cleanPlayers.stream().filter(p -> hasMunicipalities(p)).collect(Collectors.toList());

                /*
        if (!hasMunicipalities(player)){
            players.remove(player);
        }*/
    }

    /**
     * cambia el turno, hace que el jugador que termino su turno vuelva al fondo de la lista, elimina jugadores
     * que hayan perdido y calcula los nuevos gauchos producidos por el nuevo jugador de turno
     */
    public void changeTurn() {
        //fixme: funciona mal el cancel
        players.add(players.get(0));
        players.remove(0);
        this.removePlayerIfHasNotMunicipalities();

        List<Municipality> municipalities2 = municipalities.stream().filter(z -> z.getOwner().equals(players.get(0))).collect(Collectors.toList());

        municipalities2.
                        forEach(m -> m.produceGauchos(this.map));

    }


    /**
     * obtiene una municipalidad basado en un nombre
     */
    public Municipality getMunicipality(String name) {
        return municipalities.stream().filter(m -> m.getNombre().equals(name)).findFirst().orElse(null);
    }

    /**
     * mueve gauchos de una municipalidad a otra
     */
    public void moveGauchos(int amount, Municipality origin, Municipality destination) {
        this.validateEnoughGauchos(amount, origin);

        origin.removeGauchos(amount);

        destination.addGauchos(amount);

    }

    /**
     * verifica que se posean los suficientes gauchos para realizar un traslado a otro municipio
     */
    private void validateEnoughGauchos(int amount, Municipality origin) {
        if ((origin.getGauchos() - amount) < 0)
            throw new RuntimeException("El municipio no posee suficientes gauchos");
    }

    // --------------- Getters y Setters --------------

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public int getMunicipalityLimit() {
        return municipalityLimit;
    }

    public Long getId() {
        return id;
    }

    public Map getMap() {
        return map;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GameState getState() {
        return state;
    }


    public void setState(GameState state) {
        this.state = state;
    }

    public List<Municipality> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(List<Municipality> municipalities) {
        this.municipalities = municipalities;
    }

    public String getProvince() {
        return this.map.getProvince();
    }

}
