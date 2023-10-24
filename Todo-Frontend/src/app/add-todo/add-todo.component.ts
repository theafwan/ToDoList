import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { Router } from '@angular/router';
import { TodoService } from '../todo.service';

@Component({
  selector: 'app-add-todo',
  templateUrl: './add-todo.component.html',
  styleUrls: ['./add-todo.component.css']
})
export class AddTodoComponent implements OnInit {
  todoForm: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private todoService: TodoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.todoForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      tasks: this.formBuilder.array([this.createTask()]) // Initialize with one default task
    });
  }

  createTask(): FormGroup {
    return this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  addTask(): void {
    const tasksArray = this.todoForm.get('tasks') as FormArray;
    tasksArray.push(this.createTask());
  }

  removeTask(index: number): void {
    const tasksArray = this.todoForm.get('tasks') as FormArray;
    tasksArray.removeAt(index);
  }

  onSubmit(): void {
    if (this.todoForm.valid) {
      const todoData = {
        id: 0,
        name: this.todoForm.value.name,
        description: this.todoForm.value.description,
        tasks: this.todoForm.value.tasks
      };

      // Call the createTodo service
      this.todoService.createTodo(todoData).subscribe((response) => {
        if (response) {
          // Todo creation successful, navigate back to the list of todos
          this.router.navigate(['/todos']);
        } else {
          // Handle error, e.g., show an error message
        }
      });
    }
  }

  onCancel(): void {
    // Navigate back to the list of todos
    this.router.navigate(['/todos']);
  }

  get tasks() {
    return this.todoForm.get('tasks') as FormArray;
  }
}
