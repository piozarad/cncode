package CordConverter;

import java.util.Optional;

import javax.swing.JOptionPane;

import BasicControls.Sterowanie;




public class CzasCyklu {
	

	
	
	private static int CycleTime(Sterowanie sterowanie,Function[] functionList, int start, int end, int rapidFeedrate, int toolChangeTime,int posowObrotuStolu)
	{
		float cycleTime=0;
		float feed=1;
		boolean insideCycle=false;
		int posuwSzybki = rapidFeedrate;
		float actualBRotation=0;
		int functionType=0;
		float lastCycleTime=0;
		Point previousPoint=new Point(400f,500f,800f);
		Point actualPoint = new Point(400f,500f,800f);
		Function f;
		Point srodekOkregu = new Point(0f,0f);
		float r;
		Wektor pierwszy = new Wektor(1,1);
		Wektor drugi = new Wektor(1,1);
		
		
		for(int i=0;i<functionList.length; i++)
		{
			
			f=functionList[i];
			if(f.getPoint()!=null)
				actualPoint.updatePoint(f.getPoint()); 
			
			if(i==start)
				cycleTime=0;
			if(i==end)
				break;
			
			if(f.getMFunctin()==6)
			{
				cycleTime+=toolChangeTime;
				previousPoint.setX(400f);
				previousPoint.setY(500f);
				previousPoint.setZ(800f);
			}
			//rozpedzenie wrzeciona
			else if(f.getMFunctin()==3)
				cycleTime+=1;
			
			if(f.getFeed()!=-1)
				feed=f.getFeed();
				
			if(f.containsFunction(0))
				functionType=0;
			else if(f.containsFunction(1))
			{
				functionType=1;
			}
			else if( f.containsFunction(2))
			{
				functionType=2;
			}
			else if(f.containsFunction(3))
				functionType=3;
			
			else if(f.containsFunction(81,82,84,85,86,87,88))
			{
				functionType=800;
				insideCycle=true;
				
				// DOJAZD DO PUNKTU R
				if(previousPoint.getZ() - f.getRcycleParam()[0]>1)
				{
					cycleTime+=Math.abs(((previousPoint.getZ() - f.getRcycleParam()[0])/posuwSzybki)*60);
				}
					
					//SIMENS WSZYTKIE CYKLE
					if(sterowanie.isType(ControlTypes.SINUMERIC))
					{
						if(f.containsFunction(84,85))
							lastCycleTime+=(2*((f.getRcycleParam()[3] - f.getRcycleParam()[2])/feed));
						else lastCycleTime+=((f.getRcycleParam()[3] - f.getRcycleParam()[2])/feed);
						previousPoint.updatePoint(new Point(f.getRcycleParam()[10]));
						if(f.getRcycleParam()[4]!=-1)
						{						
								lastCycleTime+=f.getRcycleParam()[4];			
						}
						
					}
					//FANUC  WSZYSTKIE CYKLE
					else if(sterowanie.isType(ControlTypes.FANUC) || sterowanie.isType(ControlTypes.OKUMA))
					{
						
						
						if(f.containsFunction(81,82,86,87,88))
						{
							
						//WIERCENIE
						lastCycleTime+=Math.abs(((f.getRcycleParam()[0] - actualPoint.getZ())/feed)*60);
						lastCycleTime+=Math.abs(((f.getRcycleParam()[0] - actualPoint.getZ())/posuwSzybki)*60);
						previousPoint.updatePoint(new Point(f.getRcycleParam()[0]));
						}
						
						// DO G82 POSTOJ CZASOWY
						if(f.getP()!=null)
						{
							lastCycleTime+=(Integer.parseInt(f.getP())/1000d);
						}
						
						//gwintowanie , rozwiercanie
						else if(f.containsFunction(84,85))
						{
							lastCycleTime+=Math.abs((2*((f.getRcycleParam()[0] - actualPoint.getZ())/feed)*60));
							
							
							if (f.containsFunction(84))
								lastCycleTime+=1.5;  // czas na przerwy w zmianie kierunku + przestów przy wyjezdzie
							else lastCycleTime+=0.5; // chwilowy przestój przy wyjezdzie
							
							
							previousPoint.updatePoint(new Point(f.getRcycleParam()[0]));
						}
					}
					lastCycleTime+=1;
					continue;
			}
			else if(f.containsFunction(83,73,76))
			{
				if(sterowanie.isType(ControlTypes.SINUMERIC))
				{
					if(f.getRcycleParam()[1]!=null)
					{
						lastCycleTime +=(f.getRcycleParam()[1]/feed);
						lastCycleTime +=(((f.getRcycleParam()[2]-f.getRcycleParam()[1])-f.getRcycleParam()[3])/feed);
						if(f.getRcycleParam()[5]!=null)
							lastCycleTime +=((f.getRcycleParam()[2]-f.getRcycleParam()[1])/f.getRcycleParam()[5])*2;
					}
					else 
					{
						//samo wiercenie
						lastCycleTime +=(((f.getRcycleParam()[2]-f.getRcycleParam()[3])/feed)*60);
						
						//odjazdy
						if(f.getRcycleParam()[5]!=null)
							lastCycleTime +=Math.abs(60*((f.getRcycleParam()[2]-f.getRcycleParam()[1])/f.getRcycleParam()[5]) * (f.getRcycleParam()[2]-f.getRcycleParam()[3])/posuwSzybki );
					}
				}
				else
				{
					functionType=800;
					insideCycle=true;
					if(f.containsFunction(83) && actualPoint.getZ()!=null)
					{
						
						
						// DOJAZD
						
						if((previousPoint.getZ() - f.getRcycleParam()[0])>1)
						{
							cycleTime+=Math.abs(((f.getRcycleParam()[0]-previousPoint.getZ() )/posuwSzybki)*60);
							
						}
						
						//samo wiercenie

						lastCycleTime += Math.abs((((f.getRcycleParam()[0]-actualPoint.getZ())/feed))*60);
						if(f.getQ()!=null) {
						// odjazdy
						//szereg arytmeryczny, suma przejazdow liczona ze wzoru S= n*(a1 + an)/2
						float an=  Math.abs((f.getRcycleParam()[0]-actualPoint.getZ())/posuwSzybki)*60;
						// a1 + an
						an+=Math.abs(f.getQ()/posuwSzybki);
						int n=(int) Math.abs((f.getRcycleParam()[0]-actualPoint.getZ())/f.getQ());
						

								// bez 2 w mianowniku bo kazdy przejazd liczy sie razy dwa (wjazd wyjazd)
								// do an dodane sa sekundowe postoje co ma reprezentowac czas na hamowanie, zmiane kierunku
							lastCycleTime +=Math.abs(n*(an+1));
							
							// Postoje dla kazdego przejazdu
							lastCycleTime +=(n*1.5);
							
					}
						previousPoint.updatePoint(new Point(f.getRcycleParam()[0]));
					}
					else if(f.containsFunction(73) && f.getQ()!=0 && actualPoint.getZ()!=null)
					{
						lastCycleTime +=(((f.getRcycleParam()[0]-actualPoint.getZ())/feed) *60);
						
							// zak³adaj¹c, ¿e ka¿dy odjazd zajmie tylko 1.5s
							lastCycleTime +=((f.getRcycleParam()[0]-actualPoint.getZ())/f.getQ())*1.5;
							previousPoint.updatePoint(new Point(f.getRcycleParam()[0]));

					}
					else if(f.containsFunction(76))
					{
						lastCycleTime+=Math.abs(((f.getRcycleParam()[0] - actualPoint.getZ())/feed)*60);
						lastCycleTime+=Math.abs(((f.getRcycleParam()[0] - actualPoint.getZ())/posuwSzybki)*60);
						// stop i pozycjonowanie
						lastCycleTime+=3;
						
						previousPoint.updatePoint(new Point(f.getRcycleParam()[0]));					
					}
					
					continue;
				}			
			}
			//postoj czasowy    OK
			if(f.containsFunction(4))
			{
				if(sterowanie.isType(ControlTypes.OKUMA))
				{
					if(f.getP()!=null)
						cycleTime+=Integer.parseInt(f.getP());
					continue;
				}
				else
				{
					if(actualPoint!=null && actualPoint.getX()!=null)
					{
							cycleTime+=actualPoint.getX();
							continue;	
					}
				}
			}
			
			// OK
			if(f.getBRotation()!=null)
			{
				if(functionType==0)
				{
					cycleTime+=Math.abs(((f.getBRotation()-actualBRotation)/posowObrotuStolu) *60);
				}
				else
				{
					if(feed!=0) {
						cycleTime+=Math.abs(((f.getBRotation()-actualBRotation)/feed) *60);
						continue;
					}
					else cycleTime+=2;
				}
				cycleTime+=1;
			}
			if(f.containsFunction(80))
			{
				insideCycle = false;
				cycleTime+=lastCycleTime;
				lastCycleTime=0;
				continue;
			}
			
			if(insideCycle && actualPoint!=null)
			{
					cycleTime+=(Point.travelTime(previousPoint, actualPoint, posuwSzybki));
				cycleTime+=lastCycleTime;
				previousPoint.updatePoint(actualPoint);
				continue;		
			}
			
			//OK
			if(actualPoint!=null)
			{
				if((functionType==2 || functionType==3) && ((f.getCircle().get('I')!=null && f.getCircle().get('J')!=null) || f.getCircle().get('R')!=null) )
				{
					
								
					if((f.getCircle().get('I')!=null && f.getCircle().get('J')!=null) )
					{
						r =  (float) (Math.sqrt((Math.pow(f.getCircle().get('I'), 2) + Math.pow(f.getCircle().get('J'),2))));
					}
					else r=f.getCircle().get('R');
					
						srodekOkregu.setX(previousPoint.getX()+f.getCircle().get('I'));
						srodekOkregu.setY(previousPoint.getY()+f.getCircle().get('J'));
					
						pierwszy.setPunktPoczatkowy(srodekOkregu);
						pierwszy.setPunktKoncowy(previousPoint);
					
						drugi.setPunktKoncowy(actualPoint);
						drugi.setPunktPoczatkowy(srodekOkregu);
					
						r=pierwszy.dlugosc();
						
							cycleTime+=(((2 *Math.PI * r* (pierwszy.katSkierowanyMiedzyWektorami(functionType, drugi)/360) ) /Math.max(feed, 1))*60);	
					}
				//OK
				else 
					{
						cycleTime+=(Point.travelTime(previousPoint, actualPoint, (functionType!=0)?(feed):(posuwSzybki)));
					}
				
				previousPoint.updatePoint(actualPoint);	
			}
		}

		//zeby nie dzielic przez 0
		if(cycleTime==0)
			cycleTime=1;
		
		 return (int)cycleTime;
}
	
	
	public static int countCycleTime(Edytor e,int start)
	{
		e.analyze();
		
		return CycleTime(e.getControls(), e.getArrayFunction(), start, e.getFunctionSize(), Wind.options.getMaxFeed(), Wind.options.getToolChangeTime(),Wind.options.getABCAxisFeedRate());
	
	}
	
	public static int countCycleTime(Edytor e,int start, int end)
	{
		e.analyze();

		if(end==0)
			end=e.getFunctionSize();
		
		return CycleTime(e.getControls(), e.getArrayFunction(), start, end, Wind.options.getMaxFeed(), Wind.options.getToolChangeTime(),Wind.options.getABCAxisFeedRate());
	
	}

	
	public static void countCycleTime(Edytor e)
	{	
		float cycleTime = countCycleTime( e,0 ,0);

		if(cycleTime<120)
			JOptionPane.showMessageDialog(e,"Calkowity czas cyklu:" + cycleTime + " sekund " + " Ilosc sztuk na zmiane: " + (int) ((60*Wind.options.getEffectiveWorkTime())/(cycleTime+Wind.options.getPartFixureTime())),"Czas cyklu", JOptionPane.PLAIN_MESSAGE);
		else 
		{
			JOptionPane.showMessageDialog(e, "Calkowity czas cyklu:" + (int)(cycleTime/60) + " minut. " + (int)(cycleTime%60) +" sekund. Ilosc sztuk na zmiane: " +(int) (Wind.options.getEffectiveWorkTime()/((Wind.options.getPartFixureTime())/60)), "Czas cyklu", JOptionPane.PLAIN_MESSAGE);
		}
		
		Wind.log.writeInfoLog("Czas cyklu - wykonano", CzasCyklu.class.getSimpleName());

	}

}
