import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ScoreBoardShowComponent } from '../score-board-show/score-board-show.component';
import { Game } from '../shared/models/Game.model';

@Component({
  selector: 'app-score-board',
  templateUrl: './score-board.component.html',
  styleUrls: ['./score-board.component.css']
})

export class ScoreBoardComponent implements OnInit {
    
  constructor(public dialog: MatDialog) { }

  searchKey: string;
  displayedColumns: string[] = ['id', 'province', 'date', 'players', 'actions'];
  dataSource: MatTableDataSource<any>;

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit() {
    this.dataSource = new MatTableDataSource<Gamesito>(ELEMENT_DATA); // TODO SACAR

    /*this.scoreBoardService.getGames().subscribe(data =>
      {
        this.games = data;
        
      }
    );*/
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    this.dataSource.filter = this.searchKey.trim().toLowerCase();
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

export interface Gamesito { // TODO SACAR
  id: number;
  province: string;
  date: Date;
  players: Array<string>;
} 

const ELEMENT_DATA: Gamesito[] = [
  {id: 1, province: 'Chaco', date: new Date("2020-04-13T00:00:00.000+08:00"), players: ['Melisa', 'Charly']},
  {id: 2, province: 'Cordoba', date: new Date("2020-04-13T00:00:00.000+08:00"), players: ['Melisa', 'Charly']},
  {id: 3, province: 'Chaco', date: new Date("2020-04-13T00:00:00.000+08:00"), players: ['Melisa', 'Charly']},
  {id: 4, province: 'Chaco', date: new Date("2020-04-13T00:00:00.000+08:00"), players: ['Melisa', 'Charly']},
  {id: 5, province: 'Chaco', date: new Date("2020-04-13T00:00:00.000+08:00"), players: ['Melisa', 'Charly']},
  {id: 6, province: 'Chaco', date: new Date("2020-04-13T00:00:00.000+08:00"), players: ['Melisa', 'Charly']},
  {id: 7, province: 'Chaco', date: new Date("2020-04-13T00:00:00.000+08:00"), players: ['Melisa', 'Charly']}
  
];

