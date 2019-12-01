import entity.Body;
import entity.body.BodyPlanet;
import entity.Entity;
import entity.EntityBuilding;
import gui.EnumGui;
import gui.TextBox;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import render.Camera;
import render.RenderText;
import render.Texture;
import render.Textures;
import util.MathHelper;
import util.Reference;
import world.Chunk;
import world.Map;

import java.nio.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class SpaceGame {

    // The window handle
    private long window;

    // The world map, and how many ticks it's existed for
    public static Map map = new Map(10,10);

    // The global viewport to be used everywhere, I guess
    public static Camera camera = new Camera(0,0,1, map);

    public static ArrayList<TextBox> guiElements = new ArrayList<>();

    public static final int CHUNK_SIZE = 512;

    public static Texture test_texture;

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

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
        glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (action == GLFW_PRESS){
                    MouseHandler.onClick();
                }
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

        TextBox tx = new TextBox(10.77f,-3,7,7,"Test","", EnumGui.GUI_SELECTED_ENTITY);
        guiElements.add(tx);
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

                if (glfwGetKey(window, GLFW_KEY_1) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(1);
                }
                if (glfwGetKey(window, GLFW_KEY_2) == GL_TRUE){
                    map.getPlayer().setCurrentItemSlot(2);
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

        new SpaceGame().run();
    }

}