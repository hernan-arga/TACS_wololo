import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { MatDialog } from '@angular/material/dialog';
import { ScoreBoardComponent } from '../score-board/score-board.component';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {
  content = '';
  firstFormGroup: FormGroup;

  constructor(private _formBuilder: FormBuilder, private userService: UserService, public dialog: MatDialog) { }

  ngOnInit() {
    //TODO: Arreglar cuando se haga lo de admin
    
    /*this.userService.getAdminBoard().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );*/
    this.firstFormGroup = this._formBuilder.group(
      {
        firstCtrl: ['']
      }
    );
  }

  openShowStatisticsDialog(): void {
    var jeje = this.firstFormGroup.controls.firstCtrl.value;
    const dialogRef = this.dialog.open(ScoreBoardComponent, {
      width: '400px',
      data: jeje, // TODO: CAMBIAR A ScoreBoard
      disableClose: true
    });
  }
}