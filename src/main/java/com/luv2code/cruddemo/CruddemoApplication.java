package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.StudentDAO;
import com.luv2code.cruddemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
		return runner -> {
			createStudent(studentDAO);
			readStudent(studentDAO);
			queryForAllStudents(studentDAO);
			queryForStudentLastName(studentDAO);
			updateStudentInfoById(studentDAO);
		};
	}

	private void updateStudentInfoById(StudentDAO studentDAO) {
		System.out.println("Update student info by student ID ... ");
		Optional<Student> student = readStudent(studentDAO);
		if(student.isEmpty()) {
			return;
		}
		Student updatedStudent = student.get();
		Scanner scanner = new Scanner(System.in);
		// ask user if they want to change first name
		System.out.println("original first name: " + updatedStudent.getFirstName()
				+ "\n update it to? (If not, please leave the field blank)");
		String firstNameInput = scanner.nextLine();
		if(!firstNameInput.isEmpty()) {
			updatedStudent.setFirstName(firstNameInput);
		}
		// ask user if they want to change last name
		System.out.println("original last name: " + updatedStudent.getLastName()
				+ "\n update it to? (If not, please leave the field blank)");
		String lastNameInput = scanner.nextLine();
		if(!lastNameInput.isEmpty()) {
			updatedStudent.setLastName(lastNameInput);
		}
		// ask user if they want to change email
		System.out.println("original last name: " + updatedStudent.getEmail()
				+ "\n update it to? (If not, please leave the field blank)");
		String emailInput = scanner.nextLine();
		if(!emailInput.isEmpty()) {
			updatedStudent.setEmail(emailInput);
		}
		studentDAO.update(updatedStudent);
	}

	private void queryForStudentLastName(StudentDAO studentDAO) {
//		List<Student> theStudents = studentDAO.findByLastName("Owen");
		System.out.println("Querying for all students based on last name ... ");
		System.out.print("Last name that you're looking for: ");
		Scanner scanner = new Scanner(System.in);
		String lastName = scanner.nextLine();
		List<Student> theStudents = studentDAO.findByLastName(lastName);
		if (!theStudents.isEmpty()) {
			for (Student tempStudent: theStudents) {
				System.out.println(tempStudent);
			}
		}

	}

	private void queryForAllStudents(StudentDAO studentDAO) {
		System.out.println("Querying for all students inside Database ... ");
		List<Student> theStudents = studentDAO.findAll();
		for (Student tempStudent: theStudents) {
			System.out.println(tempStudent);
		}
	}

	private void createStudent(StudentDAO studentDAO) {
		// create the student object
		Scanner scanner = new Scanner(System.in);
		System.out.println("Creating new student object ... ");
		System.out.print("What is the first name of the new Student? ");
		String firstName = scanner.nextLine().trim();
		System.out.print("What is the last name of the new Student? ");
		String lastName = scanner.nextLine().trim();
		System.out.print("What is the email of the new Student? ");
		String email = scanner.nextLine().trim();
		Student tempStudent = new Student(firstName, lastName, email);

		// save the student object
		System.out.println("Saving the student ... ");
		studentDAO.save(tempStudent);

		// display the student object
		System.out.println("Saved student. Generated id: " + tempStudent.getId());
	}

	private Optional<Student> readStudent(StudentDAO studentDAO) {
		// retrieve student based on the id : primary key
		System.out.println("Reading an existed student object ... ");
		System.out.print("Student id that you're looking for: ");
		Scanner scanner = new Scanner(System.in);
		int studentId = scanner.nextInt();
		System.out.println("Displaying student with id of '"+ studentId +"'");

		Optional<Student> myStudent = studentDAO.findById(studentId);
//		Optional<Student> myStudent = studentDAO.findById(2);

		if (myStudent.isPresent()) {
			System.out.println("Found the student: " + myStudent.get() + "\n");
			return myStudent;
		} else {
			System.out.println("Sorry, no student was found");
		}
		return Optional.empty();

	}


}
