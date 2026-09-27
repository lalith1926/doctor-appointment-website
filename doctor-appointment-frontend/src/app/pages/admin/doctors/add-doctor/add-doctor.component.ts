import { Component } from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  FormsModule
} from '@angular/forms';

import {
  Router,
  RouterLink
} from '@angular/router';

import {
  AdminService
} from '../../../../services/admin.service';

import {
  AdminDoctorRequest
} from '../../../../models/admin-doctor-request';


@Component({
  selector: 'app-add-doctor',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],

  templateUrl:
    './add-doctor.component.html',

  styleUrl:
    './add-doctor.component.css'
})
export class AddDoctorComponent {

  doctor: AdminDoctorRequest = {

    name: '',
    email: '',
    phone: '',
    password: '',

    specialization: '',
    experience: '',
    qualification: '',
    hospital: '',

    consultationFee: 0,

    availableFrom: '09:00',
    availableTo: '17:00'
  };


  loading = false;

  errorMessage = '';

  successMessage = '';


  constructor(
    private adminService: AdminService,
    private router: Router
  ) {}


  // =========================================================
  // CREATE DOCTOR
  // =========================================================

  createDoctor(): void {

    this.errorMessage = '';
    this.successMessage = '';


    // -----------------------------------------
    // BASIC VALIDATION
    // -----------------------------------------

    if (
      !this.doctor.name ||
      !this.doctor.email ||
      !this.doctor.phone ||
      !this.doctor.password ||
      !this.doctor.specialization ||
      !this.doctor.experience ||
      !this.doctor.qualification ||
      !this.doctor.hospital
    ) {

      this.errorMessage =
        'Please fill in all required fields.';

      return;
    }


    if (this.doctor.password.length < 6) {

      this.errorMessage =
        'Password must be at least 6 characters.';

      return;
    }


    if (this.doctor.consultationFee < 0) {

      this.errorMessage =
        'Consultation fee cannot be negative.';

      return;
    }


    if (
      !this.doctor.availableFrom ||
      !this.doctor.availableTo
    ) {

      this.errorMessage =
        'Please select doctor availability.';

      return;
    }


    // -----------------------------------------
    // START LOADING
    // -----------------------------------------

    this.loading = true;


    // -----------------------------------------
    // CREATE DOCTOR API
    // -----------------------------------------

    this.adminService
      .createDoctor(this.doctor)
      .subscribe({

        next: (response) => {

          console.log(
            'Doctor created successfully:',
            response
          );

          this.loading = false;

          this.successMessage =
            'Doctor account created successfully!';


          // -----------------------------------
          // GO BACK TO ADMIN DASHBOARD
          // -----------------------------------

          setTimeout(() => {

            this.router.navigate(
              ['/admin/dashboard']
            );

          }, 1200);

        },


        error: (error: any) => {

          console.error(
            'CREATE DOCTOR FAILED:',
            error
          );

          this.loading = false;

          this.errorMessage =
            error.error?.message ||
            'Unable to create doctor. Please try again.';
        }

      });

  }


  // =========================================================
  // RESET FORM
  // =========================================================

  resetForm(): void {

    this.doctor = {

      name: '',
      email: '',
      phone: '',
      password: '',

      specialization: '',
      experience: '',
      qualification: '',
      hospital: '',

      consultationFee: 0,

      availableFrom: '09:00',
      availableTo: '17:00'
    };

    this.errorMessage = '';
    this.successMessage = '';
  }

}