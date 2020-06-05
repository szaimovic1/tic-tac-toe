package ba.unsa.etf.vi.seminarski;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JFrame {

    Position position = new Position();
    JButton[] buttons = new JButton[Position.SIZE];

    public Game() {
        setLayout(new GridLayout(Position.DIM, Position.DIM));
        for(int i = 0; i < Position.SIZE; i++) {
            JButton button = createButton();
            buttons[i] = button;
            final int idx = i;
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (position.board[idx] == ' ') {
                        button.setText(Character.toString(position.turn));
                        position.move(idx);
                        if (!position.isGameEnd()) {
                            int best = position.bestMove();
                            buttons[best].setText(Character.toString(position.turn));
                            position.move(best);
                        }
                        if (position.isGameEnd()) {
                            String message = position.isWinFor('x') ? "RESULT: You won!\n" :
                                    position.isWinFor('o') ? "RESULT: Computer won!\n" : "RESULT: Draw\n";
                            int option = JOptionPane.showConfirmDialog(null, message + "\nDo you want to play again?", "Game Result", JOptionPane.YES_NO_OPTION);
                            //0 for 'yes'
                            //1 for 'no'
                            if (option == 0) {
                                setVisible(false);
                                new Game();
                            } else if (option == 1) {
                                setVisible(false);
                            }
                        }
                    }
                }
            });
        }
        pack();
        //ovo je ako hocemo full screen
        //setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        //ovo je ako hocemo malo manje, centrirano da bude
        setSize(600,600);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private JButton createButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(100, 100));
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setFont(new Font(null, Font.PLAIN, 100));
        add(button);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game();
            }
        });
    }
}
