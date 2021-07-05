package sample;

public class Subjects {
    private String SubID;
    private String SubName;
    private double creditSub;
    private double factorSub;

    public Subjects() {}

    public Subjects(String subID, String subName, double creditSub, double factorSub) {
        SubID = subID;
        SubName = subName;
        this.creditSub = creditSub;
        this.factorSub = factorSub;
    }

    public String getSubID() {
        return SubID;
    }

    public void setSubID(String subID) {
        SubID = subID;
    }

    public String getSubName() {
        return SubName;
    }

    public void setSubName(String subName) {
        SubName = subName;
    }

    public double getCreditSub() {
        return creditSub;
    }

    public void setCreditSub(double creditSub) {
        this.creditSub = creditSub;
    }

    public double getFactorSub() {
        return factorSub;
    }

    public void setFactorSub(double factorSub) {
        this.factorSub = factorSub;
    }
}
