package tacs.wololo.model.APIs;


import org.springframework.web.client.RestTemplate;
import tacs.wololo.model.APIs.GeoData.Centroide;
import tacs.wololo.model.APIs.GeoData.DatosTopologicos;
import tacs.wololo.model.APIs.GeoData.Result;


import java.util.List;
import java.util.stream.Collectors;

public class AsterAPI {

    private String URL = "https://api.opentopodata.org/v1/srtm30m";

    private RestTemplate restTemplate = new RestTemplate();


    public double heightByLocation(Centroide centroide) {

        return restTemplate.getForObject("https://api.opentopodata.org/v1/srtm30m"+"?locations="+centroide.lat+","+centroide.lon, DatosTopologicos.class).results.get(0).elevation;

    }

    public List<Double> multipleHeights(List<Centroide> centroides) {


        List<String> latitudesYLongitudes = centroides.stream().map(z->z.lat.toString()+","+z.lon.toString()).collect(Collectors.toList());

        String queryParam = String.join(" | ",latitudesYLongitudes);

        System.out.println(queryParam);

        List<Result> resultados= restTemplate.getForObject("https://api.opentopodata.org/v1/srtm30m"+"?locations="+queryParam, DatosTopologicos.class).results;



        /*Collections.sort(resultados, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                return o1.elevation.compareTo(o2.elevation);
            }
        });*/
        return resultados.stream().map(z->z.elevation).collect(Collectors.toList());


    }


}
