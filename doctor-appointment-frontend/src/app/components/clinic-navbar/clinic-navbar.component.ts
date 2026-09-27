import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-clinic-navbar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './clinic-navbar.component.html'
})
export class ClinicNavbarComponent {

  constructor(
    public authService: AuthService,
    private router: Router
  ) {}


  logout(): void {

    this.authService.logout();

    this.router.navigate(['/login']);
  }

}