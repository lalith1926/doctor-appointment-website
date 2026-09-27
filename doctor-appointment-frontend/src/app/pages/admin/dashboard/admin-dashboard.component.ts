import {
  Component,
  OnInit
} from '@angular/core';

import {
  CommonModule
} from '@angular/common';

import {
  RouterLink
} from '@angular/router';

import {
  AdminService,
  AdminDashboardResponse
} from '../../../services/admin.service';


@Component({

  selector: 'app-admin-dashboard',

  standalone: true,

  imports: [
    CommonModule,
    RouterLink
  ],

  templateUrl: './admin-dashboard.component.html',

  styleUrl: './admin-dashboard.component.css'

})
export class AdminDashboardComponent
  implements OnInit {


  // =========================================================
  // DASHBOARD DATA
  // =========================================================

  doctorCount = 0;

  patientCount = 0;

  appointmentCount = 0;


  // =========================================================
  // UI STATE
  // =========================================================

  loading = true;

  errorMessage = '';


  constructor(
    private adminService: AdminService
  ) {}


  // =========================================================
  // INITIAL LOAD
  // =========================================================

  ngOnInit(): void {

    this.loadDashboardStats();

  }


  // =========================================================
  // LOAD DASHBOARD STATISTICS
  // =========================================================

  loadDashboardStats(): void {

    this.loading = true;

    this.errorMessage = '';


    this.adminService
      .getDashboardStats()
      .subscribe({

        next: (
          stats: AdminDashboardResponse
        ) => {

          this.doctorCount =
            stats.doctorCount;

          this.patientCount =
            stats.patientCount;

          this.appointmentCount =
            stats.appointmentCount;

          this.loading = false;

        },


        error: (error: any) => {

          console.error(
            'Unable to load dashboard statistics:',
            error
          );

          this.loading = false;

          this.errorMessage =
            error.error?.message ||
            'Unable to load dashboard statistics. Please try again.';

        }

      });

  }

}