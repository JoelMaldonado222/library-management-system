import java.util.Scanner;


// MainMenu class to display menu and handle user interactions
// MainMenu.java
import java.util.Scanner;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibraryManager manager = new LibraryManager();

    public static void main(String[] args) {
        //  Prompt for initial data file
        System.out.print("Enter path to patron data file (or leave blank to skip): ");
        String path = scanner.nextLine().trim();
        if (!path.isEmpty()) {
            manager.addPatronFromFile(path);
            System.out.println("\nPatrons after loading file:");
            manager.displayAllPatrons();
        }

        //  Now drop into your existing menu loop
        while (true) {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add a Patron");
            System.out.println("2. Remove a Patron");
            System.out.println("3. Display All Patrons");
            System.out.println("4. Exit");

            int choice = getIntInput("Choice: ");
            switch (choice) {
                case 1: addPatronInteractive();    break;
                case 2: removePatronInteractive(); break;
                case 3: manager.displayAllPatrons(); break;
                case 4:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Prompt user to add a new patron with validation at each step
    private static void addPatronInteractive() {
        String id = getValidatedInput(
                "Enter 7-digit ID: ",          // Prompt for ID
                "\\d{7}",                       // Regex for exactly 7 digits
                "ID must be exactly 7 digits."    // Error message
        );
        String name = getValidatedInput(
                "Enter full name: ",             // Prompt for name
                "[A-Za-z ]+",                     // Regex for letters and spaces
                "Name must contain only letters and spaces." // Error
        );


        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();

// Loop until a valid address is entered
        while (address.isEmpty()) {
            System.out.println("Address cannot be left blank. Please enter a valid address:");
            address = scanner.nextLine().trim();
        }


        double fine = getDoubleInput(
                "Enter overdue fine ($0–250): ", // Prompt for fine
                0, 250                           // Range limits
        );
        manager.addPatron(new Patron(id, name, address, fine)); // Add to manager
    }

    // Prompt user to remove a patron by validated ID
    private static void removePatronInteractive() {
        String id = getValidatedInput(
                "Enter 7-digit ID to remove: ",  // Prompt for removal ID
                "\\d{7}",                       // Regex check
                "ID must be exactly 7 digits."    // Error message
        );
        manager.removePatron(id);                            // Perform removal
    }

    // Read an integer from console, retrying on invalid input
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);                         // Show prompt
            String line = scanner.nextLine();                 // Read input
            try {
                return Integer.parseInt(line.trim());        // Parse int
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    // Read a double within provided range, retry on errors or out-of-range
    private static double getDoubleInput(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);                         // Show prompt
            String line = scanner.nextLine();                 // Read input
            try {
                double value = Double.parseDouble(line.trim()); // Parse double
                if (value < min || value > max) {            // Check range
                    System.out.printf("Value must be between %.2f and %.2f%n", min, max);
                    continue;
                }
                return value;                                // Valid value
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }


    // Read a string that matches regex, retry on invalid input
    // this method gets called when adding n removing patronssss
    private static String getValidatedInput(String prompt, String regex, String errorMessage) {
        while (true) {
            System.out.print(prompt);                         // Show prompt
            String input = scanner.nextLine().trim();         // Read input
            if (input.matches(regex)) {                       // Validate
                return input;                                 // Return if valid
            }
            System.out.println(errorMessage);                 // Show error
        }
    }
}
