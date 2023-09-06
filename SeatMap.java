import java.util.ArrayList;
import java.util.List;

public class SeatMap {
    
    private int numSeatsAisle = -1;
    private int numSeatsRows = -1;
    private int[][] seatMap;
    private String[] seatTypes;
    private int startInt = -1;
    private double cost = -1;

    public SeatMap(int numSeatsAisleIn, int numSeatsRowsIn, int startIntIn, double costIn) {
        setNumSeatsAisle(numSeatsAisleIn);
        setNumSeatsRows(numSeatsRowsIn);
        setStartInt(startIntIn);
        setCost(costIn);
        setSeatMap();
    }

    public void setNumSeatsAisle(int numSeatsAisleIn) {
        numSeatsAisle = numSeatsAisleIn;
    }

    public int getNumSeatsAisle() {
        return numSeatsAisle;
    }

    public void setNumSeatsRows(int numSeatsRowsIn) {
        numSeatsRows = numSeatsRowsIn;
    }

    public int getNumSeatsRows() {
        return numSeatsRows;
    }

    public void setStartInt(int startIntIn) {
        startInt = startIntIn;
    }

    public int getStartInt() {
        return startInt;
    }

    public void setCost(double costIn) {
        cost = costIn;
    }

    public double getCost() {
        return cost;
    }

    public void setSeatMap() {
        seatMap = new int[getNumSeatsRows()][getNumSeatsAisle()];
    }

    public int[][] getSeatMap() {
        return seatMap;
    }

    public void setSeatTypes() {

        int aisleLen = getNumSeatsAisle();

        switch (aisleLen) {
            case 4:
                seatTypes = new String[] {"A", "D", "E", "H"};
                break;
            case 6:
                seatTypes = new String[] {"A", "C", "D", "E", "G", "H"};
                break;
            case 8:
                seatTypes = new String[] {"A", "B", "C", "D", "E", "F", "G", "H"};
                break;
            default:
                System.out.println("Invalid number of Aisle seats!");
                break;
        }
    }

    public String[] getSeatTypes() {
        return seatTypes;
    }

//////
    public void printSeatmap() {

        if (seatTypes == null) {
            setSeatTypes();
        }

        for (int i=0; i < getNumSeatsRows(); i++) {
            String rowStr = "";
            if (startInt + i  >= 10) {
                rowStr = Integer.toString(startInt + i );
            } else {
                rowStr = "0" + Integer.toString(startInt + i );
            }
            System.out.print(rowStr + " | ");
            for (int j=0; j < getNumSeatsAisle(); j++) {
                if (seatTypes[j].equalsIgnoreCase("A") || seatTypes[j].equalsIgnoreCase("H")) {
                    if (seatMap[i][j] == 0) {
                        System.out.print(" |  Window  | ");
                    } else {
                        System.out.print(" |    X     | ");
                    }
                } else if (seatTypes[j].equalsIgnoreCase("B") || seatTypes[j].equalsIgnoreCase("C") 
                            || seatTypes[j].equalsIgnoreCase("F") || seatTypes[j].equalsIgnoreCase("G")) {
                    if (seatMap[i][j] == 0) {
                        System.out.print(" | Standard | ");
                    } else {
                        System.out.print(" |    X     | ");
                    }
                } else if (seatTypes[j].equalsIgnoreCase("D") || seatTypes[j].equalsIgnoreCase("E")) {
                    if (seatMap[i][j] == 0) {
                        System.out.print(" |   Aisle  | ");
                    } else {
                        System.out.print(" |    X     | ");
                    }   
                 }
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    public String findLowestAvailableSeatIndex(int choiceOfSeatType) {
        String seatTypeStr = "";
        switch(choiceOfSeatType) {
            case 1:
                seatTypeStr = "Window";
                break;
            case 2:
                seatTypeStr = "Standard";
                break;
            case 3:
                seatTypeStr = "Aisle";
                break;
        }

        if (seatTypes == null) {
            setSeatTypes();
        }

        int[] relevantColumns = null;

        if (seatTypeStr.equalsIgnoreCase("Window")) {
            relevantColumns = findWindowColumns();
        } else if (seatTypeStr.equalsIgnoreCase("Standard")) {
            relevantColumns = findStandardColumns();
        } else if (seatTypeStr.equalsIgnoreCase("Aisle")) {
            relevantColumns = findAisleColumns();
        } else {
            System.out.println("Invalid seat type.");
            return null;
        }

        for (int row = 0; row < getNumSeatsRows(); row++) {
            for (int col : relevantColumns) {
                if (seatMap[row][col] == 0) {
                    seatMap[row][col] = 1;
                    return Integer.toString(row + startInt) + seatTypes[col];
                }
            }
        }
        System.out.println("Seat Type not Available");
        return null; 
    }

    public String[] findLowestRowWithSeats(int numSeats) {
        if (seatTypes == null) {
            setSeatTypes();
        }
    
        for (int row = 0; row < getNumSeatsRows(); row++) {
            int consecutiveEmptySeats = 0;
            List<String> seatReferences = new ArrayList<>();
    
            for (int col = 0; col < getNumSeatsAisle(); col++) {
                if (consecutiveEmptySeats == numSeats) {
                    return seatReferences.toArray(new String[0]);
                }
    
                if (seatMap[row][col] == 0) {
                    seatReferences.add(Integer.toString(row + startInt) + seatTypes[col]);
                    consecutiveEmptySeats++;
                } else {
                    consecutiveEmptySeats = 0;
                    seatReferences.clear();
                }
            }
    
            if (consecutiveEmptySeats == numSeats) {
                return seatReferences.toArray(new String[0]);
            }
        }
        return null; 
    }

    public void reserveSeat(String seatRef) {
        if (seatTypes == null) {
            setSeatTypes();
        }
    
        int row = Integer.parseInt(seatRef.substring(0, seatRef.length() - 1)) - startInt;
        char seatChar = seatRef.charAt(seatRef.length() - 1);
        int col = -1;
    
        for (int i = 0; i < seatTypes.length; i++) {
            if (seatTypes[i].equalsIgnoreCase(Character.toString(seatChar))) {
                col = i;
                break;
            }
        }
    
        if (row >= 0 && row < getNumSeatsRows() && col >= 0 && col < getNumSeatsAisle()) {
            if (seatMap[row][col] == 1) {
                System.out.println("Seat " + seatRef + " is already reserved.");
            } else {
                seatMap[row][col] = 1;
                System.out.println("Seat " + seatRef + " has been reserved.");
            }
        } else {
            System.out.println("Invalid seat reference.");
        }
    }
  
    public void unreserveSeat(String seatRef) {
        if (seatTypes == null) {
            setSeatTypes();
        }
    
        int row = Integer.parseInt(seatRef.substring(0, seatRef.length() - 1)) - startInt;
        char seatChar = seatRef.charAt(seatRef.length() - 1);
        int col = -1;
    
        for (int i = 0; i < seatTypes.length; i++) {
            if (seatTypes[i].equalsIgnoreCase(Character.toString(seatChar))) {
                col = i;
                break;
            }
        }
    
        if (row >= 0 && row < getNumSeatsRows() && col >= 0 && col < getNumSeatsAisle()) {
            if (seatMap[row][col] == 0) {
                System.out.println("Seat " + seatRef + " is already unreserved.");
            } else {
                seatMap[row][col] = 0;
                System.out.println("Seat " + seatRef + " has been unreserved.");
            }
        } else {
            System.out.println("Invalid seat reference.");
        }
    }
    
    private int[] findWindowColumns() {
        List<Integer> columns = new ArrayList<>();
        for (int col = 0; col < getNumSeatsAisle(); col++) {
            if (seatTypes[col].equalsIgnoreCase("A") || seatTypes[col].equalsIgnoreCase("H")) {
                columns.add(col);
            }
        }
        return columns.stream().mapToInt(Integer::intValue).toArray();
    }

    private int[] findStandardColumns() {
        List<Integer> columns = new ArrayList<>();
        for (int col = 0; col < getNumSeatsAisle(); col++) {
            if (seatTypes[col].equalsIgnoreCase("B") || seatTypes[col].equalsIgnoreCase("C") ||
                seatTypes[col].equalsIgnoreCase("F") || seatTypes[col].equalsIgnoreCase("G")) {
                columns.add(col);
            }
        }
        return columns.stream().mapToInt(Integer::intValue).toArray();
    }

    private int[] findAisleColumns() {
        List<Integer> columns = new ArrayList<>();
        for (int col = 0; col < getNumSeatsAisle(); col++) {
            if (seatTypes[col].equalsIgnoreCase("D") || seatTypes[col].equalsIgnoreCase("E")) {
                columns.add(col);
            }
        }
        return columns.stream().mapToInt(Integer::intValue).toArray();
    }

    public int getNumReservedSeats() {
        if (seatTypes == null) {
            setSeatTypes();
        }
        int numberOfReserved = 0;

        for (int i=0; i < getNumSeatsRows(); i++) {
            for (int j=0; j < getNumSeatsAisle(); j++) {
                    numberOfReserved += seatMap[i][j];
                }
            }
        return numberOfReserved;
    }

    public double getTotalReservedCost() {
        return getNumReservedSeats() * getCost();
    }
}
