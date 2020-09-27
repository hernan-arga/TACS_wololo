
export class Action {
  attackMun: string;
  defenceMun: string;
  ammount: number;

  constructor(attackMun: string, defenceMun: string, ammount: number){
    this.attackMun = attackMun;
    this.defenceMun = defenceMun;
    this.ammount = ammount;
  }
}