public class MunicipioProductor implements ModoMunicipio{


    public float multDef() {
        return 1;
    }

    public float coefProdGauchos() {
        return 15;
    }

    public ModoMunicipio cambioDeModo(){
        return  new MunicipioDefensa();           //fixme esto mepa que esta mal
    }
}
