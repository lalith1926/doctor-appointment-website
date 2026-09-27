import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpParams
} from '@angular/common/http';

import { Observable } from 'rxjs';

import { AvailableSlot } from '../models/available-slot';

@Injectable({
  providedIn: 'root'
})
export class SlotService {

  private readonly API_URL =
    'http://localhost:8080/api/slots';


  constructor(
    private http: HttpClient
  ) {}


  // =========================================================
  // DOCTOR - CREATE SLOT
  // =========================================================

  createDoctorSlot(
    date: string,
    startTime: string,
    endTime: string
  ): Observable<AvailableSlot> {

    const params =
      new HttpParams()
        .set('date', date)
        .set('startTime', startTime)
        .set('endTime', endTime);


    return this.http.post<AvailableSlot>(
      this.API_URL,
      null,
      { params }
    );
  }


  // =========================================================
  // DOCTOR - GET OWN SLOTS
  // =========================================================

  getDoctorSlots(
    date: string
  ): Observable<AvailableSlot[]> {

    const params =
      new HttpParams()
        .set('date', date);


    return this.http.get<AvailableSlot[]>(
      `${this.API_URL}/doctor`,
      { params }
    );
  }


  // =========================================================
  // PATIENT - GET DOCTOR SLOTS
  // =========================================================

  getSlots(
    doctorId: number,
    date: string
  ): Observable<AvailableSlot[]> {

    const params =
      new HttpParams()
        .set(
          'doctorId',
          doctorId.toString()
        )
        .set(
          'date',
          date
        );


    return this.http.get<AvailableSlot[]>(
      this.API_URL,
      { params }
    );
  }
}