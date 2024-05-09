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


public class CakePane extends StackPane {

    private final int x_max = 5;
    private final int y_max = 5;
    private final int pieces_kind = 6;
    private final int pieces_max = 8;


    private final int[][] position = {{1,0},{2,0},{0,1},{3,1},{0,2},{3,2},{1,3},{2,3}};	//放置蛋糕片的座標
    private final int[][] dir = {{0,-1},{-1, 0},{0,1},{1,0},{0,0}};				//蛋糕相對位置上左下右中
    private final String[] dir_str = {"top", "left", "bottom", "right", "mid"};	//蛋糕相對位置上左下右中string版


    private Circle circle = new Circle(35);
    private GridPane whole_cake = new GridPane();			//按座標放置的蛋糕片
    private ArrayList<Integer> pieces = new ArrayList<>();	//蛋糕片花色ArrayList版
    private int[] pieces_num = new int[pieces_kind];		//6種花色的各自數量array版




    public CakePane() {
        getChildren().add(circle);
        getChildren().add(whole_cake);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        whole_cake.setHgap(6);
        whole_cake.setVgap(0.5);
        for(int i=0;i<pieces_kind;i++) {
            pieces_num[i] = 0;
        }
        StackPane.setAlignment(whole_cake, Pos.CENTER);
    }

    public void place_piece(int p) {
        whole_cake.getChildren().clear();
        pieces.add(p);
        pieces_num[p]++;
        Collections.sort(pieces, Comparator.naturalOrder());
        for(int i=0;i<pieces_max;i++) {
            if(i<pieces.size()) {
                whole_cake.add(new Label(Integer.toString(pieces.get(i))), position[i][0] + 2, position[i][1] + 5);
            }
            else {
                whole_cake.add(new Label(" "), position[i][0] + 2, position[i][1] + 5);
            }
        }
        full();
    }

    public void erase_piece(int p) {
        whole_cake.getChildren().clear();
        pieces.remove(Integer.valueOf(p));
        pieces_num[p]--;
        for(int i=0;i<pieces_max;i++) {
            if(i<pieces.size()) {
                whole_cake.add(new Label(Integer.toString(pieces.get(i))), position[i][0] + 2, position[i][1] + 5);
            }
            else {
                whole_cake.add(new Label(" "), position[i][0] + 2, position[i][1] + 5);
            }
        }
    }



    public void put_cake_to_table(CakePane[][] cakes, int x, int y) {
        if(cakes[x][y].getPieces().size() > 0) {
            return;
        }

        for(int i=0;i<getPieces().size();i++) {
            cakes[x][y].place_piece(getPieces().get(i));
        }

        cakes[x][y].mix(cakes, x, y);
    }




    public void mix(CakePane[][] cakes, int x, int y) {

        PiecesPlace P = new PiecesPlace(cakes, x, y);

        for(int i=0;i<5;i++) {

            cakes[x + dir[i][0]][y + dir[i][1]].clearPieces();

            for(int p : P.getCake_dir(i).getPieces()) {
                cakes[x + dir[i][0]][y + dir[i][1]].place_piece(p);
            }

        }

    }






    public void place_pieces(CakePane[][] cakes, int x, int y, int p, int from) {
        for(int i=0;i<4;i++) {	//上左下右
            if(from != -1 && i == (from + 2) % 4) {
                continue;
            }

            int num = cakes[x][y].getPieces().size();

            for(int j=0;j<pieces_max - num;j++) {	//要放到的位置的空格數
                if(cakes[x + dir[i][0]][y + dir[i][1]].getPiecesNum(p) == 0) {
                    break;
                }

                place_pieces(cakes, x + dir[i][0], y + dir[i][1], p, i);

                move(cakes[x][y], cakes[x + dir[i][0]][y + dir[i][1]], p);
            }
        }
    }



    public int count_dir(CakePane[][] cakes, int x, int y, int p) {
        int count = 0;

        for(int i=0;i<5;i++) {
            count += cakes[x + dir[i][0]][y + dir[i][1]].getPiecesNum(p);
        }

        return count;
    }

    public void full() {
        if(pieces.size() == pieces_max) {
            clearPieces();
            circle.setFill(Color.BLUE);

            EventHandler<ActionEvent> eventHandler = e -> {
                if(circle.getFill() != Color.BLUE) {
                    circle.setFill(Color.BLUE);
                }
                else {
                    circle.setFill(Color.WHITE);
                }
            };

            Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
            animation.setCycleCount(1);
            animation.play();
        }
    }


    public void move(CakePane to, CakePane from, int p) {
        to.place_piece(p);
        from.erase_piece(p);
    }


    public void clearPieces() {
        whole_cake.getChildren().clear();
        for(int i=0;i<pieces_kind;i++) {
            pieces_num[i] = 0;
        }
        pieces.removeAll(pieces);
    }

    public void refresh() {
        clearPieces();
        int randomNumCake = (int) (Math.random() * (pieces_max-1) % (pieces_max-1)) + 1;

        for(int j=0;j<randomNumCake;j++) {
            int randomCake = (int) (Math.random() * pieces_kind % pieces_kind);

            place_piece(randomCake);

        }
    }

    public String positon(int x, int y) {
        if(x == 0 && y == 0){
            return "left_top";
        }
        else if(x == 0 && y == y_max-1) {
            return "left_bottom";
        }
        else if(x == x_max-1 && y == 0) {
            return "right_top";
        }
        else if(x == x_max-1 && y == y_max-1) {
            return "right_bottom";
        }
        else if(x == 0) {
            return "left";
        }
        else if(y == 0) {
            return "top";
        }
        else if(x == x_max-1) {
            return "right";
        }
        else if(y == y_max-1) {
            return "bottom";
        }
        return "";
    }


    public void choosed() {
        if(pieces.size() > 0) {
            circle.setFill(Color.ORANGE);
        }
        else {
            circle.setFill(Color.YELLOW);
        }
    }

    public void unchoosed() {
        circle.setFill(Color.WHITE);
    }

    public int getPiecesNum(int n) {
        return pieces_num[n];
    }

    public ArrayList<Integer> getPieces() {
        return pieces;
    }

    public static void main(String[] args) {


    }

}
