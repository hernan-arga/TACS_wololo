package tacs.wololo.model;

public class Municipio {
    private int gauchos;

    private double altura;

    private double coefDist = 2;

    private double coefAlt= 2;

    private ModoMunicipio modo;

    public Municipio(int gauchos, double altura, ModoMunicipio modo)
    {
        this.gauchos = gauchos;
        this.altura = altura;
        this.modo = modo;
    }

    public ModoMunicipio getModo() {
        return modo;
    }



    public void atacarMunicipio(Municipio atacante, Municipio defensor, Mapa mapa){

    }

    private double multiplicadorAltura(Mapa mapa){
       return  (this.altura-mapa.getMinAltura())/
               (coefAlt*(mapa.getMaxAltura()-mapa.getMinAltura())); // TODO: ver que pasa si altura min = altura max
    }

    public void producirGauchos(Mapa mapa){
        int gauchosAAgregar = (int) (modo.getCoefProdGauchos()*(this.multiplicadorAltura(mapa)));
        this.agregarGauchos(gauchosAAgregar);
    }

    private void agregarGauchos(int gauchos)
    {
        this.gauchos+=gauchos;
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
               - municipioDefensor.gauchos*municipioDefensor.multAlt(mapa)*municipioDefensor.getModo().getMultDef());
    }

    int gauchosDefensoresFinal(Municipio municipioAtacante, Mapa mapa){
        return (int) (Math.ceil(gauchos*multAlt(mapa)* modo.getMultDef()
                - municipioAtacante.gauchos*multDist(municipioAtacante, mapa))/
                (multAlt(mapa)* modo.getMultDef()));
    }

    // --------------- Getters y Setters --------------
    public void setModo(ModoMunicipio modo) {
        this.modo = modo;
    }

    public int getGauchos()
    {
        return this.gauchos;
    }
}
