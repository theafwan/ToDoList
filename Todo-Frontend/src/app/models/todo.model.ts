import { Task } from './task.model';
export class Todo {
  id?: number;
  name: string = '';
  description: string = '';
  tasks: Task[] = [];
}
