package tacs.wololo.model;

import java.util.List;

public class Provincia {
    private String nombre;
    private List<Municipio> municipios;

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }
}
