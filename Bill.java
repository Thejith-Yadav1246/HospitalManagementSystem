
public class Bill {
    int billId;
    int tokenNumber;
    int patientId;
    String doctorId;
    float registrationFee;
    float consultationFee;
    float totalAmount;
    String paymentStatus;

    public Bill(int billId, int tokenNumber, int patientId,
                String doctorId, float registrationFee,
                float consultationFee, String paymentStatus) {

        this.billId = billId;
        this.tokenNumber = tokenNumber;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.registrationFee = registrationFee;
        this.consultationFee = consultationFee;
        this.totalAmount = registrationFee + consultationFee;
        this.paymentStatus = paymentStatus;
    }
}
