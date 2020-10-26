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

    public ProducerMunicipality(GameStyle gameStyle){
        this.gameStyle = gameStyle;
        this.multDef = gameStyle.getProdMultDef();
        this.coefProdGauchos = gameStyle.getProdCoefProdGauchos();
    }

    public MunicipalityMode changeMode(){
        return new DefendingMunicipality(gameStyle);
    }

}
