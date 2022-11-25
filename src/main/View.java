import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
    private JFrame window;
    private GamePanel gamePanel;

    private JPanel savePage;

    public View () {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window.setLayout(new ScrollPaneLayout());
        window.setPreferredSize(new Dimension(800, 600));
        window.setResizable(false);
        window.setTitle("Controlled Chaos");

        gamePanel = new GamePanel();
        window.add(gamePanel);
        window.addKeyListener(gamePanel.keyH);

        window.pack();

        window.setLocationRelativeTo(null);


        window.setVisible(true);                                    // currently opens up the game window
    }

    public JFrame getWindow() {
        return window;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }



}
