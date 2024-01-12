package com.snake.Views;

import com.snake.Templates.OurButton;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// Made by Marinus Juhl

public abstract class ButtonOverlayView extends StackPane
{
    protected OurButton[] buttons;
    protected int focusElementIndex;

    public void setBackground(Color overlayColor)
    {
        BackgroundFill bgFill = new BackgroundFill(overlayColor, null, getInsets());
        Background bg = new Background(bgFill);
        setBackground(bg);
    }

    public void setBasicFormatting()
    {
        VBox buttonGroup = new VBox(20.0, buttons);
        buttonGroup.setAlignment(Pos.CENTER);
        getChildren().add(buttonGroup);

        for (OurButton button : buttons)
        {
            button.button.setFont(new Font(20));
        }
    }
}
