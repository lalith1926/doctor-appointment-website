import { Injectable } from '@angular/core';

import {
  HttpClient
} from '@angular/common/http';

import {
  Observable
} from 'rxjs';

import {
  AdminDoctorRequest
} from '../models/admin-doctor-request';

import {
  AdminDoctorResponse
} from '../models/admin-doctor-response';


export interface AdminDashboardResponse {

  doctorCount: number;

  patientCount: number;

  appointmentCount: number;
}


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly API_URL =
    'http://localhost:8080/api/admin';


  constructor(
    private http: HttpClient
  ) {}


  // =========================================================
  // DASHBOARD STATISTICS
  // =========================================================

  getDashboardStats():
    Observable<AdminDashboardResponse> {

    return this.http.get<AdminDashboardResponse>(
      `${this.API_URL}/dashboard`
    );
  }


  // =========================================================
  // CREATE DOCTOR
  // =========================================================

  createDoctor(
    request: AdminDoctorRequest
  ): Observable<AdminDoctorResponse> {

    return this.http.post<AdminDoctorResponse>(
      `${this.API_URL}/doctors`,
      request
    );
  }


  // =========================================================
  // GET ALL DOCTORS
  // =========================================================

  getAllDoctors():
    Observable<AdminDoctorResponse[]> {

    return this.http.get<AdminDoctorResponse[]>(
      `${this.API_URL}/doctors`
    );
  }


  // =========================================================
  // DEACTIVATE DOCTOR
  // =========================================================

  deactivateDoctor(
    doctorId: number
  ): Observable<AdminDoctorResponse> {

    return this.http.put<AdminDoctorResponse>(
      `${this.API_URL}/doctors/${doctorId}/deactivate`,
      null
    );
  }


  // =========================================================
  // ACTIVATE DOCTOR
  // =========================================================

  activateDoctor(
    doctorId: number
  ): Observable<AdminDoctorResponse> {

    return this.http.put<AdminDoctorResponse>(
      `${this.API_URL}/doctors/${doctorId}/activate`,
      null
    );
  }

}