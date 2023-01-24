import java.util.Scanner;

public class GameDriver {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Player[] players = getPlayers(sc);
        Game game = new Game(players[0], players[1]);
        game.start();
    }

    private static Player[] getPlayers(Scanner sc) {
        Player[] players = new Player[2];
        String playerOne, playerTwo;
        do {
            System.out.println("What is the name of the first player?");
            playerOne = sc.nextLine();
            System.out.println("What is the name of the second player?");
            playerTwo = sc.nextLine();
        } while (playerOne.length() < 5 || playerOne.length() > 20 || playerTwo.length() < 5 || playerTwo.length() > 20
                || playerOne.equals(playerTwo));
        players[0] = new Player(playerOne);
        players[1] = new Player(playerTwo);
        return players;
    }
}

class Player {
    String name;
    int win;
    int loss;
    Statistics stat;

    public Player(String player) {
        name = player;
    }
}

class Statistics {
    int[] rounds = new int[4];

    @Override
    public String toString() {
        String res = "\n score ---1 : wins, -1 : loss , 0 : tie\n";
        for (int i = 1; i <= 3; i++) {
            res += "Round " + i + " :" + rounds[i] + " ";
        }
        return res;
    }
}

class Game {
    private Player[] players;
    private int[][] rulesArray;
    private Scanner sc;
    private String[] weapons = new String[] { "", "rock", "paper", "scissors", "saw" };

    public Game(Player player1, Player player2) {
        sc = new Scanner(System.in);
        players = new Player[] { new Player("computer"), player1, player2 };
        rulesArray = new int[][] {
                { 0, -1, 1, 1 },
                { 1, 0, -1, -1 },
                { -1, 1, 0, -1 },
                { -1, 1, 1, 0 }
        };
    }

    public void start() {
        int input = showMenu();
        if (input == 1) {
            playGame();
        } else if (input == 2) {
            showRules();
        } else if (input == 3) {
            showStat();
        } else if (input == 4) {
            System.out.println("Goodbye!");
            return;
        }
    }

    private int showMenu() {
        System.out.println("1. Play Game");
        System.out.println("2. Show Rules");
        System.out.println("3. Show Statistics");
        System.out.println("4. Exit");
        int input = sc.nextInt();
        sc.nextLine();
        return input;
    }

    private void playGame() {
        for (Player player : players) {
            player.win = 0;
            player.loss = 0;
            player.stat = new Statistics();
        }
        for (int round = 1; round <= 3; round++) {
            System.out.println("Round :" + round);
            System.out.println("1. rock");
            System.out.println("2. paper");
            System.out.println("3. scissors");
            System.out.println("4. saw");
            System.out.println(players[1].name + " enter your choice:");
            int p1Choice = sc.nextInt();
            sc.nextLine();
            System.out.println(players[1].name + " chose " + weapons[p1Choice] + "\n");

            System.out.println(players[2].name + " enter your choice:");
            int p2Choice = sc.nextInt();
            sc.nextLine();

            System.out.println(players[2].name + " chose " + weapons[p2Choice] + "\n");
            int computer = (int) (Math.random() * 4);
            System.out.println("computer chose " + weapons[computer + 1] + "\n");
            int result1 = rulesArray[p1Choice - 1][computer];
            int result2 = rulesArray[p2Choice - 1][computer];
            if (result1 == 1) {
                System.out.println(players[1].name + " vs computer: " + players[1].name + " wins! \n");
                players[1].win++;
                players[1].stat.rounds[round] = 1;
            } else if (result1 == -1) {
                System.out.println(players[1].name + " vs computer: computer wins! \n");
                players[1].loss++;
                players[1].stat.rounds[round] = -1;
            } else {
                System.out.println(players[1].name + " vs computer: it's a tie! \n");
                players[1].stat.rounds[round] = 0;
            }
            if (result2 == 1) {
                System.out.println(players[2].name + " vs computer: " + players[2].name + " wins! \n");
                players[2].win++;
                players[2].stat.rounds[round] = 1;
            } else if (result2 == -1) {
                System.out.println(players[2].name + " vs computer: computer wins! \n");
                players[2].loss++;
                players[2].stat.rounds[round] = -1;
            } else {
                System.out.println(players[2].name + " vs computer: it's a tie! \n");
                players[2].stat.rounds[round] = 0;
            }
        }
        start();
    }

    private void showRules() {
        System.out.println(
                "a.	Rock breaks scissors and Saw therefore rock wins over scissors and saw. Rock loses against paper.");
        System.out.println(
                "b.	Scissors cut paper therefore scissors win over paper. Scissors lose against rock and Saw.");
        System.out.println(
                "c.	Paper covers rock therefore paper wins over rock. Paper loses against scissors and saw");
        System.out.println(
                "d.	Saw cuts through scissors and paper therefore saw wins over scissors and paper. Saw loses against rock.");
        System.out.println(
                "e.	If player and computer make the same selection, there is a tie");
        System.out.println(
                "\n You win your game against the computer if you win more rounds. Ties are possible.");
        System.out.println(
                "\n Overall human winner is the player with more wins against the computer. (Viewable in Statistics Menu) \n \n");

        start();
    }

    private void showStat() {
        if (players[1].win > players[2].win) {
            System.out.println("---------" + players[1].name + " is the winner " + "---------");
        } else if (players[1].win < players[2].win) {
            System.out.println("---------" + players[2].name + " is the winner " + "---------");
        } else {
            System.out.println("---------" + "no winner! you are tied" + "---------");

        }
        System.out.println("Player 1: " + players[1].name);
        System.out.println("Wins: " + players[1].win);
        System.out.println("Losses: " + players[1].loss);
        System.out.println(players[1].stat);
        System.out.println();
        System.out.println("Player 2: " + players[2].name);
        System.out.println("Wins: " + players[2].win);
        System.out.println("Losses: " + players[2].loss);
        System.out.println(players[2].stat);

        start();
    }
}
