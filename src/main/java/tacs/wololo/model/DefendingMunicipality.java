package tacs.wololo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.IOException;

@Entity
@DiscriminatorValue("defending")
public class DefendingMunicipality extends MunicipalityMode {

    GameStyle gameStyle;

    public DefendingMunicipality() {
    }

    public DefendingMunicipality(GameStyle gameStyle){
        this.gameStyle = gameStyle;
        this.multDef = gameStyle.getDefMultDef();
        this.coefProdGauchos = gameStyle.getDefCoefProdGauchos();
    }

    public MunicipalityMode changeMode(){
        return new ProducerMunicipality(this.gameStyle);
    }
}
