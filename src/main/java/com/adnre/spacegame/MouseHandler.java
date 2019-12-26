package com.adnre.spacegame;

import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.EntityCursor;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.gui.EnumGui;
import com.adnre.spacegame.gui.TextBox;
import com.adnre.spacegame.item.Item;
import com.adnre.spacegame.item.ItemBuilding;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;
import org.lwjgl.BufferUtils;
import com.adnre.spacegame.render.Camera;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.World;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

public class MouseHandler {

    static Camera camera = SpaceGame.camera;

    public static void onClick(){

        World world = SpaceGame.world;
        EntityCursor cursor = world.getCursor();

        TextBox cText;
        for (int i = SpaceGame.guiElements.size() - 1; i >= 0; i--){
            cText = SpaceGame.guiElements.get(i);

            if (cText.isVisible()){
                if (CollisionUtil.isPointCollidingInBox(cursor.getScreenX(), cursor.getScreenY(), cText.getX(),
                        cText.getY(), cText.getWidth(), cText.getHeight())){
                    cText.onClick();

                    if (cText.getGuiID() == EnumGui.GUI_BUTTON_STORE_OPEN){
                        GuiHandler.storeScreen = true;
                    }else if (cText.getGuiID() == EnumGui.GUI_BUTTON_STORE_CLOSE){
                        GuiHandler.storeScreen = false;
                    }else if (cText.getGuiID() == EnumGui.GUI_BUTTON_PAUSE_RESUME){
                        SpaceGame.setPaused(false);
                    }

                    return;
                }
            }
        }

        if (world.getPlayer() != null){
            EntityPlayer player = world.getPlayer();
            EntityBuilding building = null;

            // 16 units is the arbitrary item use radius
            if (MathHelper.distance(cursor.getX(), cursor.getY(), player.getX(), player.getY()) < Reference.TOOL_USE_RADIUS){

                // Checking the player's inventory for ItemBuildings
                ItemStack itemstack = player.getCurrentItemStack();
                if (itemstack != null){
                    Item item = itemstack.getItem();

                    // All building type items work the same
                    if (item instanceof ItemBuilding){

                        // Creating a new EntityBuilding
                        building = ((ItemBuilding) item).getBuilding(cursor.getX(), cursor.getY(), player.getDir(), world);

                        // Deducting the cost and removing the object from inventory
                        world.spawnEntity(building);
                        itemstack.shrink();

                        // Miscellaneous item types with custom behaviors (Todo onclick behavior within each item)
                    } else if (item == Items.ITEM_MINING_LASER){
                        player.setToolActive(true);
                    }
                }
            }
        }
    }

    public static void onRelease(){
        World world = SpaceGame.world;
        if (world.getPlayer() != null){
            world.getPlayer().setToolActive(false);
        }
    }

    public static void onScroll(double dx, double dy){
        // Zooming in
        if (dy > 0){
            if (camera.getZoom() + (camera.getZoom() / 25) < Reference.MAX_ZOOM){
                camera.setZoom( camera.getZoom() + ( camera.getZoom() / 25 ) * dy * 5 );
            }
        // Zooming out
        }else{
            if (camera.getZoom() > Reference.MIN_ZOOM){
                camera.setZoom( camera.getZoom() + ( camera.getZoom() / 25 ) * dy * 5 );
            }
        }
    }

    public static void update( long window ){
        World world = SpaceGame.world;
        // :(
        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, posX, null);
        double xpos = posX.get(0);

        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, null, posY);
        double ypos = posY.get(0);

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(window, w, h);
        int windowWidth = w.get();
        int windowHeight = h.get();

        world.getCursor().setX( MathHelper.screenToWorldX(xpos, windowWidth, camera.getX(), camera.getZoom() ) );
        world.getCursor().setY( MathHelper.screenToWorldY(ypos, windowHeight, camera.getY(), camera.getZoom() ) );

        world.getCursor().setScreenX(MathHelper.screenToGLX(xpos, windowWidth));
        world.getCursor().setScreenY(MathHelper.screenToGLY(ypos, windowHeight));
    }
}
