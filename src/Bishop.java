public class Bishop extends Piece{
    public Bishop(Position position, int color, Game game){
        this.game = game;
        this.position = position;
        this.color = color;
        this.name = "Bishop";
        this.firstMove = true;
        updatePiece();
    }
    @Override
    public void movable(){
        int x, y, i=this.position.row, j=this.position.column;
        x=i+1; y=j+1;
        while (x<8 && y<8){
            if (game.board[x][y] == null) game.moveable[x][y] = true;
            else if (game.board[x][y].color == game.board[i][j].color) break;
            else{
                game.moveable[x][y] = true;
                break;
            }
            x++; y++;
        }
        x=i-1; y=j+1;
        while (0<=x && y<8){
            if (game.board[x][y] == null) game.moveable[x][y] = true;
            else if (game.board[x][y].color == game.board[i][j].color) break;
            else{
                game.moveable[x][y] = true;
                break;
            }
            x--; y++;
        }
        x=i+1; y=j-1;
        while (x<8 && 0<=y){
            if (game.board[x][y] == null) game.moveable[x][y] = true;
            else if (game.board[x][y].color == game.board[i][j].color) break;
            else{
                game.moveable[x][y] = true;
                break;
            }
            x++; y--;
        }
        x=i-1; y=j-1;
        while (0<=x && 0<=y){
            if (game.board[x][y] == null) game.moveable[x][y] = true;
            else if (game.board[x][y].color == game.board[i][j].color) break;
            else{
                game.moveable[x][y] = true;
                break;
            }
            x--; y--;
        }
    }

    @Override
    public void movableCheck(){
        int x, y, i=this.position.row, j=this.position.column;
        x=i+1; y=j+1;
        while (x<8 && y<8){
            game.checkCheck[x][y] = true;
            if (game.board[x][y] != null) break;
            x++; y++;
        }
        x=i-1; y=j+1;
        while (0<=x && y<8){
            game.checkCheck[x][y] = true;
            if (game.board[x][y] != null) break;
            x--; y++;
        }
        x=i+1; y=j-1;
        while (x<8 && 0<=y){
            game.checkCheck[x][y] = true;
            if (game.board[x][y] != null) break;
            x++; y--;
        }
        x=i-1; y=j-1;
        while (0<=x && 0<=y){
            game.checkCheck[x][y] = true;
            if (game.board[x][y] != null) break;
            x--; y--;
        }
    }
}
