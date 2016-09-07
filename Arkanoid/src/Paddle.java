import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Sprite;

public class Paddle extends Sprite {

	public Paddle() {
		super(30, 3,Color.DARKGRAY);
	}

	public boolean collided(Ball ball){
		
		Point pos = ball.getPosition();
		int raio = ball.getRadius();
		
		Rect rect = getBounds();
		int top = rect.y;
		int bottom = rect.y + rect.height;
		int left = rect.x;
		int right = rect.x + rect.width;
		
		if (pos.x-raio > right) {
			return false;
		}
		if(pos.x+raio < left) {
			return false;
		}
		if(pos.y+raio < top) {
			return false;
		}
		if(pos.y-raio > bottom) {
			return false;
		}
		
		return true;
	}

	@Override
	public Point getPosition() {
		Point posPaddle = super.getPosition();
		return new Point(posPaddle.x, posPaddle.y);
	}

}
