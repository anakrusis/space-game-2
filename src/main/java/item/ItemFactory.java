package item;

import render.Texture;
import render.Textures;

public class ItemFactory extends ItemBuilding {
    @Override
    public Texture getTexture() {
        return Textures.factory;
    }
}
