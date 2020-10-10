import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ProvinceInfo } from '../shared/models/provinceInfo.model';
import { ProvincesService } from '../_services/provinces.service';
import { UserInfo } from '../shared/models/userInfo.model';
import { UsersService } from '../_services/users.service';
import { Observable } from 'rxjs/internal/Observable';
import { map, startWith } from 'rxjs/operators';
import { GamesService } from '../_services/games.service';
import { GameInfo } from '../shared/models/gameInfo.model';
import { TokenStorageService } from '../_services/token-storage.service';
import { Router } from '@angular/router';

/**
 * @title Stepper overview
 */

@Component({
  selector: 'app-game-create',
  templateUrl: './game-create.component.html',
  styleUrls: ['./game-create.component.css']
})
export class GameCreateComponent implements OnInit {
  provinceSelected: ProvinceInfo;
  municipalitiesAmount = 0;
  isLinear = true;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  isInputDisabled: boolean = true;
  provinces: ProvinceInfo[] = new Array();
  users: UserInfo[] = new Array();

  currentUserUsername: String
  myControl = new FormControl();
  filteredUsers: Observable<UserInfo[]>;

  constructor(private _formBuilder: FormBuilder,
    private provincesService: ProvincesService,
    private usersService: UsersService,
    private gamesService: GamesService,
    private tokenStorageService: TokenStorageService,
    private router: Router) { }

  ngOnInit() {

    const user = this.tokenStorageService.getUser();
    this.currentUserUsername = user.username;

    this.provincesService.getProvincesList().subscribe(
      result => { result.forEach(p => this.provinces.push(p)); this.sortProvinces(); }
    );

    this.provinceSelected = new ProvinceInfo("provincia sin seleccionar", 2);

    this.usersService.getUsersList().subscribe(
      result => { result.forEach(p => { if (p.username !== this.currentUserUsername) { this.users.push(p) } }) }
    );

    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', [Validators.required]]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrlFirstCondition: ['', Validators.required],
      secondCtrlSecondCondition: [{ value: '' }, [Validators.required]]
    });


    this.filteredUsers = this.firstFormGroup.controls.firstCtrl.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );


  }

  sortProvinces() {
    this.provinces.sort(function (p1, p2) {
      if (p1.name > p2.name) { return 1; }
      if (p1.name < p2.name) { return -1; }
      return 0;
    });
  }

  private _filter(value: String): UserInfo[] {
    const filterValue = value.toLowerCase();

    return this.users.filter(user => user.username.toLowerCase().includes(filterValue));
  }

  public isNotAUsernameValid(): Boolean {
    return !this.users.some(user => this.firstFormGroup.controls.firstCtrl.value == user.username);
  };

  public createGame() {
    var userNames = new Array<String>();
    var municipalitiesCant = this.secondFormGroup.controls.secondCtrlSecondCondition.value;
    var provinceName = this.provinceSelected.name;
    userNames.push(this.firstFormGroup.controls.firstCtrl.value);
    userNames.push(this.currentUserUsername);

    var gameInfo = new GameInfo(userNames, municipalitiesCant, provinceName);
    this.gamesService.createGame(gameInfo).subscribe(
      data => { this.router.navigate(['/game', data.id]); }
    );


  }

  public municipalitiesCantUnder100(municipalitiesCant: number): boolean {
    return municipalitiesCant < 100;
  }

}
