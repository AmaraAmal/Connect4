package com.example.connect4;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Toast  {

    public StackPane showToast(String msg) {
        Label toastLabel = new Label(msg);
        toastLabel.setStyle("-fx-background-color: #666666; -fx-text-fill: white; -fx-padding: 10px;");

        StackPane stackPane = new StackPane(toastLabel);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);

        //Stage stage = new Stage();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), stackPane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setDelay(Duration.millis(500));
        fadeTransition.setOnFinished(event -> {
            //stage.close();
            toastLabel.setStyle("-fx-background-color: white; -fx-text-fill: white; -fx-padding: 10px;");

        });

        //Scene scene = new Scene(stackPane, 300, 100);
        //stage.setScene(scene);
        //stage.setOpacity(0.0); // Hide the stage initially
        //stage.show();
        fadeTransition.play();
        return stackPane;
    }

    public static void main(String[] args) {
        //launch(args);
    }
}
