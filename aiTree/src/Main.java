import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static int[][] wins = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    static String player = "X";
    static String[] board = new String[]{"_", "_", "_",
                                         "_", "_", "_",
                                         "_", "_", "_"};

    public static void main(String[] args) {
        runGame();
    }

    public static void runGame() {
        display(board);
        while (!isWin(board) || !isTie(board)) {

            board = userMove(player, board);
            display(board);
            player = switchPlayer(player);
            if (isWin(board) || isTie(board)) {
                break;
            }

            aiTree ai = new aiTree(player, board);
            board = ai.findBestMove();
            display(board);
            player = switchPlayer(player);
            if (isWin(board) || isTie(board)) {
                break;
            }
        }
    }


    public static String[] userMove(String player, String[] board) {
        boolean goodLoc = false;
        while (goodLoc == false) {
            Scanner myObj = new Scanner(System.in);
            System.out.println("Enter loc: ");
            int loc = myObj.nextInt();
            if (board[loc].equals("_")) {
                board[loc] = player;
                goodLoc = true;
            }
        }
        return board;
    }


    public static boolean isTie(String[] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i].equals("_"))
                return false;
        }
        return true;
    }

    public static boolean isWin(String[] board) {
        for (int[] win : wins) {
            if (board[win[0]].equals("X") || board[win[0]].equals("O")) {
                if (board[win[0]].equals(board[win[1]]) && board[win[1]].equals(board[win[2]])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String switchPlayer(String player) {
        if (player.equals("X")) {
            return "O";
        }
        return "X";
    }

    public static void display(String[] board) {
        System.out.println();
        for (int loc = 0; loc < board.length; loc++) {
            System.out.print(board[loc] + " ");
            if (loc == 2 || loc == 5)
                System.out.println();
        }
        System.out.println();
        System.out.println();

    }


}