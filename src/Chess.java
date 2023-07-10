import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Chess extends JFrame{
    public static final int ROW = 8;
    public static final int COLUMN = 8;
    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public static Game game;

    public static JFrame frame = new JFrame();
    public static GamePanel palette = new GamePanel();
    public static JButton[][] cell = new JButton[ROW][COLUMN];
    public static JButton undoButton = new JButton();

    public static void initialize(){
        game = new Game();
    }

    public static void makeGUI() {
        frame.setTitle("Chess");
        frame.add(palette);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(780, 700));
        frame.pack();
        palette.setLayout(null);
        frame.setVisible(true);
        palette.setVisible(true);
        undoButton.setBackground(Color.WHITE);
        undoButton.setText("Undo");
        undoButton.setSize(100, 40);
        undoButton.setLocation(660, 300);
        undoButton.addActionListener(e -> game.undo());
        undoButton.setVisible(true);
        palette.add(undoButton);
        game.initializeGUI();
    }
    public static void main(String[] args){
        initialize();
        makeGUI();
    }

}