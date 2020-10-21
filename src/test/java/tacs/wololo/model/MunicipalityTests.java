package tacs.wololo.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tacs.wololo.model.APIs.GeoData.Centroide;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MunicipalityTests {
    private Municipality municipality;
    private Municipality municipalityStrong;
    private Municipality municipalityWeak;
    private Municipality attacker;
    private Municipality defender;
    private Map map;
    private Map aMap;

    private static double EXPECTED_HM = 1.25;
    private static double EXPECTED_DISTM = 0.75;
    private static double EXPECTED_AG_PROD = -50.0;
    private static double EXPECTED_DG_PROD = 40.0;
    private static double EXPECTED_AG_DEFN = -81;//Está mal en el enunciado. Dice 31.0 con 250 gauchos en attacker, pero tiene 100
    private static double EXPECTED_DG_DEFN = 52.0; //Está mal el resultado en el enunciado. Dice -20
    private static double G_TO_ADD_PROD = 11.0;
    private static double G_TO_ADD_DEFN = 7;
    @Before
    public void init() throws IOException {
        ProducerMunicipality producerMunicipality = mock(ProducerMunicipality.class);
        when(producerMunicipality.getCoefProdGauchos()).thenReturn((float) 15);
        when(producerMunicipality.getMultDef()).thenReturn((float) 1);


        municipality = new Municipality("strong",10, 10, producerMunicipality, new Centroide(56.0, 123.0));
        municipalityStrong = new Municipality("strong",10000, 10, producerMunicipality, new Centroide(1.0, 1.0));
        municipalityWeak = new Municipality("weak",5, 10, producerMunicipality, new Centroide(0.0, 0.0));


        //Mismo ejemplo de cálculo que en el enunciado del TP
        ProducerMunicipality producer = new ProducerMunicipality(GameStyle.NORMAL);
        attacker = new Municipality("player 1", 100, 0, producer, new Centroide(0.0, 0.0));
        defender = new Municipality("player 2", 100, 1500, producer, new Centroide(0.0, -0.1349));
        aMap = mock(Map.class);
        when(aMap.getMaxHeight()).thenReturn((double) 2000);
        when(aMap.getMinHeight()).thenReturn((double) 1000);
        when(aMap.getDistMin()).thenReturn((double) 10);
        when(aMap.getDistMax()).thenReturn((double) 20);

        map = mock(Map.class);
        when(map.getMaxHeight()).thenReturn((double) 20);
        when(map.getMinHeight()).thenReturn((double) 5);
        when(map.getDistMin()).thenReturn((double) 1);
        when(map.getDistMax()).thenReturn((double) 100);
    }


    @Test
    public void municipalityAttacksAndWins()
    {
        municipalityStrong.attackMunicipality(municipalityWeak, map);

        assertEquals("strong", municipalityWeak.getOwner());
        assertEquals(2102,municipalityStrong.getGauchos());
    }

    @Test
    public void municipalityAttacksAndLoses()
    {
        municipalityWeak.attackMunicipality(municipalityStrong, map);

        assertEquals(0,municipalityWeak.getGauchos());
        assertEquals(9999, municipalityStrong.getGauchos());
    }

    // Cada turno es posible cambiar el estado de un municipio entre producción o defensa
    @Test
    public void fromProducerToDefending() throws IOException {
        DefendingMunicipality modoDefensa = new DefendingMunicipality();
        municipality.setMode(modoDefensa);
        assertEquals(municipality.getMode(),modoDefensa);
    }

    @Test
    public void produceGauchos()
    {
        municipality.produceGauchos(map);
        assertEquals(22, municipality.getGauchos());
    }


    @Test
    public void distanceToMunicipality() {
        Assert.assertEquals(157.2, municipalityWeak.distanceToMunicipality(municipalityStrong), 1.0);
    }

    @Test
    public void gauchosDefenderProduceG_TO_ADD_PRODIfProducer() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = null;
        method = Municipality.class.getDeclaredMethod("gauchosToAdd", Map.class);
        method.setAccessible(true);
        int output = (int) method.invoke(defender, aMap);

        Assert.assertEquals(G_TO_ADD_PROD, output, 0.0);
    }


    @Test
    public void gauchosDefenderProduceG_TO_ADD_DEFNIfProducer() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException, IOException {

        defender.changeMode();

        Method method = null;
        method = Municipality.class.getDeclaredMethod("gauchosToAdd", Map.class);
        method.setAccessible(true);
        int output = (int) method.invoke(defender, aMap);

        Assert.assertEquals(G_TO_ADD_DEFN, output, 0.0);
    }



    @Test
    @SuppressWarnings("unchecked")
    public void heightMultiplierOfDefenderIsEXPECTED_HM() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = null;
        Double output = null;
        method = Municipality.class.getDeclaredMethod("multAlt", Map.class);
        method.setAccessible(true);
        output = (Double) method.invoke(defender, aMap);

        Assert.assertEquals(EXPECTED_HM,output, 0);
    }
    @Test
    @SuppressWarnings("unchecked")
    public void distanceMultiplierOfDefenderIsEXPECTED_DISTM() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = null;
        Double output = null;
        method = Municipality.class.getDeclaredMethod("multDist", Municipality.class, Map.class);
        method.setAccessible(true);
        output = (Double) method.invoke(defender, attacker, aMap);

        Assert.assertEquals(EXPECTED_DISTM,output, 0.01);
    }


    @Test
    public void endingAttackingGauchosIsEXPECTED_AGIfProducer() {
        Assert.assertEquals(EXPECTED_AG_PROD, attacker.endingAttackingGauchos(defender, aMap), 0.0);
    }

    @Test
    public void endingDefenderGauchosIsEXPECTED_DGIfProducer() {
        Assert.assertEquals(EXPECTED_DG_PROD, defender.endingDefendersGauchos(attacker, aMap), 0.0);
    }


    @Test
    public void endingAttackingGauchosIsEXPECTED_AGIfDefending() throws IOException {
        defender.changeMode();
        Assert.assertEquals(EXPECTED_AG_DEFN, attacker.endingAttackingGauchos(defender, aMap), 0.0);
    }

    @Test
    public void endingDefenderGauchosIsEXPECTED_DGIfDefending() throws IOException {
        defender.changeMode();
        //attacker.changeMode();
        Assert.assertEquals(EXPECTED_DG_DEFN, defender.endingDefendersGauchos(attacker, aMap), 0.0);
    }


}
