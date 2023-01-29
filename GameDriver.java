import java.util.Scanner;

public class GameDriver {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Player[] players = getPlayers(sc);
        Game game = new Game(players[0], players[1]);
        game.menu();
    }

    private static Player[] getPlayers(Scanner sc) {
        Player[] players = new Player[2];
        String playerOne, playerTwo;

        while (true) {
            System.out.println("What is the name of the first player?");
            playerOne = sc.nextLine();
            System.out.println("What is the name of the second player?");
            playerTwo = sc.nextLine();

            if (playerOne.length() < 5 || playerOne.length() > 20 || playerTwo.length() < 5
                    || playerTwo.length() > 20) {
                System.out.println("Error: Name must be between 5 and 20 characters.");

            } else if (playerOne.equals(playerTwo)) {
                System.out.println("Error: Names can not be identical.");
            } else {
                break;
            }

        }

        players[0] = new Player(playerOne);
        players[1] = new Player(playerTwo);
        return players;
    }
}

class Player {
    String name;
    int win;
    int loss;
    int tie;
    Statistics stat;

    public Player(String player) {
        name = player;
    }
}

class Statistics {
    int[] rounds = new int[4];
    String[] status = { "tie", "win", "loss" };

    @Override
    public String toString() {
        String res = "Last Round Stats Detailed: ";
        for (int i = 1; i <= 3; i++) {
            res += "Round " + i + ": " + status[rounds[i]] + "| ";
        }
        return res;
    }
}

class Game {
    private Player[] players;
    private int[][] rulesArray;
    int p1totalWin = 0;
    int p1totalLoss = 0;
    int p1totalTie = 0;
    int p2totalWin = 0;
    int p2totalLoss = 0;
    int p2totalTie = 0;
    int p1gameWon = 0;
    int p1gameloss = 0;
    int p1gametie = 0;
    int p2gameWon = 0;
    int p2gameloss = 0;
    int p2gametie = 0;

    private Scanner sc;
    private String[] weapons = new String[] { "", "rock", "paper", "scissors", "saw" };

    public Game(Player player1, Player player2) {
        sc = new Scanner(System.in);
        players = new Player[] { new Player("computer"), player1, player2 };
        rulesArray = new int[][] {
                { 0, 2, 1, 1 },
                { 1, 0, 2, 2 },
                { 2, 1, 0, 2 },
                { 2, 1, 1, 0 }
        };
    }

    public void menu() {
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
        } else {
            System.out.println("wrong input try again");
            menu();

        }
    }

    private int showMenu() {
        System.out.println("1. Play Game");
        System.out.println("2. Show Game Rules");
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
            player.tie = 0;
            player.stat = new Statistics();
        }

        for (int round = 1; round <= 3; round++) {

            System.out.println("---Round: " + round);
            System.out.println("1. rock");
            System.out.println("2. paper");
            System.out.println("3. scissors");
            System.out.println("4. saw");
            System.out.println("\n" + players[1].name + " choose your weapon:");
            int p1Choice = sc.nextInt();
            sc.nextLine();
            if (p1Choice < 1 || p1Choice > 4) {
                System.out.println("wrong pick try again");
                playGame();
            }
            System.out.println(players[1].name + " chose " + weapons[p1Choice] + "\n");

            System.out.println(players[2].name + " choose your weapon:");
            int p2Choice = sc.nextInt();
            sc.nextLine();
            if (p2Choice < 1 || p2Choice > 4) {
                System.out.println("wrong pick try again");
                playGame();
            }

            System.out.println(players[2].name + " chose " + weapons[p2Choice] + "\n");
            int computer = (int) (Math.random() * 4);
            System.out.println("computer chose " + weapons[computer + 1] + "\n");

            int result1 = rulesArray[p1Choice - 1][computer];
            int result2 = rulesArray[p2Choice - 1][computer];
            if (result1 == 1) {
                System.out.println(players[1].name + " vs computer : " + players[1].name + " wins!");
                players[1].win++;
                p1totalWin++;
                players[1].stat.rounds[round] = 1;
            } else if (result1 == 2) {
                System.out.println(players[1].name + " vs computer :" + " computer wins!");
                players[1].loss++;
                p1totalLoss++;
                players[1].stat.rounds[round] = 2;
            } else {
                System.out.println(players[1].name + " vs computer : " + " It's a tie!");
                players[1].tie++;
                p1totalTie++;
                players[1].stat.rounds[round] = 0;
            }
            if (result2 == 1) {
                System.out.println(players[2].name + " vs computer : " + players[2].name + " wins!\n");
                p2totalWin++;
                players[2].win++;

                players[2].stat.rounds[round] = 1;
            } else if (result2 == 2) {
                System.out.println(players[2].name + " vs computer :" + " computer wins!\n");
                players[2].loss++;
                p2totalLoss++;
                players[2].stat.rounds[round] = 2;
            } else {
                System.out.println(players[2].name + " vs computer : " + " It's a tie! \n");
                players[2].tie++;
                p2totalTie++;
                players[2].stat.rounds[round] = 0;
            }
        }
        System.out.println("Game Result:");
        if (players[1].win > players[1].loss) {
            System.out.println(
                    "---------" + players[1].name + " vs " + " computer : " + players[1].name + " wins " + "---------");
            p1gameWon++;
        } else if (players[1].win < players[1].loss) {
            System.out.println(
                    "---------" + players[1].name + " vs " + " computer : " + "computer wins " + "---------");
            p1gameloss++;
        } else {
            System.out.println(
                    "---------" + players[1].name + " vs " + " computer : " + "it's a tie" + "---------");
            p1gametie++;

        }
        if (players[2].win > players[2].loss) {
            System.out.println(
                    "---------" + players[2].name + " vs " + " computer : " + players[2].name + " wins " + "---------\n");
            p2gameWon++;
        } else if (players[2].win < players[2].loss) {
            System.out.println(
                    "---------" + players[2].name + " vs " + " computer : " + "computer wins "  + "---------\n");
            p2gameloss++;
        } else {
            System.out.println(
                    "---------" + players[2].name + " vs " + " computer : " + "it's a tie" + "---------\n");
            p2gametie++;

        }
        menu();
    }

    private void showRules() {
        System.out.println(
        		"---Rules---");
    	System.out.println(
                "a.	Rock breaks scissors and Saw therefore rock wins over scissors and saw. Rock loses against paper.");
        System.out.println(
                "b.	Scissors cut paper therefore scissors win over paper. Scissors lose against rock and Saw.");
        System.out.println(
                "c.	Paper covers rock therefore paper wins over rock. Paper loses against scissors and saw");
        System.out.println(
                "d.	Saw cuts through scissors and paper therefore saw wins over scissors and paper. Saw loses against rock.");
        System.out.println(
                "e.	If player and computer make the same selection, there is a tie.");
        System.out.println(
        		"-  Winner of the game against the computer is one who won more rounds. (Announced after each game)");
        System.out.println(
        		"-  Overall human winner is based on the greater number of won games against the computer and least games lost. (Announced in statistics) \n");

        menu();
    }

    private void showStat() {
        System.out.println("---Statistics---");
    	if (p1gameWon > p2gameWon) {
            System.out.println(
                    "---------" + players[1].name + " won " + p1gameWon + " game/s and is the winner " + "---------\n");
        } else if (p1gameWon < p2gameWon) {
            System.out.println(
                    "---------" + players[2].name + " won " + p2gameWon + " game/s and is the winner " + "---------\n");
        } else {
            System.out.println("---------" + "it's tied! keep playing!" + "---------\n");

        }
        System.out.println("Total games played :" + (p1totalLoss + p1totalTie + p1totalWin) / 3 + " | Total rounds played " + (p1totalLoss + p1totalTie + p1totalWin) + "\n");

        System.out.println("---Player 1: " + players[1].name);
        /*
        System.out.println("Last Round Stats:");
        System.out.println("- Wins: " + players[1].win);
        System.out.println("- Losses: " + players[1].loss);
        System.out.println("- Ties: " + players[1].tie);
        */
        System.out.println(players[1].stat);
        System.out.println("Total Stats of all rounds: " + " wins: " + p1totalWin + "| losses: " + p1totalLoss
                + "| ties: " + p1totalTie);
        System.out.println("Total Stats of all games: " + " wins: " + p1gameWon + "| losses: " + p1gameloss
                + "| ties: " + p1gametie);

        System.out.println();
        System.out.println("---Player 2: " + players[2].name);
        /*
        System.out.println("Last Round Stats:");
        System.out.println("- Wins: " + players[2].win);
        System.out.println("- Losses: " + players[2].loss);
        System.out.println("- Ties: " + players[2].tie);
        */
        System.out.println(players[2].stat);
        System.out.println("Total Stats of all rounds: " + " wins: " + p2totalWin + "| losses: " + p2totalLoss
                + "| ties: " + p2totalTie);
        System.out.println("Total Stats of all games: " + " wins: " + p2gameWon + "| losses: " + p2gameloss
                + "| ties: " + p2gametie + "\n");

        menu();
    }
}
