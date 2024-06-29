import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ClassRoom } from '../../../Entity/ClassRoom';
import { ClassroomService } from '../../../Services/classroom.service';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { NgForm } from '@angular/forms'; // Đảm bảo đã import NgForm từ @angular/forms
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-classroom',
  standalone: true,
  imports: [CommonModule, FormsModule, ToastrModule],
  templateUrl: './classroom.component.html',
  styleUrl: './classroom.component.scss',
  providers: []
})
export class ClassroomComponent implements OnInit{
  
  data: ClassRoom[] = [];
  currentPage: number = 0;
  pageSize: number = 8;
  totalPages: number = 0;
  private modalService = inject(NgbModal);
  closeResult = '';
  classroom: ClassRoom = {
    id: '',
    className: '',
    email: '',
    establishmentDate: null
  };
  @ViewChild('ngForm') ngForm: NgForm | undefined; // Khai báo ngForm như một ViewChild
  test: Number = 1;
  action: string = 'Save';


    constructor(private classService: ClassroomService, 
      private toastr: ToastrService,
      private router: Router
    ){

    }

    ngOnInit() {
      this.loadPage(this.currentPage);
    }

    loadPage(page: number): void {
      this.classService.getClass(this.pageSize, page).subscribe(data => {
        this.data = data.content;
        this.totalPages = data.totalPages;
        this.currentPage = page;
      })
    }

    nextPage(): void {
      if(this.currentPage <= this.totalPages - 1) 
        this.loadPage(this.currentPage + 1);
    }

    prePage(): void {
      if(this.currentPage !== 0)
        this.loadPage(this.currentPage - 1);
    }

    addClass(): void {
      if (this.ngForm?.form?.valid) {
        this.classService.addClass(this.classroom).subscribe({
          next: () => {
            this.loadPage(0);
            this.toastr.success('ClassRoom add successfully!', 'Success');
          },error: () => {
            this.loadPage(0);
            this.toastr.error('ClassRoom add fail!', 'Error');
        }});
      } else {
        this.toastr.error('Form is invalid', 'Error');
      }
    }

    updateClass(): void{
      if(this.ngForm?.form?.valid) {
        this.classService.updateClass(this.classroom).subscribe({
          next: () => {
            this.test = 0;
            this.toastr.success('ClassRoom updated successfully!', 'Success');
          },error: () => {
            this.test = 1;
            this.toastr.error('ClassRoom updated failed!', 'Error');
          }
        });
      }else {
        this.toastr.error('Form is invalid', 'Error');
      }
      this.convertDefaultClass();
      this.modalService.dismissAll();
      this.loadPage(0);
    }

    deleteClass(id: string): void {
      this.classService.delelteClass(id).subscribe({
        next: () => {
          this.test = 0;
          this.notification();
        },error: () => {
          this.test = 1;
          this.notification();
      }});
      this.convertDefaultClass();
      this.modalService.dismissAll();
    }

    confirmDelete(id: string, className: string, email: string, establishmentDate: Date | null): void {
      this.classroom.id = id;
      this.classroom.className = className;
      this.classroom.email = email;
      this.classroom.establishmentDate = establishmentDate;
    }

    notification(): void {
      if(this.test === 0)
        this.toastr.success('ClassRoom deleted successfully!', 'Success');
      else
        this.toastr.error('ClassRoom deleted failed!', 'Error');
      this.loadPage(0);
    }


    open(content: TemplateRef<any>) {
      this.action = 'Save';
      this.convertDefaultClass();
      this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        },
      );
    }

    openFormDeleteClass(content: TemplateRef<any>, id: string, className: string, email: string, establishmentDate: Date | null) : void{
      this.action = 'Delete';
      this.classroom.id = id;
      this.classroom.className = className;
      this.classroom.email = email;
      this.classroom.establishmentDate = establishmentDate;
      this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' });
    }

    openFormUpdateClass(content: TemplateRef<any>, id: string, className: string, email: string, establishmentDate: Date | null) : void{
      this.action = 'Update';
      this.classroom.id = id;
      this.classroom.className = className;
      this.classroom.email = email;
      this.classroom.establishmentDate = establishmentDate;
      this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' });
    }

    convertDefaultClass() : void {
      this.classroom.id =  '',
      this.classroom.className = '',
      this.classroom.email = '',
      this.classroom.establishmentDate = null
    }

    private getDismissReason(reason: any): string {
      switch (reason) {
        case ModalDismissReasons.ESC:
          return 'by pressing ESC';
        case ModalDismissReasons.BACKDROP_CLICK:
          return 'by clicking on a backdrop';
        default:
          return `with: ${reason}`;
      }
    }

    // See list student
    openListStudent(id: string): void {
      this.router.navigate(['class', id]);
    }
}
