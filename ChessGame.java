import java.util.Scanner;

// Class representing a chess piece
abstract class ChessPiece {
    String color;

    public ChessPiece(String color) {
        this.color = color;
    }

    abstract boolean isValidMove(int startX, int startY, int endX, int endY, ChessPiece[][] board);

    @Override
    public String toString() {
        return this.color.charAt(0) + this.getClass().getSimpleName().substring(0, 1);
    }
}

// Pawn class
class Pawn extends ChessPiece {
    public Pawn(String color) {
        super(color);
    }

    @Override
    boolean isValidMove(int startX, int startY, int endX, int endY, ChessPiece[][] board) {
        int direction = this.color.equals("White") ? -1 : 1;
        if (startY == endY && board[endX][endY] == null && endX - startX == direction) {
            return true;
        }
        return false;
    }
}

// Chess board class
class ChessBoard {
    ChessPiece[][] board;

    public ChessBoard() {
        this.board = new ChessPiece[8][8];
        initializeBoard();
    }

    // Initialize board with pawns
    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("Black");
            board[6][i] = new Pawn("White");
        }
    }

    // Print the board
    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    // Move a piece
    public boolean movePiece(int startX, int startY, int endX, int endY, String currentPlayer) {
        ChessPiece piece = board[startX][startY];

        if (piece == null || !piece.color.equals(currentPlayer)) {
            System.out.println("Invalid move: No piece of your color at the start position.");
            return false;
        }

        if (piece.isValidMove(startX, startY, endX, endY, board)) {
            board[endX][endY] = piece;
            board[startX][startY] = null;
            return true;
        } else {
            System.out.println("Invalid move: The piece cannot move that way.");
            return false;
        }
    }
}

// Main class to run the game
public class ChessGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChessBoard chessBoard = new ChessBoard();
        String currentPlayer = "White";

        while (true) {
            chessBoard.printBoard();
            System.out.println(currentPlayer + "'s turn. Enter move (e.g., 6 0 5 0): ");

            int startX = scanner.nextInt();
            int startY = scanner.nextInt();
            int endX = scanner.nextInt();
            int endY = scanner.nextInt();

            if (chessBoard.movePiece(startX, startY, endX, endY, currentPlayer)) {
                currentPlayer = currentPlayer.equals("White") ? "Black" : "White";
            }
        }
    }
}
