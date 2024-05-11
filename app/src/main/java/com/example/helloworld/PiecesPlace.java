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

public class PiecesPlace {

    private final int x_max = 5;
    private final int y_max = 5;
    private final int pieces_kind = 6;
    private final int pieces_max = 8;


    private final int[][] dir_all = {{0,-1},{-1, 0},{0,1},{1,0},{0,0}};


    private CakePane[] cake_dir = new CakePane[5];
    private int[] pieces_num = new int[pieces_kind];
    private ArrayList<Integer> num_in_order = new ArrayList<>();
    private ArrayList<Integer> dir = new ArrayList<>();


    public PiecesPlace(CakePane[][] cakes, int x, int y) {

        int count_full = 0;

        for(int i=0;i<5;i++) {
            cake_dir[i] = new CakePane();
        }

        for(int i=0;i<pieces_kind;i++) {
            pieces_num[i] = cakes[x][y].count_dir(cakes, x, y, i);

            if(pieces_num[i] >= pieces_max) {
                count_full++;
            }
        }


        for(int i=0;i<pieces_kind;i++) {
            num_in_order.add(i);
        }

        Collections.sort(num_in_order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(pieces_num[o1], pieces_num[o2]);
            }
        });

        for(int i=0;i<5;i++) {
            if(cakes[x + dir_all[i][0]][y + dir_all[i][1]].getPieces().size() != 0) {
                dir.add(i);
            }
        }

        int num_index = 0;

        for(int i=0;i<count_full;i++) {
            int p = num_in_order.get(num_index);

            while(pieces_num[p] >= pieces_max) {
                for(int j=0;j<pieces_max;j++) {
                    cake_dir[dir.get(0)].place_piece(num_in_order.get(0));
                }
                pieces_num[p] -= pieces_max;
                dir.remove(0);
            }

            if(pieces_num[p] == 0) {
                num_in_order.remove(0);
            }
            else {
                num_index++;
            }
        }

        for(int i=0;i<num_in_order.size();i++) {
            int dir_index;
            int p = num_in_order.get(i);

            if((i / dir.size()) % 2 == 0) {
                dir_index = i % dir.size();
            }
            else {
                dir_index = dir.size() - 1 - (i % dir.size());
            }

            while(pieces_num[p] > 0) {

                cake_dir[dir.get(dir_index)].place_piece(p);

                pieces_num[p]--;
            }


        }



    }

    public CakePane getCake_dir(int i) {
        return cake_dir[i];
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
