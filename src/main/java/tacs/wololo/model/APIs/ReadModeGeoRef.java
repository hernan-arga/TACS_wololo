package tacs.wololo.model.APIs;

import tacs.wololo.model.APIs.GeoData.DatosMunicipio;
import tacs.wololo.model.APIs.GeoData.DatosProvincia;
import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.Municipality;

import java.util.List;

public interface ReadModeGeoRef {

    public Municipality municipioPorNombre(String nombre);
    public List<Municipality> municipioPorProvincia(String provincia);
    public List<Provincia> listarProvincias();
}
