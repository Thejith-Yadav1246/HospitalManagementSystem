import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class HospitalSystem {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== HOSPITAL MANAGEMENT SYSTEM ===");
            System.out.println("1. Receptionist Login");
            System.out.println("2. Hospital Owner Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> login("RECEPTIONIST");

                case "2" -> login("OWNER");

                case "3" -> {
                    System.out.println("Exiting application...");
                    return;
                    }

                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        
    }

    static void login(String role) {

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (role.equals("RECEPTIONIST")
                && username.equals("s")
                && password.equals("s1")) {
            receptionistDashboard();
        } else if (role.equals("OWNER")
                && username.equals("o")
                && password.equals("o1")) {
            ownerDashboard();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    static void receptionistDashboard() {
        while (true) {
        System.out.println("\n=== RECEPTIONIST DASHBOARD ===");
        System.out.println("1. Register New Patient");
        System.out.println("2. Search Existing Patient");
        System.out.println("3. View Available Doctors");
        System.out.println("4. Generate OPD Token");
        System.out.println("5. Manage Patient Queue");
        System.out.println("6. Generate Bill");
        System.out.println("7. View Patient Records");
        System.out.println("8. Payment Management");
        System.out.println("9. Logout");
        System.out.print("Enter choice: ");
        String choice;
        choice = sc.nextLine();

            switch (choice) {
                case "1" -> registerPatient();
                case "2" -> searchPatient();
                case "3" -> new DoctorStorage().viewAvailableDoctors();
                case "4" -> generateOPDToken();
                case "5" -> managePatientQueue();
                case "6" -> generateBill();
                case "7" -> PatientStorage.viewAllPatients();
                case "8" -> paymentManagement();
                case "9" -> {
                    System.out.println("Logging out...");
                    return;
                }

                default -> System.out.println("Invalid choice");
             }
        }
    }

    static void ownerDashboard() {
         String choice;

        while (true) {
            System.out.println("\n=== HOSPITAL OWNER DASHBOARD ===");
            System.out.println("1. Manage Doctors");
            System.out.println("2. View Patient Records");
            System.out.println("3. Hospital Overview");
            System.out.println("4. View Revenue and Payments");
            System.out.println("5. View Common Complaints");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextLine();

            switch (choice) {
                case "1" -> manageDoctors();
                case "2" -> PatientStorage.viewAllPatients();
                case "3" -> viewHospitalOverview();
                case "4" -> paymentManagement();
                case "5" -> PatientStorage.viewCommonComplaints();
                case "6" -> {
                    System.out.println("Logging out...");
                    return;
                 }

                default -> System.out.println("Invalid choice");
            }
        }
    }

    static void manageDoctors() {
    DoctorStorage ds = new DoctorStorage();

    while (true) {
        System.out.println("\n=== MANAGE DOCTORS ===");
        System.out.println("1. Add Doctor");
        System.out.println("2. View All Doctors");
        System.out.println("3. Update Doctor Availability");
        System.out.println("4. Back to Owner Dashboard");
        System.out.print("Enter choice: ");

        String choice = sc.nextLine();

        switch (choice) {
            case "1" -> {
                System.out.print("Enter Doctor ID: ");
                String id = sc.nextLine();

                if (id.trim().isEmpty()) {
                    System.out.println("Doctor ID cannot be empty.");
                    break;
                }

                if (ds.doctorExists(id)) {
                    System.out.println("Doctor ID already exists!");
                    break;
                }

                System.out.print("Enter Doctor Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Specialization: ");
                String specialization = sc.nextLine();

                if (name.trim().isEmpty() || specialization.trim().isEmpty()) {
                    System.out.println("Name and specialization cannot be empty.");
                    break;
                }

                float fee;

                while (true) {
                    System.out.print("Enter Consultation Fee: ");

                    try {
                        fee = Float.parseFloat(sc.nextLine());

                        if (fee < 0) {
                            System.out.println("Fee cannot be negative. Try again.");
                            continue;
                        }

                        break;

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid fee. Enter a number.");
                    }
                }

                String answer;

                while (true) {
                    System.out.print("Available? (yes/no): ");
                    answer = sc.nextLine();

                    if (answer.equalsIgnoreCase("yes")
                            || answer.equalsIgnoreCase("no")) {
                        break;
                    }

                    System.out.println("Please enter yes or no.");
                }

                boolean available = answer.equalsIgnoreCase("yes");

                Doctor doctor = new Doctor(
                        id, name, specialization, fee, available
                );

                ds.saveDoctor(doctor);
            }
            case "2" -> ds.viewDoctors();

            case "3" -> {
                System.out.print("Enter Doctor ID: ");
                String doctorId = sc.nextLine();

                if (!ds.doctorExists(doctorId)) {
                    System.out.println("Doctor not found!");
                    break;
                }

                System.out.print("Set availability (yes/no): ");
                String status = sc.nextLine();

                if (!status.equalsIgnoreCase("yes")
                        && !status.equalsIgnoreCase("no")) {
                    System.out.println("Enter yes or no.");
                    break;
                }

                boolean newAvailability = status.equalsIgnoreCase("yes");

                if (ds.updateAvailability(doctorId, newAvailability)) {
                    System.out.println("Availability updated successfully.");
                } else {
                    System.out.println("Failed to update availability.");
                }
            }
            case "4" -> {
                return;
            }

            default -> System.out.println("Invalid choice.");
        }
    }
}

    
static void registerPatient() {
    System.out.println("\n=== PATIENT REGISTRATION ===");
    int id;

    while (true) {
        System.out.print("Enter Patient ID: ");

        try {
            id = Integer.parseInt(sc.nextLine());

            if (id <= 0) {
                System.out.println("Patient ID must be positive.");
                continue;
            }
            
            if (PatientStorage.patientExists(id)) {
            System.out.println("Patient ID already exists. Enter a different ID.");
            continue;
            }
            break;

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Enter a whole number.");
        }
    }


    
String name;

    while (true) {
        System.out.print("Enter Patient Name: ");
        name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. Try again.");
            continue;
        }
        break;
    }
    int age;
    while (true) {
        System.out.print("Enter Age: ");

        try {
            age = Integer.parseInt(sc.nextLine());

            if (age <= 0) {
                System.out.println("Age must be greater than zero.");
                continue;
            }

            break;

        } catch (NumberFormatException e) {
            System.out.println("Invalid age. Enter a whole number.");
        }
    }

    String gender;

    while (true) {
        System.out.print("Enter Gender: ");
        gender = sc.nextLine().trim();

        if (!gender.isEmpty()) {
            break;
        }

        System.out.println("Gender cannot be empty.");
    }

    String phone;
    while (true) {
    System.out.print("Enter Phone Number: ");
    phone = sc.nextLine().trim();

    if (phone.matches("[0-9]{10}")) {
        break;
    }

    System.out.println("Invalid phone number. Enter exactly 10 digits.");
}

   String problem;

    while (true) {
        System.out.print("Enter Health Problem: ");
        problem = sc.nextLine().trim();

        if (!problem.isEmpty()) {
            break;
        }

        System.out.println("Health problem cannot be empty.");
    }

    Patient patient = new Patient(
        id, name, age, gender, phone, problem
    );

    PatientStorage.savePatient(patient);

    System.out.println("Patient registered successfully.");
}


static void searchPatient() {
    int id;

    while (true) {
        System.out.print("Enter Patient ID to search: ");

        try {
            id = Integer.parseInt(sc.nextLine());

            if (id <= 0) {
                System.out.println("Patient ID must be positive.");
                continue;
            }

            break;

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Enter a whole number.");
        }
    }
  
    Patient patient = PatientStorage.findPatient(id);

    if (patient == null) {
        System.out.println("Patient not found.");
        return;
    }

    System.out.println("\n=== PATIENT DETAILS ===");
    System.out.println("Patient ID: " + patient.patientId);
    System.out.println("Name: " + patient.name);
    System.out.println("Age: " + patient.age);
    System.out.println("Gender: " + patient.gender);
    System.out.println("Phone: " + patient.phone);
    System.out.println("Health Problem: " + patient.problem);
}


static void generateOPDToken() {
    int patientId;

    while (true) {
        System.out.print("Enter Patient ID: ");

        try {
            patientId = Integer.parseInt(sc.nextLine());

            if (patientId <= 0) {
                System.out.println("Patient ID must be positive.");
                continue;
            }

            break;

        } catch (NumberFormatException e) {
            System.out.println("Invalid Patient ID. Enter a whole number.");
        }
    }

    System.out.print("Enter Doctor ID: ");
    String doctorId = sc.nextLine().trim();

    if (doctorId.isEmpty()) {
        System.out.println("Doctor ID cannot be empty.");
        return;
    }

    TokenStorage.generateToken(patientId, doctorId);
}

static void managePatientQueue() {
    System.out.print("Enter Doctor ID: ");
    String doctorId = sc.nextLine();

    if (!TokenStorage.hasTokensForDoctor(doctorId)) {
        System.out.println("No tokens found for this doctor.");
        return;
    }

    TokenStorage.viewQueue(doctorId);

    String choice;

    while (true) {
        System.out.print(
            "\nDo you want to change a token status? (yes/no): "
        );

        choice = sc.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            break;
        } else if (choice.equalsIgnoreCase("no")) {
            return;
        } else {
            System.out.println("Invalid input. Enter yes or no.");
        }
    }

    int tokenNumber;

    while (true) {
        System.out.print("Enter Token Number: ");

        try {
            tokenNumber = Integer.parseInt(sc.nextLine());

            if (tokenNumber <= 0) {
                System.out.println(
                    "Token number must be greater than zero."
                );
                continue;
            }

            if (!TokenStorage.tokenExists(tokenNumber, doctorId)) {
                System.out.println(
                    "Token not found. Please enter a valid token number."
                );
                continue;
            }

            break;

        } catch (NumberFormatException e) {
            System.out.println(
                "Invalid token number. Please enter a number."
            );
        }
    }

    String newStatus;

    while (true) {
        System.out.println("\n===== UPDATE TOKEN STATUS =====");
        System.out.println("1. Waiting");
        System.out.println("2. In Progress");
        System.out.println("3. Completed");
        System.out.println("4. Cancelled");
        System.out.print("Choose new status: ");

        String statusChoice = sc.nextLine();

        switch (statusChoice) {
            case "1" -> newStatus = "Waiting";
            case "2" -> newStatus = "In Progress";
            case "3" -> newStatus = "Completed";
            case "4" -> newStatus = "Cancelled";
            default -> {
                System.out.println(
                    "Invalid choice. Please select 1 to 4."
                );
                continue;
            }
        }

        break;
    }

    TokenStorage.updateTokenStatus(
        tokenNumber, doctorId, newStatus
    );

    System.out.println("\nUpdated Queue:");
    TokenStorage.viewQueue(doctorId);
}

static void generateBill() {
    int patientId;

    while (true) {
        System.out.print("Enter Patient ID: ");

        try {
            patientId = Integer.parseInt(sc.nextLine());

            if (patientId <= 0) {
                System.out.println("Patient ID must be positive.");
                continue;
            }

            break;
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Enter a whole number.");
        }
    }

    Patient patient = PatientStorage.findPatient(patientId);

    if (patient == null) {
        System.out.println("Patient not found. Cannot generate bill.");
        return;
    }

    System.out.print("Enter Doctor ID: ");
    String doctorId = sc.nextLine().trim();

    DoctorStorage doctorStorage = new DoctorStorage();
    Doctor doctor = doctorStorage.findDoctor(doctorId);

    if (doctor == null) {
        System.out.println("Doctor not found. Cannot generate bill.");
        return;
    }

    int tokenNumber;

    while (true) {
        System.out.print("Enter OPD Token Number: ");

        try {
            tokenNumber = Integer.parseInt(sc.nextLine());

            if (tokenNumber <= 0) {
                System.out.println("Token number must be positive.");
                continue;
            }

            break;
        } catch (NumberFormatException e) {
            System.out.println("Invalid token number. Enter a whole number.");
        }
    }

    Token token = TokenStorage.findToken(tokenNumber, doctorId);

    if (token == null ||
        !token.patientId.equals(String.valueOf(patientId))) {
        System.out.println("Token does not match this patient and doctor.");
        return;
    }

    if (!token.status.equals("Waiting") && !token.status.equals("Completed")) {
        System.out.println("Bill can only be generated for a Waiting or Completed token.");
        return;
    }

    if (BillStorage.hasBillForToken(tokenNumber, doctorId)) {
        System.out.println("A bill already exists for this visit.");
        return;
    }

    float registrationFee =
        BillStorage.hasPreviousBill(patientId) ? 0.0f : 100.0f;

    int billId = BillStorage.getNextBillId();

    Bill bill = new Bill(
        billId,
        tokenNumber,
        patientId,
        doctorId,
        registrationFee,
        doctor.consultationFee,
        "Pending"
    );

    System.out.println("\n===== PATIENT BILL =====");
    System.out.println("Bill ID: " + bill.billId);
    System.out.println("Token Number: " + bill.tokenNumber);
    System.out.println("Patient Name: " + patient.name);
    System.out.println("Doctor Name: " + doctor.name);
    System.out.println("Registration Fee: Rs. " + bill.registrationFee);
    System.out.println("Consultation Fee: Rs. " + bill.consultationFee);
    System.out.println("Total Amount: Rs. " + bill.totalAmount);
    System.out.println("Payment Status: " + bill.paymentStatus);

    BillStorage.saveBill(bill);
}

static void viewHospitalOverview() {
    int totalDoctors = 0;
    int availableDoctors = 0;
    int totalTokens = 0;
    int waitingTokens = 0;
    int pendingBills = 0;

    // Count doctors
    File doctorFile = new File("doctors.txt");

    try (Scanner reader = new Scanner(doctorFile)) {
        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split(",", -1);

            if (data.length == 5) {
                totalDoctors++;

                if (data[4].equalsIgnoreCase("true")) {
                    availableDoctors++;
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Could not read doctor records.");
        return;
    }

    // Count tokens
    File tokenFile = new File("tokens.txt");

    if (tokenFile.exists()) {
        try (Scanner reader = new Scanner(tokenFile)) {
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(",", -1);

                if (data.length == 4) {
                    totalTokens++;

                    if (data[3].equalsIgnoreCase("Waiting")) {
                        waitingTokens++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read token records.");
            return;
        }
    }

    // Count pending bills
    File billFile = new File("bills.txt");

    if (billFile.exists()) {
        try (Scanner reader = new Scanner(billFile)) {
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(",", -1);

                if (data.length == 8 &&
                    data[7].equalsIgnoreCase("Pending")) {
                    pendingBills++;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read bill records.");
            return;
        }
    }

    System.out.println("\n===== HOSPITAL OVERVIEW =====");
    System.out.println("Total Doctors: " + totalDoctors);
    System.out.println("Available Doctors: " + availableDoctors);
    System.out.println("Unavailable Doctors: "
            + (totalDoctors - availableDoctors));
    System.out.println("Total OPD Tokens: " + totalTokens);
    System.out.println("Waiting Patients: " + waitingTokens);
    System.out.println("Pending Bills: " + pendingBills);
}

static void paymentManagement() {
    while (true) {
        System.out.println("\n===== PAYMENT MANAGEMENT =====");
        System.out.println("1. View Pending Bills");
        System.out.println("2. Collect Payment");
        System.out.println("3. View Revenue Report");
        System.out.println("4. Back to Owner Dashboard");
        System.out.print("Enter choice: ");

        String choice = sc.nextLine();

        switch (choice) {
            case "1" -> BillStorage.viewPendingBills();

            case "2" -> {
                int billId;

                while (true) {
                    System.out.print("Enter Bill ID: ");

                    try {
                        billId = Integer.parseInt(sc.nextLine());

                        if (billId <= 0) {
                            System.out.println("Bill ID must be positive.");
                            continue;
                        }

                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a valid numeric Bill ID.");
                    }
                }

                BillStorage.collectPayment(billId);
            }
            case "3" -> BillStorage.viewRevenue();

            case "4" -> {
                return;
            }

            default -> System.out.println("Invalid choice.");
        }
    }
}
}