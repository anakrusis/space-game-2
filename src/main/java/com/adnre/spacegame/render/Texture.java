package com.adnre.spacegame.render;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class Texture {
    private int id;
    private int width;
    private int height;
    private ByteBuffer pixels;

    private String filename;

    public Texture(String filename){
        this.filename = filename;
        Textures.textures.add(this);
    }

    public void init(){
        BufferedImage bi;
        try {
            bi = ImageIO.read( Texture.class.getResource(filename) );
            width = bi.getWidth();
            height = bi.getHeight();

            int[] rawPixels = new int[width * height * 4];
            rawPixels = bi.getRGB(0, 0, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    int pixel = rawPixels[i * width + j];
                    pixels.put( (byte)((pixel >> 16) & 0xff ) );
                    pixels.put( (byte)((pixel >> 8) & 0xff ) );
                    pixels.put( (byte)((pixel) & 0xff ) );
                    pixels.put( (byte)((pixel >> 24) & 0xff ) );
                }
            }
            ((Buffer)pixels).flip();
            this.pixels = pixels;
            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,pixels);

        }catch (IOException baderror){
            baderror.printStackTrace();
        }
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ByteBuffer getPixels() {
        return pixels;
    }
}
