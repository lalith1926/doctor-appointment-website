import { Doctor } from './doctor';

export interface AvailableSlot {
  id: number;
  doctor: Doctor;
  date: string;
  startTime: string;
  endTime: string;
  booked: boolean;
}