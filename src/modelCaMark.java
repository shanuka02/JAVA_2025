public class modelCaMark {
    private String studentId;
    private double q1Mark,q2Mark,q3Mark,q4Mark,qTotal,ass1Mark,ass2Mark,assTotal,midMark,totalCa;
    private int nuOfQuises,nuOfAssess;

    public modelCaMark(String studentId, double q1Mark, double q2Mark, double q3Mark,double q4Mark, double qTotal,double ass1Mark, double ass2Mark, double assTotal, double midMark, double totalCa,int nuOfQuises,int nuOfAssess) {
        this.studentId = studentId;
        this.q1Mark = q1Mark;
        this.q2Mark = q2Mark;
        this.q3Mark = q3Mark;
        this.q4Mark = q4Mark;
        this.qTotal = qTotal;
        this.ass1Mark = ass1Mark;
        this.ass2Mark = ass2Mark;
        this.assTotal = assTotal;
        this.midMark = midMark;
        this.totalCa = totalCa;
        this.nuOfQuises = nuOfQuises;
        this.nuOfAssess = nuOfAssess;
    }

    public int getNuOfQuises(){
        return nuOfQuises;
    }

    public int getNuOfAssess(){
        return nuOfAssess;
    }

    public String getStudentId() {
        return studentId;
    }

    public double getQ1Mark() {
        return q1Mark;
    }

    public double getQ2Mark() {
        return q2Mark;
    }

    public double getQ3Mark() {
        return q3Mark;
    }

    public double getQ4Mark(){
        return q4Mark;
    }
    public double getQTotal() {
        return qTotal;
    }

    public double getAss1Mark() {
        return ass1Mark;
    }

    public double getAss2Mark() {
        return ass2Mark;
    }

    public double getAssTotal() {
        return assTotal;
    }

    public double getMidMark() {
        return midMark;
    }

    public double getTotalCa() {
        return totalCa;
    }

    public void setQTotal(double quizTotal) {
        this.qTotal = quizTotal;
    }

    public void setAssTotal(double assesTotal) {
        this.assTotal = assesTotal;
    }

    public void setTotalCa(double tempca) {
        this.totalCa = tempca;
    }
}
