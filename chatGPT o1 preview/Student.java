// Student.java
public class Student extends User {
    private String studentID;

    public Student(String username, String password, String name, String email, String studentID) {
        super(username, password, name, email);
        this.studentID = studentID;
    }

    // Getter and Setter for studentID
    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }
}
