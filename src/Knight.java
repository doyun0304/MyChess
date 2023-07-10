public class Knight extends Piece{
    public Knight(Position position, int color, Game game){
        this.game = game;
        this.position = position;
        this.color = color;
        this.name = "Knight";
        this.firstMove = true;
        updatePiece();
    }
    @Override
    public void movable(){
        Position[] knightMove = new Position[8];
        knightMove[0] = new Position(-1, -2);
        knightMove[1] = new Position(-1, 2);
        knightMove[2] = new Position(-2, -1);
        knightMove[3] = new Position(-2, 1);
        knightMove[4] = new Position(1, -2);
        knightMove[5] = new Position(1, 2);
        knightMove[6] = new Position(2, -1);
        knightMove[7] = new Position(2, 1);

        for(int idx=0; idx<8; idx++){
            Position to = position.add(knightMove[idx]);
            int i = position.row, j=position.column, x = to.row, y = to.column;
            if (x<0 || x>7 || y<0 || y>7) continue;
            if (game.board[x][y] == null) game.moveable[x][y] = true;
            else if (game.board[x][y].color != game.board[i][j].color) game.moveable[x][y] = true;
        }
    }

    @Override
    public void movableCheck(){
        Position[] knightMove = new Position[8];
        knightMove[0] = new Position(-1, -2);
        knightMove[1] = new Position(-1, 2);
        knightMove[2] = new Position(-2, -1);
        knightMove[3] = new Position(-2, 1);
        knightMove[4] = new Position(1, -2);
        knightMove[5] = new Position(1, 2);
        knightMove[6] = new Position(2, -1);
        knightMove[7] = new Position(2, 1);

        for(int idx=0; idx<8; idx++){
            Position to = position.add(knightMove[idx]);
            int x = to.row, y = to.column;
            if (x<0 || x>7 || y<0 || y>7) continue;
            game.checkCheck[x][y] = true;
        }
    }
}