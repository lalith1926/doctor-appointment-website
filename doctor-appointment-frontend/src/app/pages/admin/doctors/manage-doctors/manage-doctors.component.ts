import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  RouterLink
} from '@angular/router';

import {
  AdminService
} from '../../../../services/admin.service';

import {
  AdminDoctorResponse
} from '../../../../models/admin-doctor-response';


@Component({
  selector: 'app-manage-doctors',

  standalone: true,

  imports: [
    CommonModule,
    RouterLink
  ],

  templateUrl:
    './manage-doctors.component.html',

  styleUrl:
    './manage-doctors.component.css'
})
export class ManageDoctorsComponent
  implements OnInit {

  doctors: AdminDoctorResponse[] = [];

  loading = true;

  errorMessage = '';

  actionLoadingId: number | null = null;


  constructor(
    private adminService: AdminService
  ) {}


  ngOnInit(): void {

    this.loadDoctors();

  }


  // =========================================================
  // LOAD DOCTORS
  // =========================================================

  loadDoctors(): void {

    this.loading = true;

    this.errorMessage = '';


    this.adminService
      .getAllDoctors()
      .subscribe({

        next: (doctors) => {

          this.doctors = doctors;

          this.loading = false;
        },

        error: (error: any) => {

          console.error(
            'Unable to load doctors:',
            error
          );

          this.loading = false;

          this.errorMessage =
            error.error?.message ||
            'Unable to load doctors. Please try again.';
        }

      });

  }


  // =========================================================
  // REMOVE / DEACTIVATE
  // =========================================================

  deactivateDoctor(
    doctor: AdminDoctorResponse
  ): void {

    const confirmed =
      window.confirm(
        `Are you sure you want to remove ${doctor.name}?`
      );

    if (!confirmed) {
      return;
    }


    this.actionLoadingId =
      doctor.doctorId;


    this.adminService
      .deactivateDoctor(
        doctor.doctorId
      )
      .subscribe({

        next: (updatedDoctor) => {

          const index =
            this.doctors.findIndex(
              d =>
                d.doctorId ===
                updatedDoctor.doctorId
            );

          if (index !== -1) {

            this.doctors[index] =
              updatedDoctor;

          }

          this.actionLoadingId = null;
        },

        error: (error: any) => {

          console.error(
            'Unable to remove doctor:',
            error
          );

          this.actionLoadingId = null;

          this.errorMessage =
            error.error?.message ||
            'Unable to remove doctor.';
        }

      });

  }


  // =========================================================
  // RESTORE DOCTOR
  // =========================================================

  activateDoctor(
    doctor: AdminDoctorResponse
  ): void {

    this.actionLoadingId =
      doctor.doctorId;


    this.adminService
      .activateDoctor(
        doctor.doctorId
      )
      .subscribe({

        next: (updatedDoctor) => {

          const index =
            this.doctors.findIndex(
              d =>
                d.doctorId ===
                updatedDoctor.doctorId
            );

          if (index !== -1) {

            this.doctors[index] =
              updatedDoctor;

          }

          this.actionLoadingId = null;
        },

        error: (error: any) => {

          console.error(
            'Unable to restore doctor:',
            error
          );

          this.actionLoadingId = null;

          this.errorMessage =
            error.error?.message ||
            'Unable to restore doctor.';
        }

      });

  }

}