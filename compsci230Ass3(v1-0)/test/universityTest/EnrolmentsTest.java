package universityTest;


import university.Enrolments;
import junit.framework.TestCase;

public class EnrolmentsTest extends TestCase {

	Enrolments myApp;
	
	protected void setUp() throws Exception {
		super.setUp();
		myApp = new Enrolments();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	// ---------------------------------------------------------------------------------------------------------------------------
	// --- INVALID PARAMETERS ---
	// --------------------------
	
	//Enrol invalid "negative" student (id -2135) in paper delivered this semester (Phys01)
	public void testNagativeStudentEnrolledInValidPaperThrowsIllegalArgumentException() {
		boolean exceptionThrown = false; 
		
		try {
			myApp.enrolStudentInPaper(-2135, "Phys01");
		} catch (IllegalArgumentException e) {
			exceptionThrown = true; 
		}

		assertTrue(exceptionThrown);
	}
	public void testNagativeStudentEnrolledInValidPaperHasNoPaper() {
		boolean exceptionThrown = false;
		
		try {
			myApp.enrolStudentInPaper(-2135, "Phys01");
			assertFalse("Student -2135 should not have paper Phys01", myApp.listPapersForStudent(-2135).contains("Phys01"));		
			assertFalse("Paper Phys01 should not have student -2135", myApp.listStudentIdsForPaper("Phys01").contains(-2135));
		} catch (IllegalArgumentException e) {
			exceptionThrown = true; 
		}

		assertTrue(exceptionThrown);
	}

	// Enrol valid student (id 2135) in invalid "empty-string" paper delivered this semester ("")
	public void testValidStudentEnrolledInEmpty_stringPaperThrowsIllegalArgumentException() {
		boolean exceptionThrown = false; 
		
		try {
			myApp.enrolStudentInPaper(2135, "");
		} catch (IllegalArgumentException e) {
			exceptionThrown = true; 
		}

		assertTrue(exceptionThrown);
	}
	public void testValidStudentEnrolledInNon_stringPaperHasNoPaper() {
		boolean exceptionThrown = false;
		
		try {
			myApp.enrolStudentInPaper(2135, "");
			assertFalse("Student 2135 should not have paper \"\"", myApp.listPapersForStudent(2135).contains(""));		
			assertFalse("Paper \"\" should not have student 2135", myApp.listStudentIdsForPaper("").contains(2135));
		} catch (IllegalArgumentException e) {
			exceptionThrown = true; 
		}

		assertTrue(exceptionThrown);
	}

	// Enrol valid student (id 2135) in invalid "null" paper delivered this semester (null)
	public void testValidStudentEnrolledInNullPaperThrowsNullPointerException() {
		boolean exceptionThrown = false; 
		
		try {
			myApp.enrolStudentInPaper(2135, null);
		} catch (NullPointerException e) {
			exceptionThrown = true; 
		}

		assertTrue(exceptionThrown);
	}
	public void testValidStudentEnrolledInNullPaperHasNoPaper() {
		boolean exceptionThrown = false; 
		
		try {
			myApp.enrolStudentInPaper(2135, null);
			assertFalse("Student 2135 should not have paper that is not specified", myApp.listPapersForStudent(2135).contains(null));		
			assertFalse("\"null\" paper should not have student 2135", myApp.listStudentIdsForPaper(null).contains(2135));
		} catch (NullPointerException e) {
			exceptionThrown = true; 
		}

		assertTrue(exceptionThrown);
	}



	// ---------------------------------------------------------------------------------------------------------------------------
	// --- VALID PARAMETERS - BOUNDARY VALUES ---
	// ------------------------------------------

	// Enrol first valid student (id 1011) in paper delivered this semester (Phys01)
	public void testBoundaryValuesSucceeds() {
		assertTrue("Enrol should succeed for student 2135 and paper 'Phys01'", myApp.enrolStudentInPaper(2135, "Phys01"));
	}
	public void testFirstValidStudentEnrolledInValidPaperStudentHasOnePaper() {
		myApp.enrolStudentInPaper(2135, "Phys01");
		assertTrue("Student 2135 should have paper Phys01", myApp.listPapersForStudent(2135).contains("Phys01"));		
		assertTrue("Paper Phys01 should have student 2135", myApp.listStudentIdsForPaper("Phys01").contains(2135));		
	}



	// ---------------------------------------------------------------------------------------------------------------------------
	// --- VALID PARAMETERS - MID-RANGE ---
	// ------------------------------------
	
	// Enrol a valid student in a valid paper
	public void testValidStudentEnrolledInValidPaperSucceeds() {
		assertTrue("Enrol should succeed for student 2135 and paper 'Phys01'", myApp.enrolStudentInPaper(2135, "Phys01"));
	}
	public void testValidStudentEnrolledInValidPaperStudentHasOnePaper() {
		myApp.enrolStudentInPaper(2135, "Phys01");
		assertTrue("Student 2135 should have paper Phys01", myApp.listPapersForStudent(2135).contains("Phys01"));		
		assertTrue("Paper Phys01 should have student 2135", myApp.listStudentIdsForPaper("Phys01").contains(2135));		
	}
	
	
	// ---------------------------------------------------------------------------------------------------------------------------
	// --- VALID PARAMETERS - INVALID USAGE ---
	// ----------------------------------------
	
	// Enrol valid student (id 2135) in paper not delivered this semester (Comp01)
	public void testValidStudentEnrolledInInvalidPaperFails() {
		assertFalse("Paper Phys02 does not exist", myApp.enrolStudentInPaper(2135, "Phys02"));
	}
	public void testValidStudentEnrolledInInvalidCheck() {
		myApp.enrolStudentInPaper(2135, "Phys02");
		assertFalse("Student 2135 should not have paper Phys02", myApp.listPapersForStudent(2135).contains("Phys02"));		
		assertFalse("Paper Phys02 should not have student 1234", myApp.listStudentIdsForPaper("Phys02").contains(2135));
	}
	
	// Enrol invalid student (id 1234) in paper delivered this semester (Phys01)
	public void testInvalidStudentEnrolledInValidPaperFails() throws Exception {
		assertFalse("Student id 1234 does not exist", myApp.enrolStudentInPaper(1234, "Phys01"));
	}
	public void testInvalidStudentEnrolledInValidPaperCheck() throws Exception {
		myApp.enrolStudentInPaper(1234, "Phys01");
		assertFalse("Student 1234 should not have paper Phys01", myApp.listPapersForStudent(1234).contains("Phys01"));		
		assertFalse("Paper Phys01 should not have student 1234", myApp.listStudentIdsForPaper("Phys01").contains(1234));
	}


}
