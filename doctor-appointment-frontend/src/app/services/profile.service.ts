import { Injectable } from '@angular/core';

import {
  HttpClient
} from '@angular/common/http';

import {
  Observable
} from 'rxjs';


export interface PatientProfile {

  token: string | null;

  id: number;

  patientId: number;

  name: string;

  email: string;

  phone: string;

  age: number;

  gender: string;

  address: string;

  bloodGroup: string;
}


export interface DoctorProfile {

  token: string | null;

  id: number;

  doctorId: number;

  name: string;

  email: string;

  phone: string;

  specialization: string;

  experience: string;

  qualification: string;

  hospital: string;

  consultationFee: number;

  availableFrom: string;

  availableTo: string;
}


export interface PatientProfileRequest {

  name: string;

  email: string;

  phone: string;

  age: number;

  gender: string;

  address: string;

  bloodGroup: string;
}


export interface DoctorProfileRequest {

  name: string;

  email: string;

  phone: string;

  specialization: string;

  experience: string;

  qualification: string;

  hospital: string;

  consultationFee: number;

  availableFrom: string;

  availableTo: string;
}


@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private readonly API_URL =
    'http://localhost:8080/api/profile';


  constructor(
    private http: HttpClient
  ) {}


  // =========================================================
  // PATIENT
  // =========================================================

  getPatientProfile():
    Observable<PatientProfile> {

    return this.http.get<PatientProfile>(
      `${this.API_URL}/patient`
    );
  }


  updatePatientProfile(
    request: PatientProfileRequest
  ): Observable<PatientProfile> {

    return this.http.put<PatientProfile>(
      `${this.API_URL}/patient`,
      request
    );
  }


  // =========================================================
  // DOCTOR
  // =========================================================

  getDoctorProfile():
    Observable<DoctorProfile> {

    return this.http.get<DoctorProfile>(
      `${this.API_URL}/doctor`
    );
  }


  updateDoctorProfile(
    request: DoctorProfileRequest
  ): Observable<DoctorProfile> {

    return this.http.put<DoctorProfile>(
      `${this.API_URL}/doctor`,
      request
    );
  }

}