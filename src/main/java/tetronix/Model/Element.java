package tetronix.Model;

public abstract class Element {
    private Position position;
    private String color;


    public Element(Position position_, String color_){
        this.position = position_;
        this.color = color_;
    }

    public Position getPosition() {return position;}

    public void setPosition(Position position) {this.position = position;}

    public String getColor() {return color;}

    public void setColor(String color) {this.color = color;}
}
