public class Position {
    int row, column;
    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }
    public Position add(Position a)
    {
        return new Position(a.row+this.row, a.column+this.column);
    }
}