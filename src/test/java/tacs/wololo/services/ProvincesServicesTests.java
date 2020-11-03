package tacs.wololo.services;

import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import tacs.wololo.model.APIs.GeoData.Provincia;
import tacs.wololo.model.APIs.*;
import tacs.wololo.model.DTOs.ProvinceInfoDto;
import tacs.wololo.repositories.ProvinceRepository;
import tacs.wololo.services.implementations.ProvinceService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProvincesServicesTests {

    @Mock
    private ProvinceRepository provinceRepository;

    @Mock
    private GeoRef geoRef;

    @InjectMocks
    private ProvinceService provinceService;

    @Before
    public void init() {

        provinceService = new ProvinceService();
        provinceRepository = new ProvinceRepository();

        //Lista de province infoDoto
     /*   ProvinceInfoDto mendoza = mock(ProvinceInfoDto.class);
        when(mendoza.getName()).thenReturn("mendoza");
        when(mendoza.getMunicipalitiesCant()).thenReturn(50);

        List<ProvinceInfoDto> provinciasInfoDto = new ArrayList<>();
        provinciasInfoDto.add(mendoza);
//"Entre Ríos", "Santiago del Estero", -> BANEADO
//                "Santa Cruz", "Ciudad Autónoma de Buenos Aires");


        Provincia sanJuan = mock(Provincia.class);
        when(sanJuan.getNombre()).thenReturn("San Juan");

        List<Provincia> provincias = new ArrayList<>();
        when(geoRef.listarProvincias()).thenReturn(provincias);
        //when(geoRef.municipioPorNombre());
        when(provinceMocke.getProvincesInfo()).thenReturn(provinciasInfoDto);
        */


    }

    //Todo testear getProvinceList
    //Update provinceList

    @Test
    public void canObtainAprovince() {
        List<ProvinceInfoDto> provincesInfoDTOs = provinceService.getProvincesList();

    }


    @Test
    public void canAddProvincesFromAPItoRepository() {
        provinceService.updateProvincesList();
        Assert.assertNotNull(provinceRepository.getProvincesInfo());
    }


}
