
import java.util.ArrayList;

public class aiTree {
    String _aiPlayer;
    Node _root;
    int[][] wins = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8},{2, 4, 6}};
    int[] scores;

    public aiTree(String player, String[] board) {
        _aiPlayer = player;
        _root = new Node(board);
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

        String[] _board;
        ArrayList<Node> _childs;

        public Node(String[] board) {
            _board = board;
            _childs = new ArrayList();
        }

        public String[] copyBoard(String[] board) {
            String[] newBoard = new String[board.length];
            for (int i = 0; i < board.length; i++) {
                newBoard[i] = board[i];
            }
            return newBoard;
        }

        public void addChilds(String player) {
            int loc = 0;
            while (loc < 9) {
                String[] tempBoard = copyBoard(_board);
                if (tempBoard[loc].equals("_")) {
                    tempBoard[loc] = player;
                    Node temp = new Node(tempBoard);
                    _childs.add(temp);
                }
                loc++;
            }
        }

    }

    public String[] findBestMove() {
        createTree();
        return _root._childs.get(calculateWins(_root))._board;
    }

    public int calculateWins(Node node) {
        scores = new int[node._childs.size()];
        for (int i = 0; i < node._childs.size(); i++) {
            scores[i] = minMax(node._childs.get(i), false);
        }
        int loc = 0;
        double max = scores[0];
        for (int i = 1; i < scores.length; i++) {
            if (max < scores[i]) {

                loc = i;
                max = scores[i];

            }
        }
        return loc;
    }

    private int minMax(Node node, boolean isMax) {
        if (isWin(node._board) || isTie(node._board)) {
            if (isWinO(node._board)) {
                if (_aiPlayer == "O") {
                    return 1;
                }
                return -1;
            }
            if (isWinX(node._board)) {
                if (_aiPlayer == "X") {
                    return 1;
                }
                return -1;

            }
            if (isTie(node._board)) {
                return 0;
            }
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
        createTreeInternal(_root, _aiPlayer);
    }

    private void createTreeInternal(Node node, String player) {
        if (isWin(node._board) || isTie(node._board)) {
            return;
        }
        node.addChilds(player);
        for (Node child : node._childs) {
            createTreeInternal(child, switchPlayer(player));
        }

    }

    public boolean isWin(String[] board) {
        if (isWinO(board) || isWinX(board)) {
            return true;
        }
        return false;
    }

    public boolean isTie(String[] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i].equals("_"))
                return false;
        }
        return true;
    }

    public boolean isWinX(String[] board) {

        for (int[] win : wins) {
            if (board[win[0]].equals("X")) {
                if (board[win[0]].equals(board[win[1]]) && board[win[1]].equals(board[win[2]])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isWinO(String[] board) {

        for (int[] win : wins) {
            if (board[win[0]].equals("O")) {
                if (board[win[0]].equals(board[win[1]]) && board[win[1]].equals(board[win[2]])) {
                    return true;
                }
            }
        }
        return false;
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
        if (node._childs.size() == 0) {
            return;
        }
        for (int i = 0; i < node._childs.size(); i++) {
            printTreeInternal(node._childs.get(i));
        }
    }

    private void printArray(String[] board) {
        for (String loc : board) {
            System.out.print(loc + " ");
        }
        System.out.println();
    }
    public int[] getScores(){
        return scores;
    }

}
