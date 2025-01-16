import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Jump {
    int start;
    int end;
}

public class Cell {
    Jump jump;
    // getters and setters
}

public class Dice {
    int noOfDice;
    int min = 1;
    int mix = 6;

    public Dice(int noOfDice) {
        this.noOfDice = noOfDice;
    }

    public int rollDice() {
        int diceUsed = 0;
        int sum = 0;
        while (diceUsed < noOfDice) {
            sum += ThreadLocalRandom.current().nextInt(min, max+1);
            diceUsed++;
        }
        return sum;
    }
}

public class Board {
    Cell[][] cells;

    public Board(int size, int numberOfSnakes, int numberOfLadders) {
        initializeCells(size);
        addSnakesAndLadders(cells, numberOfSnakes, numberOfLadders);
    }

    private void initializeCells(int size) {
        cells = new Cell[size][size];
        for (int i=0;i<size;i++) {
            for (int j=0;j<size;j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    private void addSnakesAndLadders(Cell[][] cells, int numberOfSnakes, int numberOfLadders) {
        while (numberOfSnakes > 0) {
            int start = ThreadLocalRandom.current().nextInt(1, cells.length*cells.length-1);
            int end = ThreadLocalRandom.current().nextInt(1, cells.length*cells.length-1);
            if (end > start) {
                continue;
            }
            Jump snakeObj = new Jump();
            snakeObj.start = start;
            snakeObj.end = end;
            Cell cell = getCell(start);
            cell.jump = snakeObj;
            numberOfSnakes--;
        }
        while (numberOfLadders > 0) {
            int start = ThreadLocalRandom.current().nextInt(1, cells.length*cells.length-1);
            int end = ThreadLocalRandom.current().nextInt(1, cells.length*cells.length-1);
            if (start > end) {
                continue;
            }
            Jump ladderObj = new Jump();
            ladderObj.start = start;
            ladderObj.end = end;
            Cell cell = getCell(start);
            cell.jump = ladderObj;
            numberOfLadders--;
        }
    }

    public Cell getCell(int playerPos) {
        int row = cells.length/playerPos;
        int col = cells.length%playerPos;
        return cells[row][col];
    }
}

public class Player {
    String id;
    int currentPos;
    public Player(String id, int currentPos) {
        this.id = id;
        this.currentPos = currentPos;
    }
}

public class Game {

    Board board;
    Dice dice;
    Deque<Player> playersList = new LinkedList<>();
    Player winner;

    public Game(){  
        initializeGame();
    }

    private void initializeGame() {
        board = new Board(10, 5,4);
        dice = new Dice(1);
        winner = null;
        addPlayers();
    }

    private void addPlayers() {
        Player player1 = new Player("p1", 0);
        Player player2 = new Player("p2", 0);
        playersList.add(player1);
        playersList.add(player2);
    }

    public void startGame(){
        while(winner == null) {
            //check whose turn now
            Player playerTurn = findPlayerTurn();
            System.out.println("player turn is:" + playerTurn.id + " current position is: " + playerTurn.currentPosition);
            //roll the dice
            int diceNumbers = dice.rollDice();
            //get the new position
            int playerNewPosition = playerTurn.currentPosition + diceNumbers;
            playerNewPosition = jumpCheck(playerNewPosition);
            playerTurn.currentPosition = playerNewPosition;
            System.out.println("player turn is:" + playerTurn.id + " new Position is: " + playerNewPosition);
            //check for winning condition
            if(playerNewPosition >= board.cells.length * board.cells.length-1){
                winner = playerTurn;
            }
        }
        System.out.println("WINNER IS:" + winner.id);
    }


    private Player findPlayerTurn() {
        Player playerTurns = playersList.removeFirst();
        playersList.addLast(playerTurns);
        return playerTurns;
    }

    private int jumpCheck (int playerNewPosition) {
        if(playerNewPosition > board.cells.length * board.cells.length-1 ){
            return playerNewPosition;
        }
        Cell cell = board.getCell(playerNewPosition);
        if(cell.jump != null && cell.jump.start == playerNewPosition) {
            String jumpBy = (cell.jump.start < cell.jump.end)? "ladder" : "snake";
            System.out.println("jump done by: " + jumpBy);
            return cell.jump.end;
        }
        return playerNewPosition;
    }
}

