import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-statistics-show',
  templateUrl: './statistics-show.component.html',
  styleUrls: ['./statistics-show.component.scss']
})
export class StatisticsShowComponent implements OnInit {

  username: string;
  statistics: Array<any> = [];

  constructor(public dialogRef: MatDialogRef<StatisticsShowComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string) {
      this.username = data;
     }

  ngOnInit() {
    let prueba = new Map<string, number>(); // TODO: SACAR HARDCODEO
    prueba.set("CREATE", 5);
    prueba.set("FINISHED", 10);
    prueba.set("IN_PROGRESS", 15);
    prueba.set("CANCELLED", 2);
    // TODO: HACER QUE LO LEA DESDE EL SERVICE

    prueba.forEach((value, k) => this.statistics.push({key: k, val: value}));
  }

  onAccept(): void {
    this.dialogRef.close();
  }

}
