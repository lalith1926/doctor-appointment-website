export interface UserResponse {

  id: number;

  name: string;

  email: string;

  phone: string;

  role: 'PATIENT' | 'DOCTOR' | 'ADMIN';

}