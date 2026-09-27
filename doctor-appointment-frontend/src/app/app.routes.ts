import { Routes } from '@angular/router';

import { HomeComponent }
  from './pages/home/home/home.component';

import { LoginComponent }
  from './pages/auth/login/login.component';

import { RegisterComponent }
  from './pages/auth/register/register.component';

import { PatientDashboardComponent }
  from './pages/patient/dashboard/patient-dashboard/patient-dashboard.component';

import { DoctorDashboardComponent }
  from './pages/doctor/dashboard/doctor-dashboard/doctor-dashboard.component';

import { SlotsComponent }
  from './pages/patient/slots/slots.component';

import { AppointmentsComponent }
  from './pages/patient/appointments/appointments/appointments.component';

import { AppointmentsComponent as DoctorAppointmentsComponent }
  from './pages/doctor/appointments/appointments.component';

import { SlotsComponent as DoctorSlotsComponent }
  from './pages/doctor/slots/slots.component';

import { AdminDashboardComponent }
  from './pages/admin/dashboard/admin-dashboard.component';

import { roleGuard }
  from './guards/role.guard';

import { ManageDoctorsComponent }
  from './pages/admin/doctors/manage-doctors/manage-doctors.component';

import { AddDoctorComponent }
  from './pages/admin/doctors/add-doctor/add-doctor.component';

import { PatientProfileComponent }
  from './pages/patient/profile/patient-profile.component';

import { DoctorProfileComponent }
  from './pages/doctor/profile/doctor-profile.component';


export const routes: Routes = [

  // =========================================
  // HOME
  // =========================================

  {
    path: '',
    component: HomeComponent
  },


  // =========================================
  // AUTH
  // =========================================

  {
    path: 'login',
    component: LoginComponent
  },

  {
    path: 'register',
    component: RegisterComponent
  },


  // =========================================
  // PATIENT
  // =========================================

  {
    path: 'patient/dashboard',
    component: PatientDashboardComponent,
    canActivate: [roleGuard],
    data: {
      role: 'PATIENT'
    }
  },

  {
    path: 'patient/profile',
    component: PatientProfileComponent,
    canActivate: [roleGuard],
    data: {
      role: 'PATIENT'
    }
  },

  {
    path: 'patient/slots',
    component: SlotsComponent,
    canActivate: [roleGuard],
    data: {
      role: 'PATIENT'
    }
  },

  {
    path: 'patient/appointments',
    component: AppointmentsComponent,
    canActivate: [roleGuard],
    data: {
      role: 'PATIENT'
    }
  },


  // =========================================
  // DOCTOR
  // =========================================

  {
    path: 'doctor/dashboard',
    component: DoctorDashboardComponent,
    canActivate: [roleGuard],
    data: {
      role: 'DOCTOR'
    }
  },

  {
    path: 'doctor/profile',
    component: DoctorProfileComponent,
    canActivate: [roleGuard],
    data: {
      role: 'DOCTOR'
    }
  },

  {
    path: 'doctor/slots',
    component: DoctorSlotsComponent,
    canActivate: [roleGuard],
    data: {
      role: 'DOCTOR'
    }
  },

  {
    path: 'doctor/appointments',
    component: DoctorAppointmentsComponent,
    canActivate: [roleGuard],
    data: {
      role: 'DOCTOR'
    }
  },


  // =========================================
  // ADMIN
  // =========================================

  {
    path: 'admin/dashboard',
    component: AdminDashboardComponent,
    canActivate: [roleGuard],
    data: {
      role: 'ADMIN'
    }
  },

  {
    path: 'admin/doctors/add',
    component: AddDoctorComponent,
    canActivate: [roleGuard],
    data: {
      role: 'ADMIN'
    }
  },

  {
    path: 'admin/doctors',
    component: ManageDoctorsComponent,
    canActivate: [roleGuard],
    data: {
      role: 'ADMIN'
    }
  },


  // =========================================
  // FALLBACK
  // =========================================

  {
    path: '**',
    redirectTo: ''
  }

];