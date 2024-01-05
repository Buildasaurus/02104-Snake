module com.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.snake to javafx.fxml;
    exports com.snake;
}
