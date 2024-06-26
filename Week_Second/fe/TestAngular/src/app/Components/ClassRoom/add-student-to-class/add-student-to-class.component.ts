import { Component, OnInit } from '@angular/core';
import { ClassroomService } from '../../../Services/classroom.service';
import { Student } from '../../../Entity/Student';
import { StudentService } from '../../../Services/student.service';
import { ToastrService } from 'ngx-toastr';
import { SearchRequest } from '../../../Entity/SearchRequest';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';

@Component({
  selector: 'app-add-student-to-class',
  standalone: true,
  imports: [CommonModule, NgxPaginationModule],
  templateUrl: './add-student-to-class.component.html',
  styleUrl: './add-student-to-class.component.scss'
})
export class AddStudentToClassComponent implements OnInit{

  classId?: string;
  listStudent: Student[] = [];
  searchRequest!: SearchRequest;
  pageSize: number = 8;
  totalPages!: number;
  totalElements!: number;
  currentPage: number = 1;
  checkName: boolean = false;

  constructor(private classRoomService: ClassroomService,
    private studentService: StudentService,
    private toatr: ToastrService,
    private route: Router
  ){

  }

  ngOnInit(): void {
    this.classRoomService.currentId.subscribe(data => this.classId = data); 
    this.loadPage(1);       
  }

  addStudentToClass(studentId: string): void {
    
    this.classRoomService.addStudentToClass(studentId, this.classId!)
    .subscribe(() => {
      this.toatr.success('Set class of student successfully', 'Success');
      this.route.navigate(['/class', this.classId]);
    }, () => {
      this.toatr.error('Could not add student to class', 'Error');
    });

    
  }

  loadPage(page: number): void{
    this.classRoomService.currentId.subscribe(data => this.classId = data); 
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
      this.listStudent = [];
      throw err;
    });
  }

  onChangPage(page: number): void {
    this.loadPage(page);
    this.currentPage = page;
  }

  // nextPage(): void{
  //   if(this.currentPage <= this.totalPages - 1)
  //     this.loadPage(this.currentPage + 1);
  // }

  // prevPage(): void{
  //   if(this.currentPage !== 0)
  //     this.loadPage(this.currentPage - 1);
  // }

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
