package PlaneMachining;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import BasicControls.Sterowanie;
import CordConverter.Edytor;
import CordConverter.Point;
import CordConverter.Wind;

public class PlaneControler {


	
	PlaneModel model;
	PlaneView view;
	Edytor parent;
	
	
	
	
	public PlaneControler(PlaneModel model, PlaneView view, Edytor parent)
	{
		this.model=model;
		this.view = view;
		this.parent=parent;
		
		view.addExecuteButtonListeners(new ExecuteListener());
		view.addPlaneViewKeyListeners(new PlaneViewKeyListener());
		view.addpaintToolListener(new PaintToolListener());
		view.addGenerateButtonLister(new GenerateListener());
	}
	
	
	
	private void generateGCodeToolPathWithoutRadius()
	{
		
	
		model.calculateAdvanceInZAxispoints();
		float[] advanceInZAxispoints = model.getAdvanceInZAxisArray();
		int toolNumber = parent.getToolBar().getToolNumber();
		int blockNumber=20;
		float initialZ = model.getZ0() + model.getMaterialThickness();
		
		PrintStream pr = new PrintStream(this.parent.txt);
		System.setOut(pr);
		System.setErr(pr);
		int safeRetraction = Wind.options.getSafeRetraction();
		Sterowanie sterowanie = parent.getControls();
		List<Point> localCsPointList = model.toolpathInLocalCS();
		
		System.out.printf(Locale.CANADA,"N%d (T%d GLOWICA FI%.2f)%n" , blockNumber+=5, toolNumber , model.getToolDiameter());
		sterowanie.przygotowanieUkladuINarzedzia(15, toolNumber, safeRetraction,this.parent.getToolBar().getRotation());
		System.out.printf(Locale.CANADA,"N%d S%d M3%n" , blockNumber+=5,model.getSprindleSpeed()); 
		System.out.printf(Locale.CANADA,"N%d %s%n" , blockNumber+=5,model.getToolpath().get(0)); 
		System.out.printf(Locale.CANADA,"N%d G1 Z%.2f F%d%n" , blockNumber+=5,initialZ+20,this.model.getFeed());

		for(int i=0;i<advanceInZAxispoints.length ; i++) 
		{
			
			System.out.printf(Locale.CANADA,"N%d G1 Z%.2f%n" , blockNumber+=5,advanceInZAxispoints[i]);
			for(int j=1; j< localCsPointList.size() ; j++) 
			{
				System.out.printf(Locale.CANADA,"N%d %s%n", blockNumber+=5,localCsPointList.get(j));
			}
		
			System.out.printf(Locale.CANADA,"N%d G0 Z%.2f%n" , blockNumber+=5,initialZ+3);
			if(i!=advanceInZAxispoints.length-1) 
			{
				System.out.printf(Locale.CANADA,"N%d G0 %s%n" , blockNumber+=5,model.getToolpath().get(0)); 
			}
		}
		System.out.printf(Locale.CANADA,"N%d G0 Z%d M9 %n" , blockNumber+=5,Wind.options.getSafeRetraction());
		System.out.printf(Locale.CANADA,"N%d M5%n" , blockNumber+=5);
		System.out.printf(Locale.CANADA,"N%d M1%n" , blockNumber+=5);
	}
	
	
	
	
	private void createToolPath()
	{
		model.getToolpath().clear();
		
		switch(model.getDirectionChoosed())
		{
		case LEFT:	
			if(model.isPathWithRadius()) model.toolPathfromLeftWithRadius();
			else{
					
					model.toolPathfromLeftStraightLines();
					view.setToolPath(model.getToolpath());
				}
			view.canvas.showToolPath(true);
			view.repaint();
			break;
		case RIGHT:
			if(model.isPathWithRadius()) model.toolPathfromLeftWithRadius();
			else{
					
					model.toolPathfromRightStraightLines();
					view.setToolPath(model.getToolpath());
				}
			view.canvas.showToolPath(true);
			view.repaint();
			break;
		case UP:
			if(model.isPathWithRadius()) model.toolPathfromLeftWithRadius();
			else{
					
					model.toolPathfromUpStraightLines();
					view.setToolPath(model.getToolpath());
				}
			view.canvas.showToolPath(true);
			view.repaint();
			break;
		case DOWN:
			if(model.isPathWithRadius()) model.toolPathfromLeftWithRadius();
			else{
					
					model.toolPathfromDownStraightLines();
					view.setToolPath(model.getToolpath());
				}
			view.canvas.showToolPath(true);
			view.repaint();
			break;
			
		case NONE:
			break;
		
		}	
	}
	
	 public boolean directionChoosed() {
			if(view.getDirectionChoosed()==START_DIRECTIONS.NONE) 
			{
				JOptionPane.showMessageDialog(this.view, "Nie wybrano kierunku startu");
				
				return false;
			}
			else return true;
		}
	
	 
	 private boolean getX0()
		{
			
			if(view.getX0().isPresent())
			{
				model.setX0(view.getX0().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana baza w osi x", "B³¹d", JOptionPane.NO_OPTION);
				return false;
			}
		}

		
		
	 private boolean getY0()
		{
			
			if(view.getY0().isPresent())
			{
				model.setY0(view.getY0().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana baza w osi y", "B³¹d", JOptionPane.NO_OPTION);
				return false;
			}
		}
	 	
	 	private void getFinishingPass()
	 	{
	 		model.setFinishingPass(view.isFinishingPass());
	 	}
		
		
		private boolean getRadius()
		{
			
			if(view.getRadius().isPresent())
			{
				model.setRadius(view.getRadius().get());
				return true;
			}
			else 
			{
				JOptionPane.showMessageDialog(this.view, "le zdefiniowany promieñ na krawêdziach œcie¿ki", "B³¹d", JOptionPane.NO_OPTION);
				return false;
			}
		}
		
		private boolean getDephZ()
		{
			
			if(view.getDephZ().isPresent())
			{
				model.setMaterialThickness(view.getDephZ().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana g³êbokoœæ wybrania", "B³¹d", JOptionPane.NO_OPTION);
				return false;
			}
		}

		private boolean getDistanceFromObstacle()
		{
			if(view.getDistanceFromObstacle().isPresent())
			{
				model.setDistanceFromObstacle(view.getDistanceFromObstacle().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana odleg³oœæ od przeszkody", "B³¹d", JOptionPane.NO_OPTION);
				return false;
			}
		}
		private boolean getAp()
		{
			if(view.getAp().isPresent())
			{
				model.setAp(view.getAp().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana g³êbokoœæ skrawania", "B³¹d", JOptionPane.NO_OPTION);
				return false;
			}
			}
		
		private boolean getS(boolean showDialog)
		{
			if(view.getS().isPresent())
			{
				model.setS(view.getS().get());
				return true;
			}
			else
			{
				if(showDialog)JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana prêdkoœæ obrotowa wrzeciona", "B³¹d", JOptionPane.NO_OPTION); 
				return false;
			}
		}
		private boolean getFeed(boolean showDialog)
		{
			if(view.getFeed().isPresent())
			{
				model.setF(view.getFeed().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowany posuw", "B³¹d", JOptionPane.NO_OPTION);
				return false;
			}
		}
	
		
		private boolean getToolDiameter()
		{
			if(view.getToolDiameter().isPresent())
			{
				model.setToolDiameter(view.getToolDiameter().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana Œrednica narzêdzia", "B³¹d", JOptionPane.NO_OPTION);
				return false;
			}
		}
		
		
		
		private boolean getz()
		{
			if(view.getz().isPresent())
			{
				model.setZ0(view.getz().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana wspó³rzêdna Z obrobionej na gotowo p³yty", "B³¹d", JOptionPane.NO_OPTION);
				
				return false;
			}
			
		}
		
		private boolean getzf()
		{
			if(view.getzf().isPresent())
			{
				model.setZf(view.getzf().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana wartoœæ przejœcia wykañczaj¹cego", "B³¹d", JOptionPane.NO_OPTION);
				return false;
			}
		}
		
		private boolean geth()
		{
			if(view.geth().isPresent())
			{
				model.setH(view.geth().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana wysokoœæ p³yty", "B³¹d", JOptionPane.NO_OPTION);
				
				return false;
			}
		}
		
		private boolean getd()
		{
			if(view.getd().isPresent())
			{
				model.setD(view.getd().get());
				return true;
			}
			else
			{
				JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana szerokoœæ p³yty", "B³¹d", JOptionPane.NO_OPTION);
				return false;
			}
		}
		
		private boolean getAe()
		{
			if(view.getAe().isPresent())
			{
				model.setAe(view.getAe().get());
				
				if(model.getAe()>100) {
					model.setAe(100f);
					view.setAeTextField("100");
					JOptionPane.showMessageDialog(view, "Szerokoœæ skrawania wiêksza od œrednicy g³owicy. Ustawiono ae na 100% podanej œrednicy", "Ostrze¿enie", JOptionPane.NO_OPTION);
					return false;
				}
				if(model.getAe()<1)
				{
					model.setAe(5f);
					view.setAeTextField("5");
					JOptionPane.showMessageDialog(view, "Szerokoœæ skrawania mniejsza od 1%. Ustawiono ar na 5% podanej œrednicy", "Ostrze¿enie", JOptionPane.NO_OPTION);
					return false;
				}
				
			}
			else
				{
					JOptionPane.showMessageDialog(this.view, "Zle zdefiniowana szerokoœæ skrawania", "B³¹d", JOptionPane.NO_OPTION);
					view.setAeTextField("60");
					return false;
				}
			
				return true;
			}
		
		private void calculateFz()
		{
			//tool diameter
			int n=1;
			int z=1;
			float f = 200;
			boolean isOk = true;
			
			if(view.getNumberOfCutingEdges().isPresent())
			{
				z = view.getNumberOfCutingEdges().get();
			}
			else
			{
				view.setVcTextField("!Z!");
				isOk=false;
			}
			
			if(view.getS().isPresent())
			{
		
				n= view.getS().get();
			}
			else
			{
				view.setSprindleSpeedTextField("!Obroty!");
				isOk=false;
			}
			
			if(view.getFeed().isPresent())
			{
				f = view.getFeed().get();
			}
			else
			{
				view.setfzTextField("!Posuw!");
				isOk=false;
			
			}
			
			if(isOk)
			{

				f=  (100*(f/(n*z)))/100f;
			
				view.setfzTextField(String.format(Locale.CANADA,"%.2f", f));
			}
		}
		
	private void calculateVc()
	{
		//tool diameter
		float diameter=1f;
		int rev=1;
		boolean isOk = true;
		
		
		if(view.getToolDiameter().isEmpty())
		{
			view.setVcTextField("!Œrednica!");
			isOk=false;
		}
		else 
		{
			diameter= view.getToolDiameter().get();
		}

		if(view.getS().isEmpty())
		{
			view.setVcTextField("!Obroty!");
			isOk=false;
		}
		else
		{
			rev= view.getS().get();
				
		}

		if(isOk)
		{
		
			view.setVcTextField(Integer.toString((int)((Math.PI * rev * diameter)/1000)));
		}
	}
	
	private void calculateN()
	{
		//tool diameter
		int cuttingSpeed=1;
		float diameter=1f;
		boolean isOk = true;
		
		if(view.getVc().isPresent())
		{
			cuttingSpeed = view.getVc().get();
		}
		else
		{
			view.setSprindleSpeedTextField("!Vc!");
			isOk=false;
		}
		if(view.getToolDiameter().isEmpty())
		{
			view.setVcTextField("!Œrednica!");
			isOk=false;
		}
		else 
		{
			diameter= view.getToolDiameter().get();
		}
		if(isOk)
		{
			cuttingSpeed=(int)(cuttingSpeed*1000/(Math.PI * diameter));
		
			view.setSprindleSpeedTextField(Integer.toString(cuttingSpeed));
		}
	
	}
	
	private void calculateFeed()
	{
		//tool diameter
		int n=1;
		int z=1;
		float fz = 0.1f;
		boolean isOk = true;

		z = view.getNumberOfCutingEdges().orElse(1);
		

		if(view.getS().isEmpty())
		{
			view.setVcTextField("!Obroty!");
			isOk=false;
		}
		else
		{
			n= view.getS().get();		
		}
		
		
		if(view.getfz().isPresent())
		{
			fz = view.getfz().get();
		}
		
		else
		{
			view.setFeedTextField("!fz!");
			isOk=false;
		}
		
		if(isOk)
		{
			n=(int)(n*z*fz);
		
			view.setFeedTextField(Integer.toString(n));
		}
	}
	
	private boolean validateGeometricalParameters()
	{
		
		return getX0() && getY0() && geth() && getd() && getDephZ();
	}
	
	private boolean validateToolParameters()
	{
		return  getToolDiameter();
	}
	
	private boolean validateCuttingDataParameters()
	{
		return getFeed(true) && getS(true);
	}
	
	private boolean validateSafeSpaceBetweenObstaclesYAxis()
	{
		if(model.checkForObstacleAndToolColishionsYAxis()) return true;
		
		else 
		{
			JOptionPane.showMessageDialog(view, "Narzêdzie nie zmieœci siê miêdzy przeszkody", "B³¹d", JOptionPane.NO_OPTION);
			return false;
		}
	}
	
	private boolean validateSafeSpaceBetweenObstaclesXAxis()
	{
		if(model.checkForObstacleAndToolColishionsXAxis()) return true;
		
		else 
		{
			JOptionPane.showMessageDialog(view, "Narzêdzie nie zmieœci siê miêdzy przeszkody", "B³¹d", JOptionPane.NO_OPTION);
			return false;
		}
	}
	
	
	private boolean validateToolpathParameters()
	{
		return  getAe() && getAp() 
				 && getDistanceFromObstacle() && 
				  getz() && getzf() && validateRadius() && validateSafeSpaceBetweenObstaclesXAxis() && validateSafeSpaceBetweenObstaclesYAxis() &&
				((view.pathWithRadius() && getRadius() ) ||  !view.pathWithRadius());
	}
	
	private boolean validateAllParameters()
	{
		
		return validateGeometricalParameters() && directionChoosed() && validateToolParameters() && validateCuttingDataParameters() && validateToolpathParameters() ;
	}
	
	
	
	private void generateToolPath()
	{
		createToolPath();
		JOptionPane.showMessageDialog(view, "Obliczono œcie¿ki, jeœli s¹ akceptowalne wciœnij przycisk generuj aby wygerowaæ kod");
	}
	
	
	private void generate()
	{
			updateModel();
			generateToolPath();
			Wind.log.writeInfoLog("Plane", this.getClass().getSimpleName());
			
	
	}
	
	private void updateModel()
	{
	
		this.getFinishingPass();
		setObstacles();
		model.update();
		model.setDirectionChoosed(view.getDirectionChoosed());
		
	}
	
	
	private boolean validateRadius() 
	{
		if(model.getRadius()> model.getd() || model.getRadius() > model.geth())
		{
			JOptionPane.showMessageDialog(view, "Zby du¿y promieñ w stosunku do wymiarów p³yty", "B³¹d", JOptionPane.NO_OPTION);
			return false;
		}
		return true;
	}
	

	private void setObstacles()
	{
		model.setLeftDirectionLocked(view.isLeftSideLocked());
		model.setRightDirectionLocked(view.isRightSideLocked());
		model.setUpperDirectionLocked(view.isUpperSideLocked());
		model.setBottomDirectionLocked(view.isBottomSideLocked());
	}
	
	
	class PaintToolListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			START_DIRECTIONS selectedindex = view.getDirectionChoosed();
			if(selectedindex!= START_DIRECTIONS.NONE)
			{
			if(selectedindex==START_DIRECTIONS.LEFT &&  view.isLeftSideLocked() ||
				selectedindex==START_DIRECTIONS.RIGHT && view.isRightSideLocked() ||
				selectedindex==START_DIRECTIONS.UP && view.isUpperSideLocked()  ||
				selectedindex==START_DIRECTIONS.DOWN && view.isBottomSideLocked())
			{
				view.resetToolStartingPointComboBox();
				JOptionPane.showMessageDialog(view, "Zdefiniowano przeszkodê z tej strony", "B³¹d", JOptionPane.NO_OPTION);
			}
			else
			{
				if(validateAllParameters())
				{	
					updateModel();
					model.calculateStartingPoint(selectedindex);
					view.setStartingpoint(model.getToolStartPoint());
					view.repaint();
				}
			}
		}
		}
	}
	
	
	public class GenerateListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			generateGCodeToolPathWithoutRadius();
			parent.writelog("Wygenerowano œcie¿ki dla modelu danych:\n" + PlaneControler.this.model.toString());
			PlaneControler.this.view.dispose();
			
		}
	
	}
	
	public class ExecuteListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();
			if(o==view.getExecuteButton())
			{
				if(validateAllParameters())
				{
					generate();
					view.setToolPathGeneratedFlag(true);
					
				}
				else view.setToolPathGeneratedFlag(false);
				
			}
			else if(o==view.getReverseButton())
			{
				view.reverse();
			}
		}
		
	}
	
	public class PlaneViewKeyListener implements KeyListener
	{

		@Override
		public void keyTyped(KeyEvent e) {
			// not used
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// not used
		}

		@Override
		public void keyReleased(KeyEvent e) {

			Object o = e.getSource();
			
			if(o== PlaneControler.this.view.getSprindleSpeedTextField())
			{
				calculateVc();
				calculateFz();
			}
			else if(o== PlaneControler.this.view.getFeedPerRevTextField())
			{
				calculateFeed();
			}
			else if(o== PlaneControler.this.view.getFeedTextField())
			{
				calculateFz();
			}
			else if(o== PlaneControler.this.view.getVcTextField())
			{
				calculateN();
				calculateFz();
			}
		}
		
	}
	
	
	
}
