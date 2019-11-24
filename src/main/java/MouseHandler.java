import entity.BuildingFactory;
import entity.EntityBuilding;
import world.Map;

public class MouseHandler {
    public static void onClick(){
        Map map = SpaceGame.map;
        if (map.getPlayer() != null && map.getPlayer().getMoney() >= 50){
            BuildingFactory factory = new BuildingFactory(map.getPlayer().getX(), map.getPlayer().getY(), map.getPlayer().getDir(), map);
            map.getPlayer().addMoney(-50f);
            map.getEntities().add(factory);
        }
    }
}
