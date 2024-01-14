package com.snake.Templates;

import com.snake.Settings;
import com.snake.Utils.Resources;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

public class SelectionButton extends Button
{

    private double scaler = 7.0;

    /**
     * A button with implemented standard design for easy use.
     *
     * @param buttonName The text on the button
     */
    public SelectionButton(String buttonName)
    {
        super(buttonName);
        Image buttonbackgroundimgfile = Resources.getImageByNamesetWidth("smallplaybt", scaler);
        BackgroundSize buttonbackgroundsize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, false, false);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                buttonbackgroundsize);
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
        setStyle("-fx-cursor: hand");
        Font font = Resources.getFontByName("Adventure", 20);
        setFont(font);

        // fixed size for the button
        double width = Settings.windowWidth / scaler;
        double height = Settings.windowHeight / scaler;
        setMinSize(width, height);
        setMaxSize(10, 10);
        setPrefSize(10, 10);
    }


    /**
     * Making the button not pressed
     */
    public void changeback()
    {
        Image buttonbackgroundimgfile = Resources.getImageByNamesetWidth("smallplaybt", scaler);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1, 1, true, true, false, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
    }

    /**
     * Displaying the button as pressed
     */
    public void pressed()
    {
        Image buttonbackgroundimgfile =
                Resources.getImageByNamesetWidth("smallplaybtpressed", scaler);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1, 1, true, true, false, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
    }
}
