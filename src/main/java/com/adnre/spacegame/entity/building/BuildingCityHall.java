package com.adnre.spacegame.entity.building;

import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.util.NymGen;
import com.adnre.spacegame.world.City;
import com.adnre.spacegame.world.World;

import java.util.UUID;

public class BuildingCityHall extends EntityBuilding {
    public BuildingCityHall(double x, double y, float dir, World world, UUID nationUUID) {
        super(x, y, dir, world, nationUUID);
        this.price = 500;
        this.name = "City Hall";
    }

    public Texture getTexture(){
        return Textures.city_hall;
    }

    @Override
    public ItemStack getItemDropped() {
        return new ItemStack (Items.ITEM_CITY_HALL, 1);
    }

    @Override
    public Texture getWindowTexture() {
        return Textures.city_hall_windows;
    }

    @Override
    public void update() {
        super.update();
        if (dead && grounded){
            if (this.getCity() != null && this.getPlanetIndex() == this.getCity().getCenterIndex()){
                ((BodyPlanet)this.getGroundedBody()).getCities().remove(this.getCity().getUuid());
            }
        }else if (grounded){
            if (this.getCity() == null) {
                City city = new City(NymGen.newName(), this.getChunk().getX(), this.getChunk().getY(), this.getGroundedBodyUUID());
                ((BodyPlanet)this.getGroundedBody()).getCities().put(city.getUuid(), city);
                city.setCenterIndex(planetIndex);
                city.getTerrainIndexes().add(planetIndex);
                city.setNationUUID( nationUUID );

                int tsize = getGroundedBody().getTerrainSize();
                for (int i = 1; i < tsize; i++){
                    int index = MathHelper.loopyMod( planetIndex + i, tsize );
                    EntityBuilding build = ((BodyPlanet) getGroundedBody()).getBuilding(index);
                    if (build != null){
                        if (build.getNation() == getNation() && build.getCity() == null){
                            city.getTerrainIndexes().add(index);
                        } else {
                            break;
                        }
                    }else{
                        break;
                    }
                }
                for (int i = 1; i < tsize; i++){
                    int index = MathHelper.loopyMod( planetIndex - i, tsize );
                    EntityBuilding build = ((BodyPlanet) getGroundedBody()).getBuilding(index);
                    if (build != null){
                        if (build.getNation() == getNation() && build.getCity() == null){
                            city.getTerrainIndexes().add(index);
                        } else {
                            break;
                        }
                    }else{
                        break;
                    }
                }
            }
        }
    }
}
