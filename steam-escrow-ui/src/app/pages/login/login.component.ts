import { Component } from '@angular/core';
import {AuthenticationRequest} from '../../services/models/authentication-request';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/services/authentication.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {email: '', password: ''};
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  login() {
    this.errorMsg = [];
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (res) => {
        this.router.navigate(['products']);
      },
      error: (err) => {
        console.log('Error Response:', err); // Log the entire error object
        if (err.error && Array.isArray(err.error.validationErrors)) {
          this.errorMsg = err.error.validationErrors;
        } else if (err.error && err.error.errorMsg) {
          this.errorMsg.push(err.error.errorMsg);
        } else {
          this.errorMsg.push('An unexpected error occurred.');
        }
      }
    });
  }

  register() {
    this.router.navigate(['register']);
  }
}
