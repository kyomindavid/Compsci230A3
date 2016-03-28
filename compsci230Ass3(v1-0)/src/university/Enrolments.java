package university;

/**
 * @author Diana Kirk
 * @version 1.0 : September 19 2014
 *		Minimal functionality with enrolment for current semester only.
 *		Student and paper delivery data hard-coded 
 */

/*
 * @author Kyomin Ku, 5521999
 * @version 1.1 : 1st October 2014
 *		Fixed "testNagativeStudentEnrolledInValidPaperThrowsIllegalArgumentException"
 *			by throwing IllegalArgumentException if studentId is less than 0.
 *				-> if (studentId < 0) 
 *					throw new IllegalArgumentException();
 *
 * @version 1.2 : 2nd October 2014
 *		Fixed "testValidStudentEnrolledInEmpty_stringPaperThrowsIllegalArgumentException"
 *			by throwing IllegalArgumentException if paperName is an empty string ("").
 *				-> if (paperName == "") 
 * 					throw new IllegalArgumentException();
 *		
 * @version 1.3 : 2nd October 2014
 *		Fixed "testInvalidStudentEnrolledInValidPaperFails" 
 *			by adding return null if found is false.
 *				-> if (found == true) {
 *					return (s);
 *					} else {
 *						return null;
 *					}
 *
 * @version 1.4 : 3st October 2014
 *		Fixed "testValidStudentEnrolledInInvalidPaperFails"
 *			by matching the specified paper's name, year and the semester. 
 *				-> paperDeliveries[i].getPaperName().equals(paperName) && paperDeliveries[i].getYear() == year && paperDeliveries[i].getSemester() == semester
 *
 * @version 1.5 : 3st October 2014
 * 		Fixed "testValidStudentEnrolledInValidPaperSucceeds"
 * 			by adding break when found is true (so it returns the value straight away).
 *				-> while (i < students.length) {
 *						if (students[i].getId() == studentId) {
 *							s = students[i];
 *							found = true;
 *							break;
 *						}
 *						++i;
 *					}
 */

import java.util.ArrayList;
import java.util.Calendar;

public class Enrolments {

	// --- Fields ---
	private PaperDelivery [] paperDeliveries;
	private Student [] students;
	private int currentYear;
	private int currentSemester;    // Allowed values 1 and 2

	// --- Constants ---
	static final int MAX_PAPERS_PER_SEMESTER = 4;

	// --- Constructor ---
	/** 
	 * Sets up local data structures for student and paper delivery records and loads data 
	 * from persistent storage. Establishes the current year and semester from system clock.
	 * In this prototype version, data is hard-coded to avoid issues of file access. 
	 */
	public Enrolments() {
		// establish data structures
		paperDeliveries = new PaperDelivery[20];
		students = new Student[6];

		// load data
		loadPaperDeliveryData();
		loadStudentData();	
		
		// establish current year and semester
		Calendar now = Calendar.getInstance();
		this.currentYear = now.get(Calendar.YEAR);
		this.currentSemester = now.get(Calendar.MONTH) < 6 ? 1 : 2;
	}
	
	// --- PUBLIC API ---
	/**
	 * Enrol a student in a paper in the current semester. Enrollment involves adding the student to the paper's 
	 * enrolled-students list and adding the paper to the student's enrolled-papers list.
	 * 
	 * A student may be enrolled in a maximum of 4 papers in any one semester. The method 
	 * succeeds if the student is successfully enrolled. The method fails if 'studentId' is not in the list of students, 
	 * if 'paperName' is not the name of a delivered paper, if 'paperName' is not delivered in the current semester, 
	 * or if the maximum number of papers has been reached for the student. 
	 * 
	 * @param studentId		ID of the student to be enrolled
	 * @param paperName		name of the paper to be enrolled in
	 * @return 				'true' if the student is successfully enrolled
	 *						'false' if  'studentId' doesn't exist OR 
	 *									'paperName' is not the name of a delivered paper OR 
	 *									'paperName' is not delivered in the current semester OR
	 *									student is already enrolled in the maximum number of papers
	 * @throws 				IllegalArgumentException if 'studentId' is negative or zero
	 * @throws 				IllegalArgumentException if 'paperName' is the empty string
	 * @throws				NullPointerException if 'paperName' is null
	 */
	public boolean enrolStudentInPaper(int studentId, String paperName) throws IllegalArgumentException, NullPointerException {
		
		Student s = null;
		PaperDelivery pd = null;
		boolean inputsOk = true;  // assume student will be successfully enrolled

		if (studentId < 0)
			throw new IllegalArgumentException();
		
		if (paperName == null)
			throw new NullPointerException();
		
		if (paperName == "")
			throw new IllegalArgumentException();

		if ((s = findStudentWithId(studentId)) == null) 
			inputsOk = false; 				
		
		if ((pd = findPaperDelivery(paperName, currentYear, currentSemester)) == null)
			inputsOk = false; 		
	
		// Provided data is valid and student isn't already enrolled in maximum number of papers,
		// enrol the student by adding student to paper and paper to student
		if (inputsOk) {
			
			if (s.getNumPapersInSemester(currentYear, currentSemester) < MAX_PAPERS_PER_SEMESTER) {
				pd.enrolStudent(s); 
				s.enrol(pd);
			} else {
				inputsOk = false;
			}
		}
		
		return inputsOk;
	}
	
	
	/**
	 * Get the list of IDs of students enrolled in a paper for the current semester.
	 * 
	 * @param paperName		The name of the paper 
	 * @return	ArrayList 	The list of IDs of enrolled students for the specified paper. Assumed not null. Empty list is returned if the paper is not found.
	 */
	public ArrayList<Integer> listStudentIdsForPaper(String paperName) {
		return listStudentIdsForPaper(paperName, currentYear, currentSemester);
	}
	
	ArrayList<Integer> listStudentIdsForPaper(String paperName, int year, int semester) {
		ArrayList <Integer> ids = new ArrayList <Integer> ();
		PaperDelivery pd = findPaperDelivery(paperName, year, semester);
		if (pd != null){
			ArrayList <Student> students = pd.listStudentsEnrolled();
			for (int i=0; i<students.size(); ++i) {
				ids.add(students.get(i).getId());
			}
		}
		return ids;
	}
	
	
	/**
	 * Get list of papers a student is enrolled in for the current semester.
	 * 
	 * @param studentId		The ID of the student. Expected to be positive integer 
	 * @return				The list of papers the student is enrolled in Empty list is returned if the student is not found.
	 */	
	public ArrayList <String> listPapersForStudent(int studentId) {
		return listPapersForStudent(studentId, currentYear, currentSemester);
	}	
	
	ArrayList <String> listPapersForStudent(int studentId, int year, int semester) {
		Student s = findStudentWithId(studentId);
		ArrayList <String> names = new ArrayList <String> ();
		if (s != null)
			names = s.listPaperNamesInSemester(year, semester);
		return names;
	}

	

	// -- PRIVATE METHODS ---

	// Get the student for 'studentId'. Returns null if not found.
	private Student findStudentWithId(int studentId) { 
		Student s = null;
		int i = 0;
		boolean found = false;
		while (i < students.length) {
			if (students[i].getId() == studentId) {
				s = students[i];
				found = true;
				break;
			}
			++i;
		}
		
		if (found == true) {
			return (s);
		} else {
			return null;
		}

	}

	// Get the correct paper-year-semester instance. Returns null if not found.
	// Assumes 'paperName' is not null.
	private PaperDelivery findPaperDelivery(String paperName, int year, int semester) { 			
		PaperDelivery pd = null;
		int i = 0;
		boolean found = false;
		while ((i < paperDeliveries.length) && (!found)) {
			if (paperDeliveries[i].getPaperName().equals(paperName) && paperDeliveries[i].getYear() == year && paperDeliveries[i].getSemester() == semester) {
				pd = paperDeliveries[i];
				found = true;
			}
			++i;
		}
			
		if (found ==  true) {
			return (pd);
		} else {
			return null;
		}
	}
	

	// -- Methods for loading paper and student data ---
	private void loadPaperDeliveryData() {
		paperDeliveries[0] = new PaperDelivery("Math01", 2014, 1);
		paperDeliveries[1] = new PaperDelivery("Math01", 2014, 2);
		paperDeliveries[2] = new PaperDelivery("Math01", 2013, 1);
		paperDeliveries[3] = new PaperDelivery("Math01", 2013, 2);
		paperDeliveries[4] = new PaperDelivery("Math02", 2014, 1);
		paperDeliveries[5] = new PaperDelivery("Math02", 2014, 2);
		paperDeliveries[6] = new PaperDelivery("Phys01", 2014, 1);
		paperDeliveries[7] = new PaperDelivery("Phys01", 2014, 2);
		paperDeliveries[8] = new PaperDelivery("Phys02", 2013, 2);
		paperDeliveries[9] = new PaperDelivery("Phys01", 2012, 1);
		paperDeliveries[10] = new PaperDelivery("Phys01", 2012, 2);
		paperDeliveries[11] = new PaperDelivery("Phys02", 2011, 2);
		paperDeliveries[12] = new PaperDelivery("Chem01", 2014, 1);
		paperDeliveries[13] = new PaperDelivery("Chem01", 2014, 2);
		paperDeliveries[14] = new PaperDelivery("Chem01", 2012, 1);
		paperDeliveries[15] = new PaperDelivery("Chem01", 2012, 2);
		paperDeliveries[16] = new PaperDelivery("Biol01", 2014, 1);
		paperDeliveries[17] = new PaperDelivery("Biol01", 2014, 2);
		paperDeliveries[18] = new PaperDelivery("Geol01", 2014, 1);
		paperDeliveries[19] = new PaperDelivery("Geol01", 2014, 2);	}	
	
	private void loadStudentData() {
		students[0] = new Student(1011, "Mark");
		students[1] = new Student(1246, "Amar");
		students[2] = new Student(2135, "Pio");
		students[3] = new Student(1892, "Jill");
		students[4] = new Student(1211, "Ming");
		students[5] = new Student(2044, "Ivan");
	}			
	

	// --- MAIN ---
	public static void main(String[] args) {
		new Enrolments();
	}

}

