import { Routes } from '@angular/router';
import { StudentComponent } from './Components/Student/ListStudent/student.component';
import { ClassroomComponent } from './Components/ClassRoom/ListClassroom/classroom.component';
import { StudentDetailComponent } from './Components/Student/student-detail/student-detail.component';
import { ListStudentComponent } from './Components/ClassRoom/list-student/list-student.component';
import { AddStudentToClassComponent } from './Components/ClassRoom/add-student-to-class/add-student-to-class.component';
import { LoginComponent } from './Components/login/login.component';
import { routeGuard } from './guards/route.guard';

export const routes: Routes = [
  {
    path: 'student',
    component: StudentComponent,
    canActivate: [routeGuard]
  },
  {
    path: 'class',
    component: ClassroomComponent,
    canActivate: [routeGuard]
  },
  {
    path: 'student-detail/:id',
    component: StudentDetailComponent,
    canActivate: [routeGuard]
  },
  {
    path: 'class/:id',
    component: ListStudentComponent,
    canActivate: [routeGuard]
  },
  {
    path: 'list-student',
    component: AddStudentToClassComponent,
    canActivate: [routeGuard]
  },
  {
    path: 'login',
    component: LoginComponent
  }
];
