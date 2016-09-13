import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Sprite;

public class Tile extends Sprite {
	
	private boolean alive = true;
	private int lifeTile = 0;

	public Tile(Color color, int life) {
		super(20, 5, color);
		this.lifeTile = life;
	}

	//this method is used to verify if the ball collided with the tile.
	public boolean collided(Ball ball){
		if (!alive) {
			return false;
		}
		
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
		
		//alive = false;
		
		this.lifeTile --;
		
		this.clear(Color.LIGHTGRAY);
		
		if(this.lifeTile == 0) {
			alive = false;
		}
		
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		if (alive) {
			super.draw(canvas);
		}
	}
}
