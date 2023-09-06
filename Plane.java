public class Plane {

    private SeatMap firstClassMap;
    private SeatMap businessClassMap;
    private SeatMap travellerClassMap;
    private int firstClassStartIndex;
    private int busClassStartIndex;
    private int travClassStartIndex;
    private int numFirstClass;
    private int numBusClass;
    private int numTravClass;

    public Plane(int numFirstClassIn, int numBusClassIn, int numTravClassIn, int[] costs) {

        numFirstClass = numFirstClassIn;
        numBusClass = numBusClassIn;
        numTravClass = numTravClassIn;

        firstClassStartIndex = 1;
        busClassStartIndex = numFirstClass+1;
        travClassStartIndex = (numFirstClass + numBusClass + 1);

        firstClassMap = new SeatMap(4, numFirstClass, firstClassStartIndex, costs[0]);
        businessClassMap = new SeatMap(6, numBusClass, busClassStartIndex, costs[1]);
        travellerClassMap = new SeatMap(8, numTravClass, travClassStartIndex, costs[2]);
    }

    public SeatMap getFirstClassMap() {
        return firstClassMap;
    }

    public SeatMap getBusinessClassMap() {
        return businessClassMap;
    }

    public SeatMap getTravellerClassMap() {
        return travellerClassMap;
    }

    public int getFirstClassStartIndex() {
        return firstClassStartIndex;
    }

    public int getBusClassStartIndex() {
        return busClassStartIndex;
    }

    public int getTravClassStartIndex() {
        return travClassStartIndex;
    }

    public int getNumFirstClass() {
        return numFirstClass;
    }

    public int getNumBusClass() {
        return numBusClass;
    }

    public int getNumTravClass() {
        return numTravClass;
    }

    public void printPlaneCostReport() {
       System.out.format("%n%12s | %12s | %12s | %n", "Class", "Seats", "Cost");
       System.out.println("--------------------------------------------");
       printPlaneSeatmapReport(getFirstClassMap(), "First"); 
       printPlaneSeatmapReport(getBusinessClassMap(), "Business");
       printPlaneSeatmapReport(getTravellerClassMap(), "Traveller");
       System.out.println("--------------------------------------------\n");
    }

    public void printPlaneSeatmapReport(SeatMap seatMap, String classString) {
        System.out.format("%12s | %12d | %12f | %n", classString, seatMap.getNumReservedSeats(), seatMap.getTotalReservedCost()); 
    }

}
