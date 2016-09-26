import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

//import java.util.Random;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {
	
	private Tile tiles1[] = new Tile[8];
	private Tile tiles2[] = new Tile[8];
	private Tile tiles3[] = new Tile[8];
	private Tile tiles4[] = new Tile[8];
	private Tile tiles5[] = new Tile[8];
	private Paddle paddle;
	private Ball ball;
	private static int deltaY = 1;
	private static int deltaX = 1;
	private static int score = 0;
	private static int highScore = 0;
	private static int playerLife = 999;
	private static int level = 1;
	private static int tiles = 48;
	private static boolean startGame = false;
	private JLabel lblScore = new JLabel("SCORE: " + score, SwingConstants.CENTER);
	private JLabel lblHighScore = new JLabel("HIGH SCORE: " + highScore, SwingConstants.CENTER);
	private JLabel lblLife = new JLabel("LIFE: " + playerLife, SwingConstants.CENTER);
	private JLabel lblGameOver = new JLabel("GAME OVER");
	private JLabel lblRestart = new JLabel("Press spacebar to restart");
	private JLabel lblStartGame = new JLabel("Press enter to start");
	private JLabel lblLevel = new JLabel("LEVEL: " + level, SwingConstants.CENTER);
	private JLabel lblWinner = new JLabel("YOU WIN!");
	Font fontHeader = new Font("courier", Font.BOLD, 16);
	Font fontGameOver = new Font("courier", Font.BOLD, 32);
	Font fontRestart = new Font("courier", Font.BOLD, 22);
	private Image backgroundImage;
	private Image logoImage;

	
	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		canvas.drawImage(backgroundImage,0,0);
		canvas.drawImage(logoImage, 190, 10);
		
		//Draw tiles
		drawTiles(canvas,tiles1);
		drawTiles(canvas,tiles2);
		drawTiles(canvas,tiles3);
		drawTiles(canvas,tiles4);
		drawTiles(canvas,tiles5);
		
		//Draw components
		ball.draw(canvas);
		paddle.draw(canvas);
		canvas.add(lblScore);
		canvas.add(lblHighScore);
		canvas.add(lblLife);
		canvas.add(lblLevel);
		canvas.add(lblGameOver);
		canvas.add(lblRestart);
		canvas.add(lblStartGame);
		canvas.add(lblWinner);
	}
	
	@Override
	protected void setup() {
		
		this.setFramesPerSecond(60);
		this.setResolution(Resolution.MSX);
	
		try {
			backgroundImage = new Image("images/background.png");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
		
		try {
			logoImage = new Image("images/logo.png");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
		
		ball = new Ball();
		ball.setPosition(120,177);

		paddle = new Paddle();
		paddle.setPosition(100, 183);
		
		//Building tiles array
		if(level == 1) {
			buildTiles(tiles1, tiles1.length, Color.GRAY, 2, 19);
			buildTiles(tiles2, tiles2.length, Color.BLUE, 1, 28);
			buildTiles(tiles3, tiles3.length, Color.MAGENTA, 1, 37);
			buildTiles(tiles4, tiles4.length, Color.GREEN, 1, 46);
			buildTiles(tiles5, tiles5.length, Color.RED, 1, 55);
			tiles = 48;
		}
		else if(level == 2){
			buildTiles(tiles1, 1, Color.GREEN, 1, 19);
			buildTiles(tiles2, 3, Color.RED, 1, 28);
			buildTiles(tiles3, 5, Color.CYAN, 1, 37);
			buildTiles(tiles4, 7, Color.YELLOW, 1, 46);
			buildTiles(tiles5, tiles5.length, Color.GRAY, 2, 55);
			tiles = 32;
		}
		
		else if(level == 3){
			buildTiles(tiles1, tiles1.length, Color.GRAY, 2, 19);
			buildTiles(tiles2, tiles1.length, Color.CYAN, 1, 28);
			buildTiles(tiles3, tiles1.length, Color.GRAY, 2, 37);
			buildTiles(tiles4, tiles1.length, Color.BLUE, 1, 42);
			buildTiles(tiles5, tiles1.length, Color.GRAY, 2, 55);
			tiles = 64;
		}

		lblWinner.setVisible(false);
		lblWinner.setBounds(325, 150, 300, 300);
		lblWinner.setFont(fontGameOver);

		setupKeyboardKeys();
		
		//Building score
		lblScore.setVisible(true);
		lblScore.setBounds(590, 200, 200, 100);
		lblScore.setFont(fontHeader);
		lblScore.setForeground(java.awt.Color.white);
		
		//Building high score
		lblHighScore.setVisible(true);
		lblHighScore.setBounds(590, 250, 200, 100);
		lblHighScore.setFont(fontHeader);
		lblHighScore.setForeground(java.awt.Color.red);
		
		//Life player
		lblLife.setVisible(true);
		lblLife.setBounds(590, 100, 200, 100);
		lblLife.setFont(fontHeader);
		lblLife.setForeground(java.awt.Color.white);
		
		//Level
		lblLevel.setVisible(true);
		lblLevel.setBounds(590, 150, 200, 100);
		lblLevel.setFont(fontHeader);
		lblLevel.setForeground(java.awt.Color.white);

		//Game Over
		lblGameOver.setVisible(false);
		lblGameOver.setBounds(215, 150, 300, 300);
		lblGameOver.setFont(fontGameOver);
		lblGameOver.setForeground(java.awt.Color.red);
		
		//Restart
		lblRestart.setVisible(false);
		lblRestart.setBounds(140, 185, 325, 300);
		lblRestart.setFont(fontRestart);
		lblRestart.setForeground(java.awt.Color.white);
		
		//Start
		lblStartGame.setVisible(false);
		lblStartGame.setBounds(170, 190, 300, 300);
		lblStartGame.setFont(fontRestart);
		lblStartGame.setForeground(java.awt.Color.white);
	}

	@Override
	protected void loop() {
		if (startGame == false) { 
			lblStartGame.setVisible(true);
			Console.println("Enter to start");
			return;
		}
		//Testing axis X and Y.
		Point pos = ball.getPosition();
		if (testScreenBounds(pos.y, 10, getResolution().height)) {
			Console.println("Y");
			Console.println(deltaY);
			
			if (deltaY == 1){
				playerLife--;
//				startGame = false;
			}
			if (playerLife == 0){
				deltaY = 0;
				deltaX = 0;
				Console.println("Game Over");
				lblGameOver.setVisible(true);
				Console.println("Restart");
				lblRestart.setVisible(true);
			}
			else {
				deltaY *= -1;
			}
		}
		
		if(tiles == 0 && playerLife > 0) {
			if(level >= 3) {
				lblWinner.setVisible(true);
				lblStartGame.setVisible(false);
				lblRestart.setVisible(true);
			}
			else {
				lblStartGame.setVisible(true);
				level++;
				setup();
			}
			startGame = false;
		}
		
		if (testScreenBounds(pos.x, 12, getResolution().width - 80)) {
			deltaX *= -1;
			Console.println("X");
		}
		
		//Check collided paddle
		if (paddle.collided(ball)) {
			deltaY = -1;
			Console.println("Collided PADDLE!");
			
			try {
				playSound("sounds/paddle.wav");
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Tiles collision check
		checkTilesCollision(ball, tiles1);
		checkTilesCollision(ball, tiles2);
		checkTilesCollision(ball, tiles3);
		checkTilesCollision(ball, tiles4);
		checkTilesCollision(ball, tiles5);

		lblScore.setText("SCORE: "+ score);
		lblHighScore.setText("HIGH SCORE: "+ highScore);
		lblLife.setText("LIFE: "+ playerLife);
		lblLevel.setText("LEVEL: "+ level);
		
		ball.move(deltaX, deltaY);
		
		redraw();	
	}
	
	private boolean testScreenBounds(double pos, int min, int max) {
		if(pos > max || pos < min) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void buildTiles(Tile t[], int tileCount, Color c, int life, int posY) {
		for (int i=0; i< tileCount; i++) {
			Tile tile = new Tile(c, life);
			tile.setPosition(14 + (i*20), posY);
			t[i] = tile;
		}
	}
	
	public static void drawTiles(Canvas c, Tile t[]) {
		for(int i=0; i<t.length; i++) {
			Tile tile = t[i];
			tile.draw(c);
		}
	}
	
//	private Color randomColor() {  
//		Random randColor = new Random();
//		
//		Color colors[] = new Color[5];
//		colors[0] = Color.RED;
//		colors[1] = Color.BLUE;
//		colors[2] = Color.MAGENTA;
//		colors[3] = Color.GREEN;
//		colors[4] = Color.YELLOW;
//		
//		return colors[randColor.nextInt(5)];
//		
////		int r = randColor.nextInt(256);  
////		int g = randColor.nextInt(256);  
////		int b = randColor.nextInt(256);  
////		return new Color(r, g, b);  
//	}
//	
	public static void checkTilesCollision(Ball ball, Tile t[]) {
		for (int i=0; i<t.length; i++) {
			Tile tile = t[i];
			if (tile.collided(ball)) {
				
				if (ball.getPosition().y > tile.getPosition().y) {
					deltaY = 1;
				}
				else {
					deltaY = -1;
				}
				Console.println("Collided!");
				
				if (tile.lifeTile == 1) {
					try {
						playSound("sounds/tile_strong.wav");
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						playSound("sounds/tile_weak.wav");
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				score += 10;
				if(highScore < score){
					highScore = score;
				}
				tiles--;
			}
		}
	}
	
	public void setupKeyboardKeys() {
		bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				paddle.getPosition();
				if(paddle.getPosition().x > 14){
					paddle.move(-6, 0);
				}
			}
		});
		
		bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if(startGame == true){
					if(paddle.getPosition().x + paddle.getWidth() < getResolution().width - 82){
						paddle.move(6, 0);
					}
				}
			}
		});
		
		bindKeyPressed("SPACE", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if (playerLife == 0 || lblWinner.isVisible()) {
					deltaY = -1;
					deltaX = -1;
					playerLife = 3;
					score = 0;
					level = 1;
					startGame = false;
					setup();
				}
			}
		});
		
		bindKeyPressed("ENTER", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if (startGame == false && !lblWinner.isVisible()) {
					startGame = true;
					setup();
					lblStartGame.setVisible(false);
				}
			}
		});
	}
	
	static void playSound(String soundFile) throws LineUnavailableException, MalformedURLException, IOException, UnsupportedAudioFileException {
	    File f = new File("./" + soundFile);  
	    Clip clip = AudioSystem.getClip();
	    clip.open(AudioSystem.getAudioInputStream(f.toURI().toURL()));
	    clip.start();
	}
}
