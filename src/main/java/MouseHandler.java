import entity.EntityBuilding;
import entity.EntityCursor;
import entity.building.BuildingApartment;
import entity.building.BuildingFactory;
import entity.EntityPlayer;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MathUtil;
import render.Camera;
import util.MathHelper;
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

            if (player.getCurrentItemSlot() == 1){

                building = new BuildingFactory(cursor.getX(), cursor.getY(), map.getPlayer().getDir(), map, map.getPlayer());

            }else if (player.getCurrentItemSlot() == 2){

                building = new BuildingApartment(cursor.getX(), cursor.getY(), map.getPlayer().getDir(), map, map.getPlayer());

            }
            if (building != null && MathHelper.distance(cursor.getX(), cursor.getY(), player.getX(), player.getY()) < 16){
                if (map.getPlayer().getMoney() >= building.getPrice()){
                    map.getPlayer().addMoney(-building.getPrice());
                    map.getEntities().add(building);
                }
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
    }
}
