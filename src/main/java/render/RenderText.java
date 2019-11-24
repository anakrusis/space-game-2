package render;

import static org.lwjgl.opengl.GL11.*;

public class RenderText {

    static Texture rendererFont;
    public static void renderText(String input, float x, float y, float size){

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);

        glColor3d(0.75,0.75,1.0);
        rendererFont.bind();

        // Current x and y globally for the whole string
        float cx = x;
        float cy = y;

        // Local index for what part of the texture to use
        float xIndex, yIndex;
        char character;

        for (int i = 0; i < input.length(); i++){

            character = input.charAt(i);
            xIndex = character % 16f;
            yIndex = (int)(Math.floor(character / 16f));

            xIndex /= 16;
            yIndex /= 16;

            glBegin(GL_QUADS);
            glTexCoord2f(xIndex,yIndex);
            glVertex2f(cx,cy + size);
            glTexCoord2f(xIndex,yIndex + 1/16f);
            glVertex2f(cx,cy);
            glTexCoord2f(xIndex + 1/16f,yIndex + 1/16f);
            glVertex2f(cx + size,cy);
            glTexCoord2f(xIndex + 1/16f ,yIndex);
            glVertex2f(cx + size,cy + size);
            glEnd();

            cx += size;
        }

        glDisable(GL_BLEND);
    }

    public static void setFont(Texture font){
        rendererFont = font;
    }
}
