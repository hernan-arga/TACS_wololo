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
    public HashMap<String, Integer> getScoreBoard() {
        HashMap<String, Integer> scoreBoard = new HashMap <String, Integer> ();

        this.players.forEach(p ->
        {
            List<Municipality> municipalitiesOwner = this.municipalities.stream().filter(m -> m.getOwner().equals(p)).collect(Collectors.toList());
            scoreBoard.put(p, municipalitiesOwner.size());
        });

        return scoreBoard;
    }

    private void setMapLatAndLon() {
        List<Double> latitudes = this.municipalities.stream().map(m -> m.getCentroide().getLat()).collect(Collectors.toList());
        List<Double> longitudes = this.municipalities.stream().map(m -> m.getCentroide().getLon()).collect(Collectors.toList());
        this.map.setLatMax(Collections.max(latitudes));
        this.map.setLonMax(Collections.max(longitudes));
        this.map.setLatMin(Collections.min(latitudes));
        this.map.setLonMin(Collections.min(longitudes));
    }

    private void setDists(List<Municipality> municipalities) {
        List<List<Double>> distances = municipalities.stream().map(z -> this.allDistsTo(z, municipalities)).collect(Collectors.toList());
        List<Double> flattenDistances = distances.stream().flatMap(List::stream).filter(z -> z > 0).collect(Collectors.toList());
        map.setDistMax(Collections.max(flattenDistances));
        map.setDistMin(Collections.min(flattenDistances));
    }

    private void setCoefs() throws IOException {

        // Se lee las properties del municipality.properties
        Properties prop = new Properties();
        String propFileName = "municipality.properties";

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

    private List<Double> allDistsTo(Municipality municipality, List<Municipality> municipalities) {
        return municipalities.stream().map(x -> x.distanceToMunicipality(municipality)).collect(Collectors.toList());
    }

    private void setHeights(AsterAPI asterAPI) {
        //TODO hacerlo singleton
        List<Double> heights = asterAPI.multipleHeights(this.municipalities.stream().map(z -> z.getCentroide()).collect(Collectors.toList()));
        this.map.setMaxHeight(Collections.max(heights));
        this.map.setMinHeight(Collections.min(heights));
        this.municipalities.stream().forEach(z -> this.setHeight(z, heights));
    }


    private void setHeight(Municipality municipality, List<Double> heights) {
        municipality.setHeight(heights.get(0));
        heights.remove(0);
    }

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

    public void surrender(String player){
        this.players.remove(player);
        List<Municipality> municipalitiesOwnerSurrendered = this.municipalities.stream()
                .filter(m -> m.getOwner().equals(player)).collect(Collectors.toList());
        sortMunicipalities(municipalitiesOwnerSurrendered);
    }


    /*
     * Asign a list of municipalities to a player
     * */
    private void assignMunicipalities(String player, List<List<Municipality>> municipalities) {
        municipalities.get(0).stream().forEach(z -> z.setOwner(player));
        municipalities.remove(0);
    }

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

    private boolean hasMunicipalities(String player) {
        return municipalities.stream().anyMatch(
                m -> m.getOwner().equals(player));
    }

    private void removePlayerIfHasNotMunicipalities(String player){
        if (!hasMunicipalities(player)){
            players.remove(player);
        }
    }

    public void changeTurn() {

        players.add(players.get(0));
        players.remove(0);

        players.forEach(p -> removePlayerIfHasNotMunicipalities(p));

        municipalities.stream().filter(z ->z.getOwner().equals(players.get(0))).
                        forEach(m ->m.produceGauchos(this.map));

    }

    public Municipality getMunicipality(String name) {
        return municipalities.stream().filter(m -> m.getNombre().equals(name)).findFirst().orElse(null);
    }

    public void moveGauchos(int amount, Municipality origin, Municipality destination) {
        this.validateEnoughGauchos(amount, origin);

        System.out.println("2");

        origin.removeGauchos(amount);

        System.out.println("3");

        destination.addGauchos(amount);

        System.out.println("4");
    }

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
