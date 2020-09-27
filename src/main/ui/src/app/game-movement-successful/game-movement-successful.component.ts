import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-game-movement-successful',
  templateUrl: './game-movement-successful.component.html',
  styleUrls: ['./game-movement-successful.component.css']
})
export class GameMovementSuccessfulComponent implements OnInit {

  mensajeParaUsuario: string;

  constructor(public dialogRef: MatDialogRef<GameMovementSuccessfulComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string) {
    this.mensajeParaUsuario = data;
  }

  ngOnInit(): void {
  }

  onAccept(): void {
    this.dialogRef.close();
  }

}
