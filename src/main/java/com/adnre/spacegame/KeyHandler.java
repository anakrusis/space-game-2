package com.adnre.spacegame;

import com.adnre.spacegame.gui.GuiElements;
import com.adnre.spacegame.render.Camera;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.World;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class KeyHandler {
    // key presses which are handled when paused or unpaused
    public static void handlePausedKeys(long window){
        Camera camera = SpaceGame.camera;
        World world = SpaceGame.world;

        // legacy zoom controls (soon to be phased out for just scroll zooming)
        if (glfwGetKey(window, GLFW_KEY_Q) == GL_TRUE) {
            if (camera.getZoom() + (camera.getZoom() / 25) < Reference.MAX_ZOOM) {
                camera.setZoom(camera.getZoom() + (camera.getZoom() / 25));
            }
        }
        if (glfwGetKey(window, GLFW_KEY_E) == GL_TRUE) {
            if (camera.getZoom() > Reference.MIN_ZOOM) {
                camera.setZoom(camera.getZoom() - (camera.getZoom() / 25));
            }
        }

        if (world.getPlayer() != null){
            // Hotbar key presses
            if (glfwGetKey(window, GLFW_KEY_1) == GL_TRUE) {
                world.getPlayer().setCurrentItemSlot(0);
            }
            if (glfwGetKey(window, GLFW_KEY_2) == GL_TRUE) {
                world.getPlayer().setCurrentItemSlot(1);
            }
            if (glfwGetKey(window, GLFW_KEY_3) == GL_TRUE) {
                world.getPlayer().setCurrentItemSlot(2);
            }
            if (glfwGetKey(window, GLFW_KEY_4) == GL_TRUE) {
                world.getPlayer().setCurrentItemSlot(3);
            }
            if (glfwGetKey(window, GLFW_KEY_5) == GL_TRUE) {
                world.getPlayer().setCurrentItemSlot(4);
            }
            if (glfwGetKey(window, GLFW_KEY_6) == GL_TRUE) {
                world.getPlayer().setCurrentItemSlot(5);
            }
            if (glfwGetKey(window, GLFW_KEY_7) == GL_TRUE) {
                world.getPlayer().setCurrentItemSlot(6);
            }
            if (glfwGetKey(window, GLFW_KEY_8) == GL_TRUE) {
                world.getPlayer().setCurrentItemSlot(7);
            }
            if (glfwGetKey(window, GLFW_KEY_9) == GL_TRUE) {
                world.getPlayer().setCurrentItemSlot(8);
            }
        }
    }

    // key presses which are only handled when unpaused
    public static void handleUnpausedKeys(long window){
        World world = SpaceGame.world;
        if (world.getPlayer() != null) {
            double vel = world.getPlayer().getVelocity();
            float dir = world.getPlayer().getDir();

            if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
                world.getPlayer().setDir(dir - 0.1f);
            }
            if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
                world.getPlayer().setDir(dir + 0.1f);
            }

            if (world.getPlayer().getFuel() > 0){
                if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
                    world.getPlayer().setVelocity(vel + 0.005f);
                    world.getPlayer().addFuel(-0.01f);

                } else if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {
                    if (world.getPlayer().getVelocity() > -0.3f) {
                        world.getPlayer().setVelocity(vel - 0.005f);
                    }
                } else {
                    world.getPlayer().setVelocity(vel / 1.01);
                }
            } else {
                world.getPlayer().setVelocity(vel / 1.01);
            }

            if (glfwGetKey(window, GLFW_KEY_P) == GL_TRUE) {
                world.getPlayer().explode();
            }
            if (glfwGetKey(window, GLFW_KEY_I) == GL_TRUE) {
                GuiElements.WINDOW_STORE.setVisible(true);
            }
        }
    }
}
