import entity.BuildingFactory;
import entity.EntityBuilding;
import world.Map;

public class MouseHandler {
    public static void onClick(){
        Map map = SpaceGame.map;
        if (map.getPlayer() != null){
            BuildingFactory factory = new BuildingFactory(map.getPlayer().getX(), map.getPlayer().getY(), map.getPlayer().getDir(), map);
            map.getEntities().add(factory);
        }
    }
}
