package tacs.wololo.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum GameStyle {
    /*
    * PENNY(1), NICKEL(5), DIME(10), QUARTER(25); // usual names for US coins
    // note that the above parentheses and the constructor arguments match
    private int value;

    Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    * */
    NORMAL("normalGameStyle.properties"),
    SUPERDEFENSA("superDefensaGameStyle.properties"),
    SUPERPROD("superProdGameStyle.properties");

    private float defMultDef;
    private float defCoefProdGauchos;
    private float prodMultDef;
    private float prodCoefProdGauchos;

    public float getDefMultDef() {return this.defMultDef;}
    public float getDefCoefProdGauchos() {return this.defCoefProdGauchos;}
    public float getProdMultDef() {return this.prodMultDef;}
    public float getProdCoefProdGauchos() {return this.prodCoefProdGauchos;}


    GameStyle(String propFileName){
        this.setCoefs(propFileName);
    }

    private void setCoefs(String propFileName){
        // Se lee las properties del archivo propFileName
        Properties prop = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        float defMultDef;
        float defCoefProdGauchos;
        float prodMultDef;
        float prodCoefProdGauchos;

        try {
            prop.load(inputStream);
            defMultDef = Float.parseFloat(prop.getProperty("defMultDef"));
            defCoefProdGauchos = Float.parseFloat(prop.getProperty("defCoefProdGauchos"));
            prodMultDef = Float.parseFloat(prop.getProperty("prodMultDef"));
            prodCoefProdGauchos = Float.parseFloat(prop.getProperty("prodCoefProdGauchos"));

            inputStream.close();
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("property file '" + propFileName + "' not found in the classpath");
        }

        // Asigno los coeficientes
        this.defMultDef = defMultDef;
        this.defCoefProdGauchos = defCoefProdGauchos;
        this.prodMultDef = prodMultDef;
        this.prodCoefProdGauchos = prodCoefProdGauchos;
    }
}
