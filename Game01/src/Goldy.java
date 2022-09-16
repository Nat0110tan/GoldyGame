import java.util.Vector;

public class Goldy {
    private int x;
    private int y;
    private int direct;
    private int speed = 5;
    boolean isLive = true;


    public Goldy(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void moveUp(){
        if (y>0) {
            y -= speed;
        }
    }

    public void moveRight(){
        if (x + 55<1000) {
            x += speed;
        }
    }

    public void moveDown(){
        if (y+55<750) {
            y += speed;
        }
    }

    public void moveLeft(){
        if (x>0) {
            x -= speed;
        }
    }
    public void attack(Vector<Ball> bs){
        Ball b = null;
        switch(getDirect()){
            case 0:
                b = new Ball(getX()+22,getY()+20,getDirect());
                break;
            case 1:
                b = new Ball(getX()+47,getY()+22,getDirect());
                break;
            case 2:
                b = new Ball(getX()+22,getY()+44,getDirect());
                break;
            case 3:
                b = new Ball(getX()+2,getY()+22,getDirect());
                break;
        }
        bs.add(b);
        new Thread(b).start();
    }
}
