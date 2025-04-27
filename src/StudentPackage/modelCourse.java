package StudentPackage;

public class modelCourse {

    private int lectureMaterialId;
    private String title;
    private String uploadDate;
    private String filePath;
    private String courseId;

    public modelCourse(int lectureMaterialId, String title, String uploadDate, String filePath, String courseId) {
        this.lectureMaterialId = lectureMaterialId;
        this.title = title;
        this.uploadDate = uploadDate;
        this.filePath = filePath;
        this.courseId = courseId;
    }

    public int getLectureMaterialId() {
        return lectureMaterialId;
    }

    public void setLectureMaterialId(int lectureMaterialId) {
        this.lectureMaterialId = lectureMaterialId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
