package tacs.wololo.services.implementations;

import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.services.IProvinceService;

import java.util.ArrayList;
import java.util.List;

public class ProvinceService implements IProvinceService {

    public ProvinceService(){

    }

    public List<Provincia> getProvincesList() {
        return new ArrayList<Provincia>();
    }
}
