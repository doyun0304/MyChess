import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    public final int WHITE = 0;
    public final int BLACK = 1;
    public Piece[][] board = new Piece[Chess.ROW][Chess.COLUMN];
    public boolean[][] isClicked = new boolean[Chess.ROW][Chess.COLUMN];
    public boolean[][] moveable = new boolean[Chess.ROW][Chess.COLUMN];
    public boolean[][] checkCheck = new boolean[Chess.ROW][Chess.COLUMN];

    public int turn;
    public boolean firstClick;
    public boolean bQc, bKc, wQc, wKc;
    public boolean whiteCheck, blackCheck;
    public int cx, cy;
    public int trial;
    public Position[] kingPosition = new Position[2];
    public GameState[] SaveFile = new GameState[500];

    Game(){
        for(int i=0; i<8; i++) for(int j=0; j<8; j++) moveable[i][j] = false;
        for(int i = 0; i<Chess.ROW; i++) board[1][i] = new Pawn(new Position(1,i), BLACK, this);
        for(int i = 0; i<Chess.ROW; i++) board[6][i] = new Pawn(new Position(6,i), WHITE, this);
        board[0][0] = new Rook(new Position(0,0), BLACK, this);
        board[0][1] = new Knight(new Position(0,1), BLACK, this);
        board[0][2] = new Bishop(new Position(0,2), BLACK, this);
        board[0][3] = new Queen(new Position(0,3), BLACK, this);
        board[0][4] = new King(new Position(0,4), BLACK, this); kingPosition[BLACK] = new Position(0,4);
        board[0][5] = new Bishop(new Position(0,5), BLACK, this);
        board[0][6] = new Knight(new Position(0,6), BLACK, this);
        board[0][7] = new Rook(new Position(0,7), BLACK, this);
        board[7][0] = new Rook(new Position(7,0), WHITE, this);
        board[7][1] = new Knight(new Position(7,1), WHITE, this);
        board[7][2] = new Bishop(new Position(7,2), WHITE, this);
        board[7][3] = new Queen(new Position(7,3), WHITE, this);
        board[7][4] = new King(new Position(7,4), WHITE, this); kingPosition[WHITE] = new Position(7,4);
        board[7][5] = new Bishop(new Position(7,5), WHITE, this);
        board[7][6] = new Knight(new Position(7,6), WHITE, this);
        board[7][7] = new Rook(new Position(7,7), WHITE, this);
        whiteCheck = blackCheck = false;
        turn = WHITE;
        firstClick = true;
        bQc = bKc = wQc = wKc = true;
        trial = -1;
        updateSaveFile();
        System.out.println("GAME INITIALIZED: " + this);
    }

    public void updateBoard(){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(moveable[i][j]){
                    Chess.cell[i][j].setBackground((i+j)%2==0 ? new Color(0xFDBBBB) : new Color(0xAF6C8B));
                } else{
                    Chess.cell[i][j].setBackground((i+j)%2==0 ? new Color(0xCCE792) : new Color(0x204204));
                }
                if(isClicked[i][j]) Chess.cell[i][j].setBackground(new Color(0xA8DDF1));
                if(board[i][j] != null){
                    Chess.cell[i][j].setIcon(board[i][j].icon);
                } else{
                    Chess.cell[i][j].setIcon(null);
                }
            }
        }
        Chess.palette.repaint();
    }

    public void resetBoard(){
        for(int i=0; i<8; i++) for(int j=0; j<8; j++){
            moveable[i][j] = false;
            isClicked[i][j] = false;
        }
        updateBoard();
    }

    public void resetCheck(){
        for(int i=0; i<8; i++) for(int j=0; j<8; j++) checkCheck[i][j] = false;
    }

    public void updateSaveFile() {
        trial++;
        SaveFile[trial] = new GameState();
        for(int i=0; i<8; i++) System.arraycopy(board[i], 0, SaveFile[trial].board[i], 0, 8);
        SaveFile[trial].turn = turn;
        SaveFile[trial].bQc = bQc; SaveFile[trial].bKc = bKc;
        SaveFile[trial].wQc = wQc; SaveFile[trial].wKc = wKc;
        SaveFile[trial].whiteKing = kingPosition[WHITE]; SaveFile[trial].blackKing = kingPosition[BLACK];
    }

    public class ClickListener implements ActionListener { // functions for JButton
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
                turn = 1-turn;
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

    public void undo(){
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

    public boolean isCheck(int c){  // check movable area
        for(int i=0; i<8; i++) for(int j=0; j<8; j++) if(board[i][j]!=null) if(board[i][j].color!=c) board[i][j].movableCheck();
        return checkCheck[kingPosition[c].row][kingPosition[c].column];
    }
    
    public void initializeGUI(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Chess.cell[i][j] = new JButton();
                Chess.cell[i][j].setSize(80, 80);
                Chess.cell[i][j].setLocation(10 + j * 80, 10 + i * 80);
                Chess.cell[i][j].setBackground((i + j) % 2 == 0 ? new Color(0xCCE792) : new Color(0x204204));
                Chess.cell[i][j].setBorderPainted(false);
                Chess.palette.add(Chess.cell[i][j]);
                Chess.cell[i][j].addActionListener(new ClickListener(i, j));
            }
        }
        updateBoard();
    }
}
