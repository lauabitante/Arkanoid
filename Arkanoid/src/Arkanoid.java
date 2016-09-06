import java.util.Random;

import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {

	//private Tile tile;
	private Tile tiles[] = new Tile[10];
	private Tile tiles2[] = new Tile[10];
	private Sprite paddle;
	private Ball ball;
	private int deltaY = 1;
	private int deltaX = 1;

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		for(int i=0; i<tiles.length; i++) {
			Tile t = tiles[i];
			t.draw(canvas);
		}
		
		for(int i=0; i<tiles2.length; i++) {
			Tile t = tiles2[i];
			t.draw(canvas);
		}
		
		ball.draw(canvas);
		paddle.draw(canvas);
	}

	@Override
	protected void setup() {
		this.setFramesPerSecond(60);
		this.setResolution(Resolution.MSX);
		
		ball = new Ball();
		ball.setPosition(130,180);
		
		paddle = new Sprite(30, 3, Color.DARKGRAY);
		paddle.setPosition(100, 183);
		
		for (int i=0; i<tiles.length; i++) {
		
			Tile t = new Tile(Color.RED);
			t.setPosition(5+(i*22), 20);
			
			tiles[i] = t;
		}
		
		for (int i=0; i<tiles2.length; i++) {
			
			Tile t = new Tile(Color.GREEN);
			t.setPosition(5+(i*22), 30);
			
			tiles2[i] = t;
		}
		
//		for (int i=0; i<tiles.length; i++) {
//			//tile = new Tile(Color.RED);
//			//tile.setPosition(20, 20);
//			Tile t = new Tile(randomColor());
//			t.setPosition(5+(i*22), 20);
//			
//			tiles[i] = t;
//		}
		
		
		bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				paddle.move(-3, 0);
			}
		});
		bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				paddle.move(3, 0);
			}
		});
	}

	@Override
	protected void loop() {
		//Testando os limites do eixo X e Y.
		Point pos = ball.getPosition();
		if (testScreenBounds(pos.y,0,getResolution().height)) {
			deltaY *= -1;
		}
		if (testScreenBounds(pos.x,0,getResolution().width)) {
			deltaX *= -1;
		}
		ball.move(deltaX, deltaY);
		
//		if (tile.collided(ball)) {
//			Console.println("Collided!");
//		}
		
		for(int i=0; i<tiles.length; i++){
			Tile t = tiles[i];
			if (t.collided(ball)) {
				Console.println("Collided!");
			}
		}
		
		for(int i=0; i<tiles2.length; i++){
			Tile t = tiles2[i];
			if (t.collided(ball)) {
				Console.println("Collided!");
			}
		}
		
		redraw();	
	}
	
	private boolean testScreenBounds(double pos, int min, int max) {
		if(pos > max || pos < min) {
			return true;
		} else {
			return false;
		}
	}
	
	private Color randomColor(){  
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
	
}
