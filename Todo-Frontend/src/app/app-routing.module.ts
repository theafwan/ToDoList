import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeScreenComponent } from './welcome-screen/welcome-screen.component';
import { TodoListComponent } from './todo-list/todo-list.component';
import { TodoDetailsComponent } from './todo-details/todo-details.component';
import { AddTodoComponent } from './add-todo/add-todo.component';
import { UpdateTodoComponent } from './update-todo/update-todo.component';

const routes: Routes = [
    { path: '', component: WelcomeScreenComponent },
    { path: 'todos', component: TodoListComponent },
    { path: 'todos/:id', component: TodoDetailsComponent },
    { path: 'add-todo', component: AddTodoComponent},
    { path: 'update-todo/:id', component: UpdateTodoComponent },
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
