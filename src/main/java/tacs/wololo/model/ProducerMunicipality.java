package tacs.wololo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.IOException;

@Entity
@DiscriminatorValue("producer")
public class ProducerMunicipality extends MunicipalityMode {

    public ProducerMunicipality() throws IOException {
        this.setCoefs("producerMunicipality.properties");
    }

    public MunicipalityMode changeMode() throws IOException {
        return new DefendingMunicipality();
    }

}
