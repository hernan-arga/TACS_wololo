package tacs.wololo.model.APIs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.Municipality;

import java.util.List;

public class GeoRefTests {
    private GeoRef geoRef;

    @Before
    public void init() {
        geoRef = new GeoRef(new ReadModeJSONGeoRef());
    }

    @Test
    public void listarProvincias() {
        List<Provincia> provincias = geoRef.listarProvincias();

        Assert.assertEquals(24, provincias.size());
        Assert.assertTrue(provincias.stream().anyMatch(p -> p.getNombre().equals("Chubut")));
        Assert.assertTrue(provincias.stream().anyMatch(p -> p.getNombre().equals("Misiones")));
    }

    @Test
    public void municipioPorNombre() {
        Municipality municipioAbraPampa = geoRef.municipioPorNombre("Abra Pampa");

        Assert.assertEquals("Abra Pampa", municipioAbraPampa.nombre);
    }

    @Test(expected = RuntimeException.class)
    public void municipioPorNombreNoExiste() {
        Municipality municipioNoExiste = geoRef.municipioPorNombre("Municipio inexistente");
    }

    @Test
    public void municipioPorProvincia() {
        List<Municipality> municipalities = geoRef.municipioPorProvincia("Formosa");

        Assert.assertEquals(37, municipalities.size());
        Assert.assertTrue(municipalities.stream().anyMatch(m -> m.getNombre().equals("Fortín Lugones")));
        Assert.assertTrue(municipalities.stream().anyMatch(m -> m.getNombre().equals("Espinillo")));
    }

    @Test(expected = RuntimeException.class)
    public void municipioPorProvinciaNoExiste() {
        List<Municipality> municipalities = geoRef.municipioPorProvincia("Provincia inexistente");
    }
}
