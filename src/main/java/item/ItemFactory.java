package item;

import entity.EntityBuilding;
import entity.EntityPlayer;
import entity.building.BuildingFactory;
import render.Texture;
import render.Textures;
import world.Map;

public class ItemFactory extends ItemBuilding {
    @Override
    public Texture getTexture() {
        return Textures.factory;
    }

    @Override
    public EntityBuilding getBuilding(double x, double y, float dir, Map map, EntityPlayer player) {
        return new BuildingFactory(x, y, dir, map, player);
    }
}