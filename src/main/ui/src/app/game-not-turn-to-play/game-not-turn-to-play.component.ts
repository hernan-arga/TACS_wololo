import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-game-not-turn-to-play',
  templateUrl: './game-not-turn-to-play.component.html',
  styleUrls: ['./game-not-turn-to-play.component.css']
})
export class GameNotTurnToPlayComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<GameNotTurnToPlayComponent>) { }

  ngOnInit(): void {
  }

  onAccept(): void {
    this.dialogRef.close();
  }

}
