package tacs.wololo.model.APIs;
import org.springframework.web.bind.annotation.RestController;

import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.Municipality;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


@RestController
public class GeoRef {

    private boolean getFromAPI;
    private final String apiGeorefProperties = "properties/apiGeoref.properties";
    private ReadModeGeoRef readModeAPIGeoRef;


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


    public Municipality municipioPorNombre(String nombre) {

        return this.readModeAPIGeoRef.municipioPorNombre(nombre);

    }
    public List<Municipality> municipioPorProvincia(String provincia) {

        return this.readModeAPIGeoRef.municipioPorProvincia(provincia);

    }

    public List<Provincia> listarProvincias() {
        return this.readModeAPIGeoRef.listarProvincias();

    }


}
