
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BillStorage {

    static int getNextBillId() {
        int maxId = 0;
        File file = new File("bills.txt");

        if (!file.exists()) {
            return 1;
        }

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(",", -1);

                if (data.length == 8) {
                    try {
                        int id = Integer.parseInt(data[0]);
                        maxId = Math.max(maxId, id);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid bill record.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading bills.");
        }

        return maxId + 1;
    }

    static void saveBill(Bill bill) {
        try (FileWriter writer = new FileWriter("bills.txt", true)) {
            writer.write(
                bill.billId + "," +
                bill.tokenNumber + "," +
                bill.patientId + "," +
                bill.doctorId + "," +
                bill.registrationFee + "," +
                bill.consultationFee + "," +
                bill.totalAmount + "," +
                bill.paymentStatus + "\n"
            );

            System.out.println("Bill saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving bill.");
        }
    }

    static boolean hasPreviousBill(int patientId) {
        File file = new File("bills.txt");

        if (!file.exists()) {
            return false;
        }

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(",", -1);

                if (data.length == 8 &&
                    data[2].equals(String.valueOf(patientId))) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking previous bills.");
            return true;
        }

        return false;
    }

    static boolean hasBillForToken(int tokenNumber, String doctorId) {
        File file = new File("bills.txt");

        if (!file.exists()) {
            return false;
        }

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(",", -1);

                if (data.length == 8 &&
                    data[1].equals(String.valueOf(tokenNumber)) &&
                    data[3].equals(doctorId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking existing bills.");
            return true;
        }

        return false;
    }

    static void viewPendingBills() {
    File file = new File("bills.txt");
    boolean found = false;

    if (!file.exists()) {
        System.out.println("No bills found.");
        return;
    }

    try (Scanner reader = new Scanner(file)) {
        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split(",", -1);

            if (data.length == 8 &&
                data[7].equalsIgnoreCase("Pending")) {

                if (!found) {
                    System.out.println("\n===== PENDING BILLS =====");
                    found = true;
                }

                System.out.println("--------------------");
                System.out.println("Bill ID: " + data[0]);
                System.out.println("Token Number: " + data[1]);
                System.out.println("Patient ID: " + data[2]);
                System.out.println("Doctor ID: " + data[3]);
                System.out.println("Registration Fee: Rs. " + data[4]);
                System.out.println("Consultation Fee: Rs. " + data[5]);
                System.out.println("Total Amount: Rs. " + data[6]);
                System.out.println("Payment Status: " + data[7]);
            }
        }

        if (!found) {
            System.out.println("No pending bills.");
        }

    } catch (IOException e) {
        System.out.println("Error reading bills.");
    }
}
static boolean collectPayment(int billId) {
    File file = new File("bills.txt");

    if (!file.exists()) {
        System.out.println("No bills found.");
        return false;
    }

    StringBuilder updatedData = new StringBuilder();
    boolean found = false;

    try (Scanner reader = new Scanner(file)) {
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] data = line.split(",", -1);

            if (data.length == 8 &&
                data[0].equals(String.valueOf(billId))) {

                found = true;

                if (data[7].equalsIgnoreCase("Paid")) {
                    System.out.println("This bill is already paid.");
                    return false;
                }

                if (!data[7].equalsIgnoreCase("Pending")) {
                    System.out.println("Invalid payment status.");
                    return false;
                }

                data[7] = "Paid";
                line = String.join(",", data);
            }

            updatedData.append(line)
                       .append(System.lineSeparator());
        }

    } catch (IOException e) {
        System.out.println("Error reading bills.");
        return false;
    }

    if (!found) {
        System.out.println("Bill ID not found.");
        return false;
    }

    try (FileWriter writer = new FileWriter(file, false)) {
        writer.write(updatedData.toString());
        System.out.println("Payment recorded successfully.");
        return true;

    } catch (IOException e) {
        System.out.println("Error updating payment status.");
        return false;
    }
}
static void viewRevenue() {
    File file = new File("bills.txt");

    double totalRevenue = 0;
    double pendingAmount = 0;

    if (!file.exists()) {
        System.out.println("No billing records found.");
        return;
    }

    try (Scanner reader = new Scanner(file)) {
        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split(",", -1);

            if (data.length == 8) {
                try {
                    double amount = Double.parseDouble(data[6]);

                    if (data[7].equalsIgnoreCase("Paid")) {
                        totalRevenue += amount;
                    } else if (data[7].equalsIgnoreCase("Pending")) {
                        pendingAmount += amount;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid bill record.");
                }
            }
        }

    } catch (IOException e) {
        System.out.println("Error reading billing records.");
        return;
    }

    System.out.println("\n===== REVENUE REPORT =====");
    System.out.printf("Total Revenue Collected: Rs. %.2f%n", totalRevenue);
    System.out.printf("Pending Amount: Rs. %.2f%n", pendingAmount);
    System.out.printf("Total Billed Amount: Rs. %.2f%n",
            totalRevenue + pendingAmount);
}
}
