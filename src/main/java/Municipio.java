public class Municipio {
    int gauchos;

    float minDist;
    float maxDist;
    float minAlt;
    float maxAlt;
    float altura;

    float coefDist = 2;

    float coefAlt= 2;

    ModoMunicipio modo;


    float multDef = modo.multDef();                                  //fixme elegir si herencia o delegacion
    float multProdGauchos = modo.coefProdGauchos();

    public void atacarMunicipio( Municipio otroMunicipio){

    }

    float multiplicadorAltura(){
       return  (this.altura-minAlt)/(coefAlt*(maxAlt-minAlt));
    }

    int  producirGauchos(){
        return (int) (modo.coefProdGauchos()*(multiplicadorAltura()));
    }

    public boolean seQuedaConGauchosDespuesDeAtacar(Municipio otroMunicipio){
        return gauchosAtacantesFinal(otroMunicipio)>0;
    }

    int distanciaAMunicipio(Municipio otroMunicipio){
        return 5;                                                 //FIXME hacerlo bien
    }

    float multDist (Municipio otroMunicipio){
        return 1- (distanciaAMunicipio(otroMunicipio)-minDist)/(coefDist*(maxDist-minDist));
    }

    float multAlt (){
        return 1 + multiplicadorAltura();
    }


    public int gauchosAtacantesFinal(Municipio municipioDefensor){
       return (int) (this.gauchos*this.multDist(municipioDefensor) - municipioDefensor.gauchos*municipioDefensor.multAlt()*municipioDefensor.multDef);
    }

    int gauchosDefensoresFinal(Municipio municipioAtacante){
        return (int) (Math.ceil(gauchos*multAlt()*multDef - municipioAtacante.gauchos*multDist(this))/(multAlt()*multDef));
    }


}
