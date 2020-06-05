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
                            String message = position.isWinFor('x') ? "You won!" :
                                    position.isWinFor('o') ? "Computer won!" : "Draw";
                            JOptionPane.showMessageDialog(null, message);
                        }
                    }
                }
            });
        }
        pack();
        setVisible(true);
    }

    private JButton createButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(100, 100));
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setFont(new Font(null, Font.PLAIN, 50));
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
