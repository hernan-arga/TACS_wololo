package tacs.wololo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.IOException;

@Entity
@DiscriminatorValue("producer")
public class ProducerMunicipality extends MunicipalityMode {

    GameStyle gameStyle;

    public ProducerMunicipality() {
    }

    public ProducerMunicipality(GameStyle gameStyle) throws IOException {
        //this.setCoefs("producerMunicipality.properties");
        this.gameStyle = gameStyle;
        this.multDef = gameStyle.prodMultDef();
        this.coefProdGauchos = gameStyle.prodCoefProdGauchos();
    }

    public MunicipalityMode changeMode() throws IOException {
        return new DefendingMunicipality(gameStyle);
    }

}
