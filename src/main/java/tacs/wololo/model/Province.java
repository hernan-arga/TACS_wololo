package tacs.wololo.model;

import java.util.List;

public class Province
{
    private String name;
    private List<Municipality> municipalities;

    public List<Municipality> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(List<Municipality> municipalities) {
        this.municipalities = municipalities;
    }

    public String getName() {
        return name;
    }
}
