import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    public boolean kPressed, wPressed, sPressed, aPressed, dPressed, upPressed, downPressed, leftPressed, rightPressed;

    //TODO implement movement and attack
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        boolean eventW = (code == KeyEvent.VK_W);
        boolean eventS = (code == KeyEvent.VK_S);
        boolean eventA = (code == KeyEvent.VK_A);
        boolean eventD = (code == KeyEvent.VK_D);

        // walking audio should be started if one of the wasd keys has been pressed AND it wasn't being pressed before
        // or it has stopped playing


        /*
         * problem: this system is good for walking because it keeps updating while they're holding
         * down the button, but for attacks and everything else it keeps attacking for as long as they
         * hold the button, which results in some funky damage and sound effects
         */
        if (code == KeyEvent.VK_K) {
            kPressed = true;
        }
        if (code == KeyEvent.VK_W) {
            wPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            sPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            aPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            dPressed = true;
        }
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        if (code == KeyEvent.VK_C){gp.gameState = 4;}



        //can't make the settings page go away
        if (code == KeyEvent.VK_ESCAPE) {

            Main.view.getGamePanel().pauseThread();

        } else {

            Main.view.getGamePanel().resumeThread();

        }



    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_K) {
            kPressed = false;
        }
        if (code == KeyEvent.VK_W) {
            wPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            sPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            aPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            dPressed = false;
        }
        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }

    }

    public void reset() {
        kPressed = false;
        wPressed = false;
        sPressed = false;
        aPressed = false;
        dPressed = false;
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
    }

}
