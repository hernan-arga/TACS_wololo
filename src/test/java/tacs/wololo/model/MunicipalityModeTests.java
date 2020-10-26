package tacs.wololo.model;

import org.junit.Assert;
import org.junit.Test;


public class MunicipalityModeTests {
    @Test
    public void createDefendingMunicipality(){
        DefendingMunicipality defendingMunicipality = new DefendingMunicipality(GameStyle.NORMAL);

        Assert.assertEquals((float)1.25, defendingMunicipality.getMultDef(), 0);
        Assert.assertEquals((float)10, defendingMunicipality.getCoefProdGauchos(), 0);
    }

    @Test
    public void createProducerMunicipality(){
        ProducerMunicipality producerMunicipality = new ProducerMunicipality(GameStyle.NORMAL);

        Assert.assertEquals((float)1, producerMunicipality.getMultDef(), 0);
        Assert.assertEquals((float)15, producerMunicipality.getCoefProdGauchos(), 0);
    }
}
