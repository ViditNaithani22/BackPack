import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBackpacksComponent } from './view-backpacks.component';

describe('ViewBackpacksComponent', () => {
  let component: ViewBackpacksComponent;
  let fixture: ComponentFixture<ViewBackpacksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewBackpacksComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewBackpacksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
