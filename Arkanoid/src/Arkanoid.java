import java.awt.Font;
import java.io.IOException;
//import java.util.Random;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {
	
	private Tile tiles1[] = new Tile[11];
	private Tile tiles2[] = new Tile[11];
	private Tile tiles3[] = new Tile[11];
	private Paddle paddle;
	private Ball ball;
	private static int deltaY = 1;
	private static int deltaX = 1;
	private static int score = 0;
	private static int highScore = 0;
	private static int playerLife = 3;
	private static int level = 1;
	private static int tiles = 44;
	private static boolean startGame = false;
	private JLabel lblScore = new JLabel("Score: " + score, SwingConstants.CENTER);
	private JLabel lblHighScore = new JLabel("High Score: " + score, SwingConstants.CENTER);
	private JLabel lblLife = new JLabel("Life: " + playerLife, SwingConstants.CENTER);
	private JLabel lblGameOver = new JLabel("GAME OVER");
	private JLabel lblRestart = new JLabel("Press spacebar to restart");
	private JLabel lblStartGame = new JLabel("Press enter to start");
	private JLabel lblLevel = new JLabel("Level: " + level, SwingConstants.CENTER);
	private JLabel lblWinner = new JLabel("YOU WIN!");
	Font fontHeader = new Font("courier", Font.BOLD, 13);
	Font fontGameOver = new Font("courier", Font.BOLD, 30);
	Font fontRestart = new Font("courier", Font.BOLD, 20);
	private Image backgroundImage;

	
	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		canvas.drawImage(backgroundImage,0,0);
		
		//Draw tiles
		drawTiles(canvas,tiles1);
		drawTiles(canvas,tiles2);
		drawTiles(canvas,tiles3);
		
		//Draw components
		ball.draw(canvas);
//		ball.draw(canvas.getGraphics());
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
			backgroundImage = new Image("images/background.jpg");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
		
		ball = new Ball();
		ball.setPosition(130,180);

		paddle = new Paddle();
		paddle.setPosition(100, 183);
		
		//Building tiles array
		if(level == 1) {
			buildTiles(tiles1, Color.GRAY, 2, 20);
			buildTiles(tiles2, Color.GREEN, 1, 30);
			buildTiles(tiles3, Color.MAGENTA, 1, 40);
			tiles = 44;
		}
		else if(level == 2){
			buildTiles(tiles1, Color.GRAY, 2, 20);
			buildTiles(tiles2, Color.RED, 1, 30);
			buildTiles(tiles3, Color.CYAN, 1, 40);
			tiles = 44;
		}
		
		else if(level == 3){
			buildTiles(tiles1, Color.GRAY, 2, 20);
			buildTiles(tiles2, Color.YELLOW, 1, 30);
			buildTiles(tiles3, Color.GRAY, 2, 40);
			tiles = 55; //quantidade de batidas em tiles
		}

		lblWinner.setVisible(false);
		lblWinner.setBounds(325, 150, 300, 300);
		lblWinner.setFont(fontGameOver);

		setupKeyboardKeys();
		
		//Building score
		lblScore.setVisible(true);
		lblScore.setBounds(0, -33, 100, 100);
		lblScore.setFont(fontHeader);
		//lblScore.setForeground(Color.BLUE);
		
		//Building high score
		lblHighScore.setVisible(true);
		lblHighScore.setBounds(600, -33, 200, 100);
		lblHighScore.setFont(fontHeader);
		
		//Life player
		lblLife.setVisible(true);
		lblLife.setBounds(200, -33, 100, 100);
		lblLife.setFont(fontHeader);
		
		//Level
		lblLevel.setVisible(true);
		lblLevel.setBounds(400, -33, 100, 100);
		lblLevel.setFont(fontHeader);

		//Game Over
		lblGameOver.setVisible(false);
		lblGameOver.setBounds(325, 150, 300, 300);
		lblGameOver.setFont(fontGameOver);
		
		//Restart
		lblRestart.setVisible(false);
		lblRestart.setBounds(250, 190, 300, 300);
		lblRestart.setFont(fontRestart);
		
		//Start
		lblStartGame.setVisible(false);
		lblStartGame.setBounds(250, 190, 300, 300);
		lblStartGame.setFont(fontRestart);
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
		if (testScreenBounds(pos.y,0,getResolution().height)) {
			Console.println("Y");
			Console.println(deltaY);
			
			if (deltaY == 1){
				playerLife--;
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
//				setup();
			}
			startGame = false;
		}
		
		if (testScreenBounds(pos.x,0,getResolution().width)) {
			deltaX *= -1;
			Console.println("X");
		}
		
		//Check collided paddle
		if (paddle.collided(ball)) {
			deltaY = -1;
			Console.println("Collided PADDLE!");
		}
		
		//Tiles collision check
		checkTilesCollision(ball, tiles1);
		checkTilesCollision(ball, tiles2);
		checkTilesCollision(ball, tiles3);

		lblScore.setText("Score: "+ score);
		lblHighScore.setText("High Score: "+ highScore);
		lblLife.setText("Life: "+ playerLife);
		lblLevel.setText("Level: "+ level);
		
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
	
	public static void buildTiles(Tile t[], Color c, int life, int posY) {
		for (int i=0; i<t.length; i++) {
			Tile tile = new Tile(c, life);
			tile.setPosition(5 + (i*22), posY);
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
				if(paddle.getPosition().x > 8){
					paddle.move(-7, 0);
				}
			}
		});
		
		bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				if(startGame == true){
					if(paddle.getPosition().x + paddle.getWidth() < getResolution().width - 10){
						paddle.move(7, 0);
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
}
