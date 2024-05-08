package final_project;

import java.util.*;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.geometry.*;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;;

public class CakePane extends StackPane {

    private Circle circle = new Circle(35);
    private GridPane whole_cake = new GridPane();
    private ArrayList<Integer> pieces = new ArrayList<>();
    private int[] pieces_num = {0, 0, 0, 0, 0, 0};
    private int[][] position = {{1,0},{2,0},{0,1},{3,1},{0,2},{3,2},{1,3},{2,3}};

    public CakePane() {
        getChildren().add(circle);
        getChildren().add(whole_cake);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        whole_cake.setHgap(6);
        whole_cake.setVgap(0.5);
        StackPane.setAlignment(whole_cake, Pos.CENTER);
    }

    public void place_piece(int p) {
        whole_cake.getChildren().clear();
        pieces.add(p);
        pieces_num[p]++;
        Collections.sort(pieces, Comparator.naturalOrder());
        for(int i=0;i<8;i++) {
            if(i<pieces.size()) {
                whole_cake.add(new Label(Integer.toString(pieces.get(i))), position[i][0] + 2, position[i][1] + 5);
            }
            else {
                whole_cake.add(new Label(" "), position[i][0] + 2, position[i][1] + 5);
            }
        }
    }

    public void erase_piece(int p) {
        whole_cake.getChildren().clear();
        pieces.remove(Integer.valueOf(p));
        pieces_num[p]--;
        for(int i=0;i<8;i++) {
            if(i<pieces.size()) {
                whole_cake.add(new Label(Integer.toString(pieces.get(i))), position[i][0] + 2, position[i][1] + 5);
            }
            else {
                whole_cake.add(new Label(" "), position[i][0] + 2, position[i][1] + 5);
            }
        }
    }



    public void put_cake_to_table(CakePane plate, CakePane new_cake) {
        if(plate.getPieces().size() > 0) {
            return;
        }

        for(int i=0;i<new_cake.getPieces().size();i++) {
            plate.place_piece(new_cake.getPieces().get(i));
        }
    }

    public void mix(CakePane[][] cakes, CakePane new_cake, int x, int y) {
        for(int i=0;i<6;i++) {
            if(new_cake.getPiecesNum(i) > 0){
                if(new_cake.getPieces().size() == new_cake.getPiecesNum(i)) {
                    place_mid(cakes, new_cake, x, y, i);
                }
            }
        }
    }

    public void place_mid(CakePane[][] cakes, CakePane new_cake, int x, int y, int p) {
        int num = new_cake.getPiecesNum(p);
        if(y>0 && cakes[x][y-1].getPiecesNum(p) > 0) {
            int from_num = cakes[x][y-1].getPiecesNum(p);
            if(from_num + num >= 8) {
                for(int i=0;i<8 - num;i++) {
                    move(new_cake, cakes[x][y-1], p);
                }
                new_cake.full();
                return;
            }
            else {
                for(int i=0;i<from_num;i++) {
                    move(new_cake, cakes[x][y-1], p);
                }
            }
        }
        if(x>0 && cakes[x-1][y].getPiecesNum(p) > 0) {
            int from_num = cakes[x-1][y].getPiecesNum(p);
            if(from_num + num >= 8) {
                for(int i=0;i<8 - num;i++) {
                    move(new_cake, cakes[x-1][y], p);
                }
                new_cake.full();
                return;
            }
            else {
                for(int i=0;i<from_num;i++) {
                    move(new_cake, cakes[x-1][y], p);
                }
            }
        }
        if(y<4 && cakes[x][y+1].getPiecesNum(p) > 0) {
            int from_num = cakes[x][y+1].getPiecesNum(p);
            if(from_num + num >= 8) {
                for(int i=0;i<8 - num;i++) {
                    move(new_cake, cakes[x][y+1], p);
                }
                new_cake.full();
                return;
            }
            else {
                for(int i=0;i<from_num;i++) {
                    move(new_cake, cakes[x][y+1], p);
                }
            }
        }
        if(x<3 && cakes[x+1][y].getPiecesNum(p) > 0) {
            int from_num = cakes[x+1][y].getPiecesNum(p);
            if(from_num + num >= 8) {
                for(int i=0;i<8 - num;i++) {
                    move(new_cake, cakes[x+1][y], p);
                }
                new_cake.full();
                return;
            }
            else {
                for(int i=0;i<from_num;i++) {
                    move(new_cake, cakes[x+1][y], p);
                }
            }
        }
    }


    public void full() {
        if(pieces.size() == 8) {
            clearPieces();
            EventHandler<ActionEvent> eventHandler = e -> {
                if(circle.getFill() != Color.BLUE) {
                    circle.setFill(Color.BLUE);
                }
                else {
                    circle.setFill(Color.BLUE);
                }
            };
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
            animation.setCycleCount(2);
            animation.play();
        }
    }


    public void move(CakePane to, CakePane from, int p) {
        to.place_piece(p);
        from.erase_piece(p);
    }


    public void clearPieces() {
        whole_cake.getChildren().clear();
        for(int i=0;i<6;i++) {
            pieces_num[i] = 0;
        }
        pieces.removeAll(pieces);
    }

    public void refresh() {
        clearPieces();
        int randomNumCake = (int) (Math.random() * 7 % 7) + 1;

        for(int j=0;j<randomNumCake;j++) {
            int randomCake = (int) (Math.random() * 6 % 6);

            place_piece(randomCake);

        }
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
