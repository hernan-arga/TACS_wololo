import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-score-board',
  templateUrl: './score-board.component.html',
  styleUrls: ['./score-board.component.css']
})
export class ScoreBoardComponent implements OnInit {

  valor: string;

  constructor(public dialogRef: MatDialogRef<ScoreBoardComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string) {
      this.valor = data;
     }

  ngOnInit() {
  }

  onAccept(): void {
    this.dialogRef.close();
  }

}
