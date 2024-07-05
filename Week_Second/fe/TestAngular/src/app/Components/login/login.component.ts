import { Component } from '@angular/core';
import { Account } from '../../Entity/Account';
import { AuthService } from '../../Services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { response } from 'express';
import { Router } from '@angular/router';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

    account = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })

    constructor(private authService: AuthService,
      private toastr: ToastrService,
      private route: Router,
      private fb: FormBuilder
    ){

    }

    onSubmit() : void {
      console.log(this.account);
      
      this.authService.login(this.account.value)
      .subscribe(
        response => {
          this.authService.setToken(response.access_token);
          this.toastr.success('Login successful', 'Success');
          this.route.navigate(['student']);
        }, err => {
          this.toastr.error('Login failed', 'Error');
          console.log(err);
          
        });
    }
}
