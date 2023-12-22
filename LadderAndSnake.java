import java.util.Random;
import java.util.Scanner;

public class LadderAndSnake {
        private static final int WINNING_POSITION = 100;
        private static final int[] SNAKE_POSITIONS = {16, 47, 49, 56, 62, 64, 87, 93, 95, 98};
        private static final int[] LADDER_START_POSITIONS = {1, 4, 9, 21, 28, 36, 51, 71, 80};
        private int numberOfPlayers;
        private Player[] players;
        private Random random;
        private Scanner scanner;

        public LadderAndSnake() {

            random = new Random();
            scanner = new Scanner(System.in);
        }
        public LadderAndSnake(int numberOfPlayers){
            this.numberOfPlayers = numberOfPlayers;
        }

        private int flipDice() {
            return random.nextInt(6) + 1;
        }

        private int checkForSnakesAndLadders(int currentPosition) {
            for (int snakePosition : SNAKE_POSITIONS) {
                if (currentPosition == snakePosition) {
                    System.out.println("Oops! Snake at position " + currentPosition + ". Going down!");
                    return currentPosition - 10;
                }
            }

            for (int ladderStartPosition : LADDER_START_POSITIONS) {
                if (currentPosition == ladderStartPosition) {
                    System.out.println("Yay! Ladder at position " + currentPosition + ". Going up!");
                    return currentPosition + 10;
                }
            }

            return currentPosition;
        }

        private void playTurn(Player player) {
            System.out.println("\n" + player.getName() + "'s turn. Press Enter to roll the dice.");
            scanner.nextLine();

            int diceResult = flipDice();
            System.out.println(player.getName() + " rolled a " + diceResult + ".");

            int newPosition = player.getPosition() + diceResult;
            newPosition = checkForSnakesAndLadders(newPosition);

            if (newPosition <= WINNING_POSITION) {
                player.setPosition(newPosition);
                System.out.println(player.getName() + " is now at position " + newPosition + ".");
            }

            if (newPosition == WINNING_POSITION) {
                System.out.println(player.getName() + " wins!");
            }
        }

        public void play() {
            while (true) {
                for (Player player : players) {
                    playTurn(player);
                    if (player.getPosition() == WINNING_POSITION) {
                        System.out.println(player.getName() + " wins!");
                        return;
                    }
                }
            }
        }

        public void initializePlayers() {
            int maxAttempts = 4;
            int attempts = 0;
//            int numPlayers=0;
            Scanner scanner = new Scanner(System.in);

            while (attempts < maxAttempts) {
                System.out.print("Enter the # of players - between 2 and 4: ");

                if (scanner.hasNextInt()) {
                    numberOfPlayers = scanner.nextInt();

                    if (numberOfPlayers >= 2 && numberOfPlayers <= 4) {
                        System.out.println("You've selected " + numberOfPlayers + " players. Let the game begin!");
                        break;
                    } else {
                        System.out.println("Bad Attempt " + (attempts + 1) + " -Invalid # of player. " +
                                "Please enter # between 2 and 4 only");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.next(); // Consume invalid input to avoid an infinite loop
                }

                attempts++;

                if (attempts == maxAttempts) {
                    System.out.println("Maximum attempts reached. Exiting program.");
                    System.exit(1);
                }
            }

            players = new Player[numberOfPlayers];
            for (int i = 0; i <= numberOfPlayers - 1; i++) {
                System.out.print("Enter the name for Player " + (i + 1) + ": ");
                String playerName = scanner.next();
                players[i] = new Player(playerName);
            }
        }
    }

