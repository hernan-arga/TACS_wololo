package tacs.wololo.model;

public  class MunicipioDefensa implements ModoMunicipio{

    public float multDef() {
        return (float)1.25;
    }

    public float coefProdGauchos() {
        return 10;
    }

    public ModoMunicipio cambioDeModo(){
        return  new MunicipioProductor();           //fixme esto mepa que esta mal
    }
}
