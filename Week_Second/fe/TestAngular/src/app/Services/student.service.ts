import { Injectable } from '@angular/core';
import { environment } from '../enviroment/enviroment';
import axios from 'axios';
import { SearchRequest } from '../Entity/SearchRequest';
import { UUID } from 'crypto';
import { Student } from '../Entity/Student';
import { AuthService } from './auth.service';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private studentApi = `${environment.apiUrl}/student`;

  constructor(private http: HttpService) { }

  getListStudents(searchRequest: SearchRequest, pageSize: number, pageNumber: number) {
    var headers = this.http.createAuthorizationHeader();
    return axios.post(`${this.studentApi}/find-criteria?pageSize=${pageSize}&pageNumber=${pageNumber}`, searchRequest, {
      headers
    })
    .then(response => response.data)
    .catch(err => {throw err});
  }

  addStudent(stduentForm: FormData) {
    var headers = this.http.createAuthorizationHeader();
    return axios.post(`${this.studentApi}/save`, stduentForm, headers)
    .then(response => response.data)
    .catch(err => {throw err});
  }

  updateStudent(stduentForm: FormData) {
    var headers = this.http.createAuthorizationHeader();
    return axios.put(`${this.studentApi}/update`, stduentForm, {
      headers
    })
    .then(response => response.data)
    .catch(err => {throw err});
  }

  deleteStudent(id: string) : Promise<void> {
    var headers = this.http.createAuthorizationHeader();
    return axios.delete(`${this.studentApi}/delete/${id}`, headers)
    .then(response => response.data)
    .catch(err => {throw err});
  }

  checExistsEmail(email: string){
    var headers = this.http.createAuthorizationHeader();
    return axios.get(`${this.studentApi}/check-email/${email}`, headers);
  }
}
