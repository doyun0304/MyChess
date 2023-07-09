import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Piece extends JPanel{
    public static final int WHITE = 0, BLACK = 1;

    Position position;
    int color;
    String name;
    ImageIcon icon;
    boolean firstMove;

    public void move(Position to){
        int i=this.position.row, j=this.position.column;
        this.position = to;
        if(to.row<0 || to.row>7 || to.column<0 || to.column>7){
            System.out.println("Invalid Move");
            return;
        }
        Chess.board[to.row][to.column] = Chess.board[i][j];
        Chess.board[i][j] = null;
        Chess.updateBoard();
        Chess.updateSaveFile();
        Chess.resetCheck();
        Chess.whiteCheck = Chess.isCheck(WHITE);
        Chess.resetCheck();
        Chess.blackCheck = Chess.isCheck(BLACK);
        Chess.resetCheck();
    }

    public abstract void movable();

    public abstract void movableCheck();

    public void updatePiece(){
        Image img;
        try{
            File pieceTemp = new File("C:\\Users\\SHK\\Desktop\\경기과고\\정보\\Chess\\src\\image\\"+(color==0?"WHITE_":"BLACK_")+name+".png");
            img = ImageIO.read(pieceTemp);
            Image updateImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            icon = new ImageIcon(updateImg);
        }catch (IOException e){
            System.out.println("이미지를 로드하는 중 문제가 발생했습니다.");
        }
    }
}