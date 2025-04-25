public class modelLectureMaterials {
    private int lectureMaterialId;
    private int number;
    private String date;
    private String title;
    private String filepath;

    public  modelLectureMaterials(int lectureMaterialId, String date, String title, String filepath) {
        this.lectureMaterialId = lectureMaterialId;
        this.date = date;
        this.title = title;
        this.filepath = filepath;
    }

    public int getLectureMaterialId() {
        return lectureMaterialId;
    }
    public int getNumber() {
        return number;
    }
    public String getDate() {
        return date;
    }
    public String getTitle() {
        return title;
    }
    public String getFilepath() {
        return filepath;
    }
}
