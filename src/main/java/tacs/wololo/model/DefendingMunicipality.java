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

    public DefendingMunicipality(GameStyle gameStyle) throws IOException {
        //this.setCoefs("defendingMunicipality.properties");
        this.gameStyle = gameStyle;
        this.multDef = gameStyle.defMultDef();
        this.coefProdGauchos = gameStyle.defCoefProdGauchos();
    }

    public MunicipalityMode changeMode() throws IOException {
        return new ProducerMunicipality(this.gameStyle);
    }


}
