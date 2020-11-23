import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {ScoreBoardShowComponent} from '../score-board-show/score-board-show.component';
import {Game} from '../shared/models/game.model';
import {GamesService} from '../_services/games.service';

@Component({
  selector: 'app-score-board',
  templateUrl: './score-board.component.html',
  styleUrls: ['./score-board.component.css']
})

export class ScoreBoardComponent implements OnInit {

  constructor(public dialog: MatDialog, private gamesService: GamesService) { }

  displayedColumns: string[] = ['id', 'province', 'date', 'players', 'actions'];
  dataSource: MatTableDataSource<any>;
  showMessageNoData: boolean;

  range = new FormGroup({
    start: new FormControl('', Validators.required),
    end: new FormControl('', Validators.required)
  });

  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit() {
    this.showMessageNoData = false;
    this.dataSource = new MatTableDataSource<Game>();
    this.dataSource.paginator = this.paginator;
  }

  clearData()
  {
    this.showMessageNoData = false;
    this.dataSource = null;
    this.dataSource.paginator = this.paginator;
  }

  getDateRange() {
    this.showMessageNoData = false;
    let fromDate = this.range.value.start;
    let toDate = this.range.value.end;
    this.gamesService.getGamesByDate(fromDate, toDate).subscribe(data =>
      {
        if(data.length == 0)
          this.showMessageNoData = true;
        this.dataSource = new MatTableDataSource<Game>(data);
        this.dataSource.paginator = this.paginator;
      }
    );
  }

  openShowScoreBoardDialog(id: number): void {
    let gameId = id;
    this.dialog.open(ScoreBoardShowComponent, {
      width: '600px',
      data: gameId,
      disableClose: true
    });
  }
}
