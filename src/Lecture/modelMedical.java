package Lecture;

public class modelMedical {
    private int mediId;
    private String stuId;
    private String reason;
    private String reqDate;
    private String status;
    private String subDate;

    public modelMedical(int mediId, String stuId, String reason, String reqDate, String status, String subDate) {
        this.mediId = mediId;
        this.stuId = stuId;
        this.reason = reason;
        this.reqDate = reqDate;
        this.status = status;
        this.subDate = subDate;
    }

    public int getMediId() {
        return mediId;
    }

    public String getStuId() {
        return stuId;
    }

    public String getReason() {
        return reason;
    }

    public String getReqDate() {
        return reqDate;
    }

    public String getStatus() {
        return status;
    }

    public String getSubDate() {
        return subDate;
    }
}
