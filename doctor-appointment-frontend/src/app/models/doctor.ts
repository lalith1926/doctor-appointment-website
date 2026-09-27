export interface Doctor {
  d_id: number;
  d_name: string;
  d_specialization: string | null;
  d_experience: string | null;
  d_qualification: string | null;
  d_hospital: string | null;
  d_consultationFee: number;
  d_availableFrom: string | null;
  d_availableTo: string | null;
}