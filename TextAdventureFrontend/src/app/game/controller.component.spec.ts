import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Join_gameComponent } from './join_game.component';

describe('DashboardComponent', () => {
  let component: Join_gameComponent;
  let fixture: ComponentFixture<Join_gameComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [Join_gameComponent]
    });
    fixture = TestBed.createComponent(Join_gameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
