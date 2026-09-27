export interface Appointment {

  a_id: number;

  patientId: number;
  patientName: string;
  patientPhone: string;

  doctorId: number;
  doctorName: string;
  consultationFee: number;

  slotId: number;

  appointmentDate: string;
  appointmentTime: string;

  status:
    | 'PENDING'
    | 'CONFIRMED'
    | 'COMPLETED'
    | 'CANCELLED';

  bookedAt: string;
}