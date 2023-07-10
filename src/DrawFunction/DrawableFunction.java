package DrawFunction;

import java.awt.Dimension;
import java.awt.Graphics;

public interface DrawableFunction {

	public void draw(Graphics g);
	
	public void drawInCenter(Graphics g, Dimension windowSize);
}
