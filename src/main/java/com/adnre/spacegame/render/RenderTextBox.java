package com.adnre.spacegame.render;

import com.adnre.spacegame.gui.TextBox;

import static org.lwjgl.opengl.GL11.*;

public class RenderTextBox {
    static float headerSize = 0.35f;

    public static void renderTextBox(TextBox tx){

        // Colored background of textbox
        glColor3f(tx.getBgColor()[0], tx.getBgColor()[1], tx.getBgColor()[2]);
        glBegin(GL_POLYGON);
        glVertex2d(tx.getX(), tx.getY());
        glVertex2d(tx.getX() + tx.getWidth() , tx.getY() );
        glVertex2d(tx.getX() + tx.getWidth() , tx.getY() - tx.getHeight() );
        glVertex2d(tx.getX(), tx.getY() - tx.getHeight() );
        glEnd();

        // Text
        RenderText.renderText(tx.getHeader(), tx.getX() + 0.2f, tx.getY() - headerSize - 0.2f, headerSize, new float[]{ 0, 0, 0 }, true);
        RenderText.renderText(tx.getTextBody(), tx.getX() + 0.2f, tx.getY() - headerSize * 2 - 0.2f, headerSize, new float[]{ 0, 0, 0 }, false );

        // Black outline around textbox
        glColor3f(0,0,0);
        glBegin(GL_LINE_LOOP);
        glVertex2d(tx.getX(), tx.getY());
        glVertex2d(tx.getX() + tx.getWidth() , tx.getY() );
        glVertex2d(tx.getX() + tx.getWidth() , tx.getY() - tx.getHeight() );
        glVertex2d(tx.getX(), tx.getY() - tx.getHeight() );
        glEnd();
    }
}
