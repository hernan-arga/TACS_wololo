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
    let prueba = new Map<string, number>(); // TODO: SACAR HARDCODEO
    prueba.set("CREATE", 5);
    prueba.set("FINISHED", 10);
    prueba.set("IN_PROGRESS", 15);
    prueba.set("CANCELLED", 2);

    prueba.forEach((value, k) => this.statistics.push({key: k, val: value}));
    
    // TODO: HACER QUE LO LEA DESDE EL SERVICE
    //this.elementsStatistics.forEach((value, k) => this.statistics.push({key: k, val: value}));
  }

  onAccept(): void {
    this.dialogRef.close();
  }

}
