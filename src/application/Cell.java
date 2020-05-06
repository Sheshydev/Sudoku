package application;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Cell extends StackPane
{
    private int col = Main.COL, row = Main.ROW;
    private int numbered = 0, number = 0, x, y, planted = 0;
    private TextField tf = new TextField();
    private Text txt = new Text();
    private Rectangle box = new Rectangle();

    public int getPlanted(){
        return planted;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setPlanted(int i){
        planted = i;
    }

    public void setCoords(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setNumbered(int i){
        numbered = i;
    }

    public void setNumber(int i){
        number = i;
    }

    public int getNumber(){
        return number;
    }

    public int getNumbered(){
        return numbered;
    }

    public void setText(String str){
        Cell cell = this;
        this.getChildren().remove(txt);
        txt.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if(mouseEvent.getButton() == MouseButton.PRIMARY && cell.getPlanted() == 0){
                    cell.setTextField();
                }
            }
        });
        txt.setText(str);
        this.getChildren().add(txt);
    }

    public void inputNum(String str){
        this.setText(str);
        int i = Integer.parseInt(str);
        this.setNumber(i);
        this.setNumbered(1);
        this.getChildren().remove(tf);
    }
    public void inputNum(int num){
        String str = String.valueOf(num);
        this.setText(str);
        this.setNumber(num);
        this.setNumbered(1);
        this.getChildren().remove(tf);
        if(getPlanted() == 1){
            this.box.setFill(Color.LIGHTBLUE);
        }
    }

    public void setTextField(){
        Cell cell = this;
        this.getChildren().removeAll(box, tf);
        this.setNumbered(0);
        this.setNumber(0);
        box.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if(mouseEvent.getButton() == MouseButton.PRIMARY && cell.getNumbered() == 1 && cell.getPlanted() == 0){
                    cell.setTextField();
                }
            }
        });
        box.setHeight(50);
        box.setWidth(50);
        box.setFill(Color.WHITE);

        tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ENTER) {
                    if (isNum(tf.getText()) && Main.isChunkValid(x, y, Integer.valueOf(tf.getText())) && Main.isHorValid(y, Integer.valueOf(tf.getText())) && Main.isVertValid(x, Integer.valueOf(tf.getText()))){
                        cell.inputNum(tf.getText());
                        if (isComplete())
                        {
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setContentText("You Won!");
                            a.show();
                            Main.resetBoard();
                            Main.plantNums();
                        }
                    }
                }
            }
        });

        this.getChildren().addAll(box, tf);
    }

    public static boolean isNum(String str){
        try{
            int i = Integer.parseInt(str);
            if(i<10 && i>0){
                return true;
            }
            return false;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public boolean isComplete(){
        int num;
        for(int i = 0; i < col*row; i++){
            num = Main.cellArr[i].getNumbered();
            if(num == 0){
                return false;
            }
        }
        return true;
    }
}
