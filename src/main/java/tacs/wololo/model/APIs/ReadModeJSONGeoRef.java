package tacs.wololo.model.APIs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.Municipality;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadModeJSONGeoRef implements ReadModeGeoRef {
    private String filePathJsonProvincias = "jsons/provincias.json";
    private String filePathJsonMunicipios = "jsons/municipios.json";
    private ObjectMapper mapper;

    public ReadModeJSONGeoRef()
    {
        this.mapper = new ObjectMapper();
        this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Municipality municipioPorNombre(String nombre) {

        JsonNode jsonNode = this.getJsonNodeList(filePathJsonMunicipios, "municipios");
        Iterator<JsonNode> it = jsonNode.elements();
        Municipality municipality = null;

        try {
            while (it.hasNext()) {
                JsonNode aMunicipality = it.next();

                String nombreFromJsonNode = aMunicipality.get("nombre").toString().replace("\"","");

                if (nombreFromJsonNode.equals(nombre)) {
                    String jsonString = this.mapper.writeValueAsString(aMunicipality);
                    municipality = this.mapper.readValue(jsonString, new TypeReference<Municipality>() {});
                    break;
                }
            }

            if(municipality == null)
                throw new RuntimeException("The municipality don't exists");

            return municipality;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Municipality> municipioPorProvincia(String provincia) {
        JsonNode jsonNode = this.getJsonNodeList(filePathJsonMunicipios, "municipios");
        Iterator<JsonNode> it = jsonNode.elements();
        List<Municipality> municipalities = new ArrayList<>();
        int cantFound = 0;
        int limitOfNumberOfProvinces = 100; //fixme, no se si tiene que ser este número

        try {
            while (it.hasNext() && cantFound < limitOfNumberOfProvinces) {
                JsonNode aMunicipality = it.next();

                String nombreFromJsonNode = aMunicipality.get("provincia").get("nombre").toString().replace("\"","");

                if (nombreFromJsonNode.equals(provincia)) {
                    String jsonString = this.mapper.writeValueAsString(aMunicipality);
                    Municipality municipality = this.mapper.readValue(jsonString, new TypeReference<Municipality>() {});
                    municipalities.add(municipality);
                    cantFound ++;
                }
            }

            if(cantFound == 0)
                throw new RuntimeException("The province don't exists");

            return municipalities;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Provincia> listarProvincias() {
        JsonNode jsonNode = this.getJsonNodeList(filePathJsonProvincias, "provincias");

        List<Provincia> provincias;
        try {

            String jsonString = this.mapper.writeValueAsString(jsonNode);

            provincias = this.mapper.readValue(jsonString, new TypeReference<List<Provincia>>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return provincias;
    }


    private JsonNode getJsonNodeList(String filePath, String attribute)
    {
        try(InputStream in =
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath))
        {
            //pass InputStream a JSON-Library, using Jackson
            JsonNode jsonNode = mapper.readValue(in,
                    JsonNode.class);

            if (jsonNode.has(attribute)) {
                jsonNode = jsonNode.get(attribute);
                return jsonNode;
            }
            else
                throw new Exception("The json doesn't the attribute " + attribute);

        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
