import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { StatisticsShowComponent } from '../statistics-show/statistics-show.component';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  firstFormGroup: FormGroup;

  constructor(private _formBuilder: FormBuilder, public dialog: MatDialog) { }

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group(
      {
        firstCtrl: ['']
      }
    );
  }

  openShowStatisticsByDate(): void {
    // TODO
  }

  openShowIndividualStatisticsDialog(): void{
    let userName = this.firstFormGroup.controls.firstCtrl.value;
    const dialogRef = this.dialog.open(StatisticsShowComponent, {
      width: '600px',
      data: userName, 
      disableClose: true
    });
  }

}
