package ba.unsa.etf.vi.seminarski;

import java.util.*;

public class Position {
    public static final int DIM = 3;
    public static final int SIZE = DIM*DIM;
    public char turn;
    public char[] board;
    private Map<Integer, Integer> cache = new HashMap<Integer, Integer>();

    public Position() {
        this.turn = 'x';
        board = new char[SIZE];
        for(int i = 0; i < SIZE; i++)
            board[i] = ' ';
    }

    public Position(String position, char turn) {
        this.board = position.toCharArray();
        this.turn = turn;
    }

    @Override
    public String toString() {
        return new String(board);
    }

    public Position move(int idx) {
        board[idx] = turn;
        turn = turn == 'x' ? 'o' : 'x';
        return this;
    }

    public Position unmove(int idx) {
        board[idx] = ' ';
        turn = turn == 'x' ? 'o' : 'x';
        return this;
    }

    public List<Integer> possibleMoves() {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < board.length; i++) {
            if(board[i] == ' ')
                list.add(i);
        }
        return list;
    }

    public boolean isWinFor(char turn) {
        boolean isWin = false;

        for(int i = 0; i < SIZE; i+=DIM)
            isWin = isWin || lineMatch(turn, i, i + DIM, 1);

        for(int i = 0; i < DIM; i++)
            isWin = isWin || lineMatch(turn, i, SIZE, DIM);

        isWin = isWin || lineMatch(turn, 0, SIZE, DIM+1);
        isWin = isWin || lineMatch(turn, DIM-1, SIZE-1, DIM-1);

        return isWin;
    }

    private boolean lineMatch(char turn, int start, int end, int step) {
        for(int i = start; i < end; i += step) {
            if(board[i] != turn)
                return false;
        }
        return true;
    }

    public int blanks() {
        int total = 0;
        for(int i = 0; i < SIZE; i++) {
            if(board[i] == ' ')
                total++;
        }
        return total;
    }

    public int minimax() {
        Integer key = code();
        Integer value = cache.get(key);
        if(value != null) return value;
        if(isWinFor('x')) return blanks();
        if(isWinFor('o')) return -blanks();
        if(blanks() == 0) return 0;
        List<Integer> list = new ArrayList<>();
        for(Integer idx : possibleMoves()) {
            list.add(move(idx).minimax());
            unmove(idx);
        }
        value = turn == 'x' ? Collections.max(list) : Collections.min(list);
        cache.put(key, value);
        return value;
    }

    private int code() {
        int value = 0;
        for(int i = 0; i < SIZE; i++) {
            value = value * 3;
            if(board[i] == 'x')
                value += 1;
            else if (board[i] == 'o')
                value += 2;
        }
        return value;
    }

    public int bestMove() {
        Comparator<Integer> cmp = new Comparator<Integer>() {
            @Override
            public int compare(Integer first, Integer second) {
                int a = move(first).minimax();
                unmove(first);
                int b = move(second).minimax();
                unmove(second);
                return a - b;
            }
        };
        List<Integer> list = possibleMoves();
        return turn == 'x' ? Collections.max(list, cmp) : Collections.min(list, cmp);
    }

    public boolean isGameEnd() {
        return isWinFor('x') || isWinFor('o') || blanks() == 0;
    }


}
