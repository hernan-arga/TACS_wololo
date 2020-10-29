package tacs.wololo.model.APIs;




import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tacs.wololo.model.APIs.GeoData.DatosMunicipio;

import tacs.wololo.model.APIs.GeoData.DatosProvincia;
import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.Municipality;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


@RestController
public class GeoRef {

    private boolean getFromAPI;
    private String apiGeorefProperties = "apiGeoref.properties";


    public GeoRef() {
        /*this.readAttribute();

        if(getFromAPI) {
        //Moodo api
            }
        else //modo json
*/

    }

    private void readAttribute() {
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(apiGeorefProperties);


        try {
            prop.load(inputStream);
            this.getFromAPI = Boolean.parseBoolean(prop.getProperty("getFromAPI"));

            inputStream.close();
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("property file '" + apiGeorefProperties + "' not found in the classpath");
        }

    }

    private String URL = "https://apis.datos.gob.ar/georef/api/";


    private RestTemplate restTemplate = new RestTemplate();


    public Municipality municipioPorNombre(String nombre) {

        return restTemplate.getForObject("https://apis.datos.gob.ar/georef/api/municipios?nombre="+nombre, DatosMunicipio.class).municipios.get(0);

    }
    public List<Municipality> municipioPorProvincia(String provincia) {

        return restTemplate.getForObject("https://apis.datos.gob.ar/georef/api/municipios"+"?provincia="+provincia+"&max=1000", DatosMunicipio.class).municipios;

    }

    public List<Provincia> listarProvincias() {

        return restTemplate.getForObject("https://apis.datos.gob.ar/georef/api/provincias", DatosProvincia.class).provincias;

    }


}
