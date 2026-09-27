import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { AppointmentService } from '../../../services/appointment.service';
import { Appointment } from '../../../models/appointment';

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

    this.successMessage = '';

    this.appointmentService
      .getPatientAppointments()
      .subscribe({

        next: (appointments: Appointment[]) => {

          console.log(
            'PATIENT APPOINTMENTS:',
            appointments
          );

          this.appointments = appointments;

          this.loading = false;
        },

        error: (error: any) => {

          this.loading = false;

          console.error(
            'Failed to load patient appointments:',
            error
          );

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

    console.log(
      'PATIENT CANCEL: appointmentId =',
      appointmentId
    );


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

        next: (updatedAppointment: Appointment) => {

          console.log(
            'PATIENT CANCEL SUCCESS:',
            updatedAppointment
          );

          this.processingAppointmentId =
            null;

          this.successMessage =
            'Appointment cancelled successfully.';

          this.loadAppointments();
        },

        error: (error: any) => {

          this.processingAppointmentId =
            null;

          console.error(
            'PATIENT CANCEL FAILED:',
            error
          );

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
        return 'bg-success';

      case 'COMPLETED':
        return 'bg-primary';

      case 'CANCELLED':
        return 'bg-danger';

      case 'PENDING':
      default:
        return 'bg-warning text-dark';
    }
  }
}