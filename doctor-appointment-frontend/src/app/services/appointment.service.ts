import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Appointment } from '../models/appointment';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private readonly API_URL =
    'http://localhost:8080/api/appointments';


  constructor(
    private http: HttpClient
  ) {}


  // =========================================================
  // BOOK APPOINTMENT
  // =========================================================

  bookAppointment(
    slotId: number
  ): Observable<Appointment> {

    const params =
      new HttpParams()
        .set(
          'slotId',
          slotId.toString()
        );

    return this.http.post<Appointment>(
      `${this.API_URL}/book`,
      null,
      { params }
    );
  }


  // =========================================================
  // PATIENT APPOINTMENTS
  // =========================================================

  getPatientAppointments():
    Observable<Appointment[]> {

    return this.http.get<Appointment[]>(
      `${this.API_URL}/patient`
    );
  }


  // =========================================================
  // DOCTOR APPOINTMENTS
  // =========================================================

  getDoctorAppointments():
    Observable<Appointment[]> {

    return this.http.get<Appointment[]>(
      `${this.API_URL}/doctor`
    );
  }


  // =========================================================
  // DOCTOR CONFIRM
  // =========================================================

  confirmAppointment(
    appointmentId: number
  ): Observable<Appointment> {

    return this.http.put<Appointment>(
      `${this.API_URL}/${appointmentId}/confirm`,
      null
    );
  }


  // =========================================================
  // DOCTOR CANCEL
  // =========================================================

  cancelDoctorAppointment(
    appointmentId: number
  ): Observable<Appointment> {

    return this.http.put<Appointment>(
      `${this.API_URL}/${appointmentId}/cancel/doctor`,
      null
    );
  }


  // =========================================================
  // PATIENT CANCEL
  // =========================================================

  cancelPatientAppointment(
    appointmentId: number
  ): Observable<Appointment> {

    return this.http.put<Appointment>(
      `${this.API_URL}/${appointmentId}/cancel/patient`,
      null
    );
  }


  // =========================================================
  // DOCTOR COMPLETE
  // =========================================================

  completeAppointment(
    appointmentId: number
  ): Observable<Appointment> {

    return this.http.put<Appointment>(
      `${this.API_URL}/${appointmentId}/complete`,
      null
    );
  }
}