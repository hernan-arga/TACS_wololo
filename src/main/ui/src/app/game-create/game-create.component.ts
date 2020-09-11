import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Province } from '../shared/models/province.model';
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

  isLinear = true;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  provinces: String[] = ['Buenos Aires'];

  constructor(private _formBuilder: FormBuilder, private provincesService: ProvincesService) {}

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });

    this.provinces = this.provincesService.getProvincesList();
    
  }


}
