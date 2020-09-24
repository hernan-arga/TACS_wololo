import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Municipality } from '../shared/models/municipality.model';
import {MatInputModule} from '@angular/material/input';


@Component({
  selector: 'app-game-move-gauchos',
  templateUrl: './game-move-gauchos.component.html',
  styleUrls: ['./game-move-gauchos.component.css']
})
export class GameMoveGauchosComponent implements OnInit {
  municipality: Municipality;
  cantGauchos: number;

  constructor(
    public dialogRef: MatDialogRef<GameMoveGauchosComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Municipality,
    private _formBuilder: FormBuilder) {
      this.municipality = data;
    }

  onAccept(): void {
    this.dialogRef.close(this.cantGauchos);
  }

  ngOnInit(): void {
    
  }

}
