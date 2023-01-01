import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BackpackDetailComponent } from './backpack-detail.component';

describe('BackpackDetailComponent', () => {
  let component: BackpackDetailComponent;
  let fixture: ComponentFixture<BackpackDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BackpackDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BackpackDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
