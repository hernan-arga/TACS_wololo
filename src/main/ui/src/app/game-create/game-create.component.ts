import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Province } from '../shared/models/province.model';
import { ProvinceInfo } from '../shared/models/provinceInfo.model';
import { ProvincesService } from '../_services/provinces.service';

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

  constructor(private _formBuilder: FormBuilder, private provincesService: ProvincesService) {}

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrlFirstCondition: ['', Validators.required],
      secondCtrlSecondCondition: [{value:''}, Validators.required]
    });

    this.provincesService.getProvincesList().subscribe( 

      result => {result.forEach(p => this.provinces.push(p))}
      );

      
  }

  /*alreadySelectedProvince(municipalitiesAmount:Number){
    if(municipalitiesAmount>0){
      this.secondFormGroup.controls.secondCtrlSecondCondition.enable();
    }
  }*/



}
