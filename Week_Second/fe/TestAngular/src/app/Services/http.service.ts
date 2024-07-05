import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private authService: AuthService) { }

  createAuthorizationHeader(): { [header: string]: string } {
    const token = this.authService.getToken();
    return token ? { 'Authorization': 'Bearer ' + token } : {};
  }
}
