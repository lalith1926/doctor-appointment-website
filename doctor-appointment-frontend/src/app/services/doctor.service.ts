import { Injectable } from '@angular/core';

import {
  HttpClient
} from '@angular/common/http';

import {
  Observable,
  map
} from 'rxjs';

import {
  Doctor
} from '../models/doctor';


interface DoctorApiResponse {

  doctorId: number;

  name: string;

  email: string;

  phone: string;

  specialization: string | null;

  experience: string | null;

  qualification: string | null;

  hospital: string | null;

  consultationFee: number;

  availableFrom: string | null;

  availableTo: string | null;

}


@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private readonly API_URL =
    'http://localhost:8080/api/doctors';


  constructor(
    private http: HttpClient
  ) {}


  // =========================================================
  // GET ACTIVE DOCTORS
  // =========================================================

  getDoctors(): Observable<Doctor[]> {

    return this.http
      .get<DoctorApiResponse[]>(
        this.API_URL
      )
      .pipe(

        map(doctors =>
          doctors.map(doctor => ({

            d_id:
              doctor.doctorId,

            d_name:
              doctor.name,

            d_specialization:
              doctor.specialization,

            d_experience:
              doctor.experience,

            d_qualification:
              doctor.qualification,

            d_hospital:
              doctor.hospital,

            d_consultationFee:
              doctor.consultationFee,

            d_availableFrom:
              doctor.availableFrom,

            d_availableTo:
              doctor.availableTo

          }))
        )

      );
  }


  // =========================================================
  // GET DOCTOR BY ID
  // =========================================================

  getDoctorById(
    id: number
  ): Observable<Doctor> {

    return this.http.get<Doctor>(
      `${this.API_URL}/${id}`
    );
  }

}