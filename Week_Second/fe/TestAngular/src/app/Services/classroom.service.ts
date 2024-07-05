import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../enviroment/enviroment';
import { BehaviorSubject, Observable } from 'rxjs';
import { ClassRoom } from '../Entity/ClassRoom';
import { Student } from '../Entity/Student';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class ClassroomService {

  private classApi = `${environment.apiUrl}/class-room`;
  private classId = new BehaviorSubject<string>('');
  currentId = this.classId.asObservable();

  setClassId(classId: string){
    this.classId.next(classId);
  }

  constructor(private http: HttpClient, private httpService: HttpService) { }

  getClass(pageSize: number, pageNumber: number): Observable<any>{
    var headers = this.httpService.createAuthorizationHeader();
    return this.http.get(`${this.classApi}/find-all?pageSize=${pageSize}&pageNumber=${pageNumber}`, {
      headers
    });
  }

  addClass(classRoom: ClassRoom): Observable<ClassRoom>{
    var headers = this.httpService.createAuthorizationHeader();
    return this.http.post<ClassRoom>(`${this.classApi}/save`, classRoom, {
      headers
    });
  }

  updateClass(classRoom: ClassRoom): Observable<ClassRoom>{
    var headers = this.httpService.createAuthorizationHeader();
    return this.http.put<ClassRoom>(`${this.classApi}/update`, classRoom, {
      headers
    });
  }

  delelteClass(id: string): Observable<Boolean>{
    var headers = this.httpService.createAuthorizationHeader();
    return this.http.delete<Boolean>(`${this.classApi}/delete/${id}`, {
      headers
    });
  }

  findClassById(id: string): Observable<any>{
    var headers = this.httpService.createAuthorizationHeader();
    return this.http.get<any>(`${this.classApi}/${id}`, {
      headers
    });
  }

  removeStudent(id: string): Observable<any>{
    var headers = this.httpService.createAuthorizationHeader();
    return this.http.get<any>(`${this.classApi}/remove-student/${id}`, {
      headers
    });
  }

  addStudentToClass(studentId: string, classId: string): Observable<Boolean>{
    var headers = this.httpService.createAuthorizationHeader();
    return this.http.put<Boolean>(`${this.classApi}/add-student`, null, {
      headers,
      params: new HttpParams()
      .set('student_id', studentId)
      .set('class_id', classId)
    });
  }

  checkEmail(email: string): Observable<Boolean>{
    var headers = this.httpService.createAuthorizationHeader();
    return this.http.get<Boolean>(`${this.classApi}/check-email?email=${email}`, {
      headers
    });
  }

  convertToFormData(s: Student): FormData {
    const formData = new FormData();

    formData.append('id', s.id!);
    formData.append('name', s.name!);
    formData.append('email', s.email!);
    formData.append('description', s.description!);
    formData.append('age', s.age?.toString()!);
    formData.append('address', s.address!);
    formData.append('avatar', s.avatar!);
    formData.append('class_id', s.classRoom!);

    return formData;
  }
}
