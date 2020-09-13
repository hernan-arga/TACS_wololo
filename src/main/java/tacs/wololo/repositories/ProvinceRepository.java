package tacs.wololo.repositories;

import org.springframework.stereotype.Repository;
import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.DTOs.ProvinceInfoDto;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProvinceRepository {

    private List<ProvinceInfoDto> provincesInfo =  new ArrayList<ProvinceInfoDto>();

    public void addProvince(ProvinceInfoDto provinceInfoDto){
        provincesInfo.add(provinceInfoDto);
    }

    public void setProvinces(List<ProvinceInfoDto> provincesInfoDto){
        provincesInfo = provincesInfoDto;
    }

    public List<ProvinceInfoDto> getProvincesInfo() {
        return provincesInfo;
    }

}
