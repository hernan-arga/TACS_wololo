import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-game-finished-show-winner',
  templateUrl: './game-finished-show-winner.component.html',
  styleUrls: ['./game-finished-show-winner.component.css']
})
export class GameFinishedShowWinnerComponent implements OnInit {

  winner: string;

  constructor(
    public dialogRef: MatDialogRef<GameFinishedShowWinnerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string) {
      this.winner = data;
    }

  onAccept(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {

  }

}
