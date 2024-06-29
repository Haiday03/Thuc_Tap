import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../enviroment/enviroment';
import { Observable } from 'rxjs';
import { ClassRoom } from '../Entity/ClassRoom';

@Injectable({
  providedIn: 'root'
})
export class ClassroomService {

  private classApi = `${environment.apiUrl}/class-room`;

  constructor(private http: HttpClient) { }

  getClass(pageSize: number, pageNumber: number): Observable<any>{
    return this.http.get(`${this.classApi}/find-all?pageSize=${pageSize}&pageNumber=${pageNumber}`);
  }

  addClass(classRoom: ClassRoom): Observable<ClassRoom>{
    return this.http.post<ClassRoom>(`${this.classApi}/save`, classRoom);
  }

  updateClass(classRoom: ClassRoom): Observable<ClassRoom>{
    return this.http.put<ClassRoom>(`${this.classApi}/update`, classRoom);
  }

  delelteClass(id: string): Observable<Boolean>{
    return this.http.delete<Boolean>(`${this.classApi}/delete/${id}`);
  }
}
