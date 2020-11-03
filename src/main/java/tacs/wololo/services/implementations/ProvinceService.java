package tacs.wololo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.APIs.GeoRef;
import tacs.wololo.model.DTOs.ProvinceInfoDto;
import tacs.wololo.repositories.ProvinceRepository;
import tacs.wololo.services.IProvinceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProvinceService implements IProvinceService {

    @Autowired
    GeoRef geoRef;

    @Autowired
    ProvinceRepository provinceRepository;

    public ProvinceService(){
        this.geoRef = new GeoRef();
        this.provinceRepository = new ProvinceRepository();
    }

    public List<ProvinceInfoDto> getProvincesList() {
        return provinceRepository.getProvincesInfo();
    }

    @Scheduled(fixedDelay = 86400000) //cada 24 hs
    public void updateProvincesList(){
        //TODO: hay provincias que la api no devuelve los municipios
        List<String> provinciasBaneadas = Arrays.asList("Entre Ríos", "Santiago del Estero",
                "Santa Cruz", "Ciudad Autónoma de Buenos Aires");

        List<ProvinceInfoDto> newProvincesInfoDto = new ArrayList<ProvinceInfoDto>();
        geoRef.listarProvincias().forEach(provincia ->
        {
            if(!provinciasBaneadas.contains(provincia.getNombre())) {
                int municipalitiesCant = geoRef.municipioPorProvincia(provincia.getNombre()).size();
                newProvincesInfoDto.add(new ProvinceInfoDto(provincia.getNombre(), municipalitiesCant));
            }
        });

        provinceRepository.setProvinces(newProvincesInfoDto);

    }
}
