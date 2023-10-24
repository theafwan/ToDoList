import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; // Import form-related modules
import { TodoService } from '../todo.service';
import { Todo } from '../models/todo.model';
import { CustomResponse } from '../models/custom-response.model';

@Component({
  selector: 'app-update-todo',
  templateUrl: './update-todo.component.html',
  styleUrls: ['./update-todo.component.css'],
})
export class UpdateTodoComponent implements OnInit {
  todo: Todo | undefined;
  todoId: number | undefined;
  todoForm: FormGroup; // Define the todoForm FormGroup

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private todoService: TodoService,
    private formBuilder: FormBuilder // Inject FormBuilder
  ) {
    // Initialize the form in the constructor
    this.todoForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      tasks: this.formBuilder.array([]), // Initialize tasks as an empty array
    });
  }
  ngOnInit(): void {
    // Retrieve the todo ID from the route parameters
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null) {
      this.todoId = +idParam; // Assign the value to todoId here
      this.todoService.getTodoById(this.todoId).subscribe((data) => {
        this.todo = data.data;

        // Check if todo is defined and has tasks
        if (this.todo && this.todo.tasks) {
          // Initialize the form values based on todo data
          this.todoForm.patchValue({
            name: this.todo.name,
            description: this.todo.description,
          });

          // Initialize task values
          this.todo.tasks.forEach((task, index) => {
            this.todoForm.addControl(
              `taskName_${index}`,
              this.formBuilder.control(task.name)
            );
            this.todoForm.addControl(
              `taskDescription_${index}`,
              this.formBuilder.control(task.description)
            );
            this.todoForm.addControl(
              `taskId_${index}`,
              this.formBuilder.control(task.id)
            );
          });
        }
      });
    }
  }

  onSubmit(): void {
    // Define the onSubmit method logic here
    if (!this.todo || !this.todoId) {
      console.error('Failed to update To-Do.');
      return;
    }
    if (this.todoForm) {
      const taskControls = this.todoForm.controls;
    // Create a new Todo object with the form values
    const updatedTodo: Todo = {
      id: this.todoId,
      name: this.todoForm.value.name,
      description: this.todoForm.value.description,
      tasks: [],
    };

    const taskId: number[] = [];
    const taskNames: string[] = [];
    const taskDescriptions: string[] = [];

    // Iterate through the task names and descriptions and add them to the tasks array
    for (const key in taskControls) {
      if (key.startsWith('taskName_')) {
        taskNames.push(taskControls[key].value);
      } else if (key.startsWith('taskDescription_')) {
        taskDescriptions.push(taskControls[key].value);
      } else if (key.startsWith('taskId_')) {
        taskId.push(taskControls[key].value);
      }
    }

    for (let i = 0; i < taskNames.length; i++) {
      updatedTodo.tasks.push({
        id: taskId[i],
        name: taskNames[i],
        description: taskDescriptions[i]
      });
    }
    // Call the updateTodoById service with the updated todo
    this.todoService
      .updateTodoById(this.todoId, updatedTodo)
      .subscribe((response: CustomResponse<Todo>) => {
        if (response.success) {
          // Handle success, e.g., navigate to the list of todos
          this.router.navigate(['/todos']);
        } else {
          // Handle error, e.g., show an error message to the user
        }
      });
  }
}
}