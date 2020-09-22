package tacs.wololo.model;

import net.minidev.json.annotate.JsonIgnore;
import tacs.wololo.model.APIs.AsterAPI;
import tacs.wololo.model.APIs.GeoRef;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    Long id;
    Map map;
    String province;
    Date date;
    Queue<Player> players;
    GameState state;
    List<Municipality> municipalities;
    int municipalityLimit;

    public Game() {
    }

    public Game(Map map, String province, Date date, Queue<Player> players, GameState state, int municipalityLimit) {

        this.id = System.currentTimeMillis();
        this.municipalityLimit = municipalityLimit;
        GeoRef geoRef = new GeoRef();       //TODO hacerlo singleton que no instancie
        this.map = map;
        this.province = province;
        this.date = date;
        this.players = players;
        this.state = state;
        this.municipalities = geoRef.municipioPorProvincia(province);
        this.setMapLatAndLon();
        this.municipalities = this.municipalities.stream().limit(this.municipalityLimit).collect(Collectors.toList());
        this.setDists(this.municipalities);
        this.setHeights();
        this.sortMunicipalities();
        for (Municipality municipality : this.municipalities) {
            municipality.setGauchos(10);
            municipality.setMode(new DefendingMunicipality());
        }

    }

    private void setMapLatAndLon(){
        List<Double> latitudes = this.municipalities.stream().map(m -> m.centroide.lat).collect(Collectors.toList());
        List<Double> longitudes = this.municipalities.stream().map(m -> m.centroide.lon).collect(Collectors.toList());
        this.map.setLatMax(Collections.max(latitudes));
        this.map.setLonMax(Collections.max(longitudes));
        this.map.setLatMin(Collections.min(latitudes));
        this.map.setLonMin(Collections.min(longitudes));
    }

    private void setDists(List<Municipality> municipalities){
        List<List<Double>> distances = municipalities.stream().map(z->this.allDistsTo(z,municipalities)).collect(Collectors.toList());
        List<Double> flattenDistances = distances.stream().flatMap(List::stream).filter(z->z>0).collect(Collectors.toList());
        map.setDistMax(Collections.max(flattenDistances));
        map.setDistMin(Collections.min(flattenDistances));
    }
    private List<Double> allDistsTo (Municipality municipality, List<Municipality> municipalities){
        return municipalities.stream().map(x -> x.distanceToMunicipality(municipality)).collect(Collectors.toList());
    }

    private void setHeights(){
        AsterAPI asterAPI = new AsterAPI();     //TODO hacerlo singleton
        List<Double> heights = asterAPI.multipleHeights(this.municipalities.stream().map(z->z.centroide).collect(Collectors.toList()));
        this.map.setMaxHeight(Collections.max(heights));
        this.map.setMinHeight(Collections.min(heights));
        this.municipalities.stream().forEach(z->this.setHeight(z,heights));
    }

    private void setHeight(Municipality municipality,List<Double> heights){
        municipality.setHeight(heights.get(0));
        heights.remove(0);
    }

    private void sortMunicipalities(){
        int municipalitiesPerPlayer = 0;
        if(municipalities.size()/players.size()*players.size()==municipalities.size()){
            municipalitiesPerPlayer = municipalities.size()/players.size();
        }
        else{
            municipalitiesPerPlayer = municipalities.size()/players.size()+1;
        }
        List<List<Municipality>> municipalitiesYetToBeAdded = this.chopped(municipalities,municipalitiesPerPlayer);
        players.stream().forEach(z->assignMunicipalities(z,municipalitiesYetToBeAdded) );
    }

    private void assignMunicipalities(Player player, List<List<Municipality>> municipalities){
        municipalities.get(0).stream().forEach(z->z.setOwner(player));
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

    private void changeTurn() {
        players.add(players.peek());
        players.remove();
    }

    public Municipality getMunicipality(String id)
    {
        return municipalities.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    // --------------- Getters y Setters --------------

    public Queue<Player> getPlayers() {
        return players;
    }

    public String getProvince() {
        return province;
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

    public GameState getState() {
        return state;
    }

    public List<Municipality> getMunicipalities() {
        return municipalities;
    }
}
