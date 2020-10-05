import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ScoreBoardShowComponent } from '../score-board-show/score-board-show.component';

@Component({
  selector: 'app-score-board',
  templateUrl: './score-board.component.html',
  styleUrls: ['./score-board.component.css']
})
export class ScoreBoardComponent implements OnInit {

  content = '';
  firstFormGroup: FormGroup;
  
  constructor(private _formBuilder: FormBuilder, public dialog: MatDialog) { }

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

  openShowScoreBoardDialog(): void {
    var gameId = this.firstFormGroup.controls.firstCtrl.value;
    const dialogRef = this.dialog.open(ScoreBoardShowComponent, {
      width: '600px',
      data: gameId, 
      disableClose: true
    });
  }

}
