package DrawFunction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.font.NumericShaper;

public class RectangleWithDimensions implements DrawableFunction {

	
	
	private int wysokosc=0;
	private int szerokosc=0;
	private String wysokoscString;
	private String szerokoscString;
	private int x;
	private int y;
	private boolean isValid;
	private boolean showDimensions;

	
	
	
	public RectangleWithDimensions()
	{
		this.showDimensions=true;
		validate("50", "50", "300", "250");
	}
	
	
	
	public RectangleWithDimensions(String wysokosc, String szerokosc, String x,String y,boolean showDimensions) {
	
		this.wysokoscString = wysokosc;
		this.szerokoscString = szerokosc;
		validate(wysokoscString,szerokoscString,x,y);
		this.showDimensions=showDimensions;
	}
	
	
	/**
	 * 
	 * @param wysokosc 
	 * @param szerokosc
	 * @return zwraca true jesli podane parametry sa prawidlowe, uaktualnia pola wysokosc i szerokosc i zmienia parametr isValid na true. 
	 * Jesli podane parametry nie sa prawid³owe zwraca false, ustawia is Valid na false oraz nie uaktualnia wartosci pol szerokosc i wysokosc
	 */
	public boolean validate(String wysokosc, String szerokosc, String x, String y)
	{
		int wys;
		int szer;
		int srodekx;
		int srodeky;
	
		
		try {
			wys = (int) Float.parseFloat(wysokosc);
			szer = (int) Float.parseFloat(szerokosc);
			srodekx = Integer.parseInt(x);
			srodeky = Integer.parseInt(y);
		}
		catch(NumberFormatException | NullPointerException e)
		{
			this.isValid=false;
			return false;
		}
		
		this.szerokoscString = szerokosc.replace("-", "");
		this.wysokoscString=wysokosc.replace("-", "");
		this.szerokosc=Math.abs(szer);
		this.wysokosc=Math.abs(wys);
		this.x=srodekx;
		this.y=srodeky;
		this.isValid=true;
		return true;	
	}
	
	public void showDimensions(boolean showDimensions)
	{
		this.showDimensions=showDimensions;
	}

	@Override
	public void draw(Graphics g) {
		if(isValid)
		{
			g.setColor(Color.BLACK);
			g.drawRect(x-(szerokosc/2), y-(wysokosc/2), szerokosc, wysokosc);
			if(showDimensions)
			{
				g.drawString(szerokoscString,x-10,y+ wysokosc/2 + 15);
				g.drawString(wysokoscString,x-szerokosc/2-25,y);	
			}
		}
	}



	@Override
	public void drawInCenter(Graphics g, Dimension windowSize) {
		if(isValid)
		{
			g.setColor(Color.BLACK);
			g.drawRect(x-(szerokosc/2) +(int)windowSize.getWidth()/2, y-(wysokosc/2)+(int)windowSize.getHeight()/2, szerokosc, wysokosc);
			if(showDimensions)
			{
				g.drawString(szerokoscString,x-10 +(int)windowSize.getWidth()/2,y+ wysokosc/2 + 15+(int)windowSize.getHeight()/2);
				g.drawString(wysokoscString,x-szerokosc/2-25 +(int)windowSize.getWidth()/2,y+(int)windowSize.getHeight()/2);	
			}
		}
		
	}
}
