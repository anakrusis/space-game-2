package com.adnre.spacegame;

import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.EntityCursor;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.gui.TextBox;
import com.adnre.spacegame.gui.TextBoxHotbarItem;
import com.adnre.spacegame.item.Item;
import com.adnre.spacegame.item.ItemBuilding;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;
import org.lwjgl.BufferUtils;
import com.adnre.spacegame.render.Camera;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.Map;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

public class MouseHandler {

    static Map map = SpaceGame.map;
    static Camera camera = SpaceGame.camera;

    public static void onClick(){

        EntityCursor cursor = map.getCursor();

        TextBox cText;
        for (int i = SpaceGame.guiElements.size() - 1; i >= 0; i--){
            cText = SpaceGame.guiElements.get(i);

            if (CollisionUtil.isPointCollidingInBox(cursor.getScreenX(), cursor.getScreenY(), cText.getX(),
                    cText.getY(), cText.getWidth(), cText.getHeight())){
                cText.onClick();
                return;
            }
        }

        if (map.getPlayer() != null){
            EntityPlayer player = map.getPlayer();
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
                        building = ((ItemBuilding) item).getBuilding(cursor.getX(), cursor.getY(), player.getDir(), map, player);

                        // Deducting the cost and removing the object from inventory
                        if (player.getMoney() >= building.getPrice()){
                            player.addMoney(-building.getPrice());
                            map.getEntities().add(building);
                            itemstack.shrink();
                        }

                        // Miscellaneous item types with custom behaviors (Todo onclick behavior within each item)
                    } else if (item == Items.ITEM_MINING_LASER){
                        player.setToolActive(true);
                    }
                }
            }
        }
    }

    public static void onRelease(){
        if (map.getPlayer() != null){
            map.getPlayer().setToolActive(false);
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

        map.getCursor().setX( MathHelper.screenToWorldX(xpos, windowWidth, camera.getX(), camera.getZoom() ) );
        map.getCursor().setY( MathHelper.screenToWorldY(ypos, windowHeight, camera.getY(), camera.getZoom() ) );

        map.getCursor().setScreenX(MathHelper.screenToGLX(xpos, windowWidth));
        map.getCursor().setScreenY(MathHelper.screenToGLY(ypos, windowHeight));
    }
}