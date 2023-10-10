import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TodoService } from './todo.service';
import { Todo } from './models/todo.model';
import { CustomResponse } from './models/custom-response.model';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';


describe('TodoService', () => {
  let service: TodoService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TodoService],
    });

    service = TestBed.inject(TodoService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  // Your test cases go here
  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all todos', () => {
    const mockTodos: Todo[] = [
      { id: 1, name: 'Test Todo 1', description: 'Description 1' , tasks: []},
      { id: 2, name: 'Test Todo 2', description: 'Description 2' , tasks: []},
    ];
  
    service.getAllTodos().subscribe((todos) => {
      expect(todos).toEqual(mockTodos);
    });
  
    const req = httpTestingController.expectOne('http://localhost:8080/api/v1/todos');
    expect(req.request.method).toEqual('GET');
    req.flush(mockTodos);
    httpTestingController.verify();
  });
  
  it('should fetch a todo by ID', () => {
    const mockTodo: Todo = { id: 1, name: 'Test Todo', description: 'Description' , tasks: []};
    const todoId = 1;
  
    service.getTodoById(todoId).subscribe((todo) => {
      expect(todo).toEqual(mockTodo);
    });
  
    const req = httpTestingController.expectOne(`http://localhost:8080/api/v1/todos/${todoId}`);
    expect(req.request.method).toEqual('GET');
    req.flush(mockTodo);
    httpTestingController.verify();
  });

  it('should create a new todo', () => {
    const mockTodo: Todo = { name: 'Test Todo', description: 'Description', tasks: [] };

    service.createTodo(mockTodo).subscribe((createdTodo) => {
      expect(createdTodo).toEqual(mockTodo);
    });

    const req = httpTestingController.expectOne('http://localhost:8080/api/v1/todos');
    expect(req.request.method).toEqual('POST');
    req.flush(mockTodo);
    httpTestingController.verify();
  });

  it('should update a todo by ID', () => {
    const mockTodo: Todo = { id: 1, name: 'Updated Todo', description: 'Updated Description', tasks: [] };
    const todoId = 1;

    service.updateTodoById(todoId, mockTodo).subscribe((updatedResponse: CustomResponse<Todo>) => {
      expect(updatedResponse.data).toEqual(mockTodo);
    });

    const req = httpTestingController.expectOne(`http://localhost:8080/api/v1/todos/${todoId}`);
    expect(req.request.method).toEqual('PUT');
    req.flush({ success: true, data: mockTodo });
    httpTestingController.verify();
  });

  it('should delete a todo by ID', () => {
    const todoId = 1;

    service.deleteTodoById(todoId).subscribe((deleteResponse: CustomResponse<null>) => {
      expect(deleteResponse.success).toBe(true);
    });

    const req = httpTestingController.expectOne(`http://localhost:8080/api/v1/todos/${todoId}`);
    expect(req.request.method).toEqual('DELETE');
    req.flush({ success: true });
    httpTestingController.verify();
  });
});