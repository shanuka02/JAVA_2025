package Lecture;

public class modelAttendence {
    private String tg;
    private String name;
    private String attType;
    private int present;
    private int total;
    private double  percentage;

    // Constructor, Getters and Setters
    public modelAttendence(String tg, String name, String attType, int present, int total, double percentage) {
        this.tg = tg;
        this.name = name;
        this.attType = attType;
        this.present = present;
        this.total = total;
        this.percentage = percentage;
    }

    public String getTg() {
        return tg;
    }
    public String getName() {
        return name;
    }
    public String getAttType() {
        return attType;
    }
    public int getPresent() {
        return present;
    }
    public int getTotal() {
        return total;
    }
    public double getPercentage() {
        return percentage;
    }
}
