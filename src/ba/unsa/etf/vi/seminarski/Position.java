package ba.unsa.etf.vi.seminarski;

import java.security.PublicKey;
import java.util.Arrays;

public class Position {
    public static final int DIM = 3;
    public static final int SIZE = DIM*DIM;
    public char turn;
    public char[] board;

    public Position(char turn) {
        this.turn = 'x';
        board = new char[SIZE];
        for(int i = 0; i < SIZE; i++)
            board[i] = ' ';
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


}
