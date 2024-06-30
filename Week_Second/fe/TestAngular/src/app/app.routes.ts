import { Routes } from '@angular/router';
import { StudentComponent } from './Components/Student/ListStudent/student.component';
import { ClassroomComponent } from './Components/ClassRoom/ListClassroom/classroom.component';
import { StudentDetailComponent } from './Components/Student/student-detail/student-detail.component';
import { ListStudentComponent } from './Components/ClassRoom/list-student/list-student.component';
import { AddStudentToClassComponent } from './Components/ClassRoom/add-student-to-class/add-student-to-class.component';

export const routes: Routes = [{
    path: '', component: StudentComponent
}, {
    path: 'class', component: ClassroomComponent
}, {
    path: 'student-detail/:id', component: StudentDetailComponent
}, {
    path: 'class/:id', component: ListStudentComponent
},{
    path: 'list-student', component: AddStudentToClassComponent
}
];
