package com.doctor.website.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.doctor.website.entity.Appointment;
import com.doctor.website.entity.Doctor;
import com.doctor.website.entity.Patient;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(
            JavaMailSender mailSender,
            SpringTemplateEngine templateEngine) {

        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    // =========================================================
    // COMMON HTML EMAIL METHOD
    // =========================================================

    private void sendHtmlEmail(
            String to,
            String subject,
            String templateName,
            Context context) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true,
                            "UTF-8"
                    );

            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent =
                    templateEngine.process(
                            templateName,
                            context
                    );

            helper.setText(
                    htmlContent,
                    true
            );

            mailSender.send(message);

            System.out.println(
                    "HTML email sent successfully to: "
                    + to
            );

        } catch (MessagingException e) {

            System.out.println(
                    "HTML email sending failed."
            );

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to send email.",
                    e
            );
        }
    }

    // =========================================================
    // NEW APPOINTMENT
    // DOCTOR RECEIVES EMAIL
    // =========================================================

    public void sendNewAppointmentEmail(
            Appointment appointment) {

        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();

        if (doctor == null ||
            doctor.getUser() == null ||
            patient == null ||
            patient.getUser() == null) {

            return;
        }

        String doctorEmail =
                doctor.getUser().getEmail();

        String doctorName =
                doctor.getD_name();

        String patientName =
                patient.getUser().getName();

        Context context = new Context();

        context.setVariable(
                "doctorName",
                doctorName
        );

        context.setVariable(
                "patientName",
                patientName
        );

        context.setVariable(
                "appointmentDate",
                appointment.getAppointmentDate()
        );

        context.setVariable(
                "appointmentTime",
                appointment.getAppointmentTime()
        );

        context.setVariable(
                "status",
                appointment.getStatus()
        );

        sendHtmlEmail(
                doctorEmail,
                "New Appointment Booked - Shyam Ortho Care",
                "appointment-booked",
                context
        );
    }

    // =========================================================
    // APPOINTMENT CONFIRMED
    // PATIENT RECEIVES EMAIL
    // =========================================================

    public void sendAppointmentConfirmationEmail(
            Appointment appointment) {

        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();

        if (doctor == null ||
            doctor.getUser() == null ||
            patient == null ||
            patient.getUser() == null) {

            return;
        }

        String patientEmail =
                patient.getUser().getEmail();

        String patientName =
                patient.getUser().getName();

        String doctorName =
                doctor.getD_name();

        Context context = new Context();

        context.setVariable(
                "patientName",
                patientName
        );

        context.setVariable(
                "doctorName",
                doctorName
        );

        context.setVariable(
                "appointmentDate",
                appointment.getAppointmentDate()
        );

        context.setVariable(
                "appointmentTime",
                appointment.getAppointmentTime()
        );

        context.setVariable(
                "status",
                appointment.getStatus()
        );

        sendHtmlEmail(
                patientEmail,
                "Appointment Confirmed - Shyam Ortho Care",
                "appointment-confirmed",
                context
        );
    }

    // =========================================================
    // DOCTOR CANCELLED APPOINTMENT
    // PATIENT RECEIVES EMAIL
    // =========================================================

    public void sendDoctorCancellationEmail(
            Appointment appointment) {

        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();

        if (doctor == null ||
            doctor.getUser() == null ||
            patient == null ||
            patient.getUser() == null) {

            return;
        }

        String patientEmail =
                patient.getUser().getEmail();

        String patientName =
                patient.getUser().getName();

        String doctorName =
                doctor.getD_name();

        Context context = new Context();

        context.setVariable(
                "patientName",
                patientName
        );

        context.setVariable(
                "doctorName",
                doctorName
        );

        context.setVariable(
                "appointmentDate",
                appointment.getAppointmentDate()
        );

        context.setVariable(
                "appointmentTime",
                appointment.getAppointmentTime()
        );

        context.setVariable(
                "status",
                "CANCELLED"
        );

        sendHtmlEmail(
                patientEmail,
                "Appointment Cancelled - Shyam Ortho Care",
                "appointment-cancelled",
                context
        );
    }

    // =========================================================
    // PATIENT CANCELLED APPOINTMENT
    // DOCTOR RECEIVES EMAIL
    // =========================================================

    public void sendPatientCancellationEmail(
            Appointment appointment) {

        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();

        if (doctor == null ||
            doctor.getUser() == null ||
            patient == null ||
            patient.getUser() == null) {

            return;
        }

        String doctorEmail =
                doctor.getUser().getEmail();

        String doctorName =
                doctor.getD_name();

        String patientName =
                patient.getUser().getName();

        Context context = new Context();

        context.setVariable(
                "doctorName",
                doctorName
        );

        context.setVariable(
                "patientName",
                patientName
        );

        context.setVariable(
                "appointmentDate",
                appointment.getAppointmentDate()
        );

        context.setVariable(
                "appointmentTime",
                appointment.getAppointmentTime()
        );

        context.setVariable(
                "status",
                "CANCELLED"
        );

        sendHtmlEmail(
                doctorEmail,
                "Appointment Cancelled by Patient - Shyam Ortho Care",
                "patient-cancelled",
                context
        );
    }
}