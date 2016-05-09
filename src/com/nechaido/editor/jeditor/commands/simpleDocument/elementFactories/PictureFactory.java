package com.nechaido.editor.jeditor.commands.simpleDocument.elementFactories;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by nechaido on 5/9/16.
 */
public class PictureFactory {
    private static PictureFactory instance;
    private HashMap<String, BufferedImage> pictures;

    public static PictureFactory getInstance() {
        if (instance == null){
            instance = new PictureFactory();
        }
        return instance;
    }

    private PictureFactory() {
        pictures = new HashMap<>();
    }

    public BufferedImage getPicure(String path){
        if (!pictures.containsKey(path)){
            try {
                BufferedImage bufferedImage = ImageIO.read(new File(path));
                pictures.put(path, bufferedImage);
            } catch (IOException e) {
                return null;
            }
        }
        return pictures.get(path);
    }
}
