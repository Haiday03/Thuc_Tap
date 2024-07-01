import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { StudentService } from '../../../Services/student.service';
import { Student } from '../../../Entity/Student';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { SearchRequest } from '../../../Entity/SearchRequest';

@Component({
  selector: 'app-student-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './student-detail.component.html',
  styleUrl: './student-detail.component.scss'
})
export class StudentDetailComponent implements OnInit{

  private student_id!: string;
  student!: Student[];
  private searchRequest: SearchRequest = {
    id : ''
  };

  constructor(private studentService: StudentService,
    private route: ActivatedRoute
  ){

  }

  setId(value:string){
    this.student_id = value;
  }

  ngOnInit(){

    this.route.params.subscribe(params => {
      this.setId(params['id']);
      this.searchRequest.id = this.student_id;
      this.loadPage();
    });
    console.log(this.student);

  }

  loadPage(){
    this.studentService.getListStudents(this.searchRequest, 1, 1)
    .then(data => {
      this.student = data.content;
    }).catch(err => {
      throw new Error(err);
    })
  }

}
