package tacs.wololo.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tacs.wololo.model.APIs.AsterAPI;
import tacs.wololo.model.APIs.GeoData.Centroide;
import tacs.wololo.model.APIs.GeoRef;
import tacs.wololo.services.implementations.GmailService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTests {


    private Game aGame;
    private Game easyGame;
    private Map aMap;
    private GameState state;
    private static int MUNICIPALITY_LIMIT = 15; //DEPENDE DE LA PROVINCIA
    private Municipality municipality1;
    private Municipality municipality2;
    private Municipality municipality3;
    private List<String> players;
    GeoRef geoRefMock;
    AsterAPI asterAPImock;
    private static String PROVINCIA = "Salta";
    private Centroide centroide1;
    private Centroide centroide2;
    private Municipality attacker;
    private Municipality defender;
    List<Municipality> realMunicipalities;

    @Before
    public void init() throws IOException {
        centroide1 = new Centroide(120.1, -60.0);
        centroide2 = new Centroide(126.1, -67.0);
        List<Centroide> centroides = new ArrayList<>();
        centroides.add(centroide1);
        centroides.add(centroide2);

        municipality1 = mock(Municipality.class);
        municipality2 = mock(Municipality.class);
        municipality3 = mock(Municipality.class);
        List<Municipality> municipalities = new ArrayList<>();
        municipalities.add(municipality1);
        municipalities.add(municipality2);


        List<Double> heights = new ArrayList<>();

        when(municipality1.getHeight()).thenReturn(new Double(100));
        when(municipality2.getHeight()).thenReturn(new Double(150));
        when(municipality1.getCentroide()).thenReturn(centroide1);
        when(municipality2.getCentroide()).thenReturn(centroide2);
        when(municipality1.distanceToMunicipality(municipality2)).thenReturn((double) 1000);
        when(municipality2.distanceToMunicipality(municipality1)).thenReturn((double) 1000);

        //when(municipality1.setHeight(Double height)).then()) suponiendo que se ejecuta como función vacía

        heights.add((double) 100);
        heights.add((double) 150);

        players = new ArrayList<>();
        players.add("player 1");
        players.add("player 2");

        aMap = new Map(PROVINCIA);

        geoRefMock = mock(GeoRef.class);
        when(geoRefMock.municipioPorProvincia(PROVINCIA)).thenReturn(municipalities);
        asterAPImock = mock(AsterAPI.class);
        when(asterAPImock.multipleHeights(centroides)).thenReturn(heights);

        aGame = new Game(aMap, new Date(), players, GameState.EN_PROGRESO, MUNICIPALITY_LIMIT,
                geoRefMock, asterAPImock, GameStyle.NORMAL);
        //TODO El constructor no debería recibir estado de juego

        easyGame = new Game();
        municipalities.add(municipality3);

        List<String> playersEasyGame = new ArrayList<>();
        playersEasyGame.add("fulano");
        playersEasyGame.add("mengano");
        playersEasyGame.add("marciano");

        when(municipality1.getOwner()).thenReturn("fulano");
        when(municipality2.getOwner()).thenReturn("mengano");
        when(municipality3.getOwner()).thenReturn("mengano");

        when(municipality1.getGauchos()).thenReturn(5);

        easyGame.setPlayers(playersEasyGame);
        easyGame.setMunicipalities(municipalities);

        //----------- Municipalidades de verdad

        attacker = new Municipality("player 1", 100, 0, new ProducerMunicipality(), new Centroide(0.0, 0.0));
        defender = new Municipality("player 2", 100, 1500, new ProducerMunicipality(), new Centroide(0.0, -0.1349));
        realMunicipalities = new ArrayList<>();
        realMunicipalities.add(attacker);
        realMunicipalities.add(defender);
        aGame.setMunicipalities(realMunicipalities);


    }

    @Test
    public void getScoreBoard() {
        HashMap<String, Integer> scoreBoard = easyGame.getScoreBoard();

        HashMap<String, Integer> scoreBoardExpected = new HashMap <String, Integer> ();
        scoreBoardExpected.put("fulano", 1);
        scoreBoardExpected.put("mengano", 2);
        scoreBoardExpected.put("marciano", 0);

        Assert.assertEquals(scoreBoardExpected, scoreBoard);
    }


    @Test(expected = Exception.class)
    public void moveMoreGauchosThanIHave() throws Exception {
        easyGame.moveGauchos(100, municipality1, municipality2);
    }

    @Test
    public void maximumHeightReturns150() {
//(Map map, Date date, Queue<String> players, GameState state, int municipalityLimit, GeoRef geoRef)
        Assert.assertEquals(aGame.getMap().getMaxHeight(), 150.0, 0);
    }


    @Test
    public void minHeightReturns100() {
        Assert.assertEquals(aGame.getMap().getMinHeight(), 100.0, 0);
    }

    @Test
    public void whenMove50GauchosFromAttackerToDefender() {
        aGame.moveGauchos(50, attacker, defender);
        Assert.assertEquals(50, attacker.getGauchos(), 0.0);
        Assert.assertEquals(150, defender.getGauchos(), 0.0);
    }

    @Test
    public void WhenTurnChangesAllPlayersWithoutMunicipalitiesAreRemoved() {
        Assert.assertEquals(2, aGame.getPlayers().size());
        aGame.setMunicipalities(realMunicipalities.stream().limit(1).collect(Collectors.toList()));

        //When turn changes, all players without municipalities are removed
        aGame.changeTurn();
        Assert.assertEquals(1,aGame.getPlayers().size());
    }


    /*@Test
    public void sendEmail(){
        GmailService gmailService = new GmailService();
        User mati = new User("mati","matiasgamal98@gmail.com","matikpo");
        gmailService.sendEmail(mati);
    }*/

}

