import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { AuthService } from '../../../../services/auth.service';
import { AppointmentService } from '../../../../services/appointment.service';
import { UserResponse } from '../../../../models/user-response';
import { Appointment } from '../../../../models/appointment';

@Component({
  selector: 'app-doctor-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './doctor-dashboard.component.html',
  styleUrl: './doctor-dashboard.component.css'
})
export class DoctorDashboardComponent implements OnInit {

  user: UserResponse | null = null;

  appointments: Appointment[] = [];

  loading = true;

  appointmentsLoading = true;

  errorMessage = '';

  totalToday = 0;

  pendingToday = 0;

  confirmedToday = 0;

  completedToday = 0;

  cancelledToday = 0;


  constructor(
    private authService: AuthService,
    private appointmentService: AppointmentService
  ) {}


  ngOnInit(): void {

    this.loadDashboard();

  }


  // =========================================
  // LOAD DASHBOARD
  // =========================================

  loadDashboard(): void {

    this.loading = true;

    this.errorMessage = '';

    this.authService.getCurrentUser().subscribe({

      next: (user: UserResponse) => {

        this.user = user;

        this.loading = false;

        this.loadTodayAppointments();

      },

      error: () => {

        this.loading = false;

        this.errorMessage =
          'Unable to load your profile.';

      }

    });

  }


  // =========================================
  // LOAD TODAY'S APPOINTMENTS
  // =========================================

  loadTodayAppointments(): void {

    this.appointmentsLoading = true;

    this.appointmentService
      .getDoctorAppointments()
      .subscribe({

        next: (appointments: Appointment[]) => {

          this.appointments = appointments;

          this.calculateTodayStatistics();

          this.appointmentsLoading = false;

        },

        error: (error: any) => {

          console.error(
            'Failed to load doctor appointments:',
            error
          );

          this.appointmentsLoading = false;

        }

      });

  }


  // =========================================
  // CALCULATE TODAY'S STATISTICS
  // =========================================

  calculateTodayStatistics(): void {

    const today =
      new Date().toISOString().split('T')[0];


    const todayAppointments =
      this.appointments.filter(
        appointment =>
          appointment.appointmentDate === today
      );


    this.totalToday =
      todayAppointments.length;


    this.pendingToday =
      todayAppointments.filter(
        appointment =>
          appointment.status === 'PENDING'
      ).length;


    this.confirmedToday =
      todayAppointments.filter(
        appointment =>
          appointment.status === 'CONFIRMED'
      ).length;


    this.completedToday =
      todayAppointments.filter(
        appointment =>
          appointment.status === 'COMPLETED'
      ).length;


    this.cancelledToday =
      todayAppointments.filter(
        appointment =>
          appointment.status === 'CANCELLED'
      ).length;

  }


  // =========================================
  // LOGOUT
  // =========================================

  logout(): void {

    this.authService.logout();

    window.location.href = '/';

  }

}