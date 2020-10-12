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
import { Rival } from '../shared/models/rival.model';

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
  rivalsFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  isInputDisabled: boolean = true;
  provinces: ProvinceInfo[] = new Array();
  users: UserInfo[] = new Array();

  currentUserUsername: String
  myControl = new FormControl();
  filteredUsers: Observable<UserInfo[]>;
  rivals: Array<Rival> = [];

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

    this.rivalsFormGroup = this._formBuilder.group({
    });

    this.secondFormGroup = this._formBuilder.group({
      secondCtrlFirstCondition: ['', Validators.required],
      secondCtrlSecondCondition: [{ value: '' }, [Validators.required]]
    });

    this.addRival();


  }

  isAddRivalDisabled(){
    return this.rivals.length >= 3;
  }
  

  isDeleteRivalDisabled(){
    return this.rivals.length <= 1;
  }

  sortProvinces() {
    this.provinces.sort(function (p1, p2) {
      if (p1.name > p2.name) { return 1; }
      if (p1.name < p2.name) { return -1; }
      return 0;
    });
  }


  public notAllUsernameAreValid(): Boolean {
    return this.anyUsernameIsEmpty() || this.anyUsernameIsNotValid();
  };

  anyUsernameIsNotValid(): boolean{
    let possibleRivalsUsernames = this.rivals.map(r => {       
      var formControlChild = this.getFormControlRival(r);
      return formControlChild.value;
    });

    return this.isThereRepeatedRivals(possibleRivalsUsernames) || !this.allRivalsExists(possibleRivalsUsernames);
  }

  allRivalsExists(possibleRivalsUsernames: Array<string>): boolean{
    return possibleRivalsUsernames.every(r => {
      return this.users.some(user => r == user.username);
    });
    
  }

  isThereRepeatedRivals(possibleRivalsUsernames: Array<string>): boolean{
    const status =  possibleRivalsUsernames.some(user => {
      let counter  = 0;
      for (const iterator of possibleRivalsUsernames) {
        if (iterator === user) {
          counter += 1;
        }
      }
      return counter > 1;
    });

    return status;
  }
 

  anyUsernameIsEmpty(): boolean{

    if(this.rivals.length > 0){
      return this.rivals.some(r => {       
        var formControlChild = this.getFormControlRival(r);
        return formControlChild.hasError('required');
      });
    }

    return true;

  }

  getFormControlRival(r: Rival): AbstractControl{
    var formControlName = 'rival'+(r.id).toString();
    return this.rivalsFormGroup.controls[formControlName];
  }

  public createGame() {
    var userNames = new Array<String>();
    var municipalitiesCant = this.secondFormGroup.controls.secondCtrlSecondCondition.value;
    var provinceName = this.provinceSelected.name;

    this.rivals.forEach(r => {       
      var formControlChild = this.getFormControlRival(r);
      r.username = formControlChild.value;
    });

    this.rivals.forEach(r => userNames.push(r.username));
    userNames.push(this.currentUserUsername);

    var gameInfo = new GameInfo(userNames, municipalitiesCant, provinceName);
    this.gamesService.createGame(gameInfo).subscribe(
      data => { this.router.navigate(['/game', data.id]); }
    );


  }

  public municipalitiesCantUnder100(municipalitiesCant: number): boolean {
    return municipalitiesCant < 100;
  }

  addRival(){
    this.rivals.push(new Rival(this.rivals.length+1));     
    let rivalControlName = 'rival'+ (this.rivals.length).toString(); 
    this.rivalsFormGroup.addControl(rivalControlName, new FormControl('', Validators.required));
  }

  deleteRival(){
    let rivalControlName = 'rival'+ (this.rivals.length).toString();
    this.rivals.pop();
    this.rivalsFormGroup.removeControl(rivalControlName);
  }

}
