package tacs.wololo.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DefendingMunicipality extends MunicipalityMode {

    public DefendingMunicipality() throws IOException {
        this.setCoefs("defendingMunicipality.properties");
    }

    public MunicipalityMode changeMode() throws IOException {
        return new ProducerMunicipality();
    }


}
