import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;    // Import the File class
import java.io.IOException; // Import this class to handle errors
import java.util.Scanner; //input

public class DoctorStorage {
     File file = new File("doctors.txt");
    //save doctor method
    void saveDoctor(Doctor doctor) {
        try (FileWriter fw = new FileWriter("doctors.txt", true)) {
            fw.write(doctor.id + "," + doctor.name + ","
                    + doctor.specialization + ","
                    + doctor.consultationFee + ","
                    + doctor.available + "\n");

            System.out.println("Doctor saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving doctor.");
        }
    }
    //view doctor method
    void viewDoctors() {

        try (Scanner reader = new Scanner(file)) {
            if (!reader.hasNextLine()) {
                System.out.println("No doctors found.");
                return;
            }

            System.out.println("\n===== DOCTOR LIST =====");

            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(",");

                if (parts.length == 5) {
                    System.out.println("--------------------");
                    System.out.println("ID: " + parts[0]);
                    System.out.println("Name: " + parts[1]);
                    System.out.println("Specialization: " + parts[2]);
                    System.out.println("Fee: " + parts[3]);
                    System.out.println("Available: " + parts[4]);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("No doctor records found yet.");
        }
}

    //verifying whether doctor exists
    boolean doctorExists(String id) {
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                if (parts[0].equals(id)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        }

        return false;
}

    //updating availability 
    boolean updateAvailability(String id, boolean availability) {
        StringBuilder updatedData = new StringBuilder();
        boolean found = false;

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 5 && parts[0].equals(id)) {
                    parts[4] = String.valueOf(availability);
                    line = String.join(",", parts);
                    found = true;
                }

                updatedData.append(line).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            return false;
        }

        if (!found) {
            return false;
        }

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(updatedData.toString());
            return true;
        } catch (IOException e) {
            System.out.println("Error updating doctor availability.");
            return false;
        }
    }
}