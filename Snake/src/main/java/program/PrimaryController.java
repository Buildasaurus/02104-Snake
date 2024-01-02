package com.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController implements IController{

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public int getView()
    {
        return 2;
    }
}
