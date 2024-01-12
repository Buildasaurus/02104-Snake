package com.snake.Model;

import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.HashMap;

import com.snake.Settings;

import javafx.scene.image.Image;

public class Resources
{
    private static HashMap<String, Image> map = new HashMap<String, Image>();

    /**
     * Returns an image of the image with the given name. Only expects the name, not the filetype of
     * the image. Will look for the image in com/snake/Graphics/
     *
     * @param name The name of the file - not including .png. Is assumed to a png file
     * @return The image with the given name.
     */
    public static Image getImageByName(String name)
    {
        if (!map.containsKey(name))
        {
            URL url = Resources.class.getResource("/com/snake/Graphics/" + name + ".png");
            Image image = new Image(url.toString());
            map.put(name, image);

        }
        return map.get(name);
    }

    public static Image getImageByNamesetWidth(String name, double scaler){
        if (!map.containsKey(name))
        {
            URL url = Resources.class.getResource("/com/snake/Graphics/" + name + ".png");
            Image image = new Image(url.toString(), Settings.windowWidth/scaler, 0, true, false);
            map.put(name, image);
        }
        return map.get(name);
    }

    public static Font getFontByName(String name, int size){
        URL url = Resources.class.getResource("/com/snake/Fonts/" + name + ".ttf");
        Font font = Font.loadFont(url.toString(), size);
        return font;
    }

    public static void playSoundByName(String name)
    {
        URL url = Resources.class.getResource("/com/snake/Sounds/" + name + ".wav");
        // Media sound = new Media(url.toExternalForm());
        AudioClip mediaPlayer = new AudioClip(url.toExternalForm());
        mediaPlayer.play();
    }
}
