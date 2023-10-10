import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { AddTodoComponent } from './add-todo.component';
import { TodoService } from '../todo.service';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { ActivatedRoute } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('AddTodoComponent', () => {
  let component: AddTodoComponent;
  let fixture: ComponentFixture<AddTodoComponent>;

  beforeEach(() => {
    const activatedRouteMock = {
      snapshot: {
        paramMap: {
          get: () => '1',
        },
      },
    };
    TestBed.configureTestingModule({
      declarations: [AddTodoComponent],
      imports: [HttpClientModule, 
                MatIconModule,
                MatCardModule, 
                MatToolbarModule,
                MatFormFieldModule, 
                FormsModule,
                ReactiveFormsModule, 
                MatInputModule, 
                BrowserAnimationsModule],
      providers: [TodoService, { provide: ActivatedRoute, useValue: activatedRouteMock}]
    });
    fixture = TestBed.createComponent(AddTodoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
