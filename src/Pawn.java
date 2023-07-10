import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Pawn extends Piece {
    private static int promoteTo;
    public Pawn(Position position, int color, Game game){
        this.game = game;
        this.position = position;
        this.color = color;
        this.name = "Pawn";
        this.firstMove = true;
        updatePiece();
    }
    @Override
    public void move(Position to) { // Promotion
        this.firstMove = false;
        int i = this.position.row, j = this.position.column;
        this.position = to;
        game.board[to.row][to.column] = game.board[i][j];
        game.board[i][j] = null;
        game.updateBoard();
        game.updateSaveFile();
        game.resetCheck();
        game.whiteCheck = game.isCheck(WHITE);
        game.resetCheck();
        game.blackCheck = game.isCheck(BLACK);
        game.resetCheck();
    }

    @Override
    public void movable() {
        if(this.color==WHITE) {
            int i=position.row, j=position.column;
            if(game.board[i-1][j]==null) game.moveable[i-1][j] = true;
            if(firstMove && game.board[i-2][j]==null) game.moveable[i-2][j] = true;
            if(j>0 && game.board[i-1][j-1]!=null) if(game.board[i-1][j-1].color!=this.color) game.moveable[i-1][j-1]=true;
            if(j<7 && game.board[i-1][j+1]!=null) if(game.board[i-1][j+1].color!=this.color) game.moveable[i-1][j+1]=true;
        } else {
            int i=position.row, j=position.column;
            if(game.board[i+1][j]==null) game.moveable[i+1][j] = true;
            if(firstMove && game.board[i+2][j]==null) game.moveable[i+2][j] = true;
            if(j>0 && game.board[i+1][j-1]!=null) if(game.board[i+1][j-1].color!=this.color) game.moveable[i+1][j-1]=true;
            if(j<7 && game.board[i+1][j+1]!=null) if(game.board[i+1][j+1].color!=this.color) game.moveable[i+1][j+1]=true;
        }
    }

    ActionListener PromoteQueen = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){promoteTo = 1;}
    };
    ActionListener PromoteRook = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){promoteTo = 2;}
    };
    ActionListener PromoteBishop = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){promoteTo = 3;}
    };
    ActionListener PromoteKnight = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){promoteTo = 4;}
    };

    @Override
    public void movableCheck(){
        if(this.color==WHITE) {
            int i=position.row, j=position.column;
            if(j>0) game.checkCheck[i-1][j-1]=true;
            if(j<7) game.checkCheck[i-1][j+1]=true;
        } else {
            int i=position.row, j=position.column;
            if(j>0) game.checkCheck[i+1][j-1]=true;
            if(j<7) game.checkCheck[i+1][j+1]=true;
        }
    }
}