public class Token {
    int tokenNumber;
    String patientId;
    String doctorId;
    String status;
     public Token(int tokenNumber, String patientId,
                 String doctorId, String status) {
        this.tokenNumber = tokenNumber;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.status = status;
    }
}
