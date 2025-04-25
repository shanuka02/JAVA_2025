public class modelFinalMarks {
    private final double endMark;
    private String studentId;
    private double finalMark;
    private String result;
    private String grade;

    public modelFinalMarks( String studentId, double endMark, String grade) {
        this.studentId = studentId;
        this.endMark = endMark;
        this.grade = grade;
    }

    public double getEndMark() {
        return endMark;
    }

    public String getStudentId() {
        return studentId;
    }
    public double getFinalMark() {
        return finalMark;
    }
    public String getResult() {
        return result;
    }
    public String getGrade() {
        return grade;
    }
}
