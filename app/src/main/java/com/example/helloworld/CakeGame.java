package final_project;

import java.util.*;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.geometry.*;
import javafx.animation.RotateTransition;

public class CakeGame extends Application {


    int[] index = {-1,-1};


    @Override
    public void start(Stage primaryStage) {


        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 600, 700); //視窗


        GridPane table = new GridPane();
        table.setAlignment(Pos.CENTER);
        table.setHgap(10);
        table.setVgap(10);
        table.setPadding(new Insets(10));

        CakePane[][] cakes = new CakePane[4][5];
        for (int i=0;i<4;i++) {
            for(int j=0;j<5;j++) {
                cakes[i][j] = new CakePane();
                table.add(cakes[i][j], i, j);
            }
        }


        CakePane[] new_cakes = new CakePane[4];

        for(int i=0;i<4;i++) {;
            new_cakes[i] = new CakePane();
            new_cakes[i].refresh();
        }

        HBox new_add = new HBox(10);
        new_add.setAlignment(Pos.CENTER);
        new_add.getChildren().addAll(new_cakes);

        Button button = new Button("refresh");

        pane.setCenter(table);
        pane.setBottom(new_add);
        pane.setTop(button);
        pane.setPadding(new Insets(10));

        BorderPane.setAlignment(button, Pos.CENTER_RIGHT);

        scene.setOnKeyPressed(event -> { //鍵盤按鍵事件
            if ((event.getCode() == KeyCode.A || event.getCode() == KeyCode.D || event.getCode() == KeyCode.W || event.getCode() == KeyCode.S) && index[0] == -1) {
                index[0] = 0;
                index[1] = 0;
                cakes[index[0]][index[1]].choosed();
            }
            else if (event.getCode() == KeyCode.A) { //左鍵
                if (index[0] > 0) {
                    cakes[index[0]][index[1]].unchoosed();
                    cakes[index[0] - 1][index[1]].choosed();
                    index[0]--;
                }
            }
            else if (event.getCode() == KeyCode.D) { //右鍵
                if (index[0] < 3) {
                    cakes[index[0]][index[1]].unchoosed();
                    cakes[index[0] + 1][index[1]].choosed();
                    index[0]++;
                }
            }
            else if (event.getCode() == KeyCode.W) { //上鍵
                if (index[1] > 0) {
                    cakes[index[0]][index[1]].unchoosed();
                    cakes[index[0]][index[1] - 1].choosed();
                    index[1]--;
                }
            }
            else if (event.getCode() == KeyCode.S) { //下鍵
                if (index[1] < 4) {
                    cakes[index[0]][index[1]].unchoosed();
                    cakes[index[0]][index[1] + 1].choosed();
                    index[1]++;
                }
            }
        });

        new_cakes[0].setOnMouseClicked(event -> {
            if(index[0] != -1) {
                new_cakes[0].put_cake_to_table(cakes[index[0]][index[1]], new_cakes[0]);
                cakes[index[0]][index[1]].mix(cakes, cakes[index[0]][index[1]], index[0], index[1]);
            }
        });
        new_cakes[1].setOnMouseClicked(event -> {
            if(index[0] != -1) {
                new_cakes[1].put_cake_to_table(cakes[index[0]][index[1]], new_cakes[1]);
                cakes[index[0]][index[1]].mix(cakes, cakes[index[0]][index[1]], index[0], index[1]);
            }
        });
        new_cakes[2].setOnMouseClicked(event -> {
            if(index[0] != -1) {
                new_cakes[2].put_cake_to_table(cakes[index[0]][index[1]], new_cakes[2]);
                cakes[index[0]][index[1]].mix(cakes, cakes[index[0]][index[1]], index[0], index[1]);
            }
        });
        new_cakes[3].setOnMouseClicked(event -> {
            if(index[0] != -1) {
                new_cakes[3].put_cake_to_table(cakes[index[0]][index[1]], new_cakes[3]);
                cakes[index[0]][index[1]].mix(cakes, cakes[index[0]][index[1]], index[0], index[1]);
            }
        });


        button.setOnAction(event -> {
            for(int i=0;i<4;i++) {
                new_cakes[i].refresh();
            }
        });

        primaryStage.setTitle("Cake");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}