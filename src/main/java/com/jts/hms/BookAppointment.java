package com.jts.hms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookAppointment {
	private Connection connection;
	private Patient patient;
	private Doctor doctor;
	
	Scanner scanner = new Scanner(System.in);
	
	public BookAppointment(Connection connection , Patient patient, Doctor doctor) {
		this.connection = connection;
		this.patient = patient;
		this.doctor = doctor;
		
	}
	
	public void bookAppointment() throws SQLException {
		System.out.println("Enter patient id: ");
		int patientId = scanner.nextInt();
		
		System.out.println("Enter doctors id: ");
		int doctorId = scanner.nextInt();
		
		System.out.println("Enter appointment date yyyy-mm-dd: ");
		String appointmentDate = scanner.next();
		
		if (!patient.getPatientById(patientId)) {
			System.out.println("Please provide a valid patients id.");
			return;
		}
		if (!doctor.getDoctorById(doctorId)) {
			System.out.println("Please provide a valid patients id.");
			return;
		}
		if(!checkAvailability(connection, doctorId, appointmentDate)) {
			System.out.println("Doctor not available");
			return;
			
		}
		String query = "insert into appointments(patient_id, doctor_id,appointment_date) values (?,?,?)";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setInt(1, patientId);
			preparedStatement.setInt(2, doctorId);
			preparedStatement.setString(3, appointmentDate);
			
			if (preparedStatement.executeUpdate()> 0) {
				System.out.println("Appointment booked successfully");
				
			}else {
				System.out.println("Appointment not booked ");
			}
		}
		
	}
	public boolean checkAvailability(Connection connection, int doctorId, String appointmentDate) throws SQLException {
		String query = "select count(1) from appointments WHERE doctor_id = ? AND appointment_date = ?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentDate);
			
			try (ResultSet rs = preparedStatement.executeQuery()){
				if (rs.next()) {
					return rs.getInt(1) == 0;
				}
				
			}
		}
		return false;
			
		}
		
	}


