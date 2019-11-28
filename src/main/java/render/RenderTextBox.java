package render;

import gui.TextBox;

import static org.lwjgl.opengl.GL11.*;

public class RenderTextBox {
    static float headerSize = 0.5f;

    public static void renderTextBox(TextBox tx){
        glColor3f(tx.getBgColor()[0], tx.getBgColor()[1], tx.getBgColor()[2]);
        glBegin(GL_POLYGON);
        glVertex2d(tx.getX(), tx.getY());
        glVertex2d(tx.getX() + tx.getWidth() , tx.getY() );
        glVertex2d(tx.getX() + tx.getWidth() , tx.getY() - tx.getHeight() );
        glVertex2d(tx.getX(), tx.getY() - tx.getHeight() );
        glEnd();


        RenderText.renderText(tx.getHeader(), tx.getX(), tx.getY() - headerSize - 0.2f, headerSize, new float[]{ 0, 0, 0 });
    }
}
