package tictactoe;

public class Game {
    public static final String EMPTY = ".";
    public static final String X = "X";
    public static final String O = "O";

    String[][] board;

    String currentPlayer;

    public Game() {
        board = new String[3][3];
        currentPlayer = X;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public String toString() {
        String res = "";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res += board[i][j];
            }
            res += "\n";
        }

        return res;
    }

    /**
     * Returns true iff the game is over
     * 
     * @return true iff game over
     */
    public boolean isGameOver() {
        return isWinner(X) || isWinner(O) || isDraw();
    }

    private boolean isDraw() {
        return false; // TODO
    }

    private boolean isWinner(String player) {
        return false; // TODO
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void move(int move) {
        int row = move / 3;
        int col = move % 3;

        if (!board[row][col].equals(EMPTY)) {
            System.out.println("Already taken, choose another.");
        } else {
            board[row][col] = currentPlayer;

            // Change the player
            currentPlayer = currentPlayer.equals(X) ? O : X;
        }
    }

    public String getResult() {
        return "ok";
    }
}
