/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ScoreBoardShowService } from './score-board-show.service';

describe('Service: ScoreBoardShow', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ScoreBoardShowService]
    });
  });

  it('should ...', inject([ScoreBoardShowService], (service: ScoreBoardShowService) => {
    expect(service).toBeTruthy();
  }));
});
