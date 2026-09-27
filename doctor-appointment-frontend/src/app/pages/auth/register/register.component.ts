import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../../services/auth.service';
import { RegisterRequest } from '../../../models/register-request';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './register.component.html'
})
export class RegisterComponent {

  registerData: RegisterRequest = {
    name: '',
    email: '',
    phone: '',
    password: '',
    role: 'PATIENT'
  };

  errorMessage = '';

  successMessage = '';

  loading = false;


  constructor(
    private authService: AuthService,
    private router: Router
  ) {}


  register(): void {

    this.errorMessage = '';

    this.successMessage = '';


    if (
      !this.registerData.name ||
      !this.registerData.email ||
      !this.registerData.phone ||
      !this.registerData.password
    ) {

      this.errorMessage =
        'Please fill in all fields.';

      return;
    }


    if (this.registerData.password.length < 6) {

      this.errorMessage =
        'Password must be at least 6 characters.';

      return;
    }


    this.loading = true;


    this.authService
      .register(this.registerData)
      .subscribe({

        next: () => {

          this.loading = false;

          this.successMessage =
            'Registration successful! Redirecting to login...';


          setTimeout(() => {

            this.router.navigate([
              '/login'
            ]);

          }, 1500);

        },


        error: (error: any) => {

          console.error(
            'REGISTRATION FAILED:',
            error
          );

          this.loading = false;

          this.errorMessage =
            error.error?.message ||
            'Registration failed. Please try again.';
        }

      });

  }

}