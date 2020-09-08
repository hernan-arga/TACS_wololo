package tacs.wololo;

import org.junit.Before;
import org.junit.Test;

import tacs.wololo.model.Map;
import tacs.wololo.model.Municipality;
import tacs.wololo.model.DefendingMunicipality;
import tacs.wololo.model.ProducerMunicipality;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MunicipalityTests {
    private Municipality municipality;
    private Map map;

    @Before
    public void init()
    {
        municipality = new Municipality(0, 10, new ProducerMunicipality());
        map = mock(Map.class);
        when(map.getMaxHeight()).thenReturn((double) 20);
        when(map.getMinHeight()).thenReturn((double) 5);
    }

    // TODO: Si un municipio ataca, se obtiene el municipio atacado si quedan gauchos después del ataque. Se utilizan las siguientes ecuaciones para saber el estado final del ataque
    // TODO: Es posible mover gauchos entre municipios de un mismo jugador, pero tras recibir gauchos el municipio destino queda bloqueado y no puede realizar movimientos hacia otro lugar

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
        assertEquals(2, municipality.getGauchos());
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
