import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { AppointmentService } from '../../../services/appointment.service';
import { Appointment } from '../../../models/appointment';

@Component({
  selector: 'app-doctor-appointments',
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
  // LOAD DOCTOR APPOINTMENTS
  // =========================================================

  loadAppointments(): void {

    this.loading = true;

    this.errorMessage = '';

    this.appointmentService
      .getDoctorAppointments()
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
  // CONFIRM APPOINTMENT
  // =========================================================

  confirmAppointment(
    appointment: Appointment
  ): void {

    const appointmentId =
      (appointment as any).appointmentId;


    if (
      appointmentId === undefined ||
      appointmentId === null ||
      Number.isNaN(Number(appointmentId))
    ) {

      this.errorMessage =
        'Appointment ID is missing.';

      return;
    }


    this.processingAppointmentId =
      Number(appointmentId);

    this.errorMessage = '';

    this.successMessage = '';


    this.appointmentService
      .confirmAppointment(
        Number(appointmentId)
      )
      .subscribe({

        next: () => {

          this.processingAppointmentId = null;

          this.successMessage =
            'Appointment confirmed successfully.';

          this.loadAppointments();
        },

        error: (error: any) => {

          this.processingAppointmentId = null;

          this.errorMessage =
            error.error?.message ||
            'Unable to confirm appointment.';
        }

      });
  }


  // =========================================================
  // CANCEL APPOINTMENT
  // =========================================================

  cancelAppointment(
    appointment: Appointment
  ): void {

    const appointmentId =
      (appointment as any).appointmentId;


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
      .cancelDoctorAppointment(
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
  // COMPLETE APPOINTMENT
  // =========================================================

  completeAppointment(
    appointment: Appointment
  ): void {

    const appointmentId =
      (appointment as any).appointmentId;


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
        'Mark this appointment as completed?'
      )
    ) {
      return;
    }


    this.processingAppointmentId =
      Number(appointmentId);

    this.errorMessage = '';

    this.successMessage = '';


    this.appointmentService
      .completeAppointment(
        Number(appointmentId)
      )
      .subscribe({

        next: () => {

          this.processingAppointmentId = null;

          this.successMessage =
            'Appointment completed successfully.';

          this.loadAppointments();
        },

        error: (error: any) => {

          this.processingAppointmentId = null;

          this.errorMessage =
            error.error?.message ||
            'Unable to complete appointment.';
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