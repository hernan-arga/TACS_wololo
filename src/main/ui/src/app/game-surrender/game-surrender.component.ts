import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-game-surrender',
  templateUrl: './game-surrender.component.html',
  styleUrls: ['./game-surrender.component.css']
})
export class GameSurrenderComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<GameSurrenderComponent>) {
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
