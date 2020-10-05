import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ElementScoreBoard } from '../shared/models/elementScoreBoard.model';
import { ScoreBoardShowService } from '../_services/score-board-show.service';

@Component({
  selector: 'app-score-board-show',
  templateUrl: './score-board-show.component.html',
  styleUrls: ['./score-board-show.component.css']
})
export class ScoreBoardShowComponent implements OnInit {

  gameId: number; 
  scoreBoard: Array<ElementScoreBoard>; 

  constructor(private scoreBoardShowService: ScoreBoardShowService, public dialogRef: MatDialogRef<ScoreBoardShowComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number) {
      this.gameId = data;
     }

  ngOnInit() {
    let prueba = new Array<ElementScoreBoard>(); // TODO SACAR HARCODEO
    prueba.push(new ElementScoreBoard("Melisa", 15));
    prueba.push(new ElementScoreBoard("Carlos", 10));
    this.scoreBoard = prueba;
    
    // TODO: Hacer que lo de abajo me funciona. No me funciona porque no tengo los permisos
    /*this.scoreBoardShowService.getScoreBoard(this.gameId).subscribe(data =>
      {
        this.scoreBoard = data;
      }
    );*/
  }

  onAccept(): void {
    this.dialogRef.close();
  }

}
