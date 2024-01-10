package com.snake.Views;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Highscoretext extends Text {
    public Highscoretext(String text){
        super(text);
        setFill(Color.WHITE);
        setFont(Font.font("Arial", FontWeight.BOLD, 20));
        setTextAlignment(TextAlignment.CENTER);
    }
}
        
