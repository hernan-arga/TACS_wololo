import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-game-turn-changed',
  templateUrl: './game-turn-changed.component.html',
  styleUrls: ['./game-turn-changed.component.css']
})
export class GameTurnChangedComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<GameTurnChangedComponent>) {
  }

  onAccept(): void {
    this.dialogRef.close(true);
  }

  ngOnInit(): void {

  }

}
