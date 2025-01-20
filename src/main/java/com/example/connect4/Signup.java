package com.example.connect4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.paint.Color.ORANGE;

public class Signup{
    public Parent prt;
    public VBox sig() throws IOException{
        GameDAO gm = new GameDAO();
        gm.connect();

        VBox vb = new VBox();

        Connect4 connect4 = new Connect4();

        Label title = new Label("SIGN UP");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
        title.setFont(font);
        title.setTextFill(ORANGE);
        title.setAlignment(Pos.TOP_CENTER);

        Label nickname = new Label("Nickname :");
        TextField tf_nk = new TextField();

        Label pwd = new Label("Password :");
        TextField tf_pwd = new TextField();

        Button btn_signup = new Button("Sign Up");
        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(tf_nk.getText()==""||tf_pwd.getText()==""){
                    //alert.setContentText("Please fill in the form");
                    //alert.setAlertType(Alert.AlertType.ERROR);
                    //alert.show();
                    System.out.println("Please fill in the form");
                }
                else{
                gm.insert(tf_nk.getText(),tf_pwd.getText());
                    System.out.println("SIGNED UP");
                //then navigate to the game window
                try {
                    prt=connect4.game(tf_nk.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }}
        });


        GridPane gp = new GridPane();
        gp.add(nickname, 0, 1);
        gp.add(tf_nk, 1, 1);

        gp.add(pwd, 0, 2);
        gp.add(tf_pwd, 1, 2);

        HBox hb = new HBox();
        hb.getChildren().addAll(btn_signup);
        hb.setSpacing(15);

        gp.add(hb, 1, 3);
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(title, gp);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);

        prt=vb;

        Stage stage = new Stage();

        Scene scene =new Scene (prt, 320,240);
        stage.setTitle("Sign Up");
        stage.setScene(scene);
        stage.show();
        return vb;
    }


    public static void main(String[] args) {}
}