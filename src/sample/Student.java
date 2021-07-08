package sample;
public class Student {
    private String id;
    private String name;
    private String gender;
    private String DateOfBirth;
    private String email;
    private String PhoneNumber;
    private double score;

    public Student(String s, String s1, double v) {}

    public Student(String id, String name, String gender, String DateOfBirth, String email, String PhoneNumber, double score) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.DateOfBirth = DateOfBirth;
        this.email = email;
        this.PhoneNumber = PhoneNumber;
        this.score = score;
    }

    public Student() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
