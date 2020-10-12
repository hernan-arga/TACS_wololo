package tacs.wololo.model;

import javax.persistence.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "municipality_mode")
public abstract class MunicipalityMode
{
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    public float multDef;
    public float coefProdGauchos;

    public float getMultDef()
    {
        return this.multDef;
    }

    public float getCoefProdGauchos()
    {
        return this.coefProdGauchos;
    }

    public void setMultDef(float multDef)
    {
        this.multDef = multDef;
    }

    public void setCoefProdGauchos(float coefProdGauchos)
    {
        this.coefProdGauchos = coefProdGauchos;
    }

    public abstract MunicipalityMode changeMode() throws IOException;

    public void setCoefs(String propFileName) throws IOException {
        // Se lee las properties del municipality.properties
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

        float multDef = Float.parseFloat(prop.getProperty("multDef"));
        float coefProdGauchos = Float.parseFloat(prop.getProperty("coefProdGauchos"));

        inputStream.close();

        // Asigno los coeficientes
        this.multDef = multDef;
        this.coefProdGauchos = coefProdGauchos;
    }
}



