import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
    
public static void viewAllPatients() {
    File file = new File("patients.txt");

    if (!file.exists()) {
        System.out.println("No patient records found.");
        return;
    }

    boolean found = false;

    try (Scanner reader = new Scanner(file)) {
        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split("\\|", -1);

            if (data.length == 6) {
                try {
                    Patient patient = new Patient(
                        Integer.parseInt(data[0]),
                        data[1],
                        Integer.parseInt(data[2]),
                        data[3],
                        data[4],
                        data[5]
                    );

                    if (!found) {
                        System.out.println("\n===== PATIENT RECORDS =====");
                        found = true;
                    }

                    System.out.println("--------------------");
                    System.out.println("Patient ID: " + patient.patientId);
                    System.out.println("Name: " + patient.name);
                    System.out.println("Age: " + patient.age);
                    System.out.println("Gender: " + patient.gender);
                    System.out.println("Phone: " + patient.phone);
                    System.out.println("Health Problem: " + patient.problem);

                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid patient record.");
                }
            }
        }

        if (!found) {
            System.out.println("No valid patient records found.");
        }

    } catch (IOException e) {
        System.out.println("Error reading patient records.");
    }
}
static void viewCommonComplaints() {
    File file = new File("patients.txt");
    Map<String, Integer> complaints = new HashMap<>();

    if (!file.exists()) {
        System.out.println("No patient records found.");
        return;
    }

    try (Scanner reader = new Scanner(file)) {
        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split("\\|", -1);

            if (data.length == 6 && !data[5].trim().isEmpty()) {
               String[] problems = data[5].split(",");

            for (String item : problems) {
                String problem = item.trim().toLowerCase();

                if (!problem.isEmpty()) {
                    complaints.put(problem, complaints.getOrDefault(problem, 0) + 1);
                }
            }
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading patient records.");
        return;
    }

    if (complaints.isEmpty()) {
        System.out.println("No complaints recorded.");
        return;
    }

    ArrayList<Map.Entry<String, Integer>> sortedComplaints =
        new ArrayList<>(complaints.entrySet());

    sortedComplaints.sort(
        Map.Entry.<String, Integer>comparingByValue(
            Comparator.reverseOrder()
        )
    );

    System.out.println("\n===== COMMON PATIENT COMPLAINTS =====");

    for (Map.Entry<String, Integer> entry : sortedComplaints) {
        System.out.println(
            entry.getKey() + " : " + entry.getValue() + " patient(s)"
        );
    }
}
}