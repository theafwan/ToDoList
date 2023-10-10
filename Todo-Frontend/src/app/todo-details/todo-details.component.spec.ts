import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { TodoDetailsComponent } from './todo-details.component';
import { TodoService } from '../todo.service';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { ActivatedRoute } from '@angular/router';

describe('TodoDetailsComponent', () => {
  let component: TodoDetailsComponent;
  let fixture: ComponentFixture<TodoDetailsComponent>;

  beforeEach(() => {
    // Create a mock ActivatedRoute
    const activatedRouteMock = {
      snapshot: {
        paramMap: {
          get: () => '1',
        },
      },
    };

    TestBed.configureTestingModule({
      declarations: [TodoDetailsComponent],
      imports: [HttpClientModule,
                MatIconModule,
                MatCardModule],
      providers: [TodoService, { provide: ActivatedRoute, useValue: activatedRouteMock }],
    });
    fixture = TestBed.createComponent(TodoDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
