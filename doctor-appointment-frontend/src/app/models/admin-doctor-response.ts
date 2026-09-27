export interface AdminDoctorResponse {

  doctorId: number;

  userId: number;

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

  active: boolean;

  patientCount: number;

}