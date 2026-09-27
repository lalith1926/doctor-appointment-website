import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClinicNavbarComponent } from './clinic-navbar.component';

describe('ClinicNavbarComponent', () => {
  let component: ClinicNavbarComponent;
  let fixture: ComponentFixture<ClinicNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClinicNavbarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClinicNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
