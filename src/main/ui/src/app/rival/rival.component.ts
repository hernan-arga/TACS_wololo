import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'rival-component',
  templateUrl: './rival.component.html',
  styleUrls: ['./rival.component.css']
})
export class RivalComponent implements OnInit {

  @Input() possiblesRivals

  constructor() { }

  ngOnInit(): void {
  }

}
