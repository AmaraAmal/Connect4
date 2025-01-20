package com.example.connect4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.scene.paint.Color.ORANGE;

public class Login extends Application {
    public Parent prt;
    Alert alert;
    @Override
    public void start(Stage stage) throws IOException {
        //Separator sep = new Separator();
        //sep.setOrientation(Orientation.VERTICAL);

        GameDAO gm = new GameDAO();
        gm.connect();

        Signup sign= new Signup();
        Update update= new Update();

        Connect4 connect4 = new Connect4();

        String pwdd;

        Label title = new Label("LOGIN");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
        title.setFont(font);
        title.setTextFill(ORANGE);
        title.setAlignment(Pos.TOP_CENTER);

        Label Pwd = new Label("Password :");
        TextField tf_pwd= new TextField();

        Button btn_sign = new Button("Sign Up");
        btn_sign.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //navigate to signup window
                try {
                    prt=sign.sig();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Label nickname = new Label("Nickename :");
        TextField tf_nk= new TextField();

        Button btn_login = new Button("Login");
        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(tf_nk.getText()==""||tf_pwd.getText()==""){
                    //alert.setContentText("Please fill in the form");
                    //alert.setAlertType(Alert.AlertType.ERROR);
                    //alert.show();
                    System.out.println("Please fill in the form");
                }
                else{
                ResultSet rs_try= gm.login(tf_nk.getText());
                System.out.println("textfield: "+tf_pwd.getText());
                try {
                    String passwd = gm.showPassword(rs_try);
                    System.out.println("Password: " +passwd);
                    if(tf_pwd.getText().equals(passwd)){
                        System.out.println("LOGIN VERIFIED");
                        prt = connect4.game(tf_nk.getText());
                    }else {
                        System.out.println(("LOGIN DENIED"));
                        //alert.setContentText("Please check your login coords");
                        //alert.setAlertType(Alert.AlertType.ERROR);
                        //alert.show();
                        System.out.println("Please check your login coords");

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }}
        });

        Button btn_update = new Button("Manage Account");
        btn_update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // navigate to a new window of (update)
                try {
                    prt = update.uptd();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        GridPane gp = new GridPane();

        gp.add(nickname,0,1);
        gp.add(tf_nk,1,1);

        gp.add(Pwd,0,2);
        gp.add(tf_pwd,1,2);

        HBox hb = new HBox();
        hb.getChildren().addAll(btn_login,btn_sign,btn_update);
        hb.setSpacing(15);
        gp.add(hb,1,3);

        gp.setHgap(10);
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);

        VBox vb = new VBox();
        vb.getChildren().addAll(title,gp);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        //vb.getChildren().add(2,sep);
        prt=vb;

        Scene scene = new Scene(prt, 340, 240);
        stage.setTitle("LOGIN");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();}
}