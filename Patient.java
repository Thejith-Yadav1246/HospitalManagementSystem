public class Patient {
    int patientId;
    String name;
    int age;
    String gender;
    String phone;
    String problem;
    public Patient(int patientId, String name, int age,
                   String gender, String phone, String problem) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.problem = problem;
    }
}
