package com.adnre.spacegame.render.gui;

import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;

import static org.lwjgl.opengl.GL11.*;

public class RenderText {

    static Texture rendererFont;
    static float textspacing = 1.2f;

    public static void renderText(String input, float x, float y, float size, float[] color, boolean bold){

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);

        glColor4d(color[0], color[1], color[2], 1);
        if (bold) {
            Textures.bold_font.bind();
        } else {
            rendererFont.bind();
        }

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

            // Newline handling
            if (character == 0x0a){
                cx = x;
                cy -= size * textspacing;

            // All other text characters
            }else{
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
        }

        glDisable(GL_BLEND);
        glTexCoord2f(0,1);
    }

    // The default color is like this baby blue color (it contrasts with the white of the stars)
    public static void renderText(String input, float x, float y, float size){
        renderText(input, x, y, size, new float[]{ 0.75f, 0.75f, 1 }, false);
    }

    public static void setFont(Texture font){
        rendererFont = font;
    }
}
