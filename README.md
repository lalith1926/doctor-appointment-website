Doctor Appointment Management System

A full-stack Doctor Appointment Management System built to manage patients, doctors, appointments, time slots, notifications, and administrative operations in one application.

The project provides separate dashboards and role-based access for Patients, Doctors, and Admins, with secure authentication using JWT and Spring Security.

🌐 Project Repository

GitHub Repository – Doctor Appointment Website

📌 Project Overview

Shyam Ortho Care is a web-based doctor appointment management application designed to simplify the process of booking and managing medical appointments.

The system supports three different user roles:

👤 Patient
👨‍⚕️ Doctor
👨‍💼 Admin

Each role has its own dashboard and permissions.

Patients can find doctors and book appointments, doctors can manage their availability and appointments, and administrators can manage doctors and monitor the overall system.

✨ Features
👤 Patient Features

Patients can:

Register for an account
Login securely
View their dashboard
View available doctors
Select a specific doctor
View available appointment slots
Book an appointment
View booked appointments
View appointment status
Cancel appointments
View appointment history
Edit personal profile
Update:
Name
Email
Phone
Age
Gender
Address
Blood Group
Receive email notifications related to appointments
Appointment Flow
Patient Login
      ↓
Select Doctor
      ↓
Select Available Date
      ↓
View Available Slots
      ↓
Select Time Slot
      ↓
Book Appointment
      ↓
Doctor Receives Notification
      ↓
Doctor Confirms / Cancels
      ↓
Patient Receives Notification
👨‍⚕️ Doctor Features

Doctors have their own dashboard where they can:

Login securely
View their dashboard
View today's appointments
View appointment statistics
Manage available appointment slots
Add available time slots
View booked appointments
Confirm appointments
Cancel appointments
Mark appointments as completed
View patient appointment information
Edit doctor profile
Update:
Name
Email
Phone
Specialization
Experience
Qualification
Hospital
Consultation Fee
Available From
Available To
Receive email notifications when patients book or cancel appointments
👨‍💼 Admin Features

The Admin has access to a separate administration dashboard.

Admin functionality includes:

Admin authentication
Dashboard statistics
View total doctors
View total patients
View total appointments
Add new doctors
View all doctors
Manage doctors
Activate doctors
Deactivate doctors
View doctor information
View doctor-wise patient information
Admin Dashboard

The dashboard provides an overview of:

Doctors
Patients
Appointments
📧 Email Notifications

The application includes email notifications using Gmail SMTP and HTML email templates.

Emails are sent for important appointment events such as:

Appointment Booked

When a patient books an appointment, the doctor receives an email notification.

Appointment Confirmed

When a doctor confirms an appointment, the patient receives a confirmation email.

Doctor Cancellation

When a doctor cancels an appointment, the patient receives a cancellation email.

Patient Cancellation

When a patient cancels an appointment, the doctor receives a cancellation email.

The email templates are created using Thymeleaf.

🔐 Authentication & Security

The application uses Spring Security and JWT (JSON Web Token) for authentication.

Authentication Flow
User Login
    ↓
Spring Security Authentication
    ↓
Username + Password Validation
    ↓
JWT Token Generated
    ↓
Token Stored by Frontend
    ↓
Token Sent With API Requests
    ↓
JWT Authentication Filter
    ↓
User Authenticated

The application also uses role-based authorization.

Roles
PATIENT
DOCTOR
ADMIN

Users can only access the pages and APIs permitted for their role.

For example:

PATIENT
   ├── Patient Dashboard
   ├── Book Appointment
   ├── My Appointments
   └── Patient Profile

DOCTOR
   ├── Doctor Dashboard
   ├── Manage Slots
   ├── Appointments
   └── Doctor Profile

ADMIN
   ├── Admin Dashboard
   ├── Add Doctor
   └── Manage Doctors
🏗️ Project Architecture

The project consists of two main applications:

Doctor Appointment Website
│
├── doctor-appointment-frontend
│       └── Angular Application
│
└── DoctorWebsite
        └── Spring Boot Application
Frontend

The frontend communicates with the backend through REST APIs.

Angular
   ↓
HTTP Requests
   ↓
Spring Boot REST API
Backend

The backend handles:

Authentication
Authorization
User management
Doctor management
Patient management
Appointment management
Slot management
Email notifications
Database operations
🛠️ Technologies Used
Frontend
Angular
TypeScript
HTML5
CSS3
Bootstrap
RxJS
Angular Router
Angular HTTP Client
Backend
Java
Spring Boot
Spring Security
JWT
Spring Data JPA
Hibernate
REST APIs
Thymeleaf
Database
MySQL
Email
Gmail SMTP
Thymeleaf HTML email templates
Development Tools
Visual Studio Code
Spring Tool Suite / Eclipse
MySQL
Git
GitHub
Maven
npm
📂 Project Structure
DoctorAppointmentWebsite/
│
├── .gitignore
│
├── DoctorWebsite/
│   │
│   ├── pom.xml
│   │
│   └── src/
│       ├── main/
│       │   │
│       │   ├── java/
│       │   │   └── com/doctor/website/
│       │   │       │
│       │   │       ├── config/
│       │   │       ├── controller/
│       │   │       ├── DTO/
│       │   │       ├── entity/
│       │   │       ├── enums/
│       │   │       ├── exception/
│       │   │       ├── mapper/
│       │   │       ├── repository/
│       │   │       ├── security/
│       │   │       └── service/
│       │   │
│       │   └── resources/
│       │       ├── templates/
│       │       │   ├── appointment-booked.html
│       │       │   ├── appointment-confirmed.html
│       │       │   ├── appointment-cancelled.html
│       │       │   └── patient-cancelled.html
│       │       │
│       │       └── application-example.properties
│       │
│       └── test/
│
└── doctor-appointment-frontend/
    │
    ├── angular.json
    ├── package.json
    ├── package-lock.json
    │
    └── src/
        └── app/
            ├── components/
            ├── guards/
            ├── interceptors/
            ├── models/
            ├── pages/
            │   ├── admin/
            │   ├── auth/
            │   ├── doctor/
            │   ├── home/
            │   └── patient/
            │
            └── services/
🗄️ Database

The application uses MySQL.

The main entities include:

User
Patient
Doctor
Appointment
AvailableSlot
Basic Relationship
User
 ├── Patient
 │
 └── Doctor

Doctor
   ↓
Available Slots
   ↓
Appointments
   ↓
Patient

A user account can have a corresponding patient or doctor profile depending on the user's role.

📅 Appointment Management

Appointments follow a defined workflow.

BOOKED
   ↓
CONFIRMED
   ↓
COMPLETED

An appointment can also be cancelled:

BOOKED ─────→ CANCELLED

CONFIRMED ──→ CANCELLED

The system also handles appointment slots so that patients can only select available slots.

Cancelled appointments can be handled without permanently losing the appointment history.

👨‍⚕️ Multi-Doctor Support

The system supports multiple doctors.

The Admin can add multiple doctors, and patients can select which doctor they want to book an appointment with.

Each doctor has their own:

Profile
Specialization
Experience
Qualification
Hospital
Consultation fee
Available time
Appointment slots
Appointments

This allows the application to support a multi-doctor clinic environment.

🔄 Doctor Management

Administrators can activate or deactivate doctors.

Active Doctor
     ↓
Visible to Patients
     ↓
Can Receive Appointments

If a doctor is deactivated:

Inactive Doctor
     ↓
Cannot Login
     ↓
Not Available for New Patient Booking

The doctor can later be restored by the Admin.

🖥️ Running the Project Locally
Prerequisites

Before running the project, install:

Java
Maven
Node.js
npm
Angular CLI
MySQL
Git
⚙️ Backend Setup

Navigate to the backend folder:

cd DoctorWebsite

Create a MySQL database:

CREATE DATABASE doctor_appointment;

Configure your local Spring Boot configuration.

The project contains:

application-example.properties

Use it as a reference for your local:

application.properties

The configuration includes:

spring.datasource.url=jdbc:mysql://localhost:3306/doctor_appointment
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD

You will also need to configure:

JWT secret
JWT expiration
Gmail SMTP username
Gmail App Password
Run the backend

Using Maven:

mvn spring-boot:run

Or run the main Spring Boot application from your IDE.

The backend runs on:

http://localhost:8080
🌐 Frontend Setup

Navigate to the frontend:

cd doctor-appointment-frontend

Install dependencies:

npm install

Start Angular:

ng serve

The frontend will normally be available at:

http://localhost:4200
🔗 Frontend–Backend Communication

The Angular application communicates with the Spring Boot backend through REST APIs.

The backend base URL is:

http://localhost:8080

The frontend uses services for different application areas, including:

AuthService
AppointmentService
AdminService
DoctorService
PatientService
ProfileService
SlotService
📡 Main API Areas

The backend provides REST endpoints for:

/api/auth
/api/profile
/api/appointments
/api/slots
/api/doctors
/api/admin

The exact endpoints are implemented inside the Spring Boot controllers.

🔒 Security Notes

Sensitive configuration files are intentionally excluded from GitHub.

The following should never be committed:

Database passwords
Gmail App Passwords
JWT secrets
Production credentials
Environment variables containing secrets

The local:

application.properties

file is excluded using .gitignore.

A safe example configuration is provided as:

application-example.properties
📱 User Interface

The application contains separate interfaces for each role.

Patient
Home
 ↓
Register / Login
 ↓
Patient Dashboard
 ↓
Doctor Selection
 ↓
Available Slots
 ↓
Book Appointment
 ↓
My Appointments
Doctor
Login
 ↓
Doctor Dashboard
 ↓
Manage Slots
 ↓
Appointments
 ↓
Confirm / Cancel / Complete
Admin
Login
 ↓
Admin Dashboard
 ↓
Manage Doctors
 ↓
Add Doctor
 ↓
Activate / Deactivate Doctor
🧪 Testing

The project contains test files for the Angular application and Spring Boot application.

Before deploying the application, the main workflows can be tested including:

Patient registration
Patient login
Doctor login
Admin login
Doctor creation
Doctor activation/deactivation
Slot creation
Appointment booking
Appointment confirmation
Appointment cancellation
Appointment completion
Profile editing
Email notifications
Role-based access
🚀 Future Improvements

Possible future improvements include:

Online payment integration
SMS notifications
Forgot password functionality
Password change functionality
Doctor search and filtering
Appointment rescheduling
Prescription management
Medical history
Patient medical documents
Doctor availability calendar
Admin reports and analytics
Cloud deployment
Production database configuration
Docker support
Automated CI/CD pipeline
🎯 Learning Outcomes

This project helped demonstrate practical implementation of:

Full-stack web development
Angular application development
Spring Boot REST API development
Spring Security
JWT authentication
Role-based authorization
MySQL database integration
Hibernate/JPA relationships
CRUD operations
Appointment workflow management
Email integration
HTML email templates
Frontend/backend integration
Git and GitHub
Project structure and modular development
👨‍💻 Developer

Lalith Kanapala

Built as a full-stack Doctor Appointment Management System using Angular, Spring Boot and MySQL.

GitHub

github.com/lalith1926

Project

Doctor Appointment Website Repository

📄 License

This project is intended for educational, portfolio, and demonstration purposes.
