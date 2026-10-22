
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
                case "1":
                    login("RECEPTIONIST");
                    break;

                case "2":
                    login("OWNER");
                    break;

                case "3":
                    System.out.println("Exiting application...");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
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
        String choice;
        System.out.println("\n=== RECEPTIONIST DASHBOARD ===");
        System.out.println("1. Register New Patient");
        System.out.println("2. Search Existing Patient");
        System.out.println("3. View Available Doctors");
        System.out.println("4. Generate OPD Token");
        System.out.println("5. Manage Patient Queue");
        System.out.println("6. Generate Bill");
        System.out.println("7. View Patient Records");
        System.out.println("8. Logout");
        while (true) {
             System.out.print("Enter choice: ");
                choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Feature coming soon");
                    break;
                case "2":
                    System.out.println("Feature coming soon");
                    break;
                case "3":
                    System.out.println("Feature coming soon");
                    break;
                case "4":
                    System.out.println("Feature coming soon");
                    break;
                case "5":
                    System.out.println("Feature coming soon");
                    break;
                case "6":
                    System.out.println("Feature coming soon");
                    break;
                case "7":
                    System.out.println("Feature coming soon");
                    break;
                case "8":
                    System.out.println("Logging out...");
                return;

                default:
                    System.out.println("Invalid choice");
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
            System.out.println("8. Manage Doctor Availability");
            System.out.println("9. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Feature coming soon");
                    break;
                case "2":
                    System.out.println("Feature coming soon");
                    break;
                case "3":
                    System.out.println("Feature coming soon");
                    break;
                case "4":
                    System.out.println("Feature coming soon");
                    break;
                case "5":
                    System.out.println("Feature coming soon");
                    break;
                case "6":
                    System.out.println("Feature coming soon");
                    break;
                case "7":
                    System.out.println("Feature coming soon");
                    break;
                case "8":
                    System.out.println("Feature coming soon");
                    break;
                case "9":
                    System.out.println("Logging out...");
                return;

                default:
                    System.out.println("Invalid choice");
    }
}
    }
}
