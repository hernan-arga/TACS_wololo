import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { StatisticsShowComponent } from '../statistics-show/statistics-show.component';
import { StatisticsService } from '../_services/statistics.service';


@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  firstFormGroup: FormGroup;
  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl()
  });

  constructor(private _formBuilder: FormBuilder, public dialog: MatDialog,
    private statisticsService: StatisticsService) { }

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group(
      {
        firstCtrl: ['']
      }
    );
  }

  openShowStatisticsByDate(): void {
    let statistics = new Map<string, number>();
    let from = this.range.value.start;
    let to = this.range.value.end;

    this.statisticsService.getStatisticsByDate(from, to).subscribe(
      data => {
        statistics = data;

        this.dialog.open(StatisticsShowComponent, {
          width: '600px',
          data: statistics, 
          disableClose: true
        });
      });
  }

  async openShowIndividualStatisticsDialog(): Promise<void>{
    let statistics = new Map<string, number>();
    let userName = this.firstFormGroup.controls.firstCtrl.value;

    this.statisticsService.getIndividualStatistics(userName).subscribe(
      data => {
        statistics = data;

        this.dialog.open(StatisticsShowComponent, {
          width: '600px',
          data: statistics, 
          disableClose: true
        });
      });
  }

}
