package tacs.wololo.model.APIs;

import org.springframework.web.client.RestTemplate;
import tacs.wololo.model.APIs.GeoData.DatosMunicipio;
import tacs.wololo.model.APIs.GeoData.DatosProvincia;
import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.Municipality;

import java.util.List;

public class ReadModeAPIGeoRef implements ReadModeGeoRef {

    private RestTemplate restTemplate;

    public ReadModeAPIGeoRef() {
         restTemplate = new RestTemplate();
    }

    @Override
    public Municipality municipioPorNombre(String nombre) {

        return restTemplate.getForObject("https://apis.datos.gob.ar/georef/api/municipios?nombre="+nombre, DatosMunicipio.class).municipios.get(0);

    }
    @Override
    public List<Municipality> municipioPorProvincia(String provincia) {

        return restTemplate.getForObject("https://apis.datos.gob.ar/georef/api/municipios"+"?provincia="+provincia+"&max=1000", DatosMunicipio.class).municipios;

    }

    @Override
    public List<Provincia> listarProvincias() {

        return restTemplate.getForObject("https://apis.datos.gob.ar/georef/api/provincias", DatosProvincia.class).provincias;

    }
}
