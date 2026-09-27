import {
  Component,
  OnInit
} from '@angular/core';

import {
  CommonModule
} from '@angular/common';

import {
  FormsModule
} from '@angular/forms';

import {
  Router,
  RouterLink
} from '@angular/router';

import {
  AuthService
} from '../../../services/auth.service';

import {
  ProfileService,
  PatientProfile,
  PatientProfileRequest
} from '../../../services/profile.service';


@Component({

  selector: 'app-patient-profile',

  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],

  templateUrl: './patient-profile.component.html',

  styleUrl: './patient-profile.component.css'

})
export class PatientProfileComponent
  implements OnInit {


  // =========================================================
  // PROFILE
  // =========================================================

  profile: PatientProfile = {

    token: null,

    id: 0,

    patientId: 0,

    name: '',

    email: '',

    phone: '',

    age: 0,

    gender: '',

    address: '',

    bloodGroup: ''

  };


  // =========================================================
  // UI STATE
  // =========================================================

  loading = true;

  saving = false;

  errorMessage = '';

  successMessage = '';


  constructor(

    private profileService: ProfileService,

    private authService: AuthService,

    private router: Router

  ) {}


  // =========================================================
  // INITIAL LOAD
  // =========================================================

  ngOnInit(): void {

    this.loadProfile();

  }


  // =========================================================
  // LOAD PROFILE
  // =========================================================

  loadProfile(): void {

    this.loading = true;

    this.errorMessage = '';

    this.successMessage = '';


    this.profileService
      .getPatientProfile()
      .subscribe({

        next: (
          profile: PatientProfile
        ) => {

          this.profile = profile;

          this.loading = false;

        },


        error: (error: any) => {

          console.error(
            'Unable to load patient profile:',
            error
          );

          this.loading = false;

          this.errorMessage =
            error.error?.message ||
            'Unable to load your profile. Please try again.';

        }

      });

  }


  // =========================================================
  // SAVE PROFILE
  // =========================================================

  saveProfile(): void {

    this.errorMessage = '';

    this.successMessage = '';


    // =======================================================
    // BASIC VALIDATION
    // =======================================================

    if (!this.profile.name.trim()) {

      this.errorMessage =
        'Name is required.';

      return;

    }


    if (!this.profile.email.trim()) {

      this.errorMessage =
        'Email is required.';

      return;

    }


    if (!this.profile.phone.trim()) {

      this.errorMessage =
        'Phone number is required.';

      return;

    }


    const request: PatientProfileRequest = {

      name: this.profile.name.trim(),

      email: this.profile.email.trim(),

      phone: this.profile.phone.trim(),

      age: this.profile.age,

      gender: this.profile.gender,

      address: this.profile.address,

      bloodGroup: this.profile.bloodGroup

    };


    this.saving = true;


    // =======================================================
    // UPDATE PROFILE
    // =======================================================

    this.profileService
      .updatePatientProfile(request)
      .subscribe({

        next: (
          updatedProfile: PatientProfile
        ) => {

          this.profile =
            updatedProfile;


          // =================================================
          // UPDATE JWT
          // =================================================

          if (updatedProfile.token) {

            localStorage.setItem(
              'token',
              updatedProfile.token
            );

          }


          this.saving = false;


          // =================================================
          // RETURN TO DASHBOARD
          // =================================================

          this.router.navigate([
            '/patient/dashboard'
          ]);

        },


        error: (error: any) => {

          console.error(
            'Unable to update patient profile:',
            error
          );

          this.saving = false;

          this.errorMessage =
            error.error?.message ||
            'Unable to update your profile. Please try again.';

        }

      });

  }


  // =========================================================
  // LOGOUT
  // =========================================================

  logout(): void {

    this.authService.logout();

    window.location.href = '/';

  }

}