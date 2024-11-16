import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a ticket for a plane seat.
 */
public class Ticket {
    private String row;
    private int seat;
    private int price;
    private Person person;

    /**
     * Construct a no-argument ticket with default values.
     */
    public Ticket() {
        this.row = "";
        this.seat = 0;
        this.price = -1;
        this.person = null;
    }

    /**
     * Overrides the default toString in Object.java to display ticket details with personal details.
     *
     * @return Uses to display and print ticket details with personal details.
     */
    @Override
    public String toString() {
        return person.toString() + "\nSeat Details\n\n" + "Row:      " + getRow() + "\n" + "Seat:     " + getSeat() + "\n" + "Price:    £" + getPrice();
    }

    /**
     * Saves ticket information to a file located at "Tickets" folder in the project directory.
     * Filename is created using row letter and seat number.
     */
    public void save() {
        String fileName = PlaneManagement.rowLetter + (PlaneManagement.seatIndex + 1) + ".txt";

        try {
            //Create a directory for Tickets.
            File folder = new File("./Tickets/");
            boolean folderCreated = folder.mkdir();

            if (folderCreated) {
                System.out.println("\n\"Tickets\" folder created inside the project folder.");
            }

            //Create file inside the directory.
            File file = new File("./Tickets/" + fileName);
            boolean fileCreated = file.createNewFile();

            if (fileCreated) {
                System.out.println("\n\"" + fileName + "\"" + " file created in the \"Tickets\" folder.");
            }

            //write
            FileWriter fileWriter = new FileWriter("./Tickets/" + fileName);
            String fileLine = PlaneManagement.tickets[PlaneManagement.IndexCount].toString();

            fileWriter.write(fileLine);
            fileWriter.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Deletes the selected ticket file.
     * The file is identified using seat coordinates and the person's email.
     */
    public void delete() {
        String fileNameToFind = PlaneManagement.rowLetter + (PlaneManagement.seatIndex + 1) + ".txt";

        try {
            File folderDirectory = new File("./Tickets/");

            File[] printedFiles = folderDirectory.listFiles();

            if (printedFiles != null) {

                for (File ticket : printedFiles) {
                    if (ticket.getName().equals(fileNameToFind)) {

                        if (ticket.delete()) {
                            System.out.println("\n" + fileNameToFind + " file deleted successfully.\n");
                            break;
                        } else {
                            System.out.println(fileNameToFind + " file couldn't be deleted.");
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: NullPointerException");
        }
    }

    //getters

    /**
     * Gets the row letter of the seat.
     *
     * @return The row letter of the seat.
     */
    public String getRow() {
        return row;
    }

    /**
     * Gets the seat number of the seat.
     *
     * @return The seat number of the seat.
     */
    public int getSeat() {
        return seat;
    }

    /**
     * Gets the price of the seat.
     *
     * @return The price of the seat.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gets the person of the relevant ticket.
     *
     * @return The person of the relevant ticket.
     */
    public Person getPerson() {
        return person;
    }

    //setters

    /**
     * Sets the row letter of the seat.
     *
     * @param row The row letter of the seat.
     */
    public void setRow(String row) {
        this.row = row;
    }

    /**
     * Sets the seat number of the seat.
     *
     * @param seat The seat number of the seat.
     */
    public void setSeat(int seat) {
        this.seat = seat;
    }

    /**
     * Sets the price of the seat.
     *
     * @param price The price of the seat.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Sets the person of the relevant ticket.
     *
     * @param person The person of the relevant ticket.
     */
    public void setPerson(Person person) {
        this.person = person;
    }
}
