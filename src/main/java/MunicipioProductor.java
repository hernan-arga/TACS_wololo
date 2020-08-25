public class MunicipioProductor implements ModoMunicipio{


    @Override
    public float multDef() {
        return 1;
    }

    @Override
    public float coefProdGauchos() {
        return 15;
    }

    public ModoMunicipio cambioDeModo(){
        return  new MunicipioDefensa();           //fixme esto mepa que esta mal
    }
}
