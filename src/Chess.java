import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Chess extends JFrame{
    public static final int row = 8;
    public static final int column = 8;
    public static final int WHITE = 0;
    public static final int BLACK = 1; // delete PieceColor

    public static Piece[][] board = new Piece[row][column];
    public static JFrame frame = new JFrame();
    public static GamePanel palette = new GamePanel();
    public static JButton[][] cell = new JButton[row][column];
    public static JButton undoB = new JButton();
    public static boolean[][] isClicked = new boolean[row][column];
    public static boolean[][] moveable = new boolean[row][column];
    public static boolean[][] checkCheck = new boolean[row][column];

    public static int turn = WHITE;
    public static boolean firstClick = true;
    public static boolean bQc, bKc, wQc, wKc;
    public static boolean whiteCheck, blackCheck;
    public static int cx, cy;
    public static int trial;
    public static Position[] kingPosition = new Position[2];

    public static GameState[] SaveFile = new GameState[500];

    public static void makeGUI(){
        frame.setTitle("Chess");
        frame.add(palette);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(780,700));
        frame.pack();
        palette.setLayout(null);
        frame.setVisible(true);
        palette.setVisible(true);
        undoB.setBackground(Color.WHITE);
        undoB.setText("Undo");
        undoB.setSize(100,40);
        undoB.setLocation(660,300);
        undoB.addActionListener(e -> undo());
        undoB.setVisible(true);
        palette.add(undoB);
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                cell[i][j] = new JButton();
                cell[i][j].setSize(80,80);
                cell[i][j].setLocation(10+j*80, 10+i*80);
                cell[i][j].setBackground((i+j)%2==0 ? new Color(0xCCE792) : new Color(0x204204));
                cell[i][j].setBorderPainted(false);
                palette.add(cell[i][j]);
                cell[i][j].addActionListener(new ClickListener(i,j));
            }
        }
        updateBoard();
    }

    public static void initialize(){
        for(int i=0; i<8; i++) for(int j=0; j<8; j++) moveable[i][j] = false;
        for(int i=0; i<row; i++) board[1][i] = new Pawn(new Position(1,i), BLACK);
        for(int i=0; i<row; i++) board[6][i] = new Pawn(new Position(6,i), WHITE);
        board[0][0] = new Rook(new Position(0,0), BLACK);
        board[0][1] = new Knight(new Position(0,1), BLACK);
        board[0][2] = new Bishop(new Position(0,2), BLACK);
        board[0][3] = new Queen(new Position(0,3), BLACK);
        board[0][4] = new King(new Position(0,4), BLACK); kingPosition[BLACK] = new Position(0,4);
        board[0][5] = new Bishop(new Position(0,5), BLACK);
        board[0][6] = new Knight(new Position(0,6), BLACK);
        board[0][7] = new Rook(new Position(0,7), BLACK);
        board[7][0] = new Rook(new Position(7,0), WHITE);
        board[7][1] = new Knight(new Position(7,1), WHITE);
        board[7][2] = new Bishop(new Position(7,2), WHITE);
        board[7][3] = new Queen(new Position(7,3), WHITE);
        board[7][4] = new King(new Position(7,4), WHITE); kingPosition[WHITE] = new Position(7,4);
        board[7][5] = new Bishop(new Position(7,5), WHITE);
        board[7][6] = new Knight(new Position(7,6), WHITE);
        board[7][7] = new Rook(new Position(7,7), WHITE);
        whiteCheck = blackCheck = false;
        turn = WHITE;
        firstClick = true;
        bQc = bKc = wQc = wKc = true;
        trial = -1;
        updateSaveFile();
    }

    public static void updateBoard(){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(moveable[i][j]){
                    cell[i][j].setBackground((i+j)%2==0 ? new Color(0xFDBBBB) : new Color(0xAF6C8B));
                } else{
                    cell[i][j].setBackground((i+j)%2==0 ? new Color(0xCCE792) : new Color(0x204204));
                }
                if(isClicked[i][j]) cell[i][j].setBackground(new Color(0xA8DDF1));
                //if(isClicked[i][j]) cell[i][j].setBackground(new Color(0xD9C7F3));
                if(board[i][j] != null){
                    cell[i][j].setIcon(board[i][j].icon);
                } else{
                    cell[i][j].setIcon(null);
                }
            }
        }
        palette.repaint();
    }

    public static void resetBoard(){
        for(int i=0; i<8; i++) for(int j=0; j<8; j++){
            moveable[i][j] = false;
            isClicked[i][j] = false;
        }
        updateBoard();
    }

    public static void resetCheck(){
        for(int i=0; i<8; i++) for(int j=0; j<8; j++) checkCheck[i][j] = false;
    }

    public static void main(String[] args){
        initialize();
        makeGUI();
    }

    public static void updateSaveFile() {
        trial++;
        SaveFile[trial] = new GameState();
        for(int i=0; i<8; i++) System.arraycopy(board[i], 0, SaveFile[trial].board[i], 0, 8);
        SaveFile[trial].turn = turn;
        SaveFile[trial].bQc = bQc; SaveFile[trial].bKc = bKc;
        SaveFile[trial].wQc = wQc; SaveFile[trial].wKc = wKc;
        SaveFile[trial].whiteKing = kingPosition[WHITE]; SaveFile[trial].blackKing = kingPosition[BLACK];
    }

    public static class ClickListener implements ActionListener{ // functions for JButton
        public final int i, j;
        ClickListener(int i, int j){
            this.i=i; this.j=j;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println(trial + "\twhiteCheck:" + whiteCheck + "\tblackCheck:" + blackCheck); //Debug
            if(firstClick){
                if(board[i][j]!=null){
                    if(board[i][j].color==turn){
                        firstClick = false;
                        isClicked[i][j] = true;
                        cx = i; cy = j;
                        board[i][j].movable();
                        updateBoard();
                    }
                }
            } else if(isClicked[i][j]){
                resetBoard();
                isClicked[i][j] = false;
                firstClick = true;
            } else if(moveable[i][j]){
                turn = (turn==WHITE) ? BLACK : WHITE;
                firstClick = true;
                board[cx][cy].move(new Position(i,j));
                resetBoard();
            } else if(board[i][j]!=null){
                if(board[i][j].color==turn){
                    resetBoard();
                    isClicked[cx][cy] = false;
                    cx = i; cy = j;
                    isClicked[i][j] = true;
                    board[i][j].movable();
                }
            }
            updateBoard();
        }
    }

    public static void undo(){
        resetBoard();
        if(trial==0) return;
        trial--;
        for(int i=0; i<8; i++)
            for(int j=0; j<8; j++) {
                board[i][j] = SaveFile[trial].board[i][j];
                if(board[i][j]!=null) {
                    board[i][j].position = new Position(i,j);
                    if(board[i][j].color==WHITE&&i==6) board[i][j].firstMove = true;
                    else if(board[i][j].color==BLACK&&i==1) board[i][j].firstMove = true;
                }
            }
        turn = SaveFile[trial].turn;
        bQc = SaveFile[trial].bQc; bKc = SaveFile[trial].bKc;
        wQc = SaveFile[trial].wQc; wKc = SaveFile[trial].wKc;
        updateBoard();
        kingPosition[WHITE] = SaveFile[trial].whiteKing; kingPosition[BLACK] = SaveFile[trial].blackKing;
        whiteCheck = isCheck(WHITE); blackCheck = isCheck(BLACK);
        resetCheck();
    }

    public static boolean isCheck(int c){  // check movable area
        for(int i=0; i<8; i++) for(int j=0; j<8; j++) if(board[i][j]!=null) if(board[i][j].color!=c) board[i][j].movableCheck();
        return checkCheck[kingPosition[c].row][kingPosition[c].column];
    }
}