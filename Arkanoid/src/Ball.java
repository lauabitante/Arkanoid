import java.io.IOException;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Sprite;

public class Ball extends Sprite {

	public Ball() {
//		super(5,5,Color.DARKGRAY);
		super(ballImage(), Color.WHITE);
	}
	
	public int getRadius() {
		return getWidth() / 2;
	}
	
	public static Image ballImage(){
		Image img = null;
		try{
			img = new Image("Images/ball.png");
			img.resize(10, 10);
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
	
////	public void draw(Graphics graphics) {
//////		graphics.fillOval(20, 20, getWidth(), getHeight());
////		graphics.fillOval((int)this.getPosition().x, (int)this.getPosition().y, 20, 20);
//////		Color yellow = new Color(255, 255, 0);
//////		graphics.setColor(yellow);
////		Console.println("GRAPHICS!");
//	}
}
