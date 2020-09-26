package tacs.wololo.model;

import tacs.wololo.model.APIs.AsterAPI;
import tacs.wololo.model.APIs.GeoRef;

import java.util.*;
import java.util.stream.Collectors;

public class Game
{
    Long id;
    Map map;
    Date date;

    Queue<String> players;

    GameState state;

    List<Municipality> municipalities;

    int municipalityLimit;
    GeoRef geoRef;

    public Game() {
    }

    public Game(Map map, Date date, Queue<String> players, GameState state, int municipalityLimit, GeoRef geoRef, AsterAPI asterAPI )

    {
        this.id = System.currentTimeMillis();
        this.municipalityLimit = municipalityLimit;
        this.geoRef = geoRef;      //TODO hacerlo singleton que no instancie
        this.map = map;
        this.date = date;
        this.players = players;
        this.state = state;
        this.municipalities = this.geoRef.municipioPorProvincia(map.getProvince());
        this.setMapLatAndLon();
        this.municipalities = this.municipalities.stream().limit(this.municipalityLimit).collect(Collectors.toList());
        this.setDists(this.municipalities);
        this.setHeights(asterAPI);
        this.sortMunicipalities();

        Random random = new Random();

        for (Municipality municipality : this.municipalities)  //TODO:Parametrizar por config file
        {
            municipality.setGauchos(random.nextInt(15));

            if(random.nextInt(100) < 25)
                municipality.setMode(new ProducerMunicipality());
            else
                municipality.setMode(new DefendingMunicipality());
        }

    }

    public void setPlayers(Queue<String> players)
    {
        this.players = players;
    }

    private void setMapLatAndLon(){
        List<Double> latitudes = this.municipalities.stream().map(m -> m.getCentroide().getLat()).collect(Collectors.toList());
        List<Double> longitudes = this.municipalities.stream().map(m -> m.getCentroide().getLon()).collect(Collectors.toList());
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

    private void setHeights(AsterAPI asterAPI){
            //TODO hacerlo singleton
        List<Double> heights = asterAPI.multipleHeights(this.municipalities.stream().map(z->z.getCentroide()).collect(Collectors.toList()));
        this.map.setMaxHeight(Collections.max(heights));
        this.map.setMinHeight(Collections.min(heights));
        this.municipalities.stream().forEach(z->this.setHeight(z,heights));
    }



    private void setHeight(Municipality municipality,List<Double> heights){
        municipality.setHeight(heights.get(0));
        heights.remove(0);
    }

    private void sortMunicipalities()
    {
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


/*
* Asign a list of municipalities to a player
* */
    private void assignMunicipalities(String player, List<List<Municipality>> municipalities){
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

    public Municipality getMunicipality(String name)
    {
        return municipalities.stream().filter(m -> m.getNombre().equals(name)).findFirst().orElse(null);
    }

    public void moveGauchos(int amount, Municipality origin, Municipality destination)
    {
        this.validateEnoughGauchos(amount, origin);
        origin.removeGauchos(amount);
        destination.addGauchos(amount);
    }

    private void validateEnoughGauchos(int amount, Municipality origin)
    {
        if((origin.getGauchos() - amount) < 0)
            throw new RuntimeException("El municipio no posee suficientes gauchos");
    }

    // --------------- Getters y Setters --------------

    public Queue<String> getPlayers() {
        return players;
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

    public void setDate(Date date)
    {
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

    public String getProvince()
    {
        return this.map.getProvince();
    }
}
