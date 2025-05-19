import java.util.Random;
import java.util.Scanner;

public class StickGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        // Random jumlah stick antara 10 dan 30
        int sticks = rand.nextInt(21) + 10;
        System.out.println("Total sticks: " + sticks);

        // Tentukan giliran awal dengan coin flip
        boolean playerTurn = coinFlip(scanner) == 1;

        // Game loop
        while (sticks > 1) {
            printStatus(sticks);

            int taken;
            if (playerTurn) {
                taken = getPlayerMove(scanner);
                System.out.println("Player takes " + taken + " stick(s).");
            } else {
                taken = getComputerMove(sticks);
                System.out.println("Computer takes " + taken + " stick(s).");
            }

            sticks -= taken;
            playerTurn = !playerTurn;
        }

        // Pemain yang terakhir ambil stick ke-1 kalah
        announceWinner(playerTurn);
    }

    /**
     * Melakukan coin flip dan menentukan siapa yang jalan duluan.
     * Player pilih heads/tails, hasil ditentukan oleh RNG.
     * Return 1 jika player jalan duluan, 0 jika komputer.
     */
    public static int coinFlip(Scanner scanner) {
        Random rand = new Random();
        System.out.print("Coin flip! Choose heads or tails: ");
        String guess = scanner.next().toLowerCase();
        int result = rand.nextInt(2); // 0 = tails, 1 = heads
        String actual = result == 0 ? "tails" : "heads";
        System.out.println("Result: " + actual);
        if (guess.equals(actual)) {
            System.out.println("You go first!");
            return 1;
        } else {
            System.out.println("Computer goes first!");
            return 0;
        }
    }

    /**
     * Mendapatkan input valid dari player (ambil 1–3 stick).
     */
    public static int getPlayerMove(Scanner scanner) {
        int taken;
        while (true) {
            System.out.print("Take 1–3 sticks: ");
            if (scanner.hasNextInt()) {
                taken = scanner.nextInt();
                if (taken >= 1 && taken <= 3) {
                    break;
                }
            } else {
                scanner.next(); // clear input
            }
            System.out.println("Invalid input. Try again.");
        }
        return taken;
    }

    /**
     * Menentukan jumlah stick yang diambil komputer berdasarkan strategi menang.
     * Komputer akan menghindari kondisi stick = 5 dan mengarahkan ke posisi jebakan (5, 9, 13...).
     */
    public static int getComputerMove(int sticksLeft) {
        Random rand = new Random();

        // Jika stick <= 8, pakai endgame strategy
        if (sticksLeft <= 8) {
            switch (sticksLeft) {
                case 8: return 3;
                case 7: return 2;
                case 6: return 1;
                case 5: return rand.nextInt(3) + 1; // pengecualian: stick 5 tetap random di versi 2
                case 4: return 3;
                case 3: return 2;
                case 2: return 1;
            }
        }

        // Coba cari jumlah pengambilan agar sisa stick jatuh ke posisi menang (5,9,13,...)
        for (int i = 3; i >= 1; i--) {
            int futureSticks = sticksLeft - i;
            if (futureSticks != 5 && futureSticks > 0 &&
                (futureSticks <= 8 || isWinningPosition(futureSticks))) {
                return i;
            }
        }

        // Jika tidak ada pilihan optimal, ambil random
        return rand.nextInt(3) + 1;
    }

    /**
     * Mengecek apakah jumlah stick saat ini merupakan posisi kalah untuk player.
     * Posisi kalah adalah bentuk: 5, 9, 13, 17, ... (futureSticks - 5) habis dibagi 4.
     */
    public static boolean isWinningPosition(int sticksLeft) {
        return (sticksLeft - 5) % 4 == 0;
    }

    /**
     * Menampilkan status jumlah stick yang tersisa.
     */
    public static void printStatus(int sticksLeft) {
        System.out.println("\nSticks left: " + sticksLeft);
    }

    /**
     * Menentukan dan mencetak siapa yang kalah.
     * Jika playerTurn == true, maka setelah dibalik (player baru saja ambil),
     * berarti player kalah karena mengambil stick terakhir.
     */
    public static void announceWinner(boolean playerTurn) {
        if (playerTurn) {
            System.out.println("Player took the last stick. Player loses!");
        } else {
            System.out.println("Computer took the last stick. Computer loses!");
        }
    }
}
