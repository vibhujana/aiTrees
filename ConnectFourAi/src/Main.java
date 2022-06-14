
import java.util.Scanner;

class Main{
    public static void main(String[] args) {
        ConnecttheFour t = new ConnecttheFour();
        t.run();

    }
}


class ConnecttheFour {
    String[][] board = new String[7][7];
    String player = "X";



    public void run() {
        boolean winner = false;
        instructions();
        makeBoard();
        placing(4);

        while (winner == false) {
            switchPlayer();
            drawBoard();
            aiTurn();
            Scanner loc = new Scanner(System.in);
            System.out.println("");
            String input = loc.nextLine();

//            placing(promptUser());
//            aiTurn();
//            drawBoard();
//            winner = checkWinner();
//            if (winner == true) {
//                System.out.println("The winner is... Player " + player + "!!!!");
//                break;
//            }
//
//            switchPlayer();
//
//            aiTurn();
//            drawBoard();
//            winner = checkWinner();
//            if (winner == true) {
//                System.out.println("The winner is... Player " + player + "!!!!");
//                break;
//            }
//
//            switchPlayer();
        }

    }

    public void aiTurn(){
        ai AI = new ai(player,board);
        board = AI.findBestMove();

    }


    /*
     *
     * location -the column that the user chooses
     */
    public void placing(int location) {
        int x = 0, y = 0;
        for (int i = 6; i < board.length; i--) {
            if (board[i][location - 1].equals("_")) {
                board[i][location - 1] = player;
                break;
            }
        }
        /*
         * for (int i = 0; i < board.length - 1; i += 0) { if (board[row][i].equals("_")) { i++; } if
         * (board[row][i].equals("X") || board[row][i].equals("O")) { board[row][i - 1] = player; i = 8;
         * } }
         */

    }

    public void instructions() {
        System.out.println("Welcome to Connect 4!");
        System.out.println("To place your piece, type a number from 1 to 7");
        System.out.println("For example, 1 is column one, 2 is column 2, etc\n");
        System.out.println("Press r to restart and q to quit game");
    }

    public void makeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = "_";
            }
        }
    }

    public void drawBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int promptUser() {
        Scanner loc = new Scanner(System.in);
        int choice = -1;
        System.out.println("It's player " + player + "'s turn");
        System.out.println("Type in your column(1-7)");
        String input = loc.nextLine();
        try {
            choice = Integer.parseInt(input);
        } catch (Exception e) {
            if (input.equalsIgnoreCase("q")) {
                System.exit(0);
            } else if (input.equalsIgnoreCase("r")) {
                System.out.println("restarting...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                run();
            }
        }

        while ((choice < 1 || choice > 7) || checkIsValidPosition(choice) == false) {
            System.out.println("Invalid Choice!");
            System.out.println("Type in your location(1-7)");
            input = loc.nextLine();
            choice = Integer.parseInt(input);
        }
        return choice;
        // String response = JOptionPane.showInputDialog("");

    }

    public boolean checkIsValidPosition(int choice) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][choice - 1].equals("_")) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWinner() {
        boolean winner = false;
        for (int j = 0; j < board[0].length; j++) {
            for (int i = 6; i >= 3; i--) {
                if (board[i][j].equals(board[i - 1][j]) && board[i][j].equals(board[i - 2][j])
                        && board[i][j].equals(board[i - 3][j]) && !(board[i][j].equals("_"))) {
                    winner = true;
                }
            }
        }
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 6; j >= 3; j--) {
                if (board[i][j].equals(board[i][j - 1]) && board[i][j].equals(board[i][j - 2])
                        && board[i][j].equals(board[i][j - 3]) && !(board[i][j].equals("_"))) {
                    winner = true;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].equals(board[i + 1][j + 1]) && board[i][j].equals(board[i + 2][j + 2])
                        && board[i][j].equals(board[i + 3][j + 3]) && !(board[i][j].equals("_"))) {
                    winner = true;
                }
            }
        }
        for (int i = 6; i > 2; i--) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].equals(board[i - 1][j + 1]) && board[i][j].equals(board[i - 2][j + 2])
                        && board[i][j].equals(board[i - 3][j + 3]) && !(board[i][j].equals("_"))) {
                    winner = true;
                }
            }
        }
        return winner;
    }

    public void switchPlayer() {
        if (player.equals("O")) {
            player = "X";
        } else {
            player = "O";
        }
    }

}



  