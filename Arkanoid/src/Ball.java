import java.io.IOException;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Sprite;

public class Ball extends Sprite {

	public Ball() {
		super(ballImage());
	}
	
	public int getRadius() {
		return getWidth() / 2;
	}
	
	public static Image ballImage(){
		Image img = null;
		try{
			img = new Image("Images/ball.png");
			img.resize(6, 6);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return img;
	}

	@Override
	public Point getPosition() {
		Point pos = super.getPosition();
		int r = getRadius();
		return new Point(pos.x + r, pos.y + r);
	}
}