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
  DoctorProfile,
  DoctorProfileRequest
} from '../../../services/profile.service';


@Component({

  selector: 'app-doctor-profile',

  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],

  templateUrl: './doctor-profile.component.html',

  styleUrl: './doctor-profile.component.css'

})
export class DoctorProfileComponent
  implements OnInit {


  // =========================================================
  // PROFILE
  // =========================================================

  profile: DoctorProfile = {

    token: null,

    id: 0,

    doctorId: 0,

    name: '',

    email: '',

    phone: '',

    specialization: '',

    experience: '',

    qualification: '',

    hospital: '',

    consultationFee: 0,

    availableFrom: '',

    availableTo: ''

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
      .getDoctorProfile()
      .subscribe({

        next: (
          profile: DoctorProfile
        ) => {

          this.profile = profile;

          this.loading = false;

        },


        error: (error: any) => {

          console.error(
            'Unable to load doctor profile:',
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


    const request: DoctorProfileRequest = {

      name: this.profile.name.trim(),

      email: this.profile.email.trim(),

      phone: this.profile.phone.trim(),

      specialization:
        this.profile.specialization,

      experience:
        this.profile.experience,

      qualification:
        this.profile.qualification,

      hospital:
        this.profile.hospital,

      consultationFee:
        this.profile.consultationFee,

      availableFrom:
        this.profile.availableFrom,

      availableTo:
        this.profile.availableTo

    };


    this.saving = true;


    // =======================================================
    // UPDATE PROFILE
    // =======================================================

    this.profileService
      .updateDoctorProfile(request)
      .subscribe({

        next: (
          updatedProfile: DoctorProfile
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
            '/doctor/dashboard'
          ]);

        },


        error: (error: any) => {

          console.error(
            'Unable to update doctor profile:',
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