package tacs.wololo.model.APIs;




import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tacs.wololo.model.APIs.GeoData.DatosMunicipio;

import tacs.wololo.model.Municipality;

import java.util.List;


@RestController
public class GeoRef {


    private String URL = "https://apis.datos.gob.ar/georef/api/";


    private RestTemplate restTemplate = new RestTemplate();


    public Municipality municipioPorNombre(String nombre) {

        return restTemplate.getForObject("https://apis.datos.gob.ar/georef/api/municipios?nombre="+nombre, DatosMunicipio.class).municipios.get(0);

    }
    public List<Municipality> municipioPorProvincia(String provincia) {

        return restTemplate.getForObject("https://apis.datos.gob.ar/georef/api/municipios"+"?provincia="+provincia, DatosMunicipio.class).municipios;

    }


}
