package final_project;

import java.util.*;
import javafx.util.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.geometry.*;
import javafx.animation.*;

public class CakeGame extends Application {//I'm Joyce


    final int x_max = 5;
    final int y_max = 5;
    final int new_cake_max = 4;


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

        CakePane[][] cakes = new CakePane[x_max][y_max];
        for (int i=0;i<x_max;i++) {
            for(int j=0;j<y_max;j++) {
                cakes[i][j] = new CakePane();
                table.add(cakes[i][j], i, j);
            }
        }


        CakePane[] new_cakes = new CakePane[new_cake_max];

        for(int i=0;i<4;i++) {;
            new_cakes[i] = new CakePane();
            new_cakes[i].refresh();
        }

        HBox new_add = new HBox(10);
        new_add.setAlignment(Pos.CENTER);
        new_add.getChildren().addAll(new_cakes);

        Button button_refresh = new Button("refresh");
        Button button_clear = new Button("clear");
        VBox buttons = new VBox(10);
        buttons.getChildren().addAll(button_refresh, button_clear);
        buttons.setAlignment(Pos.BASELINE_RIGHT);

        pane.setCenter(table);
        pane.setBottom(new_add);
        pane.setTop(buttons);
        pane.setPadding(new Insets(10));





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
                if (index[0] < x_max-1) {
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
                if (index[1] < y_max-1) {
                    cakes[index[0]][index[1]].unchoosed();
                    cakes[index[0]][index[1] + 1].choosed();
                    index[1]++;
                }
            }
        });

        for(int i=0;i<new_cake_max;i++) {

            final int a = i;

            new_cakes[a].setOnMouseClicked(event -> {
                if(index[0] != -1) {
                    new_cakes[a].put_cake_to_table(cakes[index[0]][index[1]]);
                    cakes[index[0]][index[1]].mix(cakes, index[0], index[1]);
                }
            });
        }




        button_refresh.setOnAction(event -> {
            for(int i=0;i<new_cake_max;i++) {
                new_cakes[i].refresh();
            }
        });

        button_clear.setOnAction(event -> {
            for(int i=0;i<x_max;i++) {
                for(int j=0;j<y_max;j++) {
                    cakes[i][j].clearPieces();
                }
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