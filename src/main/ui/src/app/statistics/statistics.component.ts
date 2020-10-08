import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { UserInfo } from '../shared/models/userInfo.model';
import { StatisticsShowComponent } from '../statistics-show/statistics-show.component';
import { StatisticsService } from '../_services/statistics.service';
import { UsersService } from '../_services/users.service';


@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  filteredUsers: Observable<UserInfo[]>;
  users: UserInfo[] = new Array();

  firstFormGroup: FormGroup;
  range = new FormGroup({
    start: new FormControl('', Validators.required),
    end: new FormControl('', Validators.required)
  });

  constructor(private _formBuilder: FormBuilder, public dialog: MatDialog,
    private statisticsService: StatisticsService, private usersService: UsersService) { }

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group(
      {
        firstCtrl: ['', [Validators.required]]
      }
    );

    this.filteredUsers = this.firstFormGroup.controls.firstCtrl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
    
    this.usersService.getUsersList().subscribe(
      result => { result.forEach(p => this.users.push(p) ) }
    );
  }

  private _filter(value: String): UserInfo[] {
    const filterValue = value.toLowerCase();

    return this.users.filter(user => user.username.toLowerCase().includes(filterValue));
  }

  public userExists(): Boolean {
    return this.users.some(user => 
       this.firstFormGroup.controls.firstCtrl.value == user.username); 
  };

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

  openShowIndividualStatisticsDialog(): void{
    let statistics = new Map<string, number>();
    let userName = this.firstFormGroup.controls.firstCtrl.value;

    this.statisticsService.getIndividualStatistics(userName).subscribe(
      data => {
        statistics = data;

        this.dialog.open(StatisticsShowComponent, {
          width: '600px',
          height: '600px',
          data: statistics, 
          disableClose: true
        });
      });
  }

}
