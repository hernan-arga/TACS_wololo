package tacs.wololo.model;

import javax.persistence.*;

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

    public abstract MunicipalityMode changeMode();
}



