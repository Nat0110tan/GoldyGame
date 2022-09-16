public class Ball implements Runnable{
    int x;
    int y;
    int speed = 3;
    int direct;
    boolean isLive = true;

    public Ball(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public int getDirect() {
        return direct;
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

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch(direct) {
                case 0:
                    //up
                    y -= speed;
                      break;
                case 1:
                    //right
                    x += speed;
                    break;
                case 2:
                    //down
                    y += speed;
                    break;
                case 3:
                    //left
                    x -= speed;
                    break;
            }
            System.out.println("Ball: x=" + x+ " y = "+y);
            if(!isLive){
                break;
            }
            if(!(x>=0 && x<= 1000 && y>=0 && y<= 750 && isLive)){
                isLive = false;
                System.out.println("The ball is out of bounds");
                break;
            }


        }
    }
}
