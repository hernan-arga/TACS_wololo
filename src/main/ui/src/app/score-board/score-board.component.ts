import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ScoreBoardShowComponent } from '../score-board-show/score-board-show.component';
import { Game } from '../shared/models/Game.model';
import { GamesService } from '../_services/games.service';

@Component({
  selector: 'app-score-board',
  templateUrl: './score-board.component.html',
  styleUrls: ['./score-board.component.css']
})

export class ScoreBoardComponent implements OnInit {
    
  constructor(public dialog: MatDialog, private gamesService: GamesService) { }

  displayedColumns: string[] = ['id', 'province', 'date', 'players', 'actions'];
  dataSource: MatTableDataSource<any>;

  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl()
  });

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit() {
    this.dataSource = new MatTableDataSource<Game>();
  }

  clearData()
  {
    this.dataSource = null;
  }

  getDateRange() {
    let fromDate = this.range.value.start;
    let toDate = this.range.value.end;
    this.gamesService.getGamesByDate(fromDate, toDate).subscribe(data =>
      {
        this.dataSource = new MatTableDataSource<Game>(data);
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