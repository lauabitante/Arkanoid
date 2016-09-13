import java.awt.Font;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;
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
	private JLabel lblScore = new JLabel("Score: " + score, SwingConstants.CENTER);
	Font fontScore = new Font("courier", Font.PLAIN, 13);

	
	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		//Draw tiles
		drawTiles(canvas,tiles1);
		drawTiles(canvas,tiles2);
		drawTiles(canvas,tiles3);
		
		//draw ball, paddle and score
		ball.draw(canvas);
		paddle.draw(canvas);
		canvas.add(lblScore);
	}
	
	@Override
	protected void setup() {
		this.setFramesPerSecond(60);
		this.setResolution(Resolution.MSX);
		
		ball = new Ball();
		ball.setPosition(130,180);
		
		paddle = new Paddle();
		paddle.setPosition(100, 183);
		
		//Building tiles array
		buildTiles(tiles1, Color.GRAY, 2, 20);
		buildTiles(tiles2, Color.GREEN, 1, 30);
		buildTiles(tiles3, Color.MAGENTA, 1, 40);
		
		setupKeyboardKeys();
		
		//Building score
		lblScore.setVisible(true);
		lblScore.setBounds(0, -33, 100, 100);
		lblScore.setFont(fontScore);
		//lblScore.setForeground(Color.BLUE);
	}

	@Override
	protected void loop() {
		//Testando os limites do eixo X e Y.
		Point pos = ball.getPosition();
		if (testScreenBounds(pos.y,0,getResolution().height)) {
			deltaY *= -1;
			Console.println("Y");
	
		}
		if (testScreenBounds(pos.x,0,getResolution().width)) {
			deltaX *= -1;
			Console.println("X");
		}
		
		//if collided paddle
		if (paddle.collided(ball)) {
			deltaY = -1;
			Console.println("Collided PADDLE!");
		}
		
		//Tiles collision check
		checkTilesCollision(ball, tiles1);
		checkTilesCollision(ball, tiles2);
		checkTilesCollision(ball, tiles3);

		lblScore.setText("Score: "+ score);
		
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
	
	private Color randomColor() {  
		Random randColor = new Random();
		
		Color colors[] = new Color[5];
		colors[0] = Color.RED;
		colors[1] = Color.BLUE;
		colors[2] = Color.MAGENTA;
		colors[3] = Color.GREEN;
		colors[4] = Color.YELLOW;
		
		return colors[randColor.nextInt(5)];
		
//		int r = randColor.nextInt(256);  
//		int g = randColor.nextInt(256);  
//		int b = randColor.nextInt(256);  
//		return new Color(r, g, b);  
	}
	
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
			}
		}
	}
	
	public void setupKeyboardKeys() {
		bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				paddle.move(-6, 0);
			}
		});
		
		bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				paddle.move(4, 0);
			}
		});
	}
}
