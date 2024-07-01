import { Injectable } from '@angular/core';
import { environment } from '../enviroment/enviroment';
import axios from 'axios';
import { SearchRequest } from '../Entity/SearchRequest';
import { UUID } from 'crypto';
import { Student } from '../Entity/Student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private studentApi = `${environment.apiUrl}/student`;

  constructor() { }

  getListStudents(searchRequest: SearchRequest, pageSize: number, pageNumber: number) {
    return axios.post(`${this.studentApi}/find-criteria?pageSize=${pageSize}&pageNumber=${pageNumber}`, searchRequest)
    .then(response => response.data)
    .catch(err => {throw err});
  }

  addStudent(stduentForm: FormData) {
    return axios.post(`${this.studentApi}/save`, stduentForm)
    .then(response => response.data)
    .catch(err => {throw err});
  }

  updateStudent(stduentForm: FormData) {
    return axios.put(`${this.studentApi}/update`, stduentForm)
    .then(response => response.data)
    .catch(err => {throw err});
  }

  deleteStudent(id: string) : Promise<void> {
    return axios.delete(`${this.studentApi}/delete/${id}`)
    .then(response => response.data)
    .catch(err => {throw err});
  }

  checExistsEmail(email: string){
    return axios.get(`${this.studentApi}/check-email/${email}`);
  }
}
