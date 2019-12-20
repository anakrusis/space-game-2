package com.adnre.spacegame.gui;

public class TextBox {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected String header;
    protected String textBody;
    protected float[] bgColor;

    protected EnumGui guiID;

    protected boolean isVisible;

    public TextBox (float x, float y, float width, float height, String header, String textBody, EnumGui guiID, boolean isVisible){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.header = header;
        this.textBody = textBody;
        this.bgColor = new float[]{ 0.50f, 0.55f, 0.65f };
        this.guiID = guiID;
        this.isVisible = isVisible;
    }

    public TextBox (float x, float y, float width, float height, EnumGui GUI_ID ){
        this (x, y, width, height, "", "", GUI_ID, true);
    }

    public TextBox (float x, float y, float width, float height, EnumGui GUI_ID, Boolean isVisible){
        this (x, y, width, height, "", "", GUI_ID, isVisible);
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

    public float[] getBgColor() {
        return bgColor;
    }

    public void setBgColor(float[] bgColor) {
        this.bgColor = bgColor;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public void addTextBody(String textBody) { this.textBody += textBody; }

    public void setHeader(String header) {
        this.header = header;
    }

    public EnumGui getGuiID() {
        return guiID;
    }

    public void onClick(){

    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
