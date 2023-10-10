import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WelcomeScreenComponent } from './welcome-screen.component';
import { MatToolbarModule } from '@angular/material/toolbar';

describe('WelcomeScreenComponent', () => {
  let component: WelcomeScreenComponent;
  let fixture: ComponentFixture<WelcomeScreenComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WelcomeScreenComponent],
      imports: [MatToolbarModule],
    });
    fixture = TestBed.createComponent(WelcomeScreenComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
