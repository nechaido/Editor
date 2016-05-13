package com.nechaido.editor.jeditor.elementFactories;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by nechaido on 5/9/16.
 */
public class PictureFactory {
    private static PictureFactory instance;
    private HashMap<String, BufferedImage> pictures;

    private PictureFactory() {
        pictures = new HashMap<>();
    }

    public static PictureFactory getInstance() {
        if (instance == null) {
            instance = new PictureFactory();
        }
        return instance;
    }

    public BufferedImage getPicure(String path) {
        if (!pictures.containsKey(path)) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new File(path));
                pictures.put(path, bufferedImage);
            } catch (IOException e) {
                return null;
            }
        }
        return pictures.get(path);
    }

    public String getPath(BufferedImage image) {
        for (Map.Entry<String, BufferedImage> entry : pictures.entrySet()){
            if (Objects.equals(image, entry.getValue())){
                return entry.getKey();
            }
        }
        return null;
    }
}
