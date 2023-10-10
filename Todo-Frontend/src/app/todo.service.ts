// Import necessary modules and models
import { Todo } from './models/todo.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { CustomResponse } from './models/custom-response.model';

@Injectable({
  providedIn: 'root',
})
export class TodoService {
  // Define the base URL for API requests
  private baseUrl = 'http://localhost:8080/api/v1/todos';

  constructor(private http: HttpClient) {}

  // Fetch all todos from the API
  getAllTodos(): Observable<any> {
    return this.http.get(this.baseUrl);
  }

  // Fetch a todo by its ID from the API
  getTodoById(id: number): Observable<any> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get(url);
  }

  // Create a new todo by sending a POST request to the API
  createTodo(task: Todo): Observable<Todo> {
    return this.http.post<Todo>(this.baseUrl, task);
  }

  // Update a todo by its ID using a PUT request to the API
  updateTodoById(id: number, todo: Todo): Observable<CustomResponse<Todo>> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.put<CustomResponse<Todo>>(url, todo);
  }

  // Delete a todo by its ID using a DELETE request to the API
  deleteTodoById(id: number): Observable<CustomResponse<null>> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<CustomResponse<null>>(url);
  }
}