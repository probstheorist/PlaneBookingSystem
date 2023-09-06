import java.util.*;

public class PassengerDatabase {
    private Map<String, Set<Passenger>> lastNameMap;
    private Map<String, Passenger> passportMap;

    public PassengerDatabase() {
        lastNameMap = new HashMap<>();
        passportMap = new HashMap<>();
    }

    public void addPassenger(Passenger passenger) {
        lastNameMap.computeIfAbsent(passenger.getLastName(), k -> new HashSet<>()).add(passenger);
        passportMap.put(passenger.getPassportNum(), passenger);
    }

    public boolean isPassportNumberUsed(String passportNumber) {
        return passportMap.containsKey(passportNumber);
    }

    public void removePassenger(Passenger passenger) {
        lastNameMap.computeIfPresent(passenger.getLastName(), (k, v) -> {
            v.remove(passenger);
            return v.isEmpty() ? null : v;
        });
        passportMap.remove(passenger.getPassportNum());
    }

    public Set<Passenger> searchByLastName(String lastName) {
        return lastNameMap.getOrDefault(lastName, Collections.emptySet());
    }
   
    public Passenger searchByPassportNumber(String passportNum) {
        return passportMap.get(passportNum);
    }

    public void printAllSortedByLastName() {
        printPassengerSet(getSortedPassengerSet(lastNameMap.values()));
    }

    public void printAllSortedByPassportNumber() {
        List<Passenger> sortedPassengers = new ArrayList<>(passportMap.values());
        Collections.sort(sortedPassengers, Comparator.comparing(Passenger::getPassportNum));
        printPassengerList(sortedPassengers);
    }

    private Set<Passenger> getSortedPassengerSet(Collection<Set<Passenger>> passengerSets) {
        Set<Passenger> sortedPassengers = new TreeSet<>(Comparator.comparing(Passenger::getLastName));
        for (Set<Passenger> passengerSet : passengerSets) {
            sortedPassengers.addAll(passengerSet);
        }
        return sortedPassengers;
    }

    private void printPassengerSet(Set<Passenger> passengers) {
        System.out.println(" ");
        System.out.format("%20s | %20s | %11s | %8s | %9s | %8s | %n", "First Name", "Last Name", "Passport No", "Seat Ref", "Class", "Type");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Passenger passenger : passengers) {
            passenger.printPassengerDBView();
        }
        System.out.println("---------------------------------------------------------------------------------------------\n");
    }

    private void printPassengerList(List<Passenger> passengers) {
        System.out.println(" ");
        System.out.format("%20s | %20s | %11s | %8s | %9s | %8s | %n", "First Name", "Last Name", "Passport No", "Seat Ref", "Class", "Type");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Passenger passenger : passengers) {
            passenger.printPassengerDBView();
        }
        System.out.println("---------------------------------------------------------------------------------------------\n");
    }

    public void printPassengerSubset(Set<Passenger> passengers) {
        printPassengerSet(passengers);
    }
    
}
