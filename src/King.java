public class King extends Piece {
    public King(Position position, int color){
        this.position = position;
        this.color = color;
        this.name = "King";
        this.firstMove = true;
        updatePiece();
    }
    @Override
    public void move(Position to){
        int i=this.position.row, j=this.position.column;
        this.position = to;
        Chess.kingPosition[color] = to;
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

    @Override
    public void movable() {
        Chess.isCheck(color);
        Position[] kingMove = new Position[8];
        kingMove[0] = new Position(-1, -1);
        kingMove[1] = new Position(-1, 0);
        kingMove[2] = new Position(-1, 1);
        kingMove[3] = new Position(0, -1);
        kingMove[4] = new Position(0, 1);
        kingMove[5] = new Position(1, -1);
        kingMove[6] = new Position(1, 0);
        kingMove[7] = new Position(1, 1);

        for(int idx=0; idx<8; idx++){
            Position to = position.add(kingMove[idx]);
            int x = to.row, y = to.column;
            if (x<0 || x>7 || y<0 || y>7) continue;
            if (Chess.board[x][y] == null) if (!Chess.checkCheck[x][y]) Chess.moveable[x][y] = true;
            if (Chess.board[x][y]!=null) if(Chess.board[x][y].color != this.color && !Chess.checkCheck[x][y]) Chess.moveable[x][y] = true;
        }
    }

    @Override
    public void movableCheck() {
        Position[] kingMove = new Position[8];
        kingMove[0] = new Position(-1, -1);
        kingMove[1] = new Position(-1, 0);
        kingMove[2] = new Position(-1, 1);
        kingMove[3] = new Position(0, -1);
        kingMove[4] = new Position(0, 1);
        kingMove[5] = new Position(1, -1);
        kingMove[6] = new Position(1, 0);
        kingMove[7] = new Position(1, 1);

        for(int idx=0; idx<8; idx++){
            Position to = position.add(kingMove[idx]);
            int x = to.row, y = to.column;
            if(x<0 || x>7 || y<0 || y>7) continue;
            Chess.checkCheck[x][y] = true;
        }
    }
}