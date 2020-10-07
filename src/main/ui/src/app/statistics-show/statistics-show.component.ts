import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-statistics-show',
  templateUrl: './statistics-show.component.html',
  styleUrls: ['./statistics-show.component.scss']
})
export class StatisticsShowComponent implements OnInit {

  statistics: Array<any> = [];
  elementsStatistics: Map<string, number>;

  constructor(public dialogRef: MatDialogRef<StatisticsShowComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Map<string, number>) {
      this.elementsStatistics = data;
     }

  ngOnInit() {
    Object.entries(this.elementsStatistics).forEach((v) => this.statistics.push({key: v[0], val: v[1]}));
  }

  onAccept(): void {
    this.dialogRef.close();
  }

}
