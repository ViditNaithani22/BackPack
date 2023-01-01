import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBackpacksPageComponent } from './view-backpacks-page.component';

describe('ViewBackpacksPageComponent', () => {
  let component: ViewBackpacksPageComponent;
  let fixture: ComponentFixture<ViewBackpacksPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewBackpacksPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewBackpacksPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
