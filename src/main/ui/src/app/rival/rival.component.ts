import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Rival } from '../shared/models/rival.model';

@Component({
  selector: 'rival-component',
  templateUrl: './rival.component.html',
  styleUrls: ['./rival.component.css']
})
export class RivalComponent implements OnInit {

  @Input() possiblesRivals
  @Input() parentFormGroup
  @Input() rival;

  formControlName: string
  filteredUsers: Observable<Rival[]>;

  constructor() { }

  ngOnInit(): void {

    this.formControlName = 'rival'+(this.rival.id).toString();
    var formControlChild = this.parentFormGroup.controls[this.formControlName];

    this.filteredUsers = formControlChild.valueChanges
      .pipe(
        startWith(''),
        map((value: string) => this._filter(value))
      )

    //console.log(this.possiblesRivals);
  }

  private _filter(value: String): Rival[] {    
    const filterValue = value.toLowerCase();

    return this.possiblesRivals.filter(user => user.username.toLowerCase().includes(filterValue));
  }

}
