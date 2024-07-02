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
  import { Router } from '@angular/router';
  import { NgxPaginationModule } from 'ngx-pagination';
  import { NgIconComponent } from '@ng-icons/core';


  @Component({
    selector: 'app-student',
    standalone: true,
    imports: [CommonModule, FormsModule, ToastrModule, NgxPaginationModule, NgIconComponent ],
    templateUrl: './student.component.html',
    styleUrl: './student.component.scss'
  })
  export class StudentComponent implements OnInit{

    listStudent: Student[] = [];
    currentPage: number = 1;
    pageSize: number = 12;
    totalPages!: number;
    totalElements!: number;
    action: string = 'Add';
    student: Student = {id : '', name : '', email : ''};
    searchRequest: SearchRequest ={} ;
    private modalService = inject(NgbModal);
    listClass: ClassRoom[] = [];
    pageSizeClass: number = 0;
    @ViewChild('ngForm') ngForm: NgForm | undefined; // Khai báo ngForm như một ViewChild
    test: number = 0;
    checkName: boolean = true;
    testEmail: boolean = true;
    existsEmail: boolean = false;
    checkEmptyEmail: boolean = true;
    checkAge: boolean = true;
    t: boolean = false;

    constructor(private studentService: StudentService,
      private classroomService: ClassroomService,
      private toastr: ToastrService,
      private router : Router
    ){
    }

    ngOnInit(){
      this.loadPage(1);
      this.loadClassroom();
    }

    loadClassroom() : void{
      this.classroomService.getClass(1, 1).subscribe(data => {
        this.pageSizeClass = data.totalPages;
      });
      this.classroomService.getClass(this.pageSizeClass + 1, 1).subscribe(next => {
        this.listClass = next.content;
      });
    }


    onPageChange(page: number) {
      this.currentPage = page;
      this.loadPage(page);
    }

    loadPage(page: number): void{
      if(this.searchRequest === undefined)
        this.searchRequest = {};
      this.studentService.getListStudents(this.searchRequest!, this.pageSize, page)
      .then(data => {
          this.listStudent = data.content;
          this.totalPages = data.totalPages;
          this.currentPage = page;
          this.totalElements = data.totalElements;
    })
      .catch(err => {
        this.listStudent = []
        throw err;
      });
    }
    //check empty
    onChangeFieldName(value: string): void {
      this.checkName = value.trim().length > 0;
    }

    // nextPage(): void{
    //   if(this.currentPage <= this.totalPages - 1)
    //     this.loadPage(this.currentPage + 1);
    // }

    // prevPage(): void{
    //   if(this.currentPage !== 0)
    //     this.loadPage(this.currentPage - 1);
    // }

    onFileSelected(event: any): void{
      const file: File = event.target.files[0];
      if(file)
        this.student.avatar = file;
    }
    // Open Modal 
    openToAdd(content: TemplateRef<any>) {
      this.assignAll();
      this.student = {};
      this.action = 'Add';
      this.loadClassroom();
      this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result;
    }

    openToUpdate(content: TemplateRef<any>, s : Student) {
      this.assignAll();
      this.loadClassroom();
      this.action = 'Update';   
      this.student = s;
      this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result;
    }

    openToDelete(content: TemplateRef<any>, s: Student) {
      this.assignAll();
      this.action = 'Delete';
      this.student = s;
      this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result;
    }

    //check valid
    validEmail($event: any) : boolean{
      this.checkEmptyEmail = true;
      const email = ($event.target as HTMLInputElement).value;
      this.checkExistEmail();
      if(email.match(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/)){
        this.testEmail = true;
        return true;
      }
      this.testEmail = false;
      return false;
    }

    assignAll() : void {
      this.testEmail = true;
      this.existsEmail = false;
      this.checkName = true;
      this.checkEmptyEmail = true;
      this.checkAge = true;
    }

    checkExistEmail() : boolean {
      
      try {
        this.studentService.checExistsEmail(this.student.email!).then(() => {
            this.existsEmail = true;
        }).catch(() => {
          this.existsEmail = false;
        });
        return true;
      } catch (error) {
        return false;
      }
    }

    checkEmptyFields() : boolean {
      if(this.student.name === undefined || this.student.name?.trim() === '')
        {
          this.checkName = false;
          this.toastr.error('Error The name field cannot be empty!', 'Error');
          return false;
        }
        if(this.student.email === undefined || this.student.email?.trim() === '')
        {
            this.checkEmptyEmail = false;
            this.toastr.error('Error The email field cannot be empty!', 'Error');
            return false;
        }

        if(!this.testEmail)
        {
          this.toastr.error('Error the email not valid!', 'Error');
          return false;
        }
        
          return true;
    }

    checkValueAge(event: KeyboardEvent): void {
      const input = event.target as HTMLInputElement;
      const value = parseInt(input.value);

      if (!isNaN(value)) {
        this.checkAge = value >= 0;
      } else {
        this.checkAge = false;
      }
    }

    
    //action
    addStudent() : void {
        if(!this.checkEmptyFields())
          return;

        this.studentService.addStudent(this.convertToFormData())
        .then(() => {
          this.toastr.success('Add successfully!', 'Successfully');
          this.loadPage(1);
          this.student = {};
          this.modalService.dismissAll(); 
        }).catch(() => {
          this.toastr.error('Error adding', 'Error');
          this.loadPage(1);
        });
  
    }

    huhu(t : boolean) : void {
      if(t)
        this.student = {};
    }

    updateStudent(): void {   
      if(!this.checkEmptyFields())
        return;
        this.studentService.updateStudent(this.convertToFormData())
        .then(() => {
          this.toastr.success('Update successfully!', 'Successfully');
          this.loadPage(1);
          this.modalService.dismissAll();
        }).catch(() => {
          this.toastr.error('Error', 'Error Updating');
          this.loadPage(1);
        });
    }

    deleteStudent(): void {
        this.studentService.deleteStudent(this.student.id!)
        .then(() => {
          this.toastr.success('Delete successfully!', 'Successfully');
          this.loadPage(1);
        }).catch(() => {
          this.toastr.error('Error', 'Error Delete');
          this.loadPage(1);
        });
        this.modalService.dismissAll();
        this.student = {};

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
      formData.append('age', this.student.age?.toString() || '');
      formData.append('address', this.student.address || '');
      formData.append('avatar', this.student.avatar || '');
      formData.append('class_id', this.student.classroom || '');

      return formData;
    }

    //Search 
    searchByName(event: KeyboardEvent): void {
      const searchData = (event.target as HTMLInputElement).value;
      this.searchRequest.name = searchData;
      this.loadPage(1);
    }

    searchByAge(event: KeyboardEvent): void {
      const searchData = (event.target as HTMLInputElement).value;
      this.searchRequest.age = Number.parseInt(searchData);
      this.loadPage(1);
    }

    searchByEmail(event: KeyboardEvent): void {
      const searchData = (event.target as HTMLInputElement).value;
      this.searchRequest.email = searchData;
      this.loadPage(1);
    }

    searchByAddress(event: KeyboardEvent): void {
      const searchData = (event.target as HTMLInputElement).value;
      this.searchRequest.address = searchData;
      this.loadPage(1);
    }

    searchById(event: KeyboardEvent): void {
      const searchData = (event.target as HTMLInputElement).value;
      this.searchRequest.id = searchData;
      this.loadPage(1);
    }

    searchByDateStart(event: any): void {
      const searchData = event.target.value;
      this.searchRequest.dateStart = searchData;
      this.loadPage(1);
    }

    searchByDateEnd(event: any): void {
      const searchData = event.target.value;
      this.searchRequest.dateEnd = searchData;
      this.loadPage(1);
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
      this.loadPage(1);
    }

    onPageSize(event: any): void {
      if(event.target.value === '')
        return;
      const num = +event.target.value;
      if(num >= 0)
        this.setPageSize(num);
    }

  }
