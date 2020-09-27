import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Municipality } from '../shared/models/municipality.model';

@Component({
  selector: 'app-game-show-municipality-statistics',
  templateUrl: './game-show-municipality-statistics.component.html',
  styleUrls: ['./game-show-municipality-statistics.component.css']
})
export class GameShowMunicipalityStatisticsComponent implements OnInit {

  municipality: Municipality;

  constructor(public dialogRef: MatDialogRef<GameShowMunicipalityStatisticsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Municipality) {
    this.municipality = data;
  }

  ngOnInit(): void {
  }

  onAccept(): void {
    this.dialogRef.close();
  }

}
