package tacs.wololo.model;

public enum GameStyle {
    NORMAL {
        public float defMultDef() {return 1.25f;}
        public float defCoefProdGauchos() {return 10f;}
        public float prodMultDef() {return 1f;}
        public float prodCoefProdGauchos() {return 15f;}
    },
    SUPERDEFENSA {
        public float defMultDef() {return 1.75f;}
        public float defCoefProdGauchos() {return 10f;}
        public float prodMultDef() {return 1f;}
        public float prodCoefProdGauchos() {return 15f;}
    },
    SUPERPROD {
        public float defMultDef() {return 1.25f;}
        public float defCoefProdGauchos() {return 20f;}
        public float prodMultDef() {return 1f;}
        public float prodCoefProdGauchos() {return 30f;}
    };

    public abstract float defMultDef();
    public abstract float defCoefProdGauchos();
    public abstract float prodMultDef();
    public abstract float prodCoefProdGauchos();
}
