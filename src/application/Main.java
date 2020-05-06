package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Random;

public class Main extends Application {
    public static final int ROW = 9, COL = 9, WIDTH = 500, HEIGHT = 500, FILLED = 20;
    public static Cell[] cellArr = new Cell[COL*ROW];

    @Override
    public void start(Stage primaryStage) throws Exception{


        //set general layout
        GridPane root = new GridPane();
        StackPane top = new StackPane();
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(95);
        root.getRowConstraints().add(row);
        row = new RowConstraints();
        row.setPercentHeight(5);
        root.getRowConstraints().add(row);

        //set 3x3 back-grid
        GridPane grid = new GridPane();
        for (int i = 0; i < 3; i++){
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100/3);
            grid.getColumnConstraints().add(col);
        }
        for (int i = 0; i < 3; i++){
            row = new RowConstraints();
            row.setPercentHeight(100/3);
            grid.getRowConstraints().add(row);
        }

        //make solve button
        Rectangle solveButton = new Rectangle();
        Text solveTxt = new Text();
        solveTxt.setText("solve");
        solveTxt.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                AI.solve();
            }
        });

        //make front grid
        GridPane grid1 = new GridPane();
        grid.setGridLinesVisible(true);
        for (int i = 0; i < COL; i++){
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100/COL);
            grid1.getColumnConstraints().add(col);
        }
        for (int i = 0; i < ROW; i++){
            row = new RowConstraints();
            row.setPercentHeight(100/ROW);
            grid1.getRowConstraints().add(row);
        }
        for (int y = 0; y < ROW; y++){
            for (int x = 0; x < COL; x++) {
                Cell cell = new Cell();
                cell.setTextField();
                cell.setCoords(x, y);
                grid1.add(cell, x, y);
                cellArr[(y * ROW) + x] = cell;
            }
        }
        plantNums();

        //set scene
        top.getChildren().addAll(grid, grid1);
        root.addRow(0, top);
        root.addRow(1, solveTxt);
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }

    public static boolean isChunkValid(int x, int y, int match){
        HashSet set = new HashSet<Integer>();
        int[] tempArr = new int[9];
        int xDisp = 0, yDisp = 0, k = 0;

        if(x >= 3){
            xDisp = 3;
            if(x >= 6){
                xDisp = 6;
            }
        }
        if(y >= 3){
            yDisp = 3;
            if(y >= 6){
                yDisp = 6;
            }
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                tempArr[k] = cellArr[((i + yDisp) * ROW) + j + xDisp].getNumber();
                k++;
            }
        }
        for(int num : tempArr){
            if (num != 0) {
                if (match == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isHorValid(int y, int match){
        HashSet set = new HashSet<Integer>();
        int[] tempArr = new int[9];
        for(int x = 0; x < ROW; x++) {
            tempArr[x] = cellArr[(y * ROW) + x].getNumber();
        }
        for(int num : tempArr){
            if (num != 0) {
                if (match == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isVertValid(int x, int match){
        HashSet set = new HashSet<Integer>();
        int[] tempArr = new int[9];
        for(int y = 0; y < ROW; y++) {
            tempArr[y] = cellArr[(y * ROW) + x].getNumber();
        }
        for(int num : tempArr){
            if (num != 0) {
                if (match == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void resetBoard(){
        for(int i = 0; i < COL*ROW; i++){
            cellArr[i].setTextField();
        }
    }

    public static void plantNums(){
        int randPos, randNum, x, y;
        Random rand = new Random();
        for(int i = 0; i < FILLED; i++){
            randPos = rand.nextInt(COL*ROW);
            randNum = rand.nextInt(9) + 1;
            x = cellArr[randPos].getX();
            y = cellArr[randPos].getY();
            if(cellArr[randPos].getNumbered() == 0 && Main.isChunkValid(x, y, randNum) && Main.isHorValid(y, randNum) && Main.isVertValid(x, randNum)){
                cellArr[randPos].setPlanted(1);
                cellArr[randPos].inputNum(randNum);
            } else{
                i--;
            }
        }
    }

    public static void inputCell(int x, int y, int num){
        cellArr[(y * COL) + x].inputNum(num);
    }

    public static void delCell(int x, int y){
        if (cellArr[(y * COL) + x].getNumbered() != 0)
            cellArr[(y * COL) + x].setTextField();
    }
    public static Cell getCell(int x, int y){
        return cellArr[(y * COL) + x];
    }

    public static void main(String[] args) {
        launch(args);
    }
}
