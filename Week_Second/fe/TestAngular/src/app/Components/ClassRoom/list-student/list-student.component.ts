import { Component, inject, OnInit, TemplateRef } from '@angular/core';
import { Student } from '../../../Entity/Student';
import { ClassroomService } from '../../../Services/classroom.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-list-student',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './list-student.component.html',
  styleUrl: './list-student.component.scss'
})
export class ListStudentComponent implements OnInit{

  listStudent?: Student[];
  class_id?: string;
  private modalService = inject(NgbModal);
  student_id?: string;

  constructor(private classService: ClassroomService,
    private route : ActivatedRoute,
    private toastr: ToastrService,
    private router: Router
  ){

  }

  ngOnInit(): void {
      this.route.params.subscribe(params => {
        this.class_id = params['id'];
        this.loadPage();
      });
      console.log(this.listStudent);
  }

  loadPage(){
    this.classService.findClassById(this.class_id!)
    .subscribe(data => {
      this.listStudent = data.students;
    })
  }

  removeStudent(): void {
    this.classService.removeStudent(this.student_id!)
  .subscribe({next: () => {
      this.loadPage();
      this.toastr.success('Deleted student successfully', 'Success');
    }, error: () => {
      this.loadPage();
      this.toastr.error('Error deleting student', 'Error');
    }});
    this.modalService.dismissAll();
  }

  open(content: TemplateRef<any>, id: string): void {
    this.student_id = id;
    this.modalService.open(content);
  }

  openListStudent(): void {
    this.classService.setClassId(this.class_id!);
    this.router.navigate(['list-student']);
  }
}
