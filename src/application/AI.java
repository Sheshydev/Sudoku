package application;

import javafx.scene.control.Alert;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class AI
{
    private static int col = 6, row = 3;
    private static LinkedList<Cell> placed = new LinkedList<Cell>();
    private static int x, y, numOld, addNum;
    private static Cell prevCell;

    public static void solve(){
        int oldNum, numAdd;
            for (y = 0; y < row; y++)
            {
                for (x = 0; x < col; x++)
                {
                    Cell cell = Main.getCell(x, y);
                    if (cell.getPlanted() == 0)
                    {
                        if (cell.getNumbered() == 0)
                        {
                            oldNum = cell.getNumber();
                            numAdd = 1;
                            while (!isValid(x, y, oldNum + numAdd))
                            {
                                numAdd++;
                                if (oldNum + numAdd == 10)
                                {
                                    stepBack();
                                    prevCell.inputNum(numOld + addNum);
                                    placed.push(prevCell);
                                    x++;
                                    if (x >= Main.COL)
                                    {
                                        y++;
                                        x = 0;
                                    }
                                    oldNum = cell.getNumber();
                                    numAdd = 1;
                                }
                            }
                            System.out.println(String.valueOf(numOld + addNum));
                            cell.inputNum(oldNum + numAdd);
                            placed.push(cell);
                        }
                    }
                }
            }
    }

    private static void stepBack(){
        addNum = 1;
        prevCell = placed.pop();
        x = prevCell.getX();
        y = prevCell.getY();
        numOld = prevCell.getNumber();
        while(!isValid(x, y, numOld + addNum)){
            addNum++;
            if(numOld + addNum  == 10){
                prevCell.setTextField();
                stepBack();
            }
        }
    }

    private static boolean isComplete(){
        int num;
        for(int i = 0; i < col*row; i++){
            num = Main.cellArr[i].getNumbered();
            if(num == 0){
                return false;
            }
        }
        return true;
    }

    public static boolean isValid(int x1, int y1, int match){
        if(Main.isChunkValid(x1, y1 , match) && Main.isHorValid(y1, match) && Main.isVertValid(x1, match)){
            return true;
        }
        return false;
    }
}
