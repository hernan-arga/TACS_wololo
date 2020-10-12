package tacs.wololo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.IOException;

@Entity
@DiscriminatorValue("defending")
public class DefendingMunicipality extends MunicipalityMode {

    public DefendingMunicipality() throws IOException {
        this.setCoefs("defendingMunicipality.properties");
    }

    public MunicipalityMode changeMode() throws IOException {
        return new ProducerMunicipality();
    }


}
