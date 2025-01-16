import java.util.*;

public class User {
    String userName;

    public User(String username) {
        this.userName = username;
    }
}

public class Vehicle {
    int vehicleId;
    int vehicleNumber;
    Reservation reservation;
    VehicleType vehicleType;
    String companyName;
    String modelName;
    int dailyRentalCost;
    int hourlyRentalCost;
    int noOfSeats;
    Status status;
}

public enum VehicleType {
    CAR,
    BIKE;
}

public enum Status {
    ACTIVE,
    INACTIVE;    
}

public class Reservation {
    int reservationId;
    User user;
    Vehicle vehicle;
    Date bookingDate;
    Date bookingFrom;
    Date bookingTo;
    Location location;
    String pickUpLocation;
    String dropLocation;
    ReservationType reservationType;
    ReservationStatus reservationStatus;

    public int createReservation(Vehicle vehicle, User user) {
        reservationId = 1232534; //Generate a random number
        this.user = user;
        this.vehicle = vehicle;
        this.reservationType = ReservationType.DAILY;
        this.reservationStatus = ReservationStatus.SCHEDULED;
        return reservationId;
    }  
}

public enum ReservationType {
    DAILY,
    HOURLY;
}

public enum ReservationStatus {
    IDLE,
    SCHEDULED,
    INPROGRESS,
    COMPLETED;
}

public class Location {
    String address;
    String pincode;
    String state;
    String city;
    public Location(String address, String pincode, String state, String city) {
        this.address = address;
        this.pincode = pincode;
        this.state = state;
        this.city = city;
    }
}

public class VehicleInventoryManagement {
    List<Vehicle> vehicleList;
    public VehicleInventoryManagement (List<Vehicle> vehicles) {
        vehicleList = vehicles;
    }
    public List<Vehicle> getVehicles() {
        // filtering
        return vehicleList;
    }
    public setVehicles(List<Vehicle> vehicles) {
        this.vehicleList = vehicles;
    }
}

public class Store {
    int storeId;
    Location storeLocation;
    VehicleInventoryManagement vehicleInventoryManagement;
    List<Reservation> reservationList = new ArrayList<>();

    public List<Vehicle> getVehicles() {
        vehicleInventoryManagement.getVehilces();
    }

    public void setVehicles(List<Vehicle> vehicles) {
        vehicleInventoryManagement = new VehicleInventoryManagement(vehicles);
    }

    //addVehicles, update vehicles, use inventory management to update those.

    public Reservation createReservation(Vehicle vehicle, User user) {
        Reservation reservation = new Reservation();
        reservation.createReservation(vehicle, user);
        reservationList.add(reservation);
        return reservation;
    }

    public boolean completeReservation(int reservationId) {
        // Iterate through the list and mark the reservation complete
        return true;
    }
}

public class Bill {
    Reservation reservation;
    int amount;
    boolean isAmountPaid;
    public Bill (Reservation reservation) {
        this.reservation = reservation;
        amount = computeAmount();
        isAmountPaid = false;
    }
    public int computeAmount() {
        // some process;
        return 100;
    }
}

public enum PaymentMode {
    ONLINE,
    CASH;
}

public class PaymentDetails {
    PaymentMode paymentMode;
    int paymentId;
    int amount;
    Date dateOfPayment;
}

public class Payment {
    public void payBill() {
        // do processing and update the bill status;
    }
}

public class Main {

    public static void main(String args[]) {


        List<User> users = addUsers();
        List<Vehicle> vehicles = addVehicles();
        List<Store> stores = addStores(vehicles);

        VehicleRentalSystem rentalSystem = new VehicleRentalSystem(stores, users);

        //0. User comes
        User user = users.get(0);

        //1. user search store based on location
        Location location = new Location(403012, "Bangalore", "Karnataka", "India");
        Store store = rentalSystem.getStore(location);

        //2. get All vehicles you are interested in (based upon different filters)
        List<Vehicle> storeVehicles = store.getVehicles(VehicleType.CAR);


        //3.reserving the particular vehicle
       Reservation reservation = store.createReservation(storeVehicles.get(0), users.get(0));

       //4. generate the bill
        Bill bill = new Bill(reservation);

        //5. make payment
        Payment payment = new Payment();
        payment.payBill(bill);

       //6. trip completed, submit the vehicle and close the reservation
        store.completeReservation(reservation.reservationId);

    }



    public static List<Vehicle> addVehicles(){

        List<Vehicle> vehicles = new ArrayList<>();

        Vehicle vehicle1 = new Car();
        vehicle1.setVehicleID(1);
        vehicle1.setVehicleType(VehicleType.CAR);

        Vehicle vehicle2 = new Car();
        vehicle1.setVehicleID(2);
        vehicle1.setVehicleType(VehicleType.CAR);

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);

        return vehicles;
    }

    public static List<User> addUsers(){

        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setUserId(1);

        users.add(user1);
        return users;
    }

    public static List<Store> addStores(List<Vehicle> vehicles){

        List<Store> stores = new ArrayList<>();
        Store store1 = new Store();
        store1.storeId=1;
        store1.setVehicles(vehicles);

        stores.add(store1);
        return stores;
    }

}
