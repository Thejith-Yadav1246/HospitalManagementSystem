
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PatientStorage {

    public static void savePatient(Patient patient) {
        try (FileWriter writer = new FileWriter("patients.txt", true)) {
            writer.write(
                patient.patientId + "|" +
                patient.name + "|" +
                patient.age + "|" +
                patient.gender + "|" +
                patient.phone + "|" +
                patient.problem + "\n"
            );
        } catch (IOException e) {
            System.out.println("Error saving patient details.");
        }
    }

    public static boolean patientExists(int id) {
        File file = new File("patients.txt");

        if (!file.exists()) {
            return false;
        }

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split("\\|", -1);

                if (data.length > 0 &&
                    data[0].equals(String.valueOf(id))) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading patient records.");
            return true;
        }

        return false;
    }

    public static Patient findPatient(int id) {
        File file = new File("patients.txt");

        if (!file.exists()) {
            return null;
        }

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split("\\|", -1);

                if (data.length == 6 &&
                    data[0].equals(String.valueOf(id))) {
                    try {
                        return new Patient(
                            Integer.parseInt(data[0]),
                            data[1],
                            Integer.parseInt(data[2]),
                            data[3],
                            data[4],
                            data[5]
                        );
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid patient record: " + id);
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading patient records.");
        }

        return null;
    }
}
