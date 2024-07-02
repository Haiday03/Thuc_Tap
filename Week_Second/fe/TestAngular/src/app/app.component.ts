import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { StudentComponent } from './Components/Student/ListStudent/student.component';
import { ClassroomComponent } from './Components/ClassRoom/ListClassroom/classroom.component';
import { StudentDetailComponent } from './Components/Student/student-detail/student-detail.component';
import { NgIconComponent } from '@ng-icons/core';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, StudentComponent,ClassroomComponent, RouterModule, StudentDetailComponent, NgIconComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'TestAngular';
}
