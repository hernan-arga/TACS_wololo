package tacs.wololo.model;

public class Municipality {
    private int gauchos;

    private double height;

    private double coefDist = 2;

    private double coefAlt = 2;

    private MunicipalityMode mode;

    public Municipality(int gauchos, double height, MunicipalityMode mode)
    {
        this.gauchos = gauchos;
        this.height = height;
        this.mode = mode;
    }

    public MunicipalityMode getMode() {
        return mode;
    }



    public void attackMunicipality(Municipality atacante, Municipality defender, Map map){

    }

    private double heightMultiplier(Map map){
       return  (this.height - map.getMinHeight())/
               (coefAlt*(map.getMaxHeight()- map.getMinHeight())); // TODO: ver que pasa si altura min = altura max
    }

    public void produceGauchos(Map map){
        int gauchosToAdd = (int) (mode.getCoefProdGauchos()*(this.heightMultiplier(map)));
        this.addGauchos(gauchosToAdd);
    }

    private void addGauchos(int gauchos)
    {
        this.gauchos+=gauchos;
    }

    public boolean remainingGauchosAfterAttack(Municipality anotherMunicipality, Map map){
        return endingAttackingGauchos(anotherMunicipality, map)>0;
    }

    int distanceToMunicipality(Municipality anotherMunicipality){
        return 5;                                                 //FIXME hacerlo bien
    }

    double multDist (Municipality anotherMunicipality, Map map){
        return 1 - (distanceToMunicipality(anotherMunicipality)- map.getDistMin())/
                (coefDist*(map.getDistMax()- map.getDistMin()));
    }

    double multAlt (Map map){
        return 1 + heightMultiplier(map);
    }


    public int endingAttackingGauchos(Municipality defenderMunicipality,
                                      Map map){
       return (int) (this.gauchos*this.multDist(defenderMunicipality, map)
               - defenderMunicipality.gauchos*defenderMunicipality.multAlt(map)*defenderMunicipality.getMode().getMultDef());
    }

    int endingDefendersGauchos(Municipality attackingMunicipality, Map map){
        return (int) (Math.ceil(gauchos*multAlt(map)* mode.getMultDef()
                - attackingMunicipality.gauchos*multDist(attackingMunicipality, map))/
                (multAlt(map)* mode.getMultDef()));
    }

    // --------------- Getters y Setters --------------
    public void setMode(MunicipalityMode mode) {
        this.mode = mode;
    }

    public int getGauchos()
    {
        return this.gauchos;
    }
}
