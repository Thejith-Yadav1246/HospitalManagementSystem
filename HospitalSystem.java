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
                && username.equals("staff")
                && password.equals("staff123")) {
            receptionistDashboard();
        } else if (role.equals("OWNER")
                && username.equals("owner")
                && password.equals("owner123")) {
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
        System.out.println("8. Logout");
        System.out.print("Enter choice: ");
        String choice;
        choice = sc.nextLine();

            switch (choice) {
                case "1" -> registerPatient();
                case "2" -> searchPatient();
                case "3" -> System.out.println("Feature coming soon");
                case "4" -> System.out.println("Feature coming soon");
                case "5" -> System.out.println("Feature coming soon");
                case "6" -> System.out.println("Feature coming soon");
                case "7" -> System.out.println("Feature coming soon");
                case "8" -> {
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
            System.out.println("3. View Daily Reports");
            System.out.println("4. View Weekly Reports");
            System.out.println("5. View Monthly Reports");
            System.out.println("6. View Revenue and Payments");
            System.out.println("7. View Common Complaints");
            System.out.println("8. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextLine();

            switch (choice) {
                case "1" -> manageDoctors();
                case "2" -> System.out.println("Feature coming soon");
                case "3" -> System.out.println("Feature coming soon");
                case "4" -> System.out.println("Feature coming soon");
                case "5" -> System.out.println("Feature coming soon");
                case "6" -> System.out.println("Feature coming soon");
                case "7" -> System.out.println("Feature coming soon");
                case "8" -> {
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

}