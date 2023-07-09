public class Rook extends Piece{
    public Rook(Position position, int color){
        this.position = position;
        this.color = color;
        this.name = "Rook";
        this.firstMove = true;
        updatePiece();
    }
    @Override
    public void movable(){
        int x, y, i=this.position.row, j=this.position.column;
        x=i+1; y=j;
        while (x<8){
            if (Chess.board[x][y] == null) Chess.moveable[x][y] = true;
            else if (Chess.board[x][y].color == Chess.board[i][j].color) break;
            else{
                Chess.moveable[x][y] = true;
                break;
            }
            x++;
        }
        x=i-1;
        while (0<=x){
            if (Chess.board[x][y] == null) Chess.moveable[x][y] = true;
            else if (Chess.board[x][y].color == Chess.board[i][j].color) break;
            else{
                Chess.moveable[x][y] = true;
                break;
            }
            x--;
        }
        x=i; y=j-1;
        while (0<=y) {
            if (Chess.board[x][y] == null) Chess.moveable[x][y] = true;
            else if (Chess.board[x][y].color == Chess.board[i][j].color) break;
            else {
                Chess.moveable[x][y] = true;
                break;
            }
            y--;
        }
        y=j+1;
        while (y<8) {
            if (Chess.board[x][y] == null) Chess.moveable[x][y] = true;
            else if (Chess.board[x][y].color == Chess.board[i][j].color) break;
            else {
                Chess.moveable[x][y] = true;
                break;
            }
            y++;
        }
    }

    @Override
    public void movableCheck(){
        int x, y, i=this.position.row, j=this.position.column;
        x=i+1; y=j;
        while (x<8){
            Chess.checkCheck[x][y] = true;
            if (Chess.board[x][y] != null) break;
            x++;
        }
        x=i-1;
        while (0<=x){
            Chess.checkCheck[x][y] = true;
            if (Chess.board[x][y] != null) break;
            x--;
        }
        x=i; y=j-1;
        while (0<=y) {
            Chess.checkCheck[x][y] = true;
            if (Chess.board[x][y] != null) break;
            y--;
        }
        y=j+1;
        while (y<8) {
            Chess.checkCheck[x][y] = true;
            if (Chess.board[x][y] != null) break;
            y++;
        }
    }
}
