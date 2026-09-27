import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { SlotService } from '../../../services/slot.service';
import { AvailableSlot } from '../../../models/available-slot';

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

  selectedDate = '';

  startTime = '';

  endTime = '';

  slots: AvailableSlot[] = [];

  loading = false;

  creatingSlot = false;

  errorMessage = '';

  successMessage = '';


  constructor(
    private slotService: SlotService
  ) {}


  ngOnInit(): void {

    const today = new Date();

    this.selectedDate =
      today.toISOString().split('T')[0];

    this.loadSlots();
  }


  // =========================================
  // LOAD MY SLOTS
  // =========================================

  loadSlots(): void {

    if (!this.selectedDate) {

      this.errorMessage =
        'Please select a date.';

      return;
    }


    this.loading = true;

    this.errorMessage = '';

    this.successMessage = '';


    this.slotService
      .getDoctorSlots(this.selectedDate)
      .subscribe({

        next: (
          slots: AvailableSlot[]
        ) => {

          console.log(
            'DOCTOR SLOTS:',
            slots
          );

          this.slots = slots;

          this.loading = false;
        },


        error: (error: any) => {

          console.error(
            'FAILED TO LOAD SLOTS:',
            error
          );

          this.loading = false;

          this.errorMessage =
            error.error?.message ||
            'Unable to load your appointment slots.';
        }

      });
  }


  // =========================================
  // CREATE SLOT
  // =========================================

  createSlot(): void {

    this.errorMessage = '';

    this.successMessage = '';


    if (!this.selectedDate) {

      this.errorMessage =
        'Please select a date.';

      return;
    }


    if (!this.startTime) {

      this.errorMessage =
        'Please select a start time.';

      return;
    }


    if (!this.endTime) {

      this.errorMessage =
        'Please select an end time.';

      return;
    }


    if (
      this.startTime >=
      this.endTime
    ) {

      this.errorMessage =
        'End time must be after start time.';

      return;
    }


    this.creatingSlot = true;


    this.slotService
      .createDoctorSlot(
        this.selectedDate,
        this.startTime,
        this.endTime
      )
      .subscribe({

        next: (
          slot: AvailableSlot
        ) => {

          console.log(
            'SLOT CREATED:',
            slot
          );


          this.creatingSlot = false;


          this.successMessage =
            'Appointment slot created successfully!';


          this.slots = [
            ...this.slots,
            slot
          ];


          this.startTime = '';

          this.endTime = '';


          this.loadSlots();
        },


        error: (error: any) => {

          console.error(
            'CREATE SLOT FAILED:',
            error
          );


          this.creatingSlot = false;


          this.errorMessage =
            error.error?.message ||
            'Unable to create appointment slot.';
        }

      });
  }


  // =========================================
  // REFRESH
  // =========================================

  refreshSlots(): void {

    this.loadSlots();
  }

}