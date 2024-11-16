import java.util.Scanner;

// Reference - Learned how to write JavaDoc comments, using ChatGPT and Java Documentation.

public class PlaneManagement {
    /**
     * Represents the seat arrangement using a Rectangular 2D array.
     * Seat status:
     * "-1" = Invalid seats
     * "0" = Available seats
     * "1" = Booked seats
     */
    private static final int[][] seatsArr = new int[4][14];
    /**
     * Tickets list array to store Ticket objects.
     */
    static final Ticket[] tickets = new Ticket[52];
    /**
     * Persons list array to store Person objects.
     */
    private static final Person[] persons = new Person[52];

    //Seat coordinates declaration
    /**
     * Represents the row letter of the selected seat.
     */
    static String rowLetter = "";
    /**
     * Uses as the index of row letter in selection of the seats.
     */
    static int rowLetterIndex = -1;
    /**
     * Uses as the index of seat number in selection of the seats.
     */

    static int seatIndex = -1;
    /**
     * Keeps track of indexes in the Person & Ticket arrays during operations.
     */
    static int IndexCount = 0;

    /**
     * Uses to get user input from the console.
     */
    private static final Scanner input = new Scanner(System.in);

    /**
     * The main method to start the Plane Management application.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("\nWelcome to the Plane Management application");

        //Mark invalid objects as "-1"
        seatsArr[1][12] = -1;
        seatsArr[1][13] = -1;
        seatsArr[2][12] = -1;
        seatsArr[2][13] = -1;

        userMenu();
    }

    //=================================================   User menu   ==================================================

    /**
     * Allows user to select functions from the menu.
     */
    private static void userMenu() {

        System.out.println("\n**************************************************");
        System.out.println("*                  MENU OPTIONS                  *");
        System.out.println("**************************************************\n");

        System.out.println("""
                    1) Buy a seat
                    2) Cancel a seat
                    3) Find first available seat
                    4) Show seating plan
                    5) Print tickets information and total sales
                    6) Search tickets
                    0) Quit
                    
                **************************************************""");
//----------------------------------------------------------------------------------------------------------------------

        //option selection
        while (true) {
            System.out.print("\nPlease select an option: ");

            try {
                //Take input as a String and converted to Integer.
                int selectOption = Integer.parseInt(input.nextLine());

                switch (selectOption) {
                    case 0:
                        System.out.println("\n**************************************************");
                        System.out.println("               Hava a Great Day !!!               ");
                        System.out.println("**************************************************");
                        System.exit(0);
                        break;
                    case 1:
                        buy_seat();
                        break;
                    case 2:
                        cancel_seat();
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    default:
                        System.out.println("--------------------------------------------------");
                        System.out.println("* Enter a valid number from 0 to 6");
                        System.out.println("--------------------------------------------------");
                        continue;
                }

            } catch (NumberFormatException e) {
                System.out.println("--------------------------------------------------");
                System.out.println("* Enter a valid number from 0 to 6");
                System.out.println("--------------------------------------------------");
                continue;
            }
            break;
        }
    }

    //===========================================  Check array full or empty  ==========================================

    /**
     * Check the status of the seatArr and tickets according to the specified actions.
     *
     * @param action Switching to a function according to the action.
     *               - "full" for checking if seatsArr array is full.
     *               -"null" for checking if tickets array is empty.
     */
    private static void arrFullOrNull(String action) {
        if (action.equals("null")) {
            boolean noTickets = true;

            for (Ticket element : tickets) {
                if (element != null) {
                    noTickets = false;
                    break;
                }
            }

            if (noTickets) {
                System.out.println("--------------------------------------------------\nNo tickets have been booked yet.\n--------------------------------------------------");
                userMenu();
            }

        } else if (action.equals("full")) {
            boolean arrFull = true;

            for (int[] row : seatsArr) {
                for (int seat : row) {
                    if (seat != 1 && seat != -1) {
                        arrFull = false;
                        break;
                    }
                }
            }
            if (arrFull) {
                System.out.println("\n--------------------------------------------------\nAll seats have been booked.\n--------------------------------------------------\n ");
                userMenu();
            }

        }
    }

    //=============================================  Check seat validation  ============================================

    /**
     * Input validation of the user inputs.
     * Convert the entered row letter into index for easier usage in the code.
     * NumberFormatException use to handle invalid inputs.
     */
    private static void inputSeatValidation() {
        while (true) {
            //row letter
            System.out.print("\nInput a row letter (A/B/C/D): ");
            rowLetter = input.nextLine().toUpperCase();

            switch (rowLetter) {
                case "A":
                    rowLetterIndex = 0;
                    break;
                case "B":
                    rowLetterIndex = 1;
                    break;
                case "C":
                    rowLetterIndex = 2;
                    break;
                case "D":
                    rowLetterIndex = 3;
                    break;
                default:
                    System.out.println("--------------------------------------------------\n* Enter a valid row letter\n--------------------------------------------------");
                    continue;
            }
            //----------------------------------------------------------------------------------------------------------
            //seat number
            System.out.print("Input a seat number (1 - 14): ");

            try {
                seatIndex = Integer.parseInt(input.nextLine()) - 1; //To adjust the value to meet "o" index

                if (seatIndex < 0 || seatIndex > 13) {
                    System.out.println("--------------------------------------------------\n* Enter a valid seat number from 1 to 14\n--------------------------------------------------");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("--------------------------------------------------\n* Enter a valid number from 1 to 14\n--------------------------------------------------");
                continue;
            }
            break;
        }
    }

    //==================================  Exit or switching to another function  =======================================

    /**
     * Allows users confirm an action  (buy seat, cancel seat, etc.) or to exit the program.
     *
     * @param action Switching to a function according to the action.
     *               - "buy" for buying seat.
     *               - "cancel" for cancelling seat.
     *               - "firstSeat" for finding the first available seat.
     *               - "seatingPlan" for displaying the seating plan.
     *               - "printTickets" for printing tickets information and total sales.
     *               - "searchTicket" for searching for Tickets.
     */
    private static void menuOrExitConfirmation(String action) {
        while (true) {
            String confirm1 = input.nextLine().toUpperCase(); //To get String input and convert it to upperCase

            switch (confirm1) {
                case "Y":
                    //Execute functions according to the action
                    switch (action) {
                        case "buy":
                            buy_seat();
                            break;
                        case "cancel":
                            cancel_seat();
                            break;
                        case "firstSeat":
                            find_first_available();
                            break;
                        case "seatingPlan":
                            show_seating_plan();
                            break;
                        case "printTickets":
                            print_tickets_info();
                            break;
                        case "searchTickets":
                            search_ticket();
                            break;
                    }
                    break;

                case "M":
                    System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    userMenu();
                    break;
                case "N":
                    System.out.println("\n**************************************************");
                    System.out.println("               Hava a Great Day !!!               ");
                    System.out.println("**************************************************");
                    System.exit(0);
                    break;
                default:
                    System.out.println("--------------------------------------------------\n* Invalid selection\n--------------------------------------------------");
                    System.out.print("Input the selection (y/m/n): ");
                    continue;
            }
            break;
        }
    }

    //===================================  Getting details when buying ticket  =========================================

    /**
     * Get user details for the purchase.
     * Price is given according to the range of seat number
     * Create {@link Person} object and set name, surname and email with validation.
     * Create {@link Ticket} object and set row, number and price.
     * Create {@code persons} array and add {@link Person} objects into it.
     * Create {@code tickets} array and add {@link Ticket} objects into it.
     */
    private void buyTicket() {
        Person person = new Person();
        Ticket ticket = new Ticket();

        while (true) {
            System.out.print("\nEnter the passenger's name: ");
            String name = input.nextLine();


            if (name.isEmpty()) {
                System.out.println("--------------------------------------------------\n* Enter a valid name.\n--------------------------------------------------");
                continue;
            } else {
                person.setName(name);
            }

            System.out.print("Enter the passenger's surname: ");
            String surname = input.nextLine();
            if (surname.isEmpty()) {
                System.out.println("--------------------------------------------------\n* Enter the surname correctly.\n--------------------------------------------------");
                continue;
            } else {
                person.setSurname(surname);
            }

            System.out.print("Enter the passenger's email: ");
            //To remove white spaces and lowercase
            String email = input.nextLine().trim().toLowerCase();

            if (email.isEmpty()) {
                System.out.println("--------------------------------------------------\n* Enter a valid email.\n--------------------------------------------------");
                continue;
            } else {
                person.setEmail(email);
            }
            break;
        }
        //Add object into persons array
        persons[IndexCount] = person;

        //--------------------------------------------------------------------------------------------------------------

        ticket.setRow(rowLetter);
        ticket.setSeat(seatIndex + 1);

        //price for each seat.
        if (seatIndex >= 0 && seatIndex <= 4) {
            ticket.setPrice(200);

        } else if (seatIndex >= 5 && seatIndex <= 8) {
            ticket.setPrice(150);

        } else if (seatIndex >= 9 && seatIndex <= 13) {
            ticket.setPrice(180);

        } else {
            ticket.setPrice(-1); //Invalid price
        }
        ticket.setPerson(persons[IndexCount]);

        //Add object into tickets array
        tickets[IndexCount] = ticket;

        //save to a file
        ticket.save();
        IndexCount++;
    }

    //===================================================  Buy seat  ===================================================

    /**
     * Call inputSeatValidation() for input validation.
     * -Check seat availability.
     * Initiates seat purchase if the seat is available.
     * If the array is full, return back to the user menu.
     */
    private static void buy_seat() {
        System.out.println("\n**************************************************");
        System.out.println("*                   Buy a Seat                   *");
        System.out.println("**************************************************");

        //Check if the seatArr is full or not
        arrFullOrNull("full");

        while (true) {
            inputSeatValidation();

            if (seatsArr[rowLetterIndex][seatIndex] == 1) {
                System.out.println("\n==================================================");
                System.out.println("      The seat has sold. Let's Try again...       ");
                System.out.println("==================================================");
                System.out.print("\nDo you like to buy another seat (y) \nor go to main menu (m) or exit the program (n)?: ");
                menuOrExitConfirmation("buy");

            } else if (seatsArr[rowLetterIndex][seatIndex] == -1) {
                System.out.println("--------------------------------------------------\n* Invalid seat number\n--------------------------------------------------");
                inputSeatValidation();

            } else {
                System.out.println("\n==================================================");
                System.out.println("The seat is available...");
                System.out.println("==================================================");

                System.out.print("\nDo you like to buy the " + rowLetter + "-" + (seatIndex + 1) + " seat (y) or not (n): ");
                String confirm2 = input.nextLine().toUpperCase();
                //------------------------------------------------------------------------------------------------------

                if (confirm2.equals("Y")) {
                    PlaneManagement planeManagement = new PlaneManagement();
                    planeManagement.buyTicket();
                    //--------------------------------------------------------------------------------------------------
                    seatsArr[rowLetterIndex][seatIndex] = 1;
                    System.out.println("\n==================================================");
                    System.out.println("The seat booked successfully :)");
                    System.out.println("==================================================");
                    System.out.print("\nDo you like to buy another seat (y) \nor go to main menu (m) or exit the program (n)?: ");
                    menuOrExitConfirmation("buy");

                } else if (confirm2.equals("N")) {
                    System.out.println("\n==================================================");
                    System.out.println("You haven't booked the seat.");
                    System.out.println("==================================================");
                    System.out.print("\nDo you like to buy another seat (y) \nor go to main menu (m) or exit the program (n)?: ");
                    menuOrExitConfirmation("buy");
                } else {
                    System.out.println("--------------------------------------------------\n* Invalid Input\n--------------------------------------------------");
                    continue;
                }
                break;
            }
        }
    }

    //=========================================  Cancelling bought tickets  ============================================

    /**
     * To cancel a booked ticket and remove from the tickets array.
     * Check each elements in tickets array with user inputs.
     */
    private void cancelTicket() {
        int index = 0;
        for (Ticket element : tickets) {
            if (element != null) {
                if (element.getRow().equals(rowLetter) && element.getSeat() == seatIndex + 1) {
                    element.delete();

                    /*
                    Reference - Learned how to remove array elements
                    YouTube video https://www.youtube.com/watch?v=wOiUr9Rmgr0
                    */
                    for (int i = index; i < tickets.length - 1; i++) {
                        //Replace the removed ticket from the right side ticket (Shifting right side elements to left side to fill the vacancy)
                        tickets[i] = tickets[i + 1];
                    }
                    //Set the last element of the tickets array to null
                    tickets[tickets.length - 1] = null;
                    break;
                }
            }
            index++;
        }
    }

    //==================================================  Cancel seat  =================================================

    /**
     * Check input validation.
     * Ask user's email to confirm the cancellation.
     * Check that email with the email of the selected seat.
     * Execute {@code cancelTicket(int index)} and remove the ticket from tickets array.
     */
    public static void cancel_seat() {
        System.out.println("\n**************************************************");
        System.out.println("*                 Cancel a Seat                  *");
        System.out.println("**************************************************");

        //check if arr is empty or not.
        arrFullOrNull("null");

        while (true) {
            inputSeatValidation();

            if (seatsArr[rowLetterIndex][seatIndex] == -1) {
                System.out.println("--------------------------------------------------\n* Invalid seat number\n--------------------------------------------------");
                inputSeatValidation();

            } else if (seatsArr[rowLetterIndex][seatIndex] == 0) {
                System.out.println("\n==================================================");
                System.out.println("The seat is already available");
                System.out.println("==================================================");

                System.out.print("\nDo you like to cancel another seat (y) \nor go to main menu (m) or exit the program (n)?: ");
                menuOrExitConfirmation("cancel");

            } else {
                System.out.print("\nDo you like to cancel " + rowLetter + "-" + (seatIndex + 1) + " seat (y) or not (n)?: ");
                String confirm = input.nextLine().toUpperCase();

                if (confirm.equals("Y")) {
                    //Email confirmation & perform actions after the confirmation.
                    for (Ticket element : tickets) {
                        if (element != null && rowLetter.equals(element.getRow()) && (seatIndex + 1) == element.getSeat()) {
                            System.out.print("\nConfirm your email address to cancel the booking: ");
                            String confirmEmail = input.nextLine().toLowerCase();

                            if (confirmEmail.equals(element.getPerson().getEmail())) {
                                //--------------------------------------------------------------------------------------------------
                                PlaneManagement planeManagement = new PlaneManagement();
                                planeManagement.cancelTicket();
                                //--------------------------------------------------------------------------------------------------
                                seatsArr[rowLetterIndex][seatIndex] = 0;
                                System.out.println("==================================================");
                                System.out.println("The seat booking has been canceled successfully :)");
                                System.out.println("==================================================");

                            } else {
                                System.out.println("\n--------------------------------------------------");
                                System.out.println("Email doesn't match with the selected seat.");
                                System.out.println("--------------------------------------------------");

                                System.out.println("\n--------------------------------------------------");
                                System.out.println("The seat was not cancelled");
                                System.out.println("--------------------------------------------------");
                            }

                            System.out.print("\nDo you like to try again (y) or \ngo to main menu (m) or exit the program (n): ");
                            menuOrExitConfirmation("cancel");
                            break;
                        }
                    }

                } else if (confirm.equals("N")) {
                    System.out.println("==================================================");
                    System.out.println("You haven't booked the seat.");
                    System.out.println("==================================================");

                    System.out.print("\nDo you like to cancel another seat (y) \nor go to main menu (m) or exit the program (n)?: ");
                    menuOrExitConfirmation("cancel");
                } else {
                    System.out.println("--------------------------------------------------\n* Invalid Input\n--------------------------------------------------");
                    continue;
                }
            }
            break;
        }


    }

    //=======================================  Finding the first available seat  =======================================

    /**
     * Finds the first available seat, display the row letter and seat number.
     * Linear search applied.
     */
    private static void find_first_available() {
        System.out.println("\n**************************************************");
        System.out.println("*            Find first available Seat           *");
        System.out.println("**************************************************");

        //Access seatsArr using the variable "i" for rows and "j" for seat number in the related row
        for (int i = 0; i < seatsArr.length; i++) {
            for (int j = 0; j < seatsArr[i].length; j++) {
                if (seatsArr[i][j] == 0) {
                    switch (i) {
                        case 0:
                            System.out.println("Seat row letter: A");
                            System.out.println("Seat number:     " + (j + 1));
                            break;
                        case 1:
                            System.out.println("Seat row letter: B");
                            System.out.println("Seat number:     " + (j + 1));
                            break;
                        case 2:
                            System.out.println("Seat row letter: C");
                            System.out.println("Seat number:     " + (j + 1));
                            break;
                        case 3:
                            System.out.println("Seat row letter: D");
                            System.out.println("Seat number:     " + (j + 1));
                            break;
                    }
                    System.out.print("\nDo you like to recheck the first seat available (y) \nor go to main menu (m) or exit the program (n): ");
                    menuOrExitConfirmation("firstSeat");
                }
            }
        }
        System.out.println("\n==================================================");
        System.out.println("No seats are available for booking :(");
        System.out.println("==================================================");

        System.out.print("\nDo you like to recheck the first seat available (y) \nor go to main menu (m) or exit the program (n): ");
        menuOrExitConfirmation("firstSeat");
    }

    //===========================================  Show visual seating plan  ===========================================

    /**
     * Displays a visual representation of the seating plan.
     */
    private static void show_seating_plan() {
        System.out.println("\n**************************************************");
        System.out.println("*                Show seating plan               *");
        System.out.println("**************************************************\n");

        int count = 1;
        for (int[] row : seatsArr) {
            //Indicate the row letter before each row
            if (count == 1) {
                System.out.print("A | ");
            } else if (count == 2) {
                System.out.print("B | ");
            } else if (count == 3) {
                System.out.print("C | ");
            } else if (count == 4) {
                System.out.print("D | ");
            }

            for (int seat : row) {
                if (seat == 0) {
                    System.out.print(" O ");
                } else if (seat == -1) {
                    System.out.print("   ");
                } else {
                    System.out.print(" X ");
                }
            }
            System.out.println();
            count++;
        }
        System.out.print("\nDo you like to recheck the Seating Plan (y) \nor go to main menu (m) or exit the program (n): ");
        menuOrExitConfirmation("seatingPlan");
    }

    //===================================  Print tickets information and total sales  ==================================

    /**
     * Displays ticket information.
     * Provides option to re-display the ticket information, go to the main menu or exit the program.
     */
    private static void print_tickets_info() {
        while (true) {
            System.out.println("\n**************************************************");
            System.out.println("*    Print tickets information and total sales   *");
            System.out.println("**************************************************\n");

            int totalPrice = 0;
            boolean seatBooked = false;
            int count = 1;

            for (Ticket element : tickets) {

                if (element != null) {
                    System.out.println("--------------------------------------------------");
                    System.out.println("                    " + "Ticket " + count);
                    System.out.println("--------------------------------------------------\n");

                    System.out.println(element);
                    seatBooked = true;
                    totalPrice += element.getPrice();
                }
                count++;
            }

            //Displays if there's no seat has been booked in the plane.
            if (!seatBooked) {
                System.out.println("==================================================");
                System.out.println("There's no seat has been booked yet.");
                System.out.println("==================================================");
                System.out.print("\nDo you like to recheck tickets information (y) or \ngo to main menu (m) or exit the program (n): ");
                menuOrExitConfirmation("printTickets");
                break;
            }

            System.out.println("\n--------------------------------------------------");
            System.out.println("Total Amount: £" + totalPrice);
            System.out.println("--------------------------------------------------");

            System.out.print("\nDo you like to re-display tickets information (y) or \ngo to main menu (m) or exit the program (n): ");
            menuOrExitConfirmation("printTickets");
        }
    }

    //================================================  Search Tickets  ================================================

    /**
     * Allows user to search seat according to the seat selection.
     * Check seat availability.
     * Display ticket details if the seat is booked and the confirmation email address matches.
     * Allows to research tickets, go to main menu or exit the program.
     */
    public static void search_ticket() {
        System.out.println("\n**************************************************");
        System.out.println("*                 Search tickets                 *");
        System.out.println("**************************************************");

        arrFullOrNull("null");

        while (true) {
            inputSeatValidation();

            if (seatsArr[rowLetterIndex][seatIndex] == -1) {
                System.out.println("\n--------------------------------------------------");
                System.out.println("Invalid seat number.");
                System.out.println("--------------------------------------------------\n");
                continue;

            } else if (seatsArr[rowLetterIndex][seatIndex] == 0) {
                System.out.println("\n--------------------------------------------------");
                System.out.println("This seat is available");
                System.out.println("--------------------------------------------------");

                System.out.print("\nDo you like to re-search tickets (y) or \ngo to main menu (m) or exit the program (n): ");
                menuOrExitConfirmation("searchTickets");
                break;

            } else {
                System.out.println("\n==================================================");
                System.out.println("The seat has been booked.");
                System.out.println("==================================================");


                //Email confirmation & perform actions after the confirmation.
                for (Ticket element : tickets) {
                    if (element != null && rowLetter.equals(element.getRow()) && (seatIndex + 1) == element.getSeat()) {
                        System.out.print("\nConfirm your email address to display \nTicket and Personal information: ");
                        String confirmEmail = input.nextLine().toLowerCase();

                        if (confirmEmail.equals(element.getPerson().getEmail())) {
                            System.out.println(element);
                        } else {
                            System.out.println("\n--------------------------------------------------");
                            System.out.println("Email doesn't match...");
                            System.out.println("You should have bought a seat to display \nTicket and Personal information");
                            System.out.println("--------------------------------------------------");
                        }
                        System.out.print("\nDo you like to re-search tickets (y) or \ngo to main menu (m) or exit the program (n): ");
                        menuOrExitConfirmation("searchTickets");
                        break;
                    }
                }
            }
            break;
        }

    }
}
