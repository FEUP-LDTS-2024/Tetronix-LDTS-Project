package tetronix.Model;

public class Position {
    private int row_identifier;
    private int column_identifier;

    private int previousRowIdentifier;
    private int previousColumnIdentifier;

    public Position(int column_identifier_, int row_identifier_) {
        this.column_identifier = column_identifier_;
        this.row_identifier = row_identifier_;
        this.previousColumnIdentifier = column_identifier_;
        this.previousRowIdentifier = row_identifier_;
    }

    public int getColumn_identifier(){
        return this.column_identifier;
    }

    public void setColumn_identifier(int column_identifier_){
        this.column_identifier = column_identifier_;
    }

    public int getRow_identifier(){
        return this.row_identifier;
    }

    public void setRow_identifier(int row_identifier_){this.row_identifier = row_identifier_;}

    public int getPreviousColumnIdentifier() {
        return previousColumnIdentifier;
    }

    public int getPreviousRowIdentifier() {
        return previousRowIdentifier;
    }
}