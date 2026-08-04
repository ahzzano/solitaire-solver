package solitaire;

import java.util.Scanner;

import solitaire.utils.GameDisplay;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Starting deck");
        Game game = new Game();
        String autoSkip = "" ;
        do {
            System.out.print("Autoskip to end (Y/n): ");
            autoSkip = scanner.nextLine();
            if(!autoSkip.equals("Y") && !autoSkip.equals("n")) {
                System.out.print("Please enter only Y/n. Thank you");
            }
        } while (!autoSkip.equals("Y") && !autoSkip.equals("n"));

        GameDisplay display = GameDisplay.withBoard(game);
        if(autoSkip.equals("n")) {
            display.setScanner(scanner);
        }
        
        System.out.println("Starting the game GLHF");
        System.out.println("Starting state");
        display.displayState();
        System.out.println("Press enter to start the game");
        scanner.nextLine();

        boolean win = game.play();

        if (win) {
            System.out.println("You win!");
        } else {
            System.out.println("You lost!");
        }

        scanner.close();
    }
}
