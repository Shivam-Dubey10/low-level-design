import java.util.*;

enum VehicleType {
    TwoWheeler, FourWheeler;
}

public class Vehicle {
    int vehicleNo;
    VehicleType vehicleType;
    public Vehicle(int vNo, VehicleType vType) {
        this.vehicleNo = vNo;
        this.vehicleType = vType;
    }
}

public class ParkingSpot {
    boolean isEmpty;
    Vehicle vehicle;
    int spotId;
    int price;
    public ParkingSpot(int id, int price) {
        this.id = id;
        this.price = price;
        isEmpty = true;
        this.vehicle = null;
    }
    public void parkVehicle(Vehicle v) {
        this.vehicle = v;
        isEmpty = false;
    }
    public void removeVehicle() {
        this.vehicle = null;
        isEmpty = true;
    }
}

public abstract class ParkingSpotManager {
    List<ParkingSpot> parkingSpotList;
    public ParkingSpotManager(List<ParkingSpot> pList) {
        this.parkingSpotList = pList;
    }
    abstract ParkingSpot findParkingSpot();
    public void parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = findParkingSpot();
        spot.parkVehicle(vehicle);
    }
    public void removeVehicle(Vehicle v) {
        for (ParkingSpot ps : parkingSpotList) {
            if (ps.v!=null && ps.v==v) {
                ps.removeVehicle();
                break;
            }
        }
    }
}

public class TwoWheelerParkingManager extends ParkingSpotManager {
    public TwoWheelerParkingManager(List<ParkingSpot> pSpot) {
        super(pSpot);
    }
    @override
    ParkingSpot findParkingSpot() {
        // logic
        return null;
    }
}

public class FourWheelerParkingManager extends ParkingSpotManager {
    public FourWheelerParkingManager(List<ParkingSpot> pSpot) {
        super(pSpot);
    }
    @override
    ParkingSpot findParkingSpot() {
        // logic
        return null;
    }
}

public class ParkingManagerFactory {
    public ParkingSpotManager getParkingSpotManager (VehicleType vType, List<ParkingSpot> pSpotList) {
        if (vehicleType == VehicleType.TwoWheeler) {
            return new TwoWheelerParkingManager(pSpotList);
        } else if (vehicleType == VehicleType.FourWheeler) {
            return new FourWheelerParkingManager(pSpotList);
        } else {
            return null;
        }
    }
}

public class Ticket {
    long entryTime;
    ParkingSpot parkingSpot;
    Vehicle vehicle;
    public Ticket(long entryTime, ParkingSpot parkingSpot, Vehicle vehicle) {
        this.entryTime = entryTime;
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
    }
}

public class EntraceGate {
    int entraceGateNumber;
    List<ParkingSpot> pSpotList;
    ParkingManagerFactory parkingManagerFactory;
    public EntraceGate (ParkingManagerFactory factory, 
    List<ParkingSpot> pSpotList) {
        this.parkingManagerFactory = factory;
        this.pSpotList = pSpotList;
    }
    public ParkingSpot findParkingSpace(VehicleType vType, 
    List<ParkingSpot> list) {
        ParkingSpotManager manager = factory.getParkingSpotManager(vType, pSpotList);
        return manager.findParkingSpot();
    }
    public Ticket generateTicket(Vehicle vehicle, ParkingSpot parkingSpot) {
        //logic
        return null;
    }
}

public class ExitGate {
    int exitGateNumber;
    List<ParkingSpot> pSpotList;
    ParkingManagerFactory parkingManagerFactory;
    public ExitGate(List<ParkingSpot> pSpotList, 
    ParkingManagerFactory parkingManagerFactory) {
        this.pSpotList = pSpotList;
        this.parkingManagerFactory = parkingManagerFactory;
    }
    public void removeVehicle(Ticket ticket) {
        ParkingSpotManager manager = parkingManagerFactory.getParkingSpotManager(ticket.vehicle.vehicleType, pSpotList);
        manager.removeVehicle(ticket.vehicle);
    }
}

public class Main {
    public static void main(String[] args) {
        // Initialize parking spots
        List<ParkingSpot> spots = new ArrayList<>();
        for (int i = 1; i <= 100; ++i) {
            if (i <= 50)
                spots.add(new ParkingSpot(i, 10));
            else
                spots.add(new ParkingSpot(i, 20));
        }

        // Create ParkingSpotManagerFactory
        ParkingSpotManagerFactory factory = new ParkingSpotManagerFactory();

        // Create EntranceGate and ExitGate objects
        EntranceGate entranceGate = new EntranceGate(factory);
        ExitGate exitGate = new ExitGate(factory);

        // Example usage
        Vehicle vehicle = new Vehicle(123, VehicleType.TwoWheeler);
        ParkingSpot spot = entranceGate.findParkingSpace(vehicle.vehicleType, spots);
        Ticket ticket = entranceGate.generateTicket(vehicle, spot);

        // Vehicle leaves
        exitGate.removeVehicle(ticket);
    }
}