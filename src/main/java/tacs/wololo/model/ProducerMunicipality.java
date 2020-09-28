package tacs.wololo.model;

import java.io.IOException;

public class ProducerMunicipality extends MunicipalityMode {

    public ProducerMunicipality() throws IOException {
        this.setCoefs("producerMunicipality.properties");
    }

    public MunicipalityMode changeMode() throws IOException {
        return new DefendingMunicipality();
    }

}
