import java.util.*;

public enum PlayingPieceType {
    O,
    X;
}

public class PlayingPiece {
    public PlayingPieceType playingPieceType;
    public PlayingPiece (PlayingPieceType playingPieceType) {
        this.playingPieceType = playingPieceType;
    }
}

public class PlayingPieceO extends PlayingPiece {
    public PlayingPieceO () {
        super(PlayingPieceType.O);
    }
}

public class PlayingPieceX extends PlayingPiece {
    public PlayingPieceX () {
        super(PlayingPieceType.X);
    }
}

public class Board {
    public int size;
    public PlayingPiece[][] board;
    public Board(int size) {
        this.size = size;
        board = new PlayingPiece[size][size];
    }
    public boolean addPiece(int row, int col, PlayingPieceType type) {
        if (board[row][col]!=null) {
            return false;
        }
        board[row][col] = type;
        return true;
    }
    public List<Pair> getFreeCells() {
        List<Pair> freeCells = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) {
                    Pair p = new Pair(i, j);
                    freeCells.add(p);
                }
            }
        }

        return freeCells;
    }

    public void printBoard() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null) {
                   System.out.print(board[i][j].playingPieceType.name() + "   ");
                } else {
                    System.out.print("    ");

                }
                System.out.print(" | ");
            }
            System.out.println();
        }
    }
}

public class Player {
    String name;
    PlayingPieceType playingPieceType;
    public Player (String name, PlayingPieceType type) {
        this.name = name;
        this.playingPieceType = type;
    }
}

public class TicTacToeGame {

    Deque<Player> dq;
    Board board;

    public TicTocGame() {
        initializeTheGame();
    }

    public void initializeTheGame() {
        PlayingPieceX crossPiece = new PlayingPieceX();
        Player p1 = new Player("player1", crossPiece);
        PlayingPieceO roundPiece = new PlayingPieceO();
        Player p2 = new Player("player2", roundPiece);
        dq.add(p1);
        dq.add(p2);
        board = new Board(3);
    }

    public String start() {
        boolean noWinner = true;
        while (noWinner) {
            Player playerTurn = dq.removeFirst();
            board.printBoard();
            List<Pair> freeSpaces =  gameBoard.getFreeCells();
            if(freeSpaces.isEmpty()) {
                noWinner = false;
                continue;
            }
            System.out.print("Player:" + playerTurn.name + " Enter row,column: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputColumn = Integer.valueOf(values[1]);


            //place the piece
            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow,inputColumn, playerTurn.playingPiece);
            if(!pieceAddedSuccessfully) {
                //player can not insert the piece into this cell, player has to choose another cell
                System.out.println("Incorredt possition chosen, try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isThereWinner(inputRow, inputColumn, playerTurn.playingPiece.pieceType);
            if(winner) {
                return playerTurn.name;
            }
        }

        return "tie";
    }

    public boolean isThereWinner(int row, int column, PieceType pieceType) {

        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        //need to check in row
        for(int i=0;i<gameBoard.size;i++) {

            if(gameBoard.board[row][i] == null || gameBoard.board[row][i].pieceType != pieceType) {
                rowMatch = false;
            }
        }

        //need to check in column
        for(int i=0;i<gameBoard.size;i++) {

            if(gameBoard.board[i][column] == null || gameBoard.board[i][column].pieceType != pieceType) {
                columnMatch = false;
            }
        }

        //need to check diagonals
        for(int i=0, j=0; i<gameBoard.size;i++,j++) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                diagonalMatch = false;
            }
        }

        //need to check anti-diagonals
        for(int i=0, j=gameBoard.size-1; i<gameBoard.size;i++,j--) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                antiDiagonalMatch = false;
            }
        }

        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }


}

public class Pair {
    int x;
    int y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}