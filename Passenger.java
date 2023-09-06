public class Passenger {

    private String seatRef = "<EMPTY>";
    private String firstName = "<EMPTY>";
    private String lastName = "<EMPTY>";
    private String passportNum = "<EMPTY>";
    private String passengerSeatClass = "<EMPTY>";
    private String passengerSeatType = "<EMPTY>";

    public Passenger(String seatRefIn, String firstNameIn, String lastNameIn, String passportNumIn, int passengerSeatClassIn, int passengerSeatTypeIn) {
        setSeatRef(seatRefIn);
        setFirstName(firstNameIn);
        setLastName(lastNameIn);
        setPassportNum(passportNumIn);
        setPassengerSeatClass(passengerSeatClassIn);
        setPassengerSeatType(passengerSeatTypeIn);
    }

    public String getSeatRef() {
        return seatRef;
    }

    public void setSeatRef(String seatRefIn) {
        seatRef = seatRefIn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstNameIn) {
        firstName = firstNameIn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastNameIn) {
        lastName = lastNameIn;
    }

    public String getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(String passportNumIn) {
        passportNum = passportNumIn;
    }

    public String getPassengerSeatClass() {
        return passengerSeatClass;
    }

    public void setPassengerSeatClass(int passengerSeatClassIn) {
        switch(passengerSeatClassIn) {
            case 1:
                passengerSeatClass = "First";
                break;
            case 2:
                passengerSeatClass = "Business";
                break;
            case 3:
                passengerSeatClass = "Traveller";
                break;
        }
    }

    public String getPassengerSeatType() {
        return passengerSeatType;
    }

    public void setPassengerSeatType(int passengerSeatTypeIn) {
        switch(passengerSeatTypeIn) {
            case 1:
                passengerSeatType = "Window";
                break;
            case 2:
                passengerSeatType = "Standard";
                break;
            case 3:
                passengerSeatType = "Aisle";
                break;
        }
    }

    public void printPassengerDetails() {
        
        System.out.println("\nFirst Name: " + getFirstName());
        System.out.println("Last Name: " + getLastName());
        System.out.println("Passport No: " + getPassportNum());
        System.out.println("Seat ref: " + getSeatRef());
        System.out.println("Seat Class: " + getPassengerSeatClass());
        System.out.println("Seat Type: " + getPassengerSeatType() + "\n");
    }

    public void printPassengerDBView() {
        System.out.format("%20s | %20s | %11s | %8s | %9s | %8s | %n", getFirstName(), getLastName(), getPassportNum(), getSeatRef(), getPassengerSeatClass(), getPassengerSeatType());
    }
}
