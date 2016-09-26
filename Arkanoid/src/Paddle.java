import java.io.IOException;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Sprite;
import com.senac.SimpleJava.Graphics.Image;


public class Paddle extends Sprite {

	public Paddle() {
		super(paddleImage());
	}
	
	public static Image paddleImage() {
		Image img = null;
		try {
			img = new Image("images/paddle.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
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
