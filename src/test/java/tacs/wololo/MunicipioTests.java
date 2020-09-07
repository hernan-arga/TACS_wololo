package tacs.wololo;

import org.junit.Before;
import org.junit.Test;

import tacs.wololo.model.Mapa;
import tacs.wololo.model.Municipio;
import tacs.wololo.model.MunicipioDefensa;
import tacs.wololo.model.MunicipioProductor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MunicipioTests {
    private Municipio municipio;
    private Mapa mapa;

    @Before
    public void init()
    {
        municipio = new Municipio(0, 10, new MunicipioProductor());
        mapa = mock(Mapa.class);
        when(mapa.getMaxAltura()).thenReturn((double) 20);
        when(mapa.getMinAltura()).thenReturn((double) 5);
    }

    // TODO: Si un municipio ataca, se obtiene el municipio atacado si quedan gauchos después del ataque. Se utilizan las siguientes ecuaciones para saber el estado final del ataque
    // TODO: Es posible mover gauchos entre municipios de un mismo jugador, pero tras recibir gauchos el municipio destino queda bloqueado y no puede realizar movimientos hacia otro lugar

    // Cada turno es posible cambiar el estado de un municipio entre producción o defensa
    @Test
    public void deProduccionADefensa()
    {
        MunicipioDefensa modoDefensa = new MunicipioDefensa();
        municipio.setModo(modoDefensa);
        assertEquals(municipio.getModo(),modoDefensa);
    }

    @Test
    public void producirGauchos()
    {
        municipio.producirGauchos(mapa);
        assertEquals(2, municipio.getGauchos());
    }

    // Todas los números con * deben ser configurables en el sistema internamente
    @Test
    public void configurarCoefGauchos()
    {
        MunicipioDefensa modoDefensaModif = new MunicipioDefensa();
        modoDefensaModif.setCoefProdGauchos(15f);
        assertEquals(15, modoDefensaModif.getCoefProdGauchos(), 0);
    }

    @Test
    public void configurarMulDef()
    {
        MunicipioDefensa modoDefensaModif = new MunicipioDefensa();
        modoDefensaModif.setMultDef(1.5f);
        assertEquals(1.5, modoDefensaModif.getMultDef(),0);
    }
}
