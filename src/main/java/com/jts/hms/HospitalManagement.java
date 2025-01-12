package com.jts.hms;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagement {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner sc = new Scanner(System.in);
		
		try(Connection conn= DatabaseService.getConnection()){
			Patient patient = new Patient(conn);
			Doctor doctor = new Doctor(conn);
			BookAppointment appointment = new BookAppointment(conn, patient, doctor);
			
			while(true) {
				System.out.println("====Hospital Management System====");
				System.out.println("1. Add patient");
				System.out.println("2. Add doctor");
				System.out.println("3. View Patients");
				System.out.println("4. View Doctors");
				System.out.println("5. Book Appointment");
				System.out.println("6. Exit");
				
				System.out.println("Enter your choice: ");
				
				int ch = sc.nextInt();
				
				switch(ch) {
				case 1: 
					//add patient
					patient.addPatient();
					break;
				
				case 2: 
					//add doctor
					doctor.addDoctor();
					break;
				
				case 3: 
					//view patient
					patient.viewPatients();
					break;
					
				
				case 4: 
					//view doctor
					doctor.viewDoctor();
					break;
				
				case 5: 
					//book appointment
					appointment.bookAppointment();
					break;
					
				
				case 6:  
					sc.close();
					return;
				default: 
					System.out.println("Please enter a valid choice.");
					break;
					
				}
			}
		}
		
		
	}
}
