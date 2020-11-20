package tacs.wololo.model.APIs;
import org.springframework.web.bind.annotation.RestController;

import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.Municipality;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


@RestController
/**
 * API que se utiliza para obtener los datos de las municipalidades y provincias de Argentina
 */
public class GeoRef {

    private boolean getFromAPI;
    private final String apiGeorefProperties = "properties/apiGeoref.properties";
    private ReadModeGeoRef readModeAPIGeoRef;

    /**
     * Crea la clase, y en base a lo que esté definido en el archivo de configuración,
     * obtiene los datos de la API o de un json
     */
    public GeoRef() {
        this.readAttribute();

        if(getFromAPI)
            readModeAPIGeoRef = new ReadModeAPIGeoRef();
        else
            readModeAPIGeoRef = new ReadModeJSONGeoRef();
    }

    public GeoRef(ReadModeGeoRef readModeAPIGeoRef)
    {
        this.readModeAPIGeoRef = readModeAPIGeoRef;
    }

    private void readAttribute() {
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(apiGeorefProperties);


        try {
            prop.load(inputStream);
            this.getFromAPI = Boolean.parseBoolean(prop.getProperty("getFromAPI"));

            assert inputStream != null;
            inputStream.close();
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("property file '" + apiGeorefProperties + "' not found in the classpath");
        }

    }

    /**
     * Método que obtiene el municipio en base al nombre del municipio de la API
     * @param nombre Nombre del municipio a buscar
     * @return Municipio obtenido
     */
    public Municipality municipioPorNombre(String nombre) {

        return this.readModeAPIGeoRef.municipioPorNombre(nombre);

    }

    /**
     * Obtiene la lista de municipios de una provincia
     * @param provincia Nombre de una provincia de Argentina
     * @return Lista de municipios perteneciente a la provincia
     */
    public List<Municipality> municipioPorProvincia(String provincia) {

        return this.readModeAPIGeoRef.municipioPorProvincia(provincia);

    }

    /**
     * Obtiene las provincias
     * @return Lista de provincias de Argentina
     */
    public List<Provincia> listarProvincias() {
        return this.readModeAPIGeoRef.listarProvincias();

    }


}
