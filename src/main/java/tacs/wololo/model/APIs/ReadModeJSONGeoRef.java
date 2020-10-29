package tacs.wololo.model.APIs;

import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.Municipality;

import java.util.List;

public class ReadModeJSONGeoRef implements ReadModeGeoRef {

    private Gson gson;

    public ReadModeJSONGeoRef() {
        gson = new Gson();
    }
    @Override
    public Municipality municipioPorNombre(String nombre) {
        return null;
    }

    @Override
    public List<Municipality> municipioPorProvincia(String provincia) {
        return null;
    }

    @Override
    public List<Provincia> listarProvincias() {
        return null;
    }
}
