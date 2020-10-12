import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-game-surrendered',
  templateUrl: './game-surrendered.component.html',
  styleUrls: ['./game-surrendered.component.css']
})
export class GameSurrenderedComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<GameSurrenderedComponent>) {
  }

  onAccept(): void {
    this.dialogRef.close(true);
  }

  ngOnInit(): void {

  }

}
