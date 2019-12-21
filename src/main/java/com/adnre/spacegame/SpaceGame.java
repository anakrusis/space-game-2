package com.adnre.spacegame;

import com.adnre.spacegame.entity.Body;
import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.gui.*;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import com.adnre.spacegame.render.Camera;
import com.adnre.spacegame.render.RenderText;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.Map;

import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SpaceGame {

    // The window handle
    private long window;

    public static SpaceGame spaceGame = new SpaceGame();

    // The world map, and how many ticks it's existed for
    public static Map map = new Map(10,10);

    // The global viewport to be used everywhere, I guess
    public static Camera camera = new Camera(0,0,1, map);

    public static ArrayList<TextBox> guiElements = new ArrayList<>();

    public void run() {
        //System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(1280, 720, Reference.GAME_NAME + " " + Reference.VERSION, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW_PRESS){
                    MouseHandler.onClick();
                }else if (action == GLFW_RELEASE){
                    MouseHandler.onRelease();
                }
            }
        });
        glfwSetScrollCallback(window, new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                MouseHandler.onScroll(xoffset, yoffset);
            }
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        // This is up here now so it won't go up every tick
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);

        glClearColor(Reference.DEASTL_MODE ? 1 : 0.05f, 0.0f, Reference.DEASTL_MODE ? 1 : 0.05f, 0.0f);

        // Aspect ratio fixed
        glOrtho(-17.77,17.77,-10,10,-1,1);

        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback(){
            @Override
            public void invoke(long window, int width, int height) {

                // Basic window resize, should probably be redone with glOrtho soon (Can't get it to work right now)
                GL11.glViewport(0, 0, width, height);
            }
        });

        RenderText.setFont(Textures.test_texture);

        // Todo move all this to a new GuiElements class and register them

        TextBox tx = new TextBox(10.77f,-3,7,7, EnumGui.GUI_SELECTED_ENTITY);
        guiElements.add(tx);

        for (int i = 0; i < 9; i++){
            TextBoxHotbarItem tbhi = new TextBoxHotbarItem(-6.5f + (i * 1.5f), -7.5f, 1.5f, 1.5f, i);
            guiElements.add(tbhi);
        }

        TextBox storeBG = new TextBox( -6, 5, 12, 8, "Store", "", EnumGui.GUI_STORE_BACKGROUND, false);
        guiElements.add(storeBG);

        ButtonStoreBuy buyApt = new ButtonStoreBuy(-5, 4, new ItemStack(Items.ITEM_APARTMENT, 1));
        guiElements.add(buyApt);
        ButtonStoreBuy buyFac = new ButtonStoreBuy(-5, 2, new ItemStack(Items.ITEM_FACTORY, 1));
        guiElements.add(buyFac);

        Button openStoreButton = new Button(-13, -8, 5, 1, "Store", "", EnumGui.GUI_BUTTON_STORE_OPEN, true);
        guiElements.add(openStoreButton);
        Button closeStoreButton = new Button(4, 4, 1, 1, "X", "", EnumGui.GUI_BUTTON_STORE_CLOSE, false);
        guiElements.add(closeStoreButton);

        Items.register();
    }

    private void loop() {

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // All rendering goes here
            Render.renderMain(map);

            // All key handling here (for now)
            if (map.getPlayer() != null){
                double vel = map.getPlayer().getVelocity();
                float dir = map.getPlayer().getDir();

                if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE){
                    map.getPlayer().setDir(dir - 0.1f);
                }
                if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE){
                    map.getPlayer().setDir(dir + 0.1f);
                }

                if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
                    map.getPlayer().setVelocity(vel + 0.005f);
                }else if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE){
                    if (map.getPlayer().getVelocity() > -0.3f){
                        map.getPlayer().setVelocity(vel - 0.005f);
                    }
                }else{
                    map.getPlayer().setVelocity( vel / 1.01);
                }

                if (glfwGetKey(window, GLFW_KEY_Q) == GL_TRUE){
                    if (camera.getZoom() + (camera.getZoom() / 25) < Reference.MAX_ZOOM){
                        camera.setZoom( camera.getZoom() + ( camera.getZoom() / 25 ) );
                    }
                }
                if (glfwGetKey(window, GLFW_KEY_E) == GL_TRUE){
                    if (camera.getZoom() > Reference.MIN_ZOOM){
                        camera.setZoom( camera.getZoom() - ( camera.getZoom() / 25 ) );
                    }
                }

                if (glfwGetKey(window, GLFW_KEY_P) == GL_TRUE){
                    map.getPlayer().explode();
                }

                // Arrays start at zero, adggfjggfafafafa
                // Also there must be a gooder way to do this, please enlighten me

                if (glfwGetKey(window, GLFW_KEY_1) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(0);
                }
                if (glfwGetKey(window, GLFW_KEY_2) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(1);
                }
                if (glfwGetKey(window, GLFW_KEY_3) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(2);
                }
                if (glfwGetKey(window, GLFW_KEY_4) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(3);
                }
                if (glfwGetKey(window, GLFW_KEY_5) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(4);
                }
                if (glfwGetKey(window, GLFW_KEY_6) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(5);
                }
                if (glfwGetKey(window, GLFW_KEY_7) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(6);
                }
                if (glfwGetKey(window, GLFW_KEY_8) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(7);
                }
                if (glfwGetKey(window, GLFW_KEY_9) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(8);
                }

                if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE){
                    GuiHandler.storeScreen = false;
                }
            }
            MouseHandler.update(window);
            GuiHandler.update(guiElements);

            // Deleting entities marked dead, or if living, updating them
            for (int i = 0; i < map.getEntities().size(); i++){
                Entity currentEntity = map.getEntities().get(i);
                if (currentEntity.isDead()){

                    // For cleaning up the building pointers when a building is destroyed
                    if (currentEntity instanceof EntityBuilding && currentEntity.isGrounded()){
                        if (currentEntity.getGroundedBody() instanceof BodyPlanet){
                            BodyPlanet planet = (BodyPlanet)currentEntity.getGroundedBody();
                            EntityBuilding building = (EntityBuilding)currentEntity;

                            // -1 is used for floating buildings
                            if (building.getPlanetIndex() != -1){
                                planet.getBuildings()[((EntityBuilding) currentEntity).getPlanetIndex()] = null;
                            }
                        }
                    }

                    map.getEntities().remove(i);

                }else{
                    map.getEntities().get(i).update();
                }
            }

            // Updating astronomical bodies per chunk
            for (Chunk[] chunk_array : map.getChunks()){
                for (Chunk chunk : chunk_array){
                    for (Body body : chunk.getBodies()){
                        body.update();
                    }
                }
            }

            // The map steps its own time, and also handles player respawning.
            map.update();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        spaceGame.run();
    }

    public SpaceGame getSpaceGame(){
        return spaceGame;
    }
}