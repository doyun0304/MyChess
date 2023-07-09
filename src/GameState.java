public class GameState {
    public Piece[][] board = new Piece[8][8];
    public int turn;
    public boolean bQc, bKc, wQc, wKc;
    public Position whiteKing, blackKing;
}
