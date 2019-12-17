package item;

import entity.EntityBuilding;
import entity.EntityPlayer;
import entity.building.BuildingApartment;
import render.Texture;
import render.Textures;
import world.Map;

public class ItemApartment extends ItemBuilding {
    @Override
    public Texture getTexture() {
        return Textures.apartment;
    }

    @Override
    public EntityBuilding getBuilding(double x, double y, float dir, Map map, EntityPlayer player) {
        return new BuildingApartment(x, y, dir, map, player);
    }
}
