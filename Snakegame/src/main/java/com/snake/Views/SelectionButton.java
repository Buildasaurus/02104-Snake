package com.snake.Views;

import java.net.URL;

import com.snake.OurButton;
import com.snake.Settings;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SelectionButton extends Button {

    private double scaler = 6.0;

    private double x = Settings.windowWidth/scaler;

    private double y = Settings.windowHeight/scaler;
    
    public  SelectionButton(String buttonName) 
    {
        super(buttonName);
        this.scaler = scaler;

       
       URL url = OurButton.class.getResource("/com/snake/Graphics/smallplaybt.png");
        Image buttonbackgroundimgfile = new Image(url.toString(), Settings.windowWidth/scaler, 0, true, false);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
        setStyle("-fx-cursor: hand; -fx-font-size: 50");
        setPrefSize(Settings.windowWidth/scaler, Settings.windowHeight/scaler);
       


        
    }
  
    
    public void horizontally(Stage Stage) {
        
        // create a HBox 
            HBox hbox = new HBox(); 
  
            // create a label 
            Label label = new Label("this is HBox example");

            hbox.getChildren().add(label);
    }
}
