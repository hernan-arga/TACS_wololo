package tacs.wololo.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MunicipalityModeTests {
    private DefendingMunicipality defendingMunicipality;
    private ProducerMunicipality producerMunicipality;

    @Before
    public void init() throws IOException {
        // TODO: Ver que pasa en un futuro, si borramos estos tests o no, solo puse esto para que no rompan los tests
        defendingMunicipality = new DefendingMunicipality();
        producerMunicipality = new ProducerMunicipality();

        defendingMunicipality.setCoefs("defendingMunicipality.properties");
        producerMunicipality.setCoefs("producerMunicipality.properties");
    }

    @Test
    public void createDefendingMunicipality() throws IOException {
        //DefendingMunicipality defendingMunicipality = new DefendingMunicipality();

        Assert.assertEquals((float)1.25, defendingMunicipality.getMultDef(), 0);
        Assert.assertEquals((float)10, defendingMunicipality.getCoefProdGauchos(), 0);
    }

    @Test
    public void createProducerMunicipality() throws IOException {
        //ProducerMunicipality producerMunicipality = new ProducerMunicipality();

        Assert.assertEquals((float)1, producerMunicipality.getMultDef(), 0);
        Assert.assertEquals((float)15, producerMunicipality.getCoefProdGauchos(), 0);
    }
}
