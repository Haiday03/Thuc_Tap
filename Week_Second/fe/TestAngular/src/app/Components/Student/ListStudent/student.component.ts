import { Component, inject, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Student } from '../../../Entity/Student';
import { StudentService } from '../../../Services/student.service';
import { FormsModule, NgForm } from '@angular/forms';
import { SearchRequest } from '../../../Entity/SearchRequest';
import { CommonModule } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ClassRoom } from '../../../Entity/ClassRoom';
import { ClassroomService } from '../../../Services/classroom.service';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { StudentDetailComponent } from '../student-detail/student-detail.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student',
  standalone: true,
  imports: [CommonModule, FormsModule, ToastrModule],
  templateUrl: './student.component.html',
  styleUrl: './student.component.scss'
})
export class StudentComponent implements OnInit{

  listStudent: Student[] = [];
  currentPage: number = 0;
  pageSize: number = 12;
  totalPages: number = 0;
  action: string = 'Add';
  student: Student = {id : ''};
  searchRequest: SearchRequest ={} ;
	private modalService = inject(NgbModal);
  listClass: ClassRoom[] = [];
  pageSizeClass: number = 0;
  @ViewChild('ngForm') ngForm: NgForm | undefined; // Khai báo ngForm như một ViewChild
  test: number = 0;

  constructor(private studentService: StudentService,
    private classroomService: ClassroomService,
    private toastr: ToastrService,
    private router : Router
  ){
  }

  ngOnInit(){
    this.loadPage(this.currentPage);
    this.loadClassroom();
  }

  loadClassroom() : void{
    this.classroomService.getClass(1, 0).subscribe(data => {
      this.pageSizeClass = data.totalPages;
    });
    this.classroomService.getClass(this.pageSizeClass + 1, 0).subscribe(next => {
      this.listClass = next.content;
    });
  }


  loadPage(page: number): void{
    if(this.searchRequest === undefined)
      this.searchRequest = {};
    this.studentService.getListStudents(this.searchRequest!, this.pageSize, page)
    .then(data => {
        this.listStudent = data.content;
        this.totalPages = data.totalPages;
        this.currentPage = page;
  })
    .catch(err => {
      this.listStudent = []
      throw err;
    });
  }

  nextPage(): void{
    if(this.currentPage <= this.totalPages - 1)
      this.loadPage(this.currentPage + 1);
  }

  prevPage(): void{
    if(this.currentPage !== 0)
      this.loadPage(this.currentPage - 1);
  }

  onFileSelected(event: any): void{
    const file: File = event.target.files[0];
    if(file)
      this.student.avatar = file;
  }
  // Open Modal 
  openToAdd(content: TemplateRef<any>) {
    this.student = {};
    this.action = 'Add';
    this.loadClassroom();
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result;
	}

  openToUpdate(content: TemplateRef<any>, s : Student) {
    this.loadClassroom();
    this.action = 'Update';
    this.student = s;
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result;
  }

  openToDelete(content: TemplateRef<any>, s: Student) {
    this.action = 'Delete';
    this.student = s;
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result;
  }

  //action
  addStudent() : void {
    this.loadClassroom();
    if(this.ngForm?.form?.valid) {
      this.studentService.addStudent(this.convertToFormData())
      .then(() => {
        this.toastr.success('Add successfully!', 'Successfully');
        this.loadPage(0);
      }).catch(() => {
        this.toastr.error('Error', 'Error adding');
        this.loadPage(0);
      });
    }
    this.student = {};
  }

  updateStudent(): void {
    if(this.ngForm?.form?.valid){
      this.studentService.updateStudent(this.convertToFormData())
      .then(() => {
        this.toastr.success('Update successfully!', 'Successfully');
        this.loadPage(0);
      }).catch(() => {
        this.toastr.error('Error', 'Error Updating');
        this.loadPage(0);
      });
    }

    this.modalService.dismissAll();
    this.student = {};
  }

  deleteStudent(): void {
    if(this.ngForm?.form?.valid){
      this.studentService.deleteStudent(this.student.id!)
      .then(() => {
        this.toastr.success('Delete successfully!', 'Successfully');
        this.loadPage(0);
      }).catch(() => {
        this.toastr.error('Error', 'Error Delete');
        this.loadPage(0);
      });
      this.modalService.dismissAll();
      this.student = {};
    }

    this.modalService.dismissAll();
    this.student = {};
  }

  convertToFormData(): FormData {
    const formData = new FormData();
    if(this.action === 'Update')
      formData.append('id', this.student.id || '');

    formData.append('name', this.student.name || '');
    formData.append('email', this.student.email || '');
    formData.append('description', this.student.description || '');
    formData.append('age', this.student.age?.toString() || '0');
    formData.append('address', this.student.address || '');
    formData.append('avatar', this.student.avatar || '');
    formData.append('class_id', this.student.classroom || '');

    return formData;
  }

  //Search 
  searchByName(event: KeyboardEvent): void {
    const searchData = (event.target as HTMLInputElement).value;
    this.searchRequest.name = searchData;
    this.loadPage(0);
  }

  searchByAge(event: KeyboardEvent): void {
    const searchData = (event.target as HTMLInputElement).value;
    this.searchRequest.age = Number.parseInt(searchData);
    this.loadPage(0);
  }

  searchByEmail(event: KeyboardEvent): void {
    const searchData = (event.target as HTMLInputElement).value;
    this.searchRequest.email = searchData;
    this.loadPage(0);
  }

  searchByAddress(event: KeyboardEvent): void {
    const searchData = (event.target as HTMLInputElement).value;
    this.searchRequest.address = searchData;
    this.loadPage(0);
  }

  searchById(event: KeyboardEvent): void {
    const searchData = (event.target as HTMLInputElement).value;
    this.searchRequest.id = searchData;
    this.loadPage(0);
  }

  // watch detail for student
  openDetailStudent(id: string): void {
    this.router.navigate(['student-detail', id]);
  } 

  // set pageSize
  setPageSize(pageSize: number): void {
    if(pageSize < 0)
      return;
    this.pageSize = pageSize;
    this.loadPage(0);
  }

  onPageSize(event: any): void {
    if(event.target.value === '')
      return;
    const num = +event.target.value;
    if(num >= 0)
      this.setPageSize(num);
  }

}
