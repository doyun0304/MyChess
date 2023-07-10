public class King extends Piece {
    public King(Position position, int color, Game game){
        this.game = game;
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
        game.kingPosition[color] = to;
        if(to.row<0 || to.row>7 || to.column<0 || to.column>7){
            System.out.println("Invalid Move");
            return;
        }
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
        game.isCheck(color);
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
            if (game.board[x][y] == null) if (!game.checkCheck[x][y]) game.moveable[x][y] = true;
            if (game.board[x][y]!=null) if(game.board[x][y].color != this.color && !game.checkCheck[x][y]) game.moveable[x][y] = true;
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
            game.checkCheck[x][y] = true;
        }
    }
}