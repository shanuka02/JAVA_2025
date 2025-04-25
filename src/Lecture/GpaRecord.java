package Lecture;

public class GpaRecord {
    private String studentId;
    private double cgpa;
    private double sgpa;

    public GpaRecord(String studentId, double cgpa, double sgpa) {
        this.studentId = studentId;
        this.cgpa = cgpa;
        this.sgpa = sgpa;
    }

    public String  getStudentId() {
        return studentId;
    }

    public double getCgpa() {
        return cgpa;
    }

    public double getSgpa() {
        return sgpa;
    }
}
