public class CourseModel {

    private String courseId;
    private String courseName;
    private int credit;
    private String cType;
    private int nuOfQuises;
    private int nuOfAssesments;
    private int caPercentage;
    private String lectureIncharge;
    private String gpaState;

    public CourseModel(String courseId, String courseName, int credit, String cType, int nuOfQuises, int nuOfAssesments, int caPercentage, String lectureIncharge, String gpaState) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.cType = cType;
        this.nuOfQuises = nuOfQuises;
        this.nuOfAssesments = nuOfAssesments;
        this.caPercentage = caPercentage;
        this.lectureIncharge = lectureIncharge;
        this.gpaState = gpaState;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredit() {
        return credit;
    }

    public String getCType() {
        return cType;
    }

    public int getNuOfQuises() {
        return nuOfQuises;
    }

    public int getNuOfAssesments() {
        return nuOfAssesments;
    }

    public int getCaPercentage() {
        return caPercentage;
    }

    public String getLectureIncharge() {
        return lectureIncharge;
    }

    public String getGpaState() {
        return gpaState;
    }
}
