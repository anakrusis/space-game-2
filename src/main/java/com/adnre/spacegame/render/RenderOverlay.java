package com.adnre.spacegame.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glColor4d;

public class RenderOverlay {
    public static void renderOverlay(){
        glEnable(GL_BLEND);
        glColor4d(0.5d,0.5d,0.5d, 0.5);
        glBlendFunc(GL_SRC_COLOR, GL_ONE_MINUS_SRC_COLOR);

        glBegin(GL_QUADS);

        glVertex2d(-17.77,-10);
        glVertex2d(-17.77,10);
        glVertex2d(17.77, 10);
        glVertex2d(17.77, -10);

        glEnd();
        glDisable(GL_BLEND);
    }
}
