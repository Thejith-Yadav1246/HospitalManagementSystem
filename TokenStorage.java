
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TokenStorage {

    public static void saveToken(Token token) {
        try (FileWriter writer =
                 new FileWriter("tokens.txt", true)) {

            writer.write(
                token.tokenNumber + "," +
                token.patientId + "," +
                token.doctorId + "," +
                token.status + "\n"
            );

        } catch (IOException e) {
            System.out.println("Error saving token.");
        }
    }

    public static int getNextTokenNumber(String doctorId) {
        int maxToken = 0;

        try (Scanner scanner =
                 new Scanner(new File("tokens.txt"))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                if (data.length == 4 &&
                    data[2].equals(doctorId)) {

                    int tokenNumber = Integer.parseInt(data[0]);

                    if (tokenNumber > maxToken) {
                        maxToken = tokenNumber;
                    }
                }
            }

        } catch (IOException e) {
            // File may not exist yet.
        }

        return maxToken + 1;
    }

    public static void generateToken(int patientId, String doctorId) {
        doctorId = doctorId.trim();
        if (!PatientStorage.patientExists(patientId)) {
            System.out.println("Patient does not exist.");
            return;
        }

        DoctorStorage doctorStorage = new DoctorStorage();

        if (!doctorStorage.doctorExists(doctorId)) {
            System.out.println("Doctor does not exist.");
            return;
        }

        if (!doctorStorage.isDoctorAvailable(doctorId)) {
            System.out.println("Doctor is unavailable.");
            return;
        }

        if (hasActiveToken(patientId, doctorId)) {
            System.out.println(
                "Patient already has an active token for this doctor."
            );
            return;
        }

        int tokenNumber = getNextTokenNumber(doctorId);

        Token token = new Token(
            tokenNumber,
            String.valueOf(patientId),
            doctorId,
            "Waiting"
        );

        saveToken(token);

        System.out.println("Token generated successfully.");
        System.out.println("Token Number: " + tokenNumber);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Status: Waiting");
    }

    public static void viewQueue(String doctorId) {
        File file = new File("tokens.txt");

        if (!file.exists()) {
            System.out.println("No tokens found.");
            return;
        }

        ArrayList<String[]> queue = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",", -1);

                if (data.length == 4 && data[2].equals(doctorId)) {
                    try {
                        Integer.parseInt(data[0]);
                        queue.add(data);
                    } catch (NumberFormatException e) {
                        System.out.println(
                            "Skipping invalid token record."
                        );
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading token records.");
            return;
        }

        if (queue.isEmpty()) {
            System.out.println("No tokens found for this doctor.");
            return;
        }

        queue.sort(
            (a, b) -> Integer.compare(
                Integer.parseInt(a[0]),
                Integer.parseInt(b[0])
            )
        );

        System.out.println("\n===== PATIENT QUEUE =====");
        System.out.println("Doctor ID: " + doctorId);

        for (String[] data : queue) {
            System.out.println(
                "Token No: " + data[0] +
                " | Patient ID: " + data[1] +
                " | Status: " + data[3]
            );
        }
    }

    public static boolean hasActiveToken(int patientId, String doctorId) {
        File file = new File("tokens.txt");

        if (!file.exists()) {
            return false;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",", -1);

                if (data.length == 4
                        && data[1].equals(String.valueOf(patientId))
                        && data[2].equals(doctorId)
                        && (data[3].equals("Waiting")
                            || data[3].equals("In Progress"))) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading token records.");
            return true;
        }

        return false;
    }

    public static boolean updateTokenStatus(
            int tokenNumber, String doctorId, String newStatus) {

        if (!newStatus.equals("Waiting")
                && !newStatus.equals("In Progress")
                && !newStatus.equals("Completed")
                && !newStatus.equals("Cancelled")) {
            System.out.println("Invalid token status.");
            return false;
        }

        File file = new File("tokens.txt");

        if (!file.exists()) {
            System.out.println("No tokens found.");
            return false;
        }

        StringBuilder updatedData = new StringBuilder();
        boolean found = false;
        boolean validTransition = false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",", -1);

                if (data.length == 4
                        && data[0].equals(String.valueOf(tokenNumber))
                        && data[2].equals(doctorId)) {

                    found = true;
                    String currentStatus = data[3];

                    if (currentStatus.equals(newStatus)) {
                        System.out.println(
                            "Token already has this status."
                        );
                        return false;
                    }

                    switch (currentStatus) {
                        case "Waiting" ->
                            validTransition =
                                newStatus.equals("In Progress")
                                || newStatus.equals("Cancelled");

                        case "In Progress" ->
                            validTransition =
                                newStatus.equals("Completed");

                        case "Completed", "Cancelled" ->
                            validTransition = false;

                        default -> validTransition = false;
                    }

                    if (!validTransition) {
                        System.out.println(
                            "Invalid status transition: "
                            + currentStatus + " -> " + newStatus
                        );
                        return false;
                    }

                    data[3] = newStatus;
                    line = String.join(",", data);
                }

                updatedData.append(line)
                           .append(System.lineSeparator());
            }

        } catch (IOException e) {
            System.out.println("Error reading token records.");
            return false;
        }

        if (!found) {
            System.out.println("Token not found.");
            return false;
        }

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(updatedData.toString());
            System.out.println("Token status updated successfully.");
            return true;

        } catch (IOException e) {
            System.out.println("Error updating token status.");
            return false;
        }
    }

    public static boolean tokenExists(int tokenNumber, String doctorId) {
        File file = new File("tokens.txt");

        if (!file.exists()) {
            return false;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",", -1);

                if (data.length == 4
                        && data[0].equals(String.valueOf(tokenNumber))
                        && data[2].equals(doctorId)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading token records.");
        }

        return false;
    }

    public static boolean hasTokensForDoctor(String doctorId) {
        File file = new File("tokens.txt");

        if (!file.exists()) {
            return false;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",", -1);

                if (data.length == 4 && data[2].equals(doctorId)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading token records.");
        }

        return false;
    }
    
static Token findToken(int tokenNumber, String doctorId) {
    File file = new File("tokens.txt");

    try (Scanner reader = new Scanner(file)) {
        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split(",", -1);

            if (data.length == 4 &&
                data[2].equals(doctorId)) {

                try {
                    if (Integer.parseInt(data[0]) == tokenNumber) {
                        return new Token(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            data[3]
                        );
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid token records.
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading token records.");
    }

    return null;
}

}