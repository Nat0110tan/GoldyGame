import javax.swing.*;
import java.util.*;

public class GameBoard extends JFrame {
    MyPanel mp = null;
    public static void main(String args[]){
        new GameBoard();
    }
    public GameBoard(){
        mp = new MyPanel();
        new Thread(mp).start();
        this.add(mp);
        this.setSize(1000,750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
