package tacs.wololo;

import org.junit.Before;
import org.junit.Test;

import tacs.wololo.model.*;
import tacs.wololo.model.APIs.GeoData.Centroide;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MunicipalityTests {
    private Municipality municipality;
    private Municipality municipalityStrong;
    private Municipality municipalityWeak;
    private Map map;
    private Player ownerStrong;
    private Player ownerWeak;

    @Before
    public void init()
    {
        ownerStrong = new Player();
        ownerWeak = new Player();

        municipality = new Municipality(ownerStrong,10, 10, new ProducerMunicipality(), new Centroide(56.0, 123.0));
        municipalityStrong = new Municipality(ownerStrong,10000, 10, new ProducerMunicipality(), new Centroide(1.0, 1.0));
        municipalityWeak = new Municipality(ownerWeak,5, 10, new ProducerMunicipality(), new Centroide(0.0, 0.0));

        ownerStrong.addMunicipality(municipality);
        ownerStrong.addMunicipality(municipalityStrong);

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

        assertEquals(ownerStrong, municipalityWeak.getOwner());
        assertEquals(2102,municipalityStrong.getGauchos());
    }

    @Test
    public void municipalityAttacksAndLoses()
    {
        municipalityWeak.attackMunicipality(municipalityStrong, map);

        assertEquals(0,municipalityWeak.getGauchos());
        assertEquals(10000, municipalityStrong.getGauchos());
    }

    @Test
    public void moveGauchos() throws Exception {
        ownerStrong.moveGauchos(5, municipalityStrong, municipality);

        assertEquals(15, municipality.getGauchos());
        assertEquals(9995, municipalityStrong.getGauchos());
    }


    @Test(expected = Exception.class)
    public void moveMoreGauchosThanIHave() throws Exception {
        ownerStrong.moveGauchos(100, municipality, municipalityStrong);
    }

    @Test(expected = Exception.class)
    public void moveMoreGauchosFromMunicipalityIDontHave() throws Exception {
        ownerStrong.moveGauchos(1, municipalityWeak, municipalityStrong);
    }

    // Cada turno es posible cambiar el estado de un municipio entre producción o defensa
    @Test
    public void fromProducerToDefending()
    {
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

    // Todas los números con * deben ser configurables en el sistema internamente
    @Test
    public void configureCoefGauchos()
    {
        DefendingMunicipality modoDefensaModif = new DefendingMunicipality();
        modoDefensaModif.setCoefProdGauchos(15f);
        assertEquals(15, modoDefensaModif.getCoefProdGauchos(), 0);
    }

    @Test
    public void configureMulDef()
    {
        DefendingMunicipality modoDefensaModif = new DefendingMunicipality();
        modoDefensaModif.setMultDef(1.5f);
        assertEquals(1.5, modoDefensaModif.getMultDef(),0);
    }
}
