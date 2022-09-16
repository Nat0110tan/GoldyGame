import java.util.Vector;

public class MyGoldy extends Goldy{
    Ball b = null;
    Vector<Ball> balls = new Vector<>();

    public MyGoldy(int x, int y) {
        super(x, y);
    }
    public void attack(){
        if(balls.size() == 15){
            return;
        }
        super.attack(balls);
    }
}
