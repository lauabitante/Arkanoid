import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {

	private Tile tile;
	private Sprite paddle;
	private Ball ball;
	private int deltaY = 1;
	private int deltaX = 1;

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		tile.draw(canvas);
		ball.draw(canvas);
		paddle.draw(canvas);
	}

	@Override
	protected void setup() {
		this.setFramesPerSecond(60);
		this.setResolution(Resolution.MSX);
		
		ball = new Ball();
		ball.setPosition(130,180);
		
		paddle = new Sprite(20,3, Color.BLACK);
		paddle.setPosition(100,185);
		
		tile = new Tile(Color.RED);
		tile.setPosition(20,20);
		
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
		if (testeLimite(pos.y,0,getResolution().height)) {
			deltaY *= -1;
		}
		if (testeLimite(pos.x,0,getResolution().width)) {
			deltaX *= -1;
		}
		ball.move(deltaX, deltaY);
		
		if (tile.bateu(ball)) {
			Console.println("Bateu");
		}
		
		redraw();	
	}
	
	private boolean testeLimite(double pos, int min, int max) {
		if(pos > max || pos < min) {
			return true;
		} else {
			return false;
		}
	}
	
}
