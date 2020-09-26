package tacs.wololo.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tacs.wololo.model.APIs.AsterAPI;
import tacs.wololo.model.APIs.GeoData.Centroide;
import tacs.wololo.model.APIs.GeoRef;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTest {

    private Game aGame;
    private Map aMap;
    private GameState state;
    private static int MUNICIPALITY_LIMIT = 15; //DEPENDE DE LA PROVINCIA
    private Municipality municipality1;
    private Municipality municipality2;
    private Queue<String> players;
    GeoRef geoRefMock;
    AsterAPI asterAPImock;
    private static String PROVINCIA = "Salta";
    private Centroide centroide1;
    private Centroide centroide2;



    @Before
    public void init() {

        centroide1 = new Centroide(120.1, -60.0);
        centroide2 = new Centroide(126.1, -67.0);
        List<Centroide> centroides = new LinkedList<>();
        centroides.add(centroide2);
        centroides.add(centroide1);


        municipality1 = mock(Municipality.class);
        municipality2 = mock(Municipality.class);
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

        heights.add(municipality1.getHeight());
        heights.add(municipality2.getHeight());

        players = new LinkedList<>();
        players.add("fulano");
        players.add("mengano");
        //players.add("fran");

        aMap = new Map(PROVINCIA);

        geoRefMock = mock(GeoRef.class);
        when(geoRefMock.municipioPorProvincia(PROVINCIA)).thenReturn(municipalities);
        asterAPImock = mock(AsterAPI.class);
        when(asterAPImock.multipleHeights(centroides)).thenReturn(heights);
        //(Map map, Date date, Queue<String> players, GameState state, int municipalityLimit, GeoRef geoRef)
        aGame = new Game(aMap, new Date(), players, GameState.CREATED, MUNICIPALITY_LIMIT, geoRefMock, asterAPImock);
        //TODO El constructor no debería recibir estado de juego
    }

    @Test
    public void maximumHeightReturns150() {
//(Map map, Date date, Queue<String> players, GameState state, int municipalityLimit, GeoRef geoRef)
        Assert.assertEquals(aGame.getMap().getMaxHeight(), 150);
    }
}
