import java.util.Scanner;
import java.util.Random;



public class TicTacToe {
    // Defines game board
    static char[][] board = new char[3][3];
    // Defines player and AI
    static char player = 'X';
    static char ai = 'O';

    public static void main(String[] args) {
        initBoard();
        printBoard();

        // Start
        while (!isGameOver()) {
            // Player turn
            playerMove();
            printBoard();

            if (isGameOver()) {
                break;
            }

            // AI turn
            aiMove();
            printBoard();
        }

        // Print winner
        printBoard();
        System.out.println(getWinner());
    }

    // Initialize game board
    public static void initBoard() {


        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }

        }

    }

    // Print game board
    public static void printBoard() {
        System.out.println("_____");
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                System.out.print(board[i][j] + " ");

            }

            System.out.println("|");

        }
        // COME UP WITH COOL BOARDER FOR BOARD
        System.out.println("_____");
        System.out.println("~*~*~*~*~*~*~*~*~*~*~*");
        System.out.println(" ");

    }

    public static void playerMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter row (1, 2, or 3): ");
        int row = scanner.nextInt() - 1;
        System.out.println("Enter column (1, 2, or 3): ");
        int col = scanner.nextInt() - 1;

        if (isValidMove(row, col)) {
            board[row][col] = player;
        } else {
            System.out.println("Invalid move");
            playerMove();
        }
    }

    // Find the best move AI
    //added in a factor that makes AI make mistakes
    public static void aiMove() {
        int[] bestMove = new int[2];
        int bestScore = Integer.MIN_VALUE;
        Random random = new Random();
        int chanceOfMistake = 50;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = ai;
                    int score = minimax(0, false);
                    board[i][j] = '-';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    } else if (score == bestScore && random.nextInt(100) < chanceOfMistake) {
                        // adds a random factor
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        board[bestMove[0]][bestMove[1]] = ai;
    }



    // Check if over
    public static boolean isGameOver() {
        return isBoardFull() || getWinner() != '-';
    }

    // Check if board full
    public static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    // Make sure valid
    public static boolean isValidMove(int row, int col) {
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            return false;
        }
        return board[row][col] == '-';
    }

    // Minimax algorithm to determine the score of move
    public static int minimax(int depth, boolean isMaximizingPlayer) {
        char winner = getWinner();
        if (winner == ai) {
            return 10 - depth;
        } else if (winner == player) {
            return depth - 10;
        } else if (isGameOver()) {
            return 0;
        }

        // Apply minimax algorithm to evaluate scores of moves
        int bestScore;
        if (isMaximizingPlayer) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = ai;
                        int score = minimax(depth + 1, false);
                        board[i][j] = '-';
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = player;
                        int score = minimax(depth + 1, true);
                        board[i][j] = '-';
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
        }
        return bestScore;
    }


    // Check Winner
    public static char getWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }

        // Check col
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != '-' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return board[0][j];
            }
        }
        // Check diag
        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }

        // No winner
        return '-';

    }
}