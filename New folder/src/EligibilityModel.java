public class EligibilityModel {
    private String studentId;
    private String eligibility;

    public EligibilityModel( String studentId, String eligibility) {
        this.studentId = studentId;
        this.eligibility = eligibility;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getEligibility() {
        return eligibility;
    }
}
