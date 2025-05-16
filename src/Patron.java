// Patron.java
// Represents a library patron with ID, name, address, and fine amount
public class Patron {
    // Fields for patron details
    private final String id;           // 7-digit numeric ID
    private final String name;         // Patron's full name
    private final String address;      // Mailing address
    private final double overdueFine;  // Overdue fines owed

    /**
     * Construct a new Patron with basic details.
     */
    public Patron(String id, String name, String address, double overdueFine) {
        this.id = id;                    // Assign ID
        this.name = name;                // Assign name
        this.address = address;          // Assign address
        this.overdueFine = overdueFine;  // Assign fine
    }

    // Getters for each field
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public double getOverdueFine() { return overdueFine; }

    /**
     * Print this patron's information to the console.
     */
    public void displayInfo() {
        System.out.printf(
                "ID: %s | Name: %s | Address: %s | Fine: $%.2f%n",
                id, name, address, overdueFine
        );
    }
}