package university;

import java.util.ArrayList;

class PaperDelivery {

	// --- Fields ---
	private String paperName;
	private int year;
	private int semester; 
	private ArrayList <Student> studentsEnrolled;

	
	// --- Constructors ---
	PaperDelivery(String paperName, int year, int semester) {
		this.paperName = new String(paperName);
		this.year = year;
		this.semester = semester;
		this.studentsEnrolled = new ArrayList <Student> ();		
	}

	// --- Methods ---
	// --- Getter/setters ---
	String getPaperName() {
		return this.paperName;
	}	
	int getYear() {
		return this.year;
	}
	int getSemester() {
		return this.semester;
	}
	
	// --- Students enrolled in paper ---
	void enrolStudent(Student student) {
		boolean found = false;
		for (Student s : studentsEnrolled) {
			if (s.getId() == student.getId())
				found = true; 
		}
		if (!found)
			studentsEnrolled.add(student);
	}
	boolean hasStudent(Student student) {
		return (studentsEnrolled.contains(student));
	}
	ArrayList <Student> listStudentsEnrolled() {
		return studentsEnrolled;
	}	
}
