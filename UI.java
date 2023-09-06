import java.util.Scanner;

public class UI {
    private PassengerDatabase passengerDatabase;
    private Plane plane;
    private int[] costs = { 1800, 1200, 1000 };

    private Scanner numScanner = new Scanner(System.in);
    private Scanner stringScanner = new Scanner(System.in);

    public UI() {
        passengerDatabase = new PassengerDatabase();
        plane = new Plane(6, 15, 19, costs);
    }

    public void run() {

        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = checkIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    bookSingleSeat();
                    break;
                case 2:
                    bookMultipleSeats();
                    break;
                case 3:
                    viewAvailableSeats();
                    break;
                case 4:
                    printCurrentPassengers();
                    break;
                case 5:
                    searchPassengerLastName();
                    break;
                case 6:
                    deletePassenger();
                    break;
                case 7:
                    printPlaneCostReport();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting...");
                    numScanner.close();
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\nMain Menu Options:");
        System.out.println("1. Book Single Seat");
        System.out.println("2. Book Multiple Seats");
        System.out.println("3. View Available Seats");
        System.out.println("4. Print Current Passengers");
        System.out.println("5. Search Passenger by Last Name");
        System.out.println("6. Delete Passengers");
        System.out.println("7. Print Plane Price Report");
        System.out.println("8. Exit\n");
    }

    private int checkIntInput(String prompt) {
        int input = 0;
        boolean validInput = false;
    
        while (!validInput) {
            System.out.print(prompt);
    
            if (numScanner.hasNextInt()) {
                input = numScanner.nextInt();
                numScanner.nextLine(); // Clear the scanner buffer
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter an integer."); 
                numScanner.nextLine(); // Clear the scanner buffer
            }
        }
        return input;
    }

    private int getUserClass() {
        int choiceOfClass;
        do {
            choiceOfClass = checkIntInput("\nEnter the choice of class (1: First, 2: Business, 3: Traveller): ");

            if (choiceOfClass < 1 || choiceOfClass > 3) {
                System.out.println("Invalid choice. Please enter a valid class choice (1: First, 2: Business, 3: Traveller).");
            }             
        } while (choiceOfClass < 1 || choiceOfClass > 3);

        return choiceOfClass;
    }      

    private int getUserSeatType(int choiceOfClass) {
        int choiceOfSeatType = -1;
        boolean isValidSeatType = false;

        do {            
            choiceOfSeatType = checkIntInput("Enter the choice of seat type (1: window, 2: standard, 3: aisle): ");

            if (choiceOfSeatType < 1 || choiceOfSeatType > 3) {       
                System.out.println("Invalid choice. Please enter a valid seat type choice (1: window, 2: standard, 3: aisle).");
            } else if (choiceOfClass == 1 && choiceOfSeatType == 2) {
                System.out.println("Invalid choice. Please enter a valid seat type choice (1: window, 3: aisle).");
            } else if (choiceOfClass >= 1 && choiceOfSeatType <= 3) {
                isValidSeatType = true;
            }     
        } while (!isValidSeatType);
        
        return choiceOfSeatType;
    }

    private String getUserFirstName() {
        System.out.print("Enter the passenger’s first name: ");
        return stringScanner.nextLine();
    }

    private String getUserLastName() {
        System.out.print("Enter the passenger’s last name: ");
        return stringScanner.nextLine();
    }

    private String getUserPassportNumber() {
        String passportNum;
        do {
            System.out.print("Enter the passport number (e.g. ABC0123): ");
            passportNum = stringScanner.nextLine();
            if (!isValidPassportNumber(passportNum)) {
                System.out.println("Invalid passport number format. Please enter a valid passport number (e.g. ABC0123).");
            } else if (passengerDatabase.isPassportNumberUsed(passportNum)) {
                System.out.println("Invalid passport number. This passport number is already in use.");
            }
        } while (!isValidPassportNumber(passportNum) || (passengerDatabase.isPassportNumberUsed(passportNum)));
        return passportNum;
    }

    private boolean isValidPassportNumber(String passportNum) {
        return passportNum.matches("[A-Z]{3}\\d{4}");
    }

    private int getUserNumSeats() {
        int numSeats;
        do {
            numSeats = checkIntInput("\nEnter the number of seats (max 3): ");

            if (numSeats < 1 || numSeats > 3) {
                System.out.println("Invalid choice. Please enter a valid number of seats (No more than 3).");
            }
        } while (numSeats < 1 || numSeats > 3);

        return numSeats;
    }      

    private void bookSingleSeat() {
        int choiceOfClass = getUserClass();
        SeatMap seatmap = getSeatMap(choiceOfClass);
        int choiceOfSeatType = getUserSeatType(choiceOfClass);
        String passSeatRef = seatmap.findLowestAvailableSeatIndex(choiceOfSeatType);
        
        String firstName = getUserFirstName();
        String lastName = getUserLastName();
        String passportNum = getUserPassportNumber();

        Passenger passenger = new Passenger(passSeatRef, firstName, lastName, passportNum, choiceOfClass, choiceOfSeatType);
        passengerDatabase.addPassenger(passenger);
    }

    private void bookMultipleSeats() {
        int numberOfSeats = getUserNumSeats();
        int choiceOfClass = getUserClass();

        SeatMap seatMap = getSeatMap(choiceOfClass);
        String[] seatRefStrings = seatMap.findLowestRowWithSeats(numberOfSeats);
        
        for (int i = 0; i < numberOfSeats; i++) {
            System.out.println("\nPassenger " + (i + 1) + ":");
  
            int choiceOfSeatType = getSeatTypeByRef(seatRefStrings[i]);

            String firstName = getUserFirstName();
            String lastName = getUserLastName();
            String passportNum = getUserPassportNumber();
            seatMap.reserveSeat(seatRefStrings[i]);

            Passenger passenger = new Passenger(seatRefStrings[i], firstName, lastName, passportNum, choiceOfClass, choiceOfSeatType);
            passengerDatabase.addPassenger(passenger);
        }
    }

    private void viewAvailableSeats() {
        int classChoice = checkIntInput("\nEnter the choice of class (1: First, 2: Business, 3: Traveller): ");
        getSeatMap(classChoice).printSeatmap();
    }

    private void printCurrentPassengers() {
        int sortChoice = checkIntInput("\nSort by (1: Last Name, 2: Passport Number): ");

        switch(sortChoice) {
            case 1:
                passengerDatabase.printAllSortedByLastName();
                break;
            case 2:
                passengerDatabase.printAllSortedByPassportNumber();
                break;
        }
    }

    private void searchPassengerLastName() {
        System.out.print("Enter the passenger’s last name: ");
        String lastName = stringScanner.nextLine();
        passengerDatabase.printPassengerSubset(passengerDatabase.searchByLastName(lastName));
    }

    private void deletePassenger() {

        System.out.print("Enter the passenger’s passport number: ");
        String lastName = stringScanner.nextLine();
        Passenger removePassenger = passengerDatabase.searchByPassportNumber(lastName);
        String passSeatRef = removePassenger.getSeatRef();
        String passClassString = removePassenger.getPassengerSeatClass();
        
        switch (passClassString) {
            case "First":
                plane.getFirstClassMap().unreserveSeat(passSeatRef);
                break;
            case "Business":
                plane.getBusinessClassMap().unreserveSeat(passSeatRef);
                break;
            case "Traveller":
                plane.getTravellerClassMap().unreserveSeat(passSeatRef);
                break;
        }

        passengerDatabase.removePassenger(removePassenger);
        System.out.format("%nPassenger %s %s deleted from %s in %s class. %n", removePassenger.getFirstName(), removePassenger.getLastName(), removePassenger.getSeatRef(), removePassenger.getPassengerSeatClass());
    }

    private void printPlaneCostReport() {
        plane.printPlaneCostReport();
    }

//// Controller Functions
    private int getSeatTypeByRef(String passSeatRef) {
        int choiceOfSeatType = -1;
        String seatChar = String.valueOf(passSeatRef.charAt(passSeatRef.length()-1));
            
        if (seatChar.equalsIgnoreCase("A") || seatChar.equalsIgnoreCase("H")) {
            choiceOfSeatType = 1;
        } else if (seatChar.equalsIgnoreCase("B") || seatChar.equalsIgnoreCase("C") || seatChar.equalsIgnoreCase("F") || seatChar.equalsIgnoreCase("G")) {
            choiceOfSeatType = 2;
        } else if (seatChar.equalsIgnoreCase("D") || seatChar.equalsIgnoreCase("E")) {
            choiceOfSeatType = 3;
        }
        return choiceOfSeatType;
    }

    private SeatMap getSeatMap(int choiceOfClass) {
        switch (choiceOfClass) {
            case 1:
                return plane.getFirstClassMap();
            case 2:
                return plane.getBusinessClassMap();
            case 3:
                return plane.getTravellerClassMap();
            default:
                return null;
        }
    }

}
