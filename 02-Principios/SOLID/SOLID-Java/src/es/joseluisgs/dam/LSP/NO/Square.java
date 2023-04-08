package es.joseluisgs.dam.LSP.NO;

public class Square extends Rectangle {
    public Square(int side) {
        super(side, side);
    }

    public Square() {
        super();
    }

    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    public void setHeight(int height) {
        super.setWidth(height);
        super.setHeight(height);
    }
}
