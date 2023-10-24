import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { TodoService } from '../todo.service';
import { UpdateTodoComponent } from './update-todo.component';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { ActivatedRoute } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';

describe('UpdateTodoComponent', () => {
  let component: UpdateTodoComponent;
  let fixture: ComponentFixture<UpdateTodoComponent>;
  
  beforeEach(() => {
    const activatedRouteMock = {
      snapshot: {
        paramMap: {
          get: () => '1',
        },
      },
    }; 

    TestBed.configureTestingModule({
      declarations: [UpdateTodoComponent],
      imports: [HttpClientModule, MatIconModule,MatCardModule, MatToolbarModule],
      providers: [TodoService, { provide: ActivatedRoute, useValue: activatedRouteMock}]
    });

    fixture = TestBed.createComponent(UpdateTodoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
