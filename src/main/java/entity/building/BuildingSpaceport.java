package entity.building;

import entity.EntityBuilding;
import entity.EntityPlayer;
import world.Map;

public class BuildingSpaceport extends EntityBuilding {
    public BuildingSpaceport(double x, double y, float dir, Map map, EntityPlayer playerPlaced) {
        super(x, y, dir, map, playerPlaced);
    }
}
