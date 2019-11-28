package gui;

public class TextBox {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected String header;
    protected String textBody;


    public TextBox (float x, float y, float width, float height, String header, String textBody){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.header = header;
        this.textBody = textBody;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public String getHeader() {
        return header;
    }

    public String getTextBody() {
        return textBody;
    }
}
