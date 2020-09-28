import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Municipality } from '../shared/models/municipality.model';
import { ImagesService } from '../_services/images.service';

@Component({
  selector: 'app-game-show-municipality-statistics',
  templateUrl: './game-show-municipality-statistics.component.html',
  styleUrls: ['./game-show-municipality-statistics.component.css']
})
export class GameShowMunicipalityStatisticsComponent implements OnInit {

  municipality: Municipality;
  imageService: ImagesService;
  municipalityImgUrl: string = '';
  isLoading = true;

  constructor(public dialogRef: MatDialogRef<GameShowMunicipalityStatisticsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Municipality,
    imageService: ImagesService) {
    this.municipality = data;
    this.imageService = imageService;
  }

  ngOnInit(): void {
    this.imageService.getImages(this.municipality.nombre).subscribe(
      data => {
        console.log(data);
        if (data.hits.length > 0) {          
          this.municipalityImgUrl = data.hits[0].webformatURL;
          console.log(this.municipalityImgUrl);
        }

        else {
          this.municipalityImgUrl = "../assets/provinces/Catamarca.png";
        }

        this.isLoading = false;
      },
      err => {
        console.log(err);
      }
    );
  }

  onAccept(): void {
    this.dialogRef.close();
  }

}
