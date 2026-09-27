import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../../services/auth.service';

import { LoginRequest } from '../../../models/login-request';
import { AuthResponse } from '../../../models/auth-response';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  loginData: LoginRequest = {
    email: '',
    password: ''
  };

  loading = false;

  errorMessage = '';


  constructor(
    private authService: AuthService,
    private router: Router
  ) {}


  login(): void {

    if (
      !this.loginData.email ||
      !this.loginData.password
    ) {

      this.errorMessage =
        'Please enter your email and password.';

      return;
    }


    this.loading = true;

    this.errorMessage = '';


    this.authService
      .login(this.loginData)
      .subscribe({

        next: (
          response: AuthResponse
        ) => {

          console.log(
            'LOGIN SUCCESS:',
            response
          );

          this.loading = false;


          /*
           * AuthResponse does not contain role.
           *
           * Ask the backend who is currently logged in.
           */

          this.authService
            .getCurrentUser()
            .subscribe({

              next: (user) => {

                console.log(
                  'CURRENT USER:',
                  user
                );


                if (user.role === 'DOCTOR') {

                  this.router.navigate([
                    '/doctor/dashboard'
                  ]);

                } else {

                  this.router.navigate([
                    '/patient/dashboard'
                  ]);

                }

              },

              error: (error: any) => {

                console.error(
                  'FAILED TO GET CURRENT USER:',
                  error
                );

                this.loading = false;

                this.errorMessage =
                  'Login succeeded, but we could not determine your account type.';
              }

            });

        },

        error: (error: any) => {

          console.error(
            'LOGIN FAILED:',
            error
          );

          this.loading = false;

          this.errorMessage =
            error.error?.message ||
            'Invalid email or password.';
        }

      });
  }

}