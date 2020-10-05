import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { ProfileComponent } from './profile/profile.component';

import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { GameCreateComponent } from './game-create/game-create.component';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DemoMaterialModule} from './material-module';
import { MatNativeDateModule } from '@angular/material/core';
import { GamePlayComponent } from './game-play/game-play.component';
import { GameMoveGauchosComponent } from './game-move-gauchos/game-move-gauchos.component';
import { GameNotTurnToPlayComponent } from './game-not-turn-to-play/game-not-turn-to-play.component';
import { GameMovementSuccessfulComponent } from './game-movement-successful/game-movement-successful.component';
import { GameShowMunicipalityStatisticsComponent } from './game-show-municipality-statistics/game-show-municipality-statistics.component';
import { GameFinishedShowWinnerComponent } from './game-finished-show-winner/game-finished-show-winner.component';
import { ScoreBoardComponent } from './score-board/score-board.component';


@NgModule({
  declarations: [	
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    BoardAdminComponent,
    BoardUserComponent,
    BoardModeratorComponent,
    ProfileComponent,
    GameCreateComponent,
    GamePlayComponent,
    GameMoveGauchosComponent,
    GameNotTurnToPlayComponent,
    GameMovementSuccessfulComponent,
    GameShowMunicipalityStatisticsComponent,
    GameFinishedShowWinnerComponent,
      ScoreBoardComponent
   ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    DemoMaterialModule,
    MatNativeDateModule,
    ReactiveFormsModule,
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }