import entity.EntityBuilding;
import entity.EntityCursor;
import entity.building.BuildingApartment;
import entity.building.BuildingFactory;
import entity.EntityPlayer;
import org.lwjgl.system.MathUtil;
import util.MathHelper;
import world.Map;

public class MouseHandler {
    public static void onClick(){
        Map map = SpaceGame.map;
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
}
