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

    @Before
    public void init() throws IOException {
        municipality = new Municipality("strong",10, 10, new ProducerMunicipality(), new Centroide(56.0, 123.0));
        municipalityStrong = new Municipality("strong",10000, 10, new ProducerMunicipality(), new Centroide(1.0, 1.0));
        municipalityWeak = new Municipality("weak",5, 10, new ProducerMunicipality(), new Centroide(0.0, 0.0));

        //Mismo ejemplo de cálculo que en el enunciado del TP
        attacker = new Municipality("player 1", 100, 0, new ProducerMunicipality(), new Centroide(0.0, 0.0));
        defender = new Municipality("player 2", 100, 1500, new ProducerMunicipality(), new Centroide(0.0, -0.1349));
        aMap = mock(Map.class);
        when(aMap.getMaxHeight()).thenReturn((double) 2000);
        when(aMap.getMinHeight()).thenReturn((double) 1000);
        when(aMap.getDistMin()).thenReturn((double) 10);
        when(aMap.getDistMax()).thenReturn((double) 20);

        /* 10*
        (this.gauchos*this.multDist(defenderMunicipality, map)
               - defenderMunicipality.gauchos*defenderMunicipality.multAlt(map)*defenderMunicipality.getMode().getMultDef());
        * */
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
        assertEquals(10000, municipalityStrong.getGauchos());
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
        assertEquals(12, municipality.getGauchos());
    }


    @Test
    public void endingAttackingGauchos() {

    }

    @Test
    public void distanceToMunicipality() {
        Assert.assertEquals(157.2, municipalityWeak.distanceToMunicipality(municipalityStrong), 1.0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void heightMultiplier() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        Method method = null;
        Double output = null;
        method = Municipality.class.getDeclaredMethod("heightMultiplier", Map.class);
        method.setAccessible(true);
        output = (Double) method.invoke(defender, aMap);

        Assert.assertEquals(1.25,output, 0);
    }

}
