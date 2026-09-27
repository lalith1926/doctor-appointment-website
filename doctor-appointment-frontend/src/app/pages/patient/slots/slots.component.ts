import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  FormsModule
} from '@angular/forms';

import {
  RouterLink
} from '@angular/router';

import {
  SlotService
} from '../../../services/slot.service';

import {
  AppointmentService
} from '../../../services/appointment.service';

import {
  DoctorService
} from '../../../services/doctor.service';

import {
  AvailableSlot
} from '../../../models/available-slot';

import {
  Appointment
} from '../../../models/appointment';

import {
  Doctor
} from '../../../models/doctor';


@Component({
  selector: 'app-slots',

  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],

  templateUrl: './slots.component.html',

  styleUrl: './slots.component.css'
})
export class SlotsComponent implements OnInit {


  // =========================================================
  // DOCTORS
  // =========================================================

  doctors: Doctor[] = [];

  selectedDoctorId: number | null = null;

  doctorsLoading = false;


  // =========================================================
  // DATE
  // =========================================================

  selectedDate = '';


  // =========================================================
  // SLOTS
  // =========================================================

  slots: AvailableSlot[] = [];

  loading = false;

  bookingSlotId: number | null = null;


  // =========================================================
  // MESSAGES
  // =========================================================

  errorMessage = '';

  successMessage = '';


  constructor(
    private slotService: SlotService,

    private appointmentService:
      AppointmentService,

    private doctorService:
      DoctorService
  ) {}


  // =========================================================
  // INIT
  // =========================================================

  ngOnInit(): void {

    const today = new Date();

    this.selectedDate =
      today.toISOString().split('T')[0];

    this.loadDoctors();
  }


  // =========================================================
  // LOAD ACTIVE DOCTORS
  // =========================================================

  loadDoctors(): void {

    this.doctorsLoading = true;

    this.errorMessage = '';

    this.doctorService
      .getDoctors()
      .subscribe({

        next: (
          doctors: Doctor[]
        ) => {

          console.log(
            'Active doctors:',
            doctors
          );

          this.doctors = doctors;

          this.doctorsLoading = false;


          // Select the first available doctor
          if (this.doctors.length > 0) {

            this.selectedDoctorId =
              this.doctors[0].d_id;

            this.loadSlots();

          } else {

            this.selectedDoctorId = null;

            this.slots = [];

          }
        },


        error: (error: any) => {

          console.error(
            'Unable to load doctors:',
            error
          );

          this.doctorsLoading = false;

          this.errorMessage =
            error.error?.message ||
            'Unable to load doctors.';
        }

      });

  }


  // =========================================================
  // DOCTOR CHANGED
  // =========================================================

  onDoctorChange(): void {

    this.errorMessage = '';

    this.successMessage = '';

    this.slots = [];


    if (!this.selectedDoctorId) {

      return;
    }


    this.loadSlots();

  }


  // =========================================================
  // LOAD SLOTS
  // =========================================================

  loadSlots(): void {

    if (!this.selectedDate) {

      this.errorMessage =
        'Please select a date.';

      return;
    }


    if (!this.selectedDoctorId) {

      this.errorMessage =
        'Please select a doctor.';

      return;
    }


    this.loading = true;

    this.errorMessage = '';

    this.successMessage = '';

    this.slots = [];


    this.slotService
      .getSlots(
        this.selectedDoctorId,
        this.selectedDate
      )
      .subscribe({

        next: (
          slots: AvailableSlot[]
        ) => {

          this.slots =
            slots.filter(
              slot => !slot.booked
            );

          this.loading = false;
        },


        error: (error: any) => {

          console.error(
            'Unable to load slots:',
            error
          );

          this.loading = false;

          this.errorMessage =
            error.error?.message ||
            'Unable to load available slots.';
        }

      });

  }


  // =========================================================
  // BOOK APPOINTMENT
  // =========================================================

  bookAppointment(
    slot: AvailableSlot
  ): void {

    if (slot.booked) {

      this.errorMessage =
        'This slot is already booked.';

      return;
    }


    if (this.bookingSlotId !== null) {

      return;
    }


    this.bookingSlotId = slot.id;

    this.errorMessage = '';

    this.successMessage = '';


    this.appointmentService
      .bookAppointment(slot.id)
      .subscribe({

        next: (
          appointment: Appointment
        ) => {

          console.log(
            'Appointment booked:',
            appointment
          );

          this.bookingSlotId = null;

          this.successMessage =
            'Appointment booked successfully!';


          this.slots =
            this.slots.filter(
              s => s.id !== slot.id
            );

        },


        error: (error: any) => {

          console.error(
            'Unable to book appointment:',
            error
          );

          this.bookingSlotId = null;

          this.errorMessage =
            error.error?.message ||
            'Unable to book appointment.';
        }

      });

  }


  // =========================================================
  // SELECTED DOCTOR
  // =========================================================

  get selectedDoctor(): Doctor | null {

    if (!this.selectedDoctorId) {

      return null;
    }


    return this.doctors.find(
      doctor =>
        doctor.d_id ===
        this.selectedDoctorId
    ) || null;

  }

}