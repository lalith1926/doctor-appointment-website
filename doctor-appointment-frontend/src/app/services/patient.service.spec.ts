import { Injectable } from '@angular/core';

import {
  HttpClient
} from '@angular/common/http';

import {
  Observable
} from 'rxjs';

import {
  DoctorResponse
} from '../models/doctor-response';


@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private readonly API_URL =
    'http://localhost:8080/api/doctors';


  constructor(
    private http: HttpClient
  ) {}


  getActiveDoctors():
    Observable<DoctorResponse[]> {

    return this.http.get<DoctorResponse[]>(
      this.API_URL
    );
  }

}