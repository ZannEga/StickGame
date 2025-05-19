import java.util.Random;
import java.util.Scanner;

public class StickGameV2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Coin flip
        System.out.println("Welcome to the Stick Game!");
        System.out.print("Choose 'heads' or 'tails': ");
        String playerChoice = scanner.nextLine().toLowerCase();

        int coinFlip = random.nextInt(100);
        String actualFlip = (coinFlip % 2 == 0) ? "tails" : "heads";

        boolean isPlayerTurn = playerChoice.equals(actualFlip);
        System.out.println("Coin flip result: " + actualFlip + " → " +
                (isPlayerTurn ? "You go first!" : "Computer goes first."));

        // Random stick count: 21 - 30
        int sticks = random.nextInt(10) + 21;

        while (sticks > 0) {
            System.out.println("\nSticks remaining: " + sticks);

            if (isPlayerTurn) {
                // Player turn
                int playerPick;
                do {
                    System.out.print("Your turn! Pick 1 to 3 sticks: ");
                    playerPick = scanner.nextInt();
                } while (playerPick < 1 || playerPick > 3 || playerPick > sticks);

                sticks -= playerPick;
                if (sticks == 0) {
                    System.out.println("You took the last stick. You lose!");
                    break;
                }
            } else {
                // Computer turn
                int compPick;

                // Smart strategy to avoid landing on 5 in its turn
                compPick = computeSmartMove(sticks);

                System.out.println("Computer picks: " + compPick);
                sticks -= compPick;

                if (sticks == 0) {
                    System.out.println("Computer took the last stick. Computer loses!");
                    break;
                }
            }

            // Switch turns
            isPlayerTurn = !isPlayerTurn;
        }

        scanner.close();
    }

    private static int computeSmartMove(int sticks) {
        // Winning logic with full awareness
        if (sticks <= 1) return 1; // force loss
        else if (sticks == 2) return 1;
        else if (sticks == 3) return 2;
        else if (sticks == 4) return 3;
        else if (sticks == 5) return 1; // in this version, we avoid letting computer land here
        else if (sticks == 6) return 1;
        else if (sticks == 7) return 2;
        else if (sticks == 8) return 3;
        else {
            // Avoid letting ourselves land on 5 in next turn
            for (int i = 1; i <= 3; i++) {
                int futureSticks = sticks - i;
                if (futureSticks != 5 && futureSticks > 0 && (futureSticks <= 8 || (futureSticks - 5) % 4 == 0)) {
                    return i;
                }
            }
            // Fallback: just pick safe number
            return Math.min(3, sticks);
        }
    }
}
