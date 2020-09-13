package tacs.wololo.model.DTOs;

public class ProvinceInfoDto {
    String name;
    int municipalitiesCant;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMunicipalitiesCant() {
        return municipalitiesCant;
    }

    public void setMunicipalitiesCant(int municipalitiesCant) {
        this.municipalitiesCant = municipalitiesCant;
    }

    public ProvinceInfoDto(String name, int municipalitiesCant) {
        this.name = name;
        this.municipalitiesCant = municipalitiesCant;
    }

    public ProvinceInfoDto() {
    }
}
