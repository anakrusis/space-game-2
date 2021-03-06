package com.adnre.spacegame.gui;

import com.adnre.spacegame.SpaceGame;

public class TextBox {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected String header;
    protected String textBody;
    protected float[] bgColor;

    protected EnumGui guiID;

    // Will it render?
    protected boolean isVisible;

    // Will it be handled by the onclick event?
    protected boolean isClickable;

    // Does it render with a black border (just hotbar items for now)
    protected boolean outlined;

    public TextBox (float x, float y, float width, float height, String header, String textBody, EnumGui guiID, boolean isVisible, boolean isClickable){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.header = header;
        this.textBody = textBody;
        this.bgColor = new float[]{ 0.50f, 0.55f, 0.65f };
        this.guiID = guiID;
        this.isVisible = isVisible;
        this.isClickable = isClickable;
        this.outlined = false;

        SpaceGame.guiElements.add(this);
    }

    public TextBox (float x, float y, float width, float height, EnumGui GUI_ID ){
        this (x, y, width, height, "", "", GUI_ID, true, true);
    }

    public TextBox (float x, float y, float width, float height, EnumGui GUI_ID, boolean isVisible){
        this (x, y, width, height, "", "", GUI_ID, isVisible, true);
    }

    public TextBox (float x, float y, float width, float height, EnumGui GUI_ID, boolean isVisible, boolean isClickable){
        this (x, y, width, height, "", "", GUI_ID, isVisible, isClickable);
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

    public void update(){

    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public boolean isOutlined() {
        return outlined;
    }

    public void setOutlined(boolean outlined) {
        this.outlined = outlined;
    }
}
