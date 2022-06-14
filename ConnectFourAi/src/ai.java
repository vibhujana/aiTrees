
import java.util.ArrayList;
import java.util.Scanner;

public class ai {
    String _aiPlayer;
    String _otherPlayer;
    Node _root;
    int[] scores;
    final int notUsed = 0;
    final int maxLevel = 5; //starts at 1
    int[] _multipliers = {notUsed, 1, 2, 100,Integer.MAX_VALUE};

    public ai(String player, String[][] board) {
        _aiPlayer = player;
        _root = new Node(board);
        _otherPlayer = switchPlayer(_aiPlayer);
    }

    class winsLossTies {
        double _wins, _loss, _ties;

        public winsLossTies(double wins, double loss, double ties) {
            _wins = wins;
            _loss = loss;
            _ties = ties;
        }

    }

    class Node {

        String[][] _board;
        ArrayList<Node> _childs;
        int _score;

        public Node(String[][] board) {
            _board = board;
            _childs = new ArrayList();
        }

        public String[][] copyBoard(String[][] board) {
            String[][] newBoard = new String[board.length][board[0].length];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    newBoard[i][j] = board[i][j];
                }
            }
            return newBoard;
        }

        public void addChilds(String player) {
            int loc = 0;
            while (loc < _board.length) {
                String[][] tempBoard = copyBoard(_board);
                for (int i = tempBoard[loc].length - 1; i >= 0; i--) {
                    if (tempBoard[i][loc].equals("_")) {
                        tempBoard[i][loc] = player;
                        Node temp = new Node(tempBoard);
                        _childs.add(temp);
                        break;
                    }
                }
                loc++;
            }
        }


    }

    public String[][] findBestMove() {
        createTree();
        return _root._childs.get(calculateWins(_root))._board;
    }

    public int calculateWins(Node node) {
        scores = new int[node._childs.size()];
        for (int i = 0; i < node._childs.size(); i++) {
            scores[i] = minMax(node._childs.get(i), false);
        }
        int loc = 0;
        double max = Integer.MIN_VALUE;
        for (int i = 0; i < scores.length; i++) {
            if (max < scores[i]) {

                loc = i;
                max = scores[i];


            }
            System.out.println(scores[i]);

        }
        return loc;
    }

    private int minMax(Node node, boolean isMax) {
        if(isWinX(node._board)){
            if(_aiPlayer.equals("X")){
                return 1;
            }
            else{
                return -1;
            }
        }
        if(isWinO(node._board)){
            if(_aiPlayer.equals("O")){
                return 1;
            }
            else{
                return -1;
            }

        }
        if(isTie(node._board)){
            return 0;
        }
        int maxVal = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;

        if (isMax) {

            for (Node child : node._childs) {
                int val = minMax(child, false);
                maxVal = Math.max(maxVal, val);

            }
            return maxVal;

        } else {
            for (Node child : node._childs) {
                int val = minMax(child, true);
                minVal = Math.min(minVal, val);
            }
            return minVal;
        }
    }


    public void createTree() {
        createTreeInternal(_root, _aiPlayer, 0);
    }



    private void createTreeInternal(Node node, String player, int levels) {
        if (levels >= maxLevel){
            return;
        }
        if (isWinX(node._board) || isTie(node._board) || isWinO(node._board)) {
            return;
        }
//        levels++;
        node.addChilds(player);
        int nextLevel = levels + 1;
        for (Node child : node._childs) {
            createTreeInternal(child, switchPlayer(player), nextLevel);
        }

    }

    public boolean isTie(String[][] board) {
        if (!isWinX(board) || !isWinO(board)) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j].equals("_")) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean isWinX(String[][] board) {
        boolean winner = false;
        for (int j = 0; j < board[0].length; j++) {
            for (int i = 6; i >= 3; i--) {
                if (board[i][j].equals(board[i - 1][j]) && board[i][j].equals(board[i - 2][j])
                        && board[i][j].equals(board[i - 3][j]) && !(board[i][j].equals("_"))) {
                    if (board[i][i].equals("X"))
                        winner = true;
                }
            }
        }
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 6; j >= 3; j--) {
                if (board[i][j].equals(board[i][j - 1]) && board[i][j].equals(board[i][j - 2])
                        && board[i][j].equals(board[i][j - 3]) && !(board[i][j].equals("_"))) {
                    if (board[i][i].equals("X"))
                        winner = true;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].equals(board[i + 1][j + 1]) && board[i][j].equals(board[i + 2][j + 2])
                        && board[i][j].equals(board[i + 3][j + 3]) && !(board[i][j].equals("_"))) {
                    if (board[i][i].equals("O"))
                        winner = true;
                }
            }
        }
        for (int i = 6; i > 2; i--) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].equals(board[i - 1][j + 1]) && board[i][j].equals(board[i - 2][j + 2])
                        && board[i][j].equals(board[i - 3][j + 3]) && !(board[i][j].equals("_"))) {
                    if (board[i][i].equals("X"))
                        winner = true;
                }
            }
        }
        return winner;
    }

    public boolean isWinO(String[][] board) {
        boolean winner = false;
        for (int j = 0; j < board[0].length; j++) {
            for (int i = 6; i >= 3; i--) {
                if (board[i][j].equals(board[i - 1][j]) && board[i][j].equals(board[i - 2][j])
                        && board[i][j].equals(board[i - 3][j]) && !(board[i][j].equals("_"))) {
                    if (board[i][i].equals("O"))
                        winner = true;
                }
            }
        }
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 6; j >= 3; j--) {
                if (board[i][j].equals(board[i][j - 1]) && board[i][j].equals(board[i][j - 2])
                        && board[i][j].equals(board[i][j - 3]) && !(board[i][j].equals("_"))) {
                    if (board[i][i].equals("O"))
                        winner = true;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].equals(board[i + 1][j + 1]) && board[i][j].equals(board[i + 2][j + 2])
                        && board[i][j].equals(board[i + 3][j + 3]) && !(board[i][j].equals("_"))) {
                    if (board[i][i].equals("O"))
                        winner = true;
                }
            }
        }
        for (int i = 6; i > 2; i--) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].equals(board[i - 1][j + 1]) && board[i][j].equals(board[i - 2][j + 2])
                        && board[i][j].equals(board[i - 3][j + 3]) && !(board[i][j].equals("_"))) {
                    if (board[i][i].equals("O"))
                        winner = true;
                }
            }
        }
        return winner;
    }

    public String switchPlayer(String player) {
        if (player.equals("X"))
            return "O";
        return "X";
    }

    public void printTree() {
        printTreeInternal(_root);
    }

    private void printTreeInternal(Node node) {
        printArray(node._board);
        System.out.println();
        if (node._childs.size() == 0) {
            return;
        }
        for (int i = 0; i < node._childs.size(); i++) {
            printTreeInternal(node._childs.get(i));
        }
    }

    private void printArray(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[] getScores() {
        return scores;
    }

}
