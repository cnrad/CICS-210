import java.util.Scanner;

import tictactoe.Game;

public class App {
    public static void main(String[] args) throws Exception {
        Game g = new Game();

        try (Scanner s = new Scanner(System.in)) {
            while (!g.isGameOver()) {
                System.out.println(g);
                System.out.println("Player " + g.getCurrentPlayer() + ", choose a move [0-8]: ");

                int move = s.nextInt();

                g.move(move);

            }

            System.out.println("Game result: " + g.getResult());
        }
    }
}
