import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TodoService } from '../todo.service';
import { Todo } from '../models/todo.model';
import { CustomResponse } from '../models/custom-response.model';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-todo-details',
  templateUrl: './todo-details.component.html',
  styleUrls: ['./todo-details.component.css']
})
export class TodoDetailsComponent implements OnInit {
  todo: Todo | undefined;
  todoId: number | undefined;
  todoForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private todoService: TodoService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.todoForm = this.formBuilder.group({
      name: [''],
      description: [''],
      tasks: this.formBuilder.array([]),
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
  if (idParam !== null) {
    this.todoId = +idParam; // Assign the value to todoId here
    this.todoService.getTodoById(this.todoId).subscribe(data => {
      this.todo = data.data;
      
    });
  }
  }

  updateTodo(): void {

    if (!this.todo || !this.todoId) {
      console.error('Failed to update To-Do.');
      return;
    }
  
    const updatedTodo: Todo = {
      id: this.todo.id,
      name: this.todoForm.get('name')?.value,
      description: this.todoForm.get('description')?.value,
      tasks: this.todoForm.get('tasks')?.value,
    };
  
    // Navigate to the update-todo component with the todo ID as a route parameter
    this.router.navigate(['/update-todo', this.todoId]);
  }

  deleteTodo(): void {
    if (!this.todoId) {
      // Handle this scenario, e.g., show an error message
      return;
    }

    // Call the deleteTodoById service
    this.todoService.deleteTodoById(this.todoId).subscribe((response: CustomResponse<null>) => {
      if (response.success) {
        // Handle success, e.g., navigate to the list of todos
        this.router.navigate(['/todos']);
      } else {
        // Handle error, e.g., show an error message to the user
      }
    });
  }
}
