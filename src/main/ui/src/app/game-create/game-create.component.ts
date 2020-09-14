import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import { ProvinceInfo } from '../shared/models/provinceInfo.model';
import { ProvincesService } from '../_services/provinces.service';
import { UserInfo } from '../shared/models/userInfo.model';
import { UsersService } from '../_services/users.service';
import { Observable } from 'rxjs/internal/Observable';
import {map, startWith} from 'rxjs/operators';

/**
 * @title Stepper overview
 */

@Component({
  selector: 'app-game-create',
  templateUrl: './game-create.component.html',
  styleUrls: ['./game-create.component.css']
})
export class GameCreateComponent implements OnInit {
  municipalitiesAmount = 0;
  isLinear = true;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  isInputDisabled: boolean = true;
  provinces: ProvinceInfo[] = new Array();
  users: UserInfo[] = new Array();

  myControl = new FormControl();
  filteredUsers: Observable<UserInfo[]>;

  constructor(private _formBuilder: FormBuilder, private provincesService: ProvincesService,
    private usersService: UsersService) {}

  ngOnInit() {

    this.provincesService.getProvincesList().subscribe( 
      result => {result.forEach(p => this.provinces.push(p))}
    );

    this.usersService.getUsersList().subscribe( 
      result => {result.forEach(p => this.users.push(p))}
    );

    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', [Validators.required]]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrlFirstCondition: ['', Validators.required],
      secondCtrlSecondCondition: [{value:''}, [Validators.required]]
    });


    this.filteredUsers = this.firstFormGroup.controls.firstCtrl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
      
  }

  private _filter(value: String): UserInfo[] {
    const filterValue = value.toLowerCase();

    return this.users.filter(user => user.username.toLowerCase().includes(filterValue));
  }

  public isNotAUsernameValid(): Boolean { 
      return !this.users.some(user => this.firstFormGroup.controls.firstCtrl.value == user.username);
  };
}
