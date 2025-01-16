import java.util.*;

public enum Direction {
    UP,
    DOWN;
}

public class ElevatorDisplay {
    Direction direction;
    int floor;
    public void setDisplay(int floor, Direction direction) {
        this.direction = direction;
        this.floor = floor;
    }
    public void showDisplay() {
        // sys out
    }
}

public enum ElevatorState {
    IDLE,
    MOVING;
}

public class ElevatorDoor {
    public void doorOpen() {
        // sys out
    }
    public void doorClose() {
        // sys out
    }
}

public class ElevatorCar {
    int id;
    int currentFloor;
    ElevatorState state;
    ElevatorDisplay display;
    Direction direction;
    ElevatorDoor elevatorDoor;
    InternalButtons internalButtons;

    public ElevatorCar() {
        this.currentFloor = 0;
        this.state = ElevatorState.IDLE;
        this.direction = Direction.UP;
        this.elevatorDoor = new ElevatorDoor();
        this.internalButtons = new InternalButtons();
        this.display = new ElevatorDisplay();
    }

    public void showDisplay() {
        display.showDisplay();
    }

    public void setDisplay() {
        display.setDisplay(currentFloor, direction);
    }

    public void pressButton(int destination) {
        internalButtons.pressButton(destination, this);
    }

    public boolean moveElevator(int destinationFloor, Direction direction) {
        int startFloor = currentFloor;
        if (direction==Direction.UP) {
            for (int i=startFloor;i<=destinationFloor;i++) {
                currentFloor = i;
                showDisplay();
                setDisplay();
                if (i==destinationFloor) {
                    return true;
                }
            }
        } else {
            for (int i=startFloor;i>=destinationFloor;i--) {
                currentFloor = i;
                showDisplay();
                setDisplay();
                if (i==destinationFloor) {
                    return true;
                }
            }
        }
        return false;
    }

}

public class ElevatorController {
    PriorityQueue<Integer> upQ;
    PriorityQueue<Integer> downQ;
    ElevatorCar elevatorCar;

    public ElevatorController(ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
        upQ = new PriorityQueue<>();
        downQ = new PriorityQueue<>((a, b) -> b-a);
    }

    public void submitExternalRequest(int floor, Direction direction){

        if(direction == Direction.DOWN) {
            downQ.offer(floor);
        } else {
            upQ.offer(floor);
        }
     }

    public void submitInternalRequest(int floor){

    }

    public void controlElevator(){
        while(true) {

            if(elevatorCar.elevatorDirection == Direction.UP){


            }
        }
    }

}

public class ElevatorCreator {

    static List<ElevatorController> elevatorControllerList = new ArrayList<>();
    static {

        ElevatorCar elevatorCar1 = new ElevatorCar();
        elevatorCar1.id =1;
        ElevatorController controller1 = new ElevatorController(elevatorCar1);

        ElevatorCar elevatorCar2 = new ElevatorCar();
        elevatorCar1.id =2;
        ElevatorController controller2 = new ElevatorController(elevatorCar2);

        elevatorControllerList.add(controller1);
        elevatorControllerList.add(controller2);
    }
}

public class ExternalDispatcher {

    List<ElevatorController>  elevatorControllerList = ElevatorCreator.elevatorControllerList;

    public void submitExternalRequest(int floor, Direction direction){


        //for simplicity, i am following even odd,
        for(ElevatorController elevatorController : elevatorControllerList) {

           int elevatorID = elevatorController.elevatorCar.id;
           if (elevatorID%2==1 && floor%2==1){
               elevatorController.submitExternalRequest(floor,direction);
           } else if(elevatorID%2==0 && floor%2==0){
               elevatorController.submitExternalRequest(floor,direction);

           }
        }
    }

}

public class Floor {
    int floorNumber;
    ExternalDispatcher externalDispatcher;


    public Floor(int floorNumber){
        this.floorNumber = floorNumber;
        externalDispatcher = new ExternalDispatcher();
    }
    public void pressButton(Direction direction) {

        externalDispatcher.submitExternalRequest(floorNumber, direction);
    }
}

public class InternalButtons {

    InternalDispatcher dispatcher = new InternalDispatcher();

    int[] availableButtons = {1,2,3,4,5,6,7,8,9,10};
    int buttonSelected;

    void pressButton(int destination, ElevatorCar elevatorCar) {

        //1.check if destination is in the list of available floors

        //2.submit the request to the jobDispatcher
        dispatcher.submitInternalRequest(destination, elevatorCar);
    }

}

public class InternalDispatcher {

    List<ElevatorController>  elevatorControllerList = ElevatorCreator.elevatorControllerList;

    public void submitInternalRequest(int floor, ElevatorCar elevatorCar){

    }
}

