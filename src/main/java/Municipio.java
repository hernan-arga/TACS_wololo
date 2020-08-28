public class Municipio {
    private int gauchos;


    private double altura;

    private double coefDist = 2;

    private double coefAlt= 2;

    ModoMunicipio modo;


    private double multDef = modo.multDef();                 //fixme elegir si herencia o delegacion
    private double multProdGauchos = modo.coefProdGauchos();

    public void atacarMunicipio(Municipio atacante, Municipio defensor, Mapa mapa){

    }

    double multiplicadorAltura(Mapa mapa){
       return  (this.altura-mapa.getMinAltura())/
               (coefAlt*(mapa.getMaxAltura()-mapa.getMinAltura()));
    }

    int producirGauchos(Mapa mapa){
        return (int) (modo.coefProdGauchos()*(multiplicadorAltura(mapa)));
    }

    public boolean seQuedaConGauchosDespuesDeAtacar(Municipio otroMunicipio, Mapa mapa){
        return gauchosAtacantesFinal(otroMunicipio, mapa)>0;
    }

    int distanciaAMunicipio(Municipio otroMunicipio){
        return 5;                                                 //FIXME hacerlo bien
    }

    double multDist (Municipio otroMunicipio, Mapa mapa){
        return 1 - (distanciaAMunicipio(otroMunicipio)-mapa.getDistMin())/
                (coefDist*(mapa.getDistMax()-mapa.getDistMin()));
    }

    double multAlt (Mapa mapa){
        return 1 + multiplicadorAltura(mapa);
    }


    public int gauchosAtacantesFinal(Municipio municipioDefensor,
                                     Mapa mapa){
       return (int) (this.gauchos*this.multDist(municipioDefensor, mapa)
               - municipioDefensor.gauchos*municipioDefensor.multAlt(mapa)*municipioDefensor.multDef);
    }

    int gauchosDefensoresFinal(Municipio municipioAtacante, Mapa mapa){
        return (int) (Math.ceil(gauchos*multAlt(mapa)*multDef
                - municipioAtacante.gauchos*multDist(municipioAtacante, mapa))/
                (multAlt(mapa)*multDef));
    }


}
