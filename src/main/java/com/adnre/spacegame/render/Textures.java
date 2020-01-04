package com.adnre.spacegame.render;

import java.util.ArrayList;

public class Textures {

    public static ArrayList<Texture> textures;
    //text
    public static final Texture test_texture = new Texture("font3.png");
    public static final Texture bold_font = new Texture ("font2.png");

    //buildings
    public static final Texture factory = new Texture("factory.png");
    public static final Texture apartment = new Texture("apartment.png");
    public static final Texture factory_off = new Texture("factory_off.png");

    //items
    public static final Texture mining_laser = new Texture("mining_laser_2.png");

    //terrain
    public static final Texture grass = new Texture("grass.png");

    public static void init(){
        textures = new ArrayList<>();
        textures.add(test_texture);
        textures.add(bold_font);
        textures.add(factory);
        textures.add(apartment);
        textures.add(factory_off);
        textures.add(mining_laser);
        textures.add(grass);

        for (Texture tex : textures){
            tex.setFilename("/" + tex.getFilename());
            tex.init();
        }
    }
}
