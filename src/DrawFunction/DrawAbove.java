package DrawFunction;

import java.awt.Dimension;
import java.awt.Graphics;

import CordConverter.Point;

public class DrawAbove implements DrawableFunction {

	
	private String message;
	private Point point;

	
	
	public DrawAbove(String message, Point point)
	{
		this.message=message;
		this.point=point;
		
	}
	
	
	
	@Override
	public void draw(Graphics g) {
		int x = point.getX().intValue();
		int y= point.getY().intValue();
		
		
		if(x<350) x+=20;
		if(x>=340) x-=65;
		if(y>=350) y-=40;
		
		
		
		g.drawString(message,x,y-15);

	}



	@Override
	public void drawInCenter(Graphics g, Dimension windowSize) {
		int x = point.getX().intValue();
		int y= point.getY().intValue();
		
		
		if(x<350) x+=20;
		if(x>=340) x-=65;
		if(y>=350) y-=40;
		
		
		
		g.drawString(message,x+((int)windowSize.getWidth())/2,((int)windowSize.getHeight()/2)-y-15);
		
	}

}
