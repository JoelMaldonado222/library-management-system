// LibraryManager.java
// Manages a collection of Patron objects
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LibraryManager {
    // Internal list to store patrons
    private final List<Patron> patrons = new ArrayList<>();

    /**
     * Add a new patron if ID is unique.
     */
    public void addPatron(Patron p) {
        for (Patron existing : patrons) {                   // Check each existing
            if (existing.getId().equals(p.getId())) {      // Duplicate ID?
                System.out.println("Error: Patron ID already exists.");
                return;                                    // Exit without adding
            }
        }
        patrons.add(p);                                     // Add to list
        System.out.println("Patron added successfully.");
    }

    /**
     * Remove a patron by their ID.
     */
    public void removePatron(String id) {
        Iterator<Patron> iter = patrons.iterator();        // Iterator for safe removal
        // dont want  // throws ConcurrentModificationException
        while (iter.hasNext()) {
            Patron p = iter.next();                         // Next patron
            if (p.getId().equals(id)) {                     // Match found?
                iter.remove();                              // Remove
                System.out.println("Patron removed successfully.");
                return;                                    // Done
            }
        }
        System.out.println("Error: Patron not found.");  // No match
    }

    /**
     * Display all patrons or a message if none exist.
     */
    public void displayAllPatrons() {
        if (patrons.isEmpty()) {                            // Check empty
            System.out.println("No patrons in system.");
        } else {
            for (Patron p : patrons) {                     // Loop through patrons
                p.displayInfo();                           // Print each
            }
        }
    }

    /**
     * Load patrons from a file (format: ID-Name-Address-Fine).
     */
    public void addPatronFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {        // Read each line
                String[] parts = line.split("-");         // Split on dash
                if (parts.length != 4) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;                                // Skip bad format
                }
                try {
                    String id      = parts[0].trim();
                    String name    = parts[1].trim();
                    String address = parts[2].trim();
                    double fine    = Double.parseDouble(parts[3].trim());
                    addPatron(new Patron(id, name, address, fine));
                } catch (Exception e) {
                    System.err.println("Skipping invalid data: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load file: " + e.getMessage());
        }
    }
}