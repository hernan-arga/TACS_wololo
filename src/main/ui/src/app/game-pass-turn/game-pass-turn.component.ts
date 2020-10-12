import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-game-pass-turn',
  templateUrl: './game-pass-turn.component.html',
  styleUrls: ['./game-pass-turn.component.css']
})
export class GamePassTurnComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<GamePassTurnComponent>) {
  }

  onAccept(): void {
    this.dialogRef.close(true);
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  ngOnInit(): void {

  }

}
