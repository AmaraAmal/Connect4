package com.example.connect4;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.paint.Color.BLACK;

public class Connect4{
    int rows = 6;
    int columns = 7;
    int cellule_size = 80;
    int btn_size = 50;

    Color player1_piece = Color.BLACK;
    Color player2_piece = Color.CYAN;

    Circle winner_circle;
    Label winner;
    int check;
    boolean play=true;


    int[][] board = new int[rows+1][columns];
    boolean player1_turn = true;
    GridPane gridPane;
    Button button;

    GameDAO gm = new GameDAO();
    String nickname;

    public GridPane game(String name) throws IOException {

        nickname=name;
        gm.connect();

        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: orange;");

        winner = new Label("won");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30);
        winner.setFont(font);
        winner.setTextFill(BLACK);
        winner.setVisible(false);
       //winner.setAlignment(Pos.TOP_CENTER);
        gridPane.add(winner,1,0);

        winner_circle = new Circle(cellule_size/3);
        //winner_circle.setFill(Color.WHITE);
        winner_circle.setVisible(false);
        //HBox hb = new HBox();
        //hb.setPadding(new Insets(50, 50, 50, 50));
        //hb.getChildren().add(winner_piece);
        gridPane.add(winner_circle,0,0);

        for (int row = 1; row < rows+1; row++) {
            for (int col = 0; col < columns; col++) {
                Rectangle rect = new Rectangle(cellule_size,cellule_size);
                rect.setFill(Paint.valueOf("white"));
                gridPane.add(rect, col, row);
            }
        }
            for (int col = 0; col < columns; col++) {
                final int coll=col;
                button = new Button("Drop");
                button.setPrefSize(cellule_size, btn_size);
                button.setStyle("-fx-border-color: black;");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        dropPiece(coll);
                        checkWin();
                    }
                });
                gridPane.add(button, col, 7);
            }
        //button.setDisable(false);

        Stage stage = new Stage();

        gridPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gridPane,650,660);
        stage.setTitle("Connect4");
        stage.setScene(scene);
        stage.show();
        return gridPane;
    }

    public void dropPiece(int col) {
        if(play == true){
            check = getFirstEmptyRow(col);
        }else {
            check =- 1;
        }
        if (check != -1) {
            Circle Piece = new Circle(cellule_size/2);
            if(player1_turn == true){
                Piece.setFill(player1_piece);
            }else {
                Piece.setFill(player2_piece);
            }
            gridPane.add(Piece, col, check);
            if(player1_turn == true){
                board[check][col] = 1;
                for (int[] rw : board) {
                    for (int cell : rw) {
                        System.out.print(cell + "\t");
                    }
                    System.out.println();
                }
            }else {
                board[check][col] = 2;
                for (int[] rw : board) {
                    for (int cell : rw) {
                        System.out.print(cell + "\t");
                    }
                    System.out.println();
                }
            }
            System.out.println("----------------");
            player1_turn = !player1_turn;
        }
    }

    public int getFirstEmptyRow(int col) {
        for (int row = rows; row > 0; row--) {
            if (board[row][col] == 0) {
                return row;
            }
        }
        return -1;
    }

    public void checkWin(){
        int p1_count;
        int p2_count;

        //COLUMNS TEST
        for (int col = 0; col < columns ; col++){
            p1_count = 0;
            p2_count = 0;
            for (int row = 1 ; row < rows ;row++){
                if (board[row][col] == 1 && board[row + 1][col] == 1){
                    p1_count += 1;
                } else if (board[row][col] == 2 && board[row + 1][col] == 2) {
                    p2_count += 1;
                } else{
                    p1_count = 0;
                    p2_count = 0;
                }
            if (p1_count == 3){
                winner_circle.setFill(player1_piece);
                winner_circle.setVisible(true);
                winner.setVisible(true);
                //button.setDisable(true);
                //gridPane.setVisible(true);
                play=false;
                gm.setScore(nickname);
            } else if (p2_count == 3) {
                winner_circle.setFill(player2_piece);
                winner_circle.setVisible(true);
                winner.setVisible(true);
                //button.setDisable(true);
                //gridPane.setVisible(true);
                play=false;
                gm.setScore(nickname);
            }
            }
        }

      //LINES TEST
        for (int row = 1; row < rows+1 ; row++){
            p1_count = 0;
            p2_count = 0;
            for (int col = 0 ; col < columns-1 ; col++){
                if (board[row][col] == 1 && board[row][col+1] == 1){
                    p1_count += 1;
                } else if (board[row][col] == 2 && board[row][col+1] == 2) {
                    p2_count += 1;
                } else{
                    p1_count = 0;
                    p2_count = 0;
                }
                if (p1_count == 3){
                    winner_circle.setFill(player1_piece);
                    winner_circle.setVisible(true);
                    winner.setVisible(true);
                    play=false;
                    gm.setScore(nickname);
                } else if (p2_count == 3) {
                    winner_circle.setFill(player2_piece);
                    winner_circle.setVisible(true);
                    winner.setVisible(true);
                    play=false;
                    gm.setScore(nickname);
                }
            }
        }

        //DIAGS TEST
        int rw = 1;
        for(int col = 3; col < columns ; col++){
            int k = col;
            p1_count = 0;
            p2_count = 0;
            if(col == 6){
                while (k>1){
                    if (board[rw + 1][k - 1] == 1 && board[rw][k] == 1){
                        p1_count += 1;
                        if (p1_count == 3){
                            winner_circle.setFill(player1_piece);
                            winner_circle.setVisible(true);
                            winner.setVisible(true);
                            play=false;
                            gm.setScore(nickname);
                        }
                    } else if (board[rw + 1][k - 1] == 2 && board[rw][k] == 2) {
                        p2_count += 1;
                        if (p2_count == 3){
                            winner_circle.setFill(player2_piece);
                            winner_circle.setVisible(true);
                            winner.setVisible(true);
                            play=false;
                            gm.setScore(nickname);
                        }
                    }
                    k -= 1;
                    if (rw == 7){
                        rw = 0;
                    }else{
                        rw += 1;
                    }
                }
                rw = 2;
                for(int m=3;m<7;m++){
                    rw=m;
                    k=col;
                    p1_count = 0;
                    p2_count = 0;
                    while (rw < 5){
                        if(board[rw + 1][k - 1] == 1 && board[rw][k] == 1){
                            p1_count += 1;
                            if (p1_count == 3){
                                winner_circle.setFill(player1_piece);
                                winner_circle.setVisible(true);
                                winner.setVisible(true);
                                play=false;
                                gm.setScore(nickname);
                            }
                        } else if (board[rw + 1][k - 1] == 2 && board[rw][k] == 2) {
                            p2_count += 1;
                            if (p2_count == 3){
                                winner_circle.setFill(player2_piece);
                                winner_circle.setVisible(true);
                                winner.setVisible(true);
                                play=false;
                                gm.setScore(nickname);
                            }
                        }
                        rw += 1;
                        k -= 1;
                    }

                }

            }else {
                rw = 1;
                p1_count = 0;
                p2_count = 0;
                while (k > 0){
                    if(board[rw + 1][k - 1] == 1 && board[rw][k] == 1){
                        p1_count += 1;
                        if (p1_count == 3){
                            winner_circle.setFill(player1_piece);
                            winner_circle.setVisible(true);
                            winner.setVisible(true);
                            play=false;
                            gm.setScore(nickname);
                        }
                    } else if (board[rw + 1][k - 1] == 2 && board[rw][k] == 2) {
                        p2_count += 1;
                        if (p2_count == 3){
                            winner_circle.setFill(player2_piece);
                            winner_circle.setVisible(true);
                            winner.setVisible(true);
                            play=false;
                            gm.setScore(nickname);
                        }
                    }
                    k -= 1;
                    if(rw==7){
                        rw=0;
                    }
                    else{rw+=1;}
                }
                rw=1;
            }
        }
        //DIAGS REVERSED TEST
        int row = 1;
        for(int col = 3; col > -1 ; col--){
            int k = col;
            p1_count = 0;
            p2_count = 0;
            if(col == 0){
                while (k<5){
                    if (board[row + 1][k + 1] == 1 && board[row][k] == 1){
                        p1_count += 1;
                        if (p1_count == 3){
                            winner_circle.setFill(player1_piece);
                            winner_circle.setVisible(true);
                            winner.setVisible(true);
                            play=false;
                            gm.setScore(nickname);
                        }
                    } else if (board[row + 1][k + 1] == 2 && board[row][k] == 2) {
                        p2_count += 1;
                        if (p2_count == 3){
                            winner_circle.setFill(player2_piece);
                            winner_circle.setVisible(true);
                            winner.setVisible(true);
                            play=false;
                            gm.setScore(nickname);
                        }
                    }
                    k += 1;
                    if (row == 7){
                        row = 0;
                    }else{
                        row += 1;
                    }
                }
                row = 2;
                for(int m=1;m<3;m++){
                    row=m;
                    k=col;
                    p1_count = 0;
                    p2_count = 0;
                    while (row < 5){
                        if(board[row + 1][k + 1] == 1 && board[row][k] == 1){
                            p1_count += 1;
                            if (p1_count == 3){
                                winner_circle.setFill(player1_piece);
                                winner_circle.setVisible(true);
                                winner.setVisible(true);
                                play=false;
                                gm.setScore(nickname);
                            }
                        } else if (board[row + 1][k + 1] == 2 && board[row][k] == 2) {
                            p2_count += 1;
                            if (p2_count == 3){
                                winner_circle.setFill(player2_piece);
                                winner_circle.setVisible(true);
                                winner.setVisible(true);
                                play=false;
                                gm.setScore(nickname);
                            }
                        }
                        row += 1;
                        k += 1;
                    }

                }

            }else {
                row = 1;
                p1_count = 0;
                p2_count = 0;
                while (k < 6){
                    if(board[row + 1][k + 1] == 1 && board[row][k] == 1){
                        p1_count += 1;
                        if (p1_count == 3){
                            winner_circle.setFill(player1_piece);
                            winner_circle.setVisible(true);
                            winner.setVisible(true);
                            play=false;
                            gm.setScore(nickname);
                        }
                    } else if (board[row + 1][k + 1] == 2 && board[row][k] == 2) {
                        p2_count += 1;
                        if (p2_count == 3){
                            winner_circle.setFill(player2_piece);
                            winner_circle.setVisible(true);
                            winner.setVisible(true);
                            play=false;
                            gm.setScore(nickname);
                        }
                    }
                    k += 1;
                    if(row==7){
                        row=0;
                    }
                    else{row+=1;}
                }
                row=1;
            }
        }
    }

    public static void main(String[] args) {
        //launch(args);
    }
}