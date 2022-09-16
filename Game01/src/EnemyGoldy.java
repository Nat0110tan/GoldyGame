import java.util.Vector;

public class EnemyGoldy extends Goldy implements Runnable{
    Vector<Ball> balls = new Vector<>();

    public EnemyGoldy(int x, int y) {
        super(x, y);
        this.setDirect(2);
    }

    @Override
    public void run() {
        while(true){
            if(isLive && balls.size() < 10 ){
                super.attack(balls);
            }

            if(!isLive){
                break;
            }
            switch(getDirect()){
                case 0:
                    for(int i = 0; i < 30; i++){
                        moveUp();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 1:
                    for(int i = 0; i < 30; i++){
                        moveRight();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for(int i = 0; i < 30; i++){
                        moveDown();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for(int i = 0; i < 30; i++){
                        moveLeft();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            setDirect((int)(Math.random() * 4));
        }
    }
}
