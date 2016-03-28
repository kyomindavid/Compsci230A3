package university;

import java.util.ArrayList;

class Student {

	// --- Static (class) methods ---
	private static final int NUM_PAPERS_MAX = 20;
	
	// --- Fields ---
	private int id;
	private String name;
	private PaperDelivery[] papers;
	
	private int papersIndex = 0;  

	
	// --- Constructors ---
	Student(int id, String name) {
		this.id = id;
		this.name = new String(name);
		this.papers = new PaperDelivery[NUM_PAPERS_MAX];
	}
	
	// --- Methods ---
	// --- Getter/setters ---
	int getId() {
		return this.id;
	}
	void setId(int id) {
		this.id = id;
	}
	void setName(String name) {
		this.name = name;
	}
	String getName() {
		return this.name;
	}
	
	// --- Methods ---

	void enrol(PaperDelivery pd) {
		if (!hasPaper(pd)) {
			papers[papersIndex] = pd;
			if (papersIndex < NUM_PAPERS_MAX -1)
				++papersIndex;
		}
	}	


	// --- Check for enrolled in specific paper delivery.
	boolean hasPaper(PaperDelivery pd) {
		boolean hasPaper = false;
		for (int i=0; i<papers.length; ++i) {
			if (papers[i] == pd)
				hasPaper = true;
		}
		return hasPaper;
	}

	int getNumPapersInSemester(int year, int semester) {
		int num = 0;
		for (int i=0; i<papers.length; ++i) {
			if ((papers[i] != null) && (papers[i].getYear() == year) &&  (papers[i].getSemester() == semester))
				num++;
		}
		return num;
	}
	
	ArrayList <String> listPaperNamesInSemester(int year, int semester) {
		ArrayList <String> names = new ArrayList <String> ();
		for (int i=0; i<papers.length; ++i) {
			if ((papers[i] !=  null) && (papers[i].getYear() == year) &&  (papers[i].getSemester() == semester))
				names.add(papers[i].getPaperName());
		}
		return names;
	}	
	
}
