import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { AppointmentService } from '../../../../services/appointment.service';
import { Appointment } from '../../../../models/appointment';

@Component({
  selector: 'app-patient-appointments',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './appointments.component.html',
  styleUrl: './appointments.component.css'
})
export class AppointmentsComponent implements OnInit {

  appointments: Appointment[] = [];

  loading = false;

  errorMessage = '';

  successMessage = '';

  processingAppointmentId: number | null = null;


  constructor(
    private appointmentService: AppointmentService
  ) {}


  ngOnInit(): void {
    this.loadAppointments();
  }


  // =========================================================
  // LOAD PATIENT APPOINTMENTS
  // =========================================================

  loadAppointments(): void {

    this.loading = true;

    this.errorMessage = '';

    this.appointmentService
      .getPatientAppointments()
      .subscribe({

        next: (appointments: Appointment[]) => {

          this.appointments = appointments;

          this.loading = false;
        },

        error: (error: any) => {

          this.loading = false;

          this.errorMessage =
            error.error?.message ||
            'Unable to load appointments.';
        }

      });
  }


  // =========================================================
  // CANCEL APPOINTMENT
  // =========================================================

  cancelAppointment(
    appointmentId: number
  ): void {

    if (
      appointmentId === undefined ||
      appointmentId === null ||
      Number.isNaN(Number(appointmentId))
    ) {

      this.errorMessage =
        'Appointment ID is missing.';

      return;
    }


    if (
      !confirm(
        'Are you sure you want to cancel this appointment?'
      )
    ) {
      return;
    }


    this.processingAppointmentId =
      Number(appointmentId);

    this.errorMessage = '';

    this.successMessage = '';


    this.appointmentService
      .cancelPatientAppointment(
        Number(appointmentId)
      )
      .subscribe({

        next: () => {

          this.processingAppointmentId = null;

          this.successMessage =
            'Appointment cancelled successfully.';

          this.loadAppointments();
        },

        error: (error: any) => {

          this.processingAppointmentId = null;

          this.errorMessage =
            error.error?.message ||
            'Unable to cancel appointment.';
        }

      });
  }


  // =========================================================
  // STATUS CSS
  // =========================================================

  getStatusClass(
    status: string
  ): string {

    switch (status) {

      case 'CONFIRMED':
        return 'confirmed';

      case 'COMPLETED':
        return 'completed';

      case 'CANCELLED':
        return 'cancelled';

      case 'PENDING':
      default:
        return 'pending';
    }
  }
}