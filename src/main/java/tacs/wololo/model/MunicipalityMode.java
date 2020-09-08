package tacs.wololo.model;

public abstract class MunicipalityMode {
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

}



