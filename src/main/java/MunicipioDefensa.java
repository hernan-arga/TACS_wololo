public  class MunicipioDefensa implements ModoMunicipio{

    @Override
    public float multDef() {
        return (float)1.25;
    }

    @Override
    public float coefProdGauchos() {
        return 10;
    }

    public ModoMunicipio cambioDeModo(){
        return  new MunicipioProductor();           //fixme esto mepa que esta mal
    }
}
