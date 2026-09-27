import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { AuthService } from '../../../../services/auth.service';
import { UserResponse } from '../../../../models/user-response';

@Component({
  selector: 'app-patient-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './patient-dashboard.component.html'
})
export class PatientDashboardComponent
  implements OnInit {

  user: UserResponse | null = null;

  loading = true;

  errorMessage = '';


  constructor(
    private authService: AuthService
  ) {}


  ngOnInit(): void {

    this.authService
      .getCurrentUser()
      .subscribe({

        next: (
          user: UserResponse
        ) => {

          this.user = user;

          this.loading = false;
        },

        error: (error: any) => {

          this.loading = false;

          this.errorMessage =
            'Unable to load your profile.';
        }

      });
  }


  logout(): void {

    this.authService.logout();

    window.location.href = '/';
  }

}