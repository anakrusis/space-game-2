import entity.building.BuildingApartment;
import entity.building.BuildingFactory;
import entity.EntityPlayer;
import world.Map;

public class MouseHandler {
    public static void onClick(){
        Map map = SpaceGame.map;
        if (map.getPlayer() != null){
            EntityPlayer player = map.getPlayer();
            if (player.getCurrentItemSlot() == 1){
                if (map.getPlayer().getMoney() >= 50){
                    BuildingFactory factory = new BuildingFactory(map.getPlayer().getX(), map.getPlayer().getY(), map.getPlayer().getDir(), map, map.getPlayer());
                    map.getPlayer().addMoney(-50f);
                    map.getEntities().add(factory);
                }
            }else if (player.getCurrentItemSlot() == 2){
                if (map.getPlayer().getMoney() >= 25){
                    BuildingApartment apt = new BuildingApartment(map.getPlayer().getX(), map.getPlayer().getY(), map.getPlayer().getDir(), map, map.getPlayer());
                    map.getPlayer().addMoney(-25f);
                    map.getEntities().add(apt);
                }
            }
        }
    }
}
