import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{
	final int originalTileSizes = 16;							//16x16 tile
	final int scale = 3;
	public final int tileSize = originalTileSizes * scale;		//48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; 	//768 pixels
	public final int screenHeight = tileSize * maxScreenRow;	//576 pixels

	public boolean paused = false;
	public Pathfinding pFinder = new Pathfinding(this);
	public int gameState;

	private int fps = 60;

	public CollisionChecker checker = new CollisionChecker(this);
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	public NonPlayableCharacter[] enemies = new NonPlayableCharacter[2];
	Thread gameThread;
	public PlayerCharacter player = new PlayerCharacter(this, keyH);
	private int currentRoomNum = 0;
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	private void newGameHelper() {
		this.setFocusable(true);
		this.requestFocusInWindow();
		if (gameThread == null) {
			this.gameThread = new Thread(this);
			startGameThread();
		}

			// assuming this is to set the position of enemies after starting a new game. probably needs to change
//			for (int i = 0; i < getEnemies().size(); i++) {
//				Enemy enemy = rooms.get(currentRoomNum).getEnemies().get(i);
//				enemy.setxCoord(100);
//				enemy.setyCoord(100);
//			}


		this.resumeThread();
	}

	public void pauseThread() {
		synchronized (this) {
			this.paused = true;
		}
	}

	public void resumeThread() {
		synchronized (this) {
			this.paused = false;
		}
	}

	public boolean readThreadState() {
		synchronized (this) {
			return this.paused;
		}
	}

	@Override
	public void run() {
		enemies[0] = new Skeleton(100,100);
		enemies[1] = new Skeleton(500,500);

		double drawInterval;					//converts from nanoseconds to seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null){

			delta = 0;
			timer = 0;
			lastTime = System.nanoTime();

			while (!readThreadState()) {
				currentTime = System.nanoTime();
				drawInterval = 1000000000. / fps;

				delta += (currentTime - lastTime) / drawInterval;
				timer += (currentTime - lastTime);


				lastTime = currentTime;

				if (delta >= 1) {
					update();
					repaint();
					delta--;
					drawCount++;
				}

				if (timer >= 1000000000) {
					Main.window.setTitle("Controlled Chaos");
					System.out.println("FPS:" + drawCount);
					drawCount = 0;
					timer = 0;
				}

				if (player.getHealth() <= 0) {
					player.kill();
					player.setDefaultValues();
					keyH.reset();
					player.setKeyHandler(null);
					//Main.view.getWindow().set
					this.pauseThread();
				}
			}
		}
	}

	public void update(){
		player.update();

		for (int i = 0; i < enemies.length; i++) {
			if(enemies[i]!=null) {
				enemies[i].update(this);
			}
		}

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;

		tileM.draw(g2);
		player.draw(g2);
		for (int i = 0; i < enemies.length; i++) {
			if(enemies[i]!=null){
				enemies[i].draw(g2,this);
			}
		}

		g2.dispose();
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int newFrameRate) {
		fps = newFrameRate;
	}

	public synchronized PlayerCharacter getPlayer() {
		return this.player;
	}

	public synchronized void setPlayer(PlayerCharacter player) {
		this.player = player;
	}

	public int getCurrentRoomNum() {
		return currentRoomNum;
	}

	public void setCurrentRoomNum(int currentRoomNum) {
		this.currentRoomNum = currentRoomNum;
	}

}