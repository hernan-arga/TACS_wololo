package tacs.wololo.model.APIs;

import org.junit.Before;
import org.junit.Test;
import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.Municipality;

import java.io.FileNotFoundException;
import java.util.List;

public class GeoRefTests {
    private ReadModeJSONGeoRef readModeJSONGeoRef;

    @Before
    public void init() {
        readModeJSONGeoRef = new ReadModeJSONGeoRef();
    }

    // TODO: SACAR, dejo esto comentado para tener como base a la hora de hacer los tests
    @Test
    public void pruebaBase() {
        // Test listarProvincias()
        /*List<Provincia> provinces = readModeJSONGeoRef.listarProvincias();
        provinces.stream().forEach(p -> System.out.println(p.getNombre()));*/

        // Test municipioPorNombre("Abra Pampa")
        // https://apis.datos.gob.ar/georef/api/municipios?nombre=Abra Pampa
        /*Municipality municipioPorNombre = readModeJSONGeoRef.municipioPorNombre("Abra Pampa");
        System.out.println(municipioPorNombre.getNombre());*/

        // Test municipioPorProvincia("Formosa")
        // https://apis.datos.gob.ar/georef/api/municipios?provincia=Formosa&max=100
        /*List<Municipality> municipalities = readModeJSONGeoRef.municipioPorProvincia("Formosa");
        municipalities.stream().forEach(p -> System.out.println(p.getNombre()));*/
    }

}
