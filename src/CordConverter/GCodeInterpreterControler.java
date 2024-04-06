package CordConverter;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.StringJoiner;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import BasicControls.Sterowanie;

public class GCodeInterpreterControler {

	private GCodeInterpreterModel model;
	private txtArea viewInput;
	private JTextArea viewOutput;
	private Edytor parent;
	private JLabel radiusLabel;
	private JLabel cicleCenterLabel;
	private Point previousPoint;
	
	
	
	public GCodeInterpreterControler(GCodeInterpreterModel model,txtArea viewInput, JTextArea viewOutput, Edytor parent, JLabel radiusLabel, JLabel cicleCenterLabel)
	{
		this.model=model;
		this.viewInput = viewInput;
		this.parent=parent;
		this.viewOutput=viewOutput;
		this.radiusLabel=radiusLabel;
		this.cicleCenterLabel=cicleCenterLabel;
		this.viewInput.addInterpreterListener(new InterpreterListener());
	}

	//display radius if G3 or G2
	private void displayRadius(Function f)
			{
				if(f.containsFunction(2,3))
				{
					
					this.radiusLabel.setText("R"+((Float)(Point.roundToThree(model.getRadius(f)))).toString());
				}
			}
	
	//TODO dopisac testy jednostkowe do tego
	private void displayCircleCenter(Function f)
	{
		if(f.containsFunction(2,3))
		{
			StringJoiner sj = new StringJoiner(";","{","}");
			if(f.getCircle().containsKey('R'))
			{
				
				
				Point center = computeCenterOfPoint(f.containsFunction(2)? 2:3,f.getPoint(),this.previousPoint,f.getCircle().get('R'));
				
				sj.add(center.getX().toString());
				sj.add(center.getY().toString());
				
				
			}
			else if(previousPoint!=null && !f.getCircle().isEmpty())
			{
				if(f.getCircle().containsKey('I') && previousPoint.getX()!=null)
				{
					sj.add(Float.toString(Point.roundToThree(previousPoint.getX()+f.getCircle().get('I'))));
				}
				if(f.getCircle().containsKey('J') && previousPoint.getY()!=null)
				{
					sj.add(Float.toString(Point.roundToThree(previousPoint.getY()+f.getCircle().get('J'))));
				}
			}
			
			this.cicleCenterLabel.setText("O" +sj);
		}
		else this.cicleCenterLabel.setText("O{}" );
	}
	
	
	
	private Point computeCenterOfPoint(int direction, Point destinationPoint, Point previousPoint, float radius)
	{
		double distanceBetweenPoint = Point.distanceXY(previousPoint, destinationPoint);
		double alfa =  Math.acos(0.5*distanceBetweenPoint/radius);
		float x;
		float y;
		
		
		if(direction ==2 || direction ==3)
		{
			
				x =  previousPoint.getX() + radius * (float)Math.cos(alfa);
				y = previousPoint.getY() + radius * (float)Math.sin(alfa);
		}	
		else throw new IllegalArgumentException();
		
		return new Point(x,y,TYPE.XY_POINT);
	}
	

	public void updateGCodes(Sterowanie s)
	{
		this.model.updateGCodeDatabase(s.updateGCodes());
	}
	
	private void findPreviousPoint(String[] s, int index)
	{
		if(index>0 && index<s.length)
			{
			Function f = new Function(s[--index]);
		
			while(index>=0 && f.isBlank() && f.getPoint()!=null)
			{
				f = new Function(s[index--]);
			}
			this.previousPoint = f.getPoint();
		}
		
	}
	
	
	/*
	 * returns previous point // Can be null
	 */
	public Point getPreviousPoint()
	{
		return this.previousPoint;
	}
	
	public class InterpreterListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) {
			//Not supported
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			viewInput.updateCaretLinePosition();
			if(viewInput.getTxtArea().getText().length()!=0)
			{
				int position = viewInput.getCaretLinePosition();
				
			
					String[] temp = parent.txt.getTxtArea().getText().split("\n");	
					Function f;
					
					
					if(temp.length!=0)
					{
					
						try
						{
							f= new Function(temp[position],parent.getControls());
						}
						catch (ArrayIndexOutOfBoundsException ex)
						{
						
							f= new Function(temp[0],parent.getControls());
							Wind.log.writeErrorLog("Index out of bounds", ex, this.getClass().getSimpleName());
						}
					
						findPreviousPoint(temp, position);		
						displayRadius(f);
						displayCircleCenter(f);
						model.explainGCodeBlock(f);
						viewOutput.setText(model.getInterpretation());
				
					}				
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//Not supported
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			//Not supported
		}

		@Override
		public void mouseExited(MouseEvent e) {
			//Not supported
		}
	}	
}
