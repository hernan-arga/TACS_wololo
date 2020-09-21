package tacs.wololo.model.DTOs;

public class AttackDto
{
    String attackMun;
    String defenceMun;

    public AttackDto() {
    }

    public AttackDto(String attackMun, String defenceMun) {
        this.attackMun = attackMun;
        this.defenceMun = defenceMun;
    }

    public String getAttackMun() {
        return attackMun;
    }

    public String getDefenceMun() {
        return defenceMun;
    }
}
