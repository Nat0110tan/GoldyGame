
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
public class MyPanel extends JPanel implements KeyListener, Runnable {
    MyGoldy mg = null;
    Vector<EnemyGoldy> egs = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    int egsSize = 5;

    Image i1 = null;
    Image i2 = null;
    Image ig = null;
    Image fin = null;

    public MyPanel() {
        mg = new MyGoldy(500, 100);
        for (int i = 0; i < egsSize; i++) {
            EnemyGoldy eg = new EnemyGoldy(110 * i, 0);
            Ball b = new Ball(eg.getX() + 17, eg.getY() + 45, eg.getDirect());
            eg.balls.add(b);
            new Thread(b).start();
            egs.add(eg);
            new Thread(eg).start();
        }
        i1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/i1.jpg"));
        i2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/i2.jpg"));
        ig = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/i3.jpg"));
        fin = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/fin.jpg"));

    }

    public void hitUser() {
        for (int i = 0; i < egs.size(); i++) {
            EnemyGoldy eg = egs.get(i);
            for (int j = 0; j < eg.balls.size(); j++) {
                Ball b = eg.balls.get(j);
                if (mg.isLive && b.isLive) {
                    hitGoldy(b, mg);
                }
            }
        }
    }

    public void hitEnemy() {
        for (int j = 0; j < mg.balls.size(); j++) {
            Ball b = mg.balls.get(j);
            if (b != null && b.isLive) {
                for (int i = 0; i < egs.size(); i++) {
                    EnemyGoldy eg = egs.get(i);
                    hitGoldy(b, eg);
                }
            }
        }

    }

    public void hitGoldy(Ball b, Goldy g) {
        switch (g.getDirect()) {
            case 0:
                if (b.x > (g.getX() - 3) && b.x < (g.getX() + 55) && b.y > g.getY() && b.y < (g.getY() + 55)) {
                    b.isLive = false;
                    g.isLive = false;
                    egs.remove(g);
                    bombs.add(new Bomb(g.getX(), g.getY()));
                }
                break;
            case 1:
                if (b.x > g.getX() && b.x < (g.getX() + 55) && b.y > g.getY() && b.y < (g.getY() + 56)) {
                    b.isLive = false;
                    g.isLive = false;
                    egs.remove(g);
                    bombs.add(new Bomb(g.getX(), g.getY()));
                }
                break;
            case 2:
                if (b.x > g.getX() && b.x < (g.getX() + 56) && b.y > g.getY() && b.y < (g.getY() + 55)) {
                    b.isLive = false;
                    g.isLive = false;
                    egs.remove(g);
                    bombs.add(new Bomb(g.getX(), g.getY()));
                }
                break;
            case 3:
                if (b.x > g.getX() && b.x < (g.getX() + 55) && b.y > (g.getY() - 3) && b.y < (g.getY() + 55)) {
                    b.isLive = false;
                    g.isLive = false;
                    egs.remove(g);
                    bombs.add(new Bomb(g.getX(), g.getY()));
                }
                break;

        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);

        if (egs.size() == 0 || !mg.isLive) {
            g.drawImage(fin, 100,100,750,500,this);
        }

        if (mg.isLive && mg != null) {
            drawGoldy(mg.getX(), mg.getY(), g, mg.getDirect(), 0);
        }

        for (int i = 0; i < mg.balls.size(); i++) {
            Ball b = mg.balls.get(i);
            if (b != null && b.isLive) {
                drawBall(b.x, b.y, g, 0);
            } else {
                mg.balls.remove(b);
            }
        }



        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bb = bombs.get(i);
            if (bb.life > 5) {
                g.drawImage(i1, bb.x, bb.y, 80, 60, this);
            } else if (bb.life > 3) {
                g.drawImage(i2, bb.x, bb.y, 80, 60, this);
            } else {
                g.drawImage(ig, bb.x, bb.y, 80, 60, this);
            }
            bb.lifeShorten();

            if (bb.life == 0) {
                bombs.remove(bb);
            }
        }

        for (int i = 0; i < egs.size(); i++) {
            EnemyGoldy eg = egs.get(i);
            if (eg.isLive) {
                drawGoldy(eg.getX(), eg.getY(), g, eg.getDirect(), 1);
                for (int j = 0; j < eg.balls.size(); j++) {
                    Ball b = eg.balls.get(j);
                    if (b.isLive) {
                        drawBall(b.getX(), b.getY(), g, 1);
                    } else {
                        eg.balls.remove(b);
                    }
                }
            }
        }
    }

    /**
     * @param x
     * @param y
     * @param g
     * @param direct
     * @param type
     */
    public void drawGoldy(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            //different type has different color
            case 0: //user's Goldy
                g.setColor(Color.orange);
                break;
            case 1: // enemy's Goldy
                g.setColor(Color.red);
                break;
        }
        //create the shape of goldy based on the direction of different goldy
        // 0 : up, 1: right, 2: down, 3: left
        switch (direct) {
            case 0: // direction : up
                g.fillOval(x, y, 10, 10);
                g.fill3DRect(x + 5, y + 10, 40, 20, false);
                g.fillOval(x + 40, y, 10, 10);
                g.setColor(Color.lightGray);
                g.fill3DRect(x - 3, y + 30, 56, 25, false);
                g.setColor(Color.white);
                g.fillRect(x + 20, y + 30, 10, 12);
                g.fillOval(x + 10, y + 15, 4, 4);
                g.fillOval(x + 30, y + 15, 4, 4);
                g.setColor(Color.black);
                g.fillOval(x + 10, y + 15, 10, 10);
                g.fillOval(x + 30, y + 15, 10, 10);
                g.fillOval(x + 22, y + 22, 6, 6);
                g.drawLine(x + 25, y + 30, x + 25, y + 42);

                break;

            case 1:// direction : right
                g.fillOval(x + 45, y, 10, 10);
                g.fill3DRect(x + 27, y + 5, 20, 40, false);
                g.fillOval(x + 45, y + 40, 10, 10);
                g.setColor(Color.lightGray);
                g.fill3DRect(x + 2, y - 3, 25, 56, false);
                g.setColor(Color.white);
                g.fillRect(x + 15, y + 20, 12, 10);
                g.fillOval(x + 36, y + 10, 4, 4);
                g.fillOval(x + 36, y + 30, 4, 4);
                g.setColor(Color.black);
                g.fillOval(x + 30, y + 10, 10, 10);
                g.fillOval(x + 30, y + 30, 10, 10);
                g.fillOval(x + 27, y + 22, 6, 6);
                g.drawLine(x + 15, y + 25, x + 27, y + 25);

                break;

            case 2: // direction : down
                g.fillOval(x, y + 45, 10, 10);
                g.fill3DRect(x + 5, y + 25, 40, 20, false);
                g.fillOval(x + 40, y + 45, 10, 10);
                g.setColor(Color.lightGray);
                g.fill3DRect(x - 3, y, 56, 25, false);
                g.setColor(Color.white);
                g.fillRect(x + 20, y + 13, 10, 12);
                g.fillOval(x + 10, y + 36, 4, 4);
                g.fillOval(x + 30, y + 36, 4, 4);
                g.setColor(Color.black);
                g.fillOval(x + 10, y + 30, 10, 10);
                g.fillOval(x + 30, y + 30, 10, 10);
                g.fillOval(x + 22, y + 24, 6, 6);
                g.drawLine(x + 25, y + 13, x + 25, y + 25);
                break;

            case 3: // direction : left
                g.fillOval(x, y, 10, 10);
                g.fill3DRect(x + 10, y + 5, 20, 40, false);
                g.fillOval(x, y + 40, 10, 10);
                g.setColor(Color.lightGray);
                g.fill3DRect(x + 30, y - 3, 25, 56, false);
                g.setColor(Color.white);
                g.fillRect(x + 30, y + 20, 12, 10);
                g.fillOval(x + 15, y + 10, 4, 4);
                g.fillOval(x + 15, y + 30, 4, 4);
                g.setColor(Color.black);
                g.fillOval(x + 15, y + 10, 10, 10);
                g.fillOval(x + 15, y + 30, 10, 10);
                g.fillOval(x + 22, y + 22, 6, 6);
                g.drawLine(x + 30, y + 25, x + 42, y + 25);

                break;


            default:
                System.out.println(10000000);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void drawBall(int x, int y, Graphics g, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.yellow);
                break;
            case 1:
                g.setColor(Color.magenta);
                break;
            default:
                System.out.println(100000);
        }
        g.fillOval(x, y, 6, 6);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            mg.setDirect(0);
            mg.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            mg.setDirect(1);
            mg.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            mg.setDirect(2);
            mg.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            mg.setDirect(3);
            mg.moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            mg.attack();
        }
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(egs.size());
            if (egs.size() == 0 || !mg.isLive) {
                break;
            }
            hitEnemy();
            hitUser();

            this.repaint();
    }
    }
}


//    public void close(){
//        mg.isLive = false;
//        for(int i = 0; i < mg.balls.size(); i++){
//            mg.balls.get(i).isLive = false;
//        }
//        for(int j = 0; j < egs.size(); j++){
//            EnemyGoldy eg = egs.get(j);
//            for(int k = 0; k < eg.balls.size(); k++){
//                eg.balls.get(k).isLive = false;
//            }
//        }
//    }
//        if(egs.size() ==0){
//            System.out.println("You win!");
//            close();
//        }
//        if(!mg.isLive){
//            System.out.println("You lose!");
//            close();
//        }