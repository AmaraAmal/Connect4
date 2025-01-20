package com.example.connect4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
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
import java.util.Collection;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.ORANGE;

public class Update {
    public Parent prt;
    public VBox uptd() throws IOException{
        GameDAO gm=new GameDAO();
        gm.connect();

        Delete delete = new Delete();

        Label title = new Label("UPDATE");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
        title.setFont(font);
        title.setTextFill(ORANGE);
        title.setAlignment(Pos.TOP_CENTER);

        Label nickname = new Label("Nickename :");
        TextField tf_nk= new TextField();

        Label Pwd = new Label("New Password :");
        TextField tf_pwd= new TextField();
        Button btn_update = new Button("Update");
        btn_update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(tf_nk.getText()==""||tf_pwd.getText()==""){
                    //alert.setContentText("Please fill in the form");
                    //alert.setAlertType(Alert.AlertType.ERROR);
                    //alert.show();
                    System.out.println("Please fill in the form");
                }
                else{
                gm.update(tf_pwd.getText(), tf_nk.getText());
            }}
        });

        Button btn_delete = new Button("Delete ");
        btn_delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //then navigate to the delete window
                try {
                    prt=delete.dlt();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        GridPane gp = new GridPane();
        gp.add(nickname,0,1);
        gp.add(tf_nk,1,1);

        HBox hb = new HBox();
        hb.getChildren().addAll(btn_update,btn_delete);
        hb.setSpacing(15);

        gp.add(hb,1,4);
        gp.add(Pwd,0,2);
        gp.add(tf_pwd,1,2);

        gp.setHgap(10);
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);


        VBox vb = new VBox();
        vb.getChildren().addAll(title,gp);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);

        prt=vb;

        Stage stage = new Stage();

        Scene scene = new Scene(prt, 320, 240);
        stage.setTitle("UPDATE");
        stage.setScene(scene);
        stage.show();
        return vb;
    }


    public static void main(String[] args) {}
}