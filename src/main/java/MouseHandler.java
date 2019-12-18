import entity.EntityBuilding;
import entity.EntityCursor;
import entity.building.BuildingApartment;
import entity.building.BuildingFactory;
import entity.EntityPlayer;
import item.Item;
import item.ItemBuilding;
import item.ItemStack;
import item.Items;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MathUtil;
import render.Camera;
import util.MathHelper;
import util.Reference;
import world.Map;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

public class MouseHandler {

    static Map map = SpaceGame.map;
    static Camera camera = SpaceGame.camera;

    public static void onClick(){

        EntityCursor cursor = map.getCursor();

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
    }
}
