import javax.swing.*;

public class Main {
	public static View view;
	public static JFrame window;
	public static String[] args;

	public static void main(String[] args) {

		Main.args = args;
		view = new View();
		window = view.getWindow();
    
		GamePanel gamePanel = view.getGamePanel();
		gamePanel.startGameThread();
	}
}
