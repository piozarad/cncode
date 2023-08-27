package CordConverter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import BasicControls.Sterowanie;
import BasicControls.SterowanieFanuc;
import BasicControls.SterowanieOkuma;
import BasicControls.SterowanieSinumeric;

public class Konwertuj extends JFrame implements ActionListener{

	//labels
	private JLabel wykrytoSterowanie;
	private JLabel strzalka;
	private JLabel wykrytoSterowanieLabel;
	private JLabel zadeklarowaneSterowanieLabel;
	
	
	//buttons
	private JButton konwertuj;
	private JButton cofnij;
	
	
	//combo
	private JComboBox<String> sterowanie;
	private JComboBox<String> noweSterowanie;
	
	
	//constants
	private static final String S[] = {"Sinumeric", "Fanuc" ,"Okuma"};
	
	
	//parent
	private final Edytor parent;
	private final Wind w;
	
	
	

	
	
	
	
	public Konwertuj(Edytor parent, Wind w)
	{
		this.parent=parent;
		this.w=w;
		setTitle("Konwertuj");
		setVisible(true);
		setResizable(false);
		setSize(500,220);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				
				cofnij.doClick();
			
			}		
		});
		
		//layout
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		
		GridBagConstraints border = new GridBagConstraints();
		border.gridx=0;
		border.gridy=0;
		border.weightx=0;
		border.weighty=0;
		border.fill=GridBagConstraints.HORIZONTAL;
		
		border.insets = new Insets(15,15,15,15);
		
			
		
		//x0 y0 
		wykrytoSterowanieLabel = new JLabel("Wykryto sterowanie: ");
		add(wykrytoSterowanieLabel,border);
		
		//x1 y0

		
		
		//x0 y1
		border.gridx=0;
		border.gridy=1;
		zadeklarowaneSterowanieLabel = new JLabel("Zamien sterowanie:");
		add(zadeklarowaneSterowanieLabel,border);
		
		
		//x1 y1
		border.gridx=1;
		border.gridy=1;
		sterowanie = new JComboBox<>(S);
		int index=0;
		
		if(parent.getControls().isType(ControlTypes.FANUC))
			index=1;
		else if(parent.getControls().isType(ControlTypes.OKUMA))
			index=2;
		sterowanie.setSelectedIndex(index);
		sterowanie.addActionListener(this);
		
		add(sterowanie,border);
		
		//x2 y1
		border.gridx=2;
		border.gridy=1;
		strzalka = new JLabel("--->");
		add(strzalka,border);
		
		
		//x3 y1
		border.gridx=3;
		border.gridy=1;
		noweSterowanie = new JComboBox<>(new String[] {"Sinumeric","Fanuc"});
		noweSterowanie.setSelectedIndex(0);
		add(noweSterowanie,border);
		
		//y2 x1
		border.gridx=1;
		border.gridy=2;
		konwertuj = new JButton("Konwertuj");
		konwertuj.addActionListener(this);
		konwertuj.setToolTipText("Modyfikuje kod w edytorze w taki sposób aby wykonywa³ siê na maszynie z podanym sterowaniem");
		add(konwertuj,border);
		
		//x2 y2
		border.gridx=2;
		border.gridy=2;
		cofnij = new JButton("Cofnij");
		cofnij.addActionListener(this);
		add(cofnij,border);
		
		
		this.sterowanie.setSelectedIndex(1);
	}
	
	

	
	
	
	
	
	
	
	
	void covertFanucToOkuma()
	{

		if(parent.analyzeProgram())
		{
			Function f;
			for(int i =0; i<parent.getListLength(); i++)
			{
				f = parent.getfunction(i);
				
				
				//macro
				if(f.getMacro()!=null)
				{
					
					if(f.getMacro().contains("%"))
					{
						String newMacro = f.getMacro();
						if(newMacro.length()==1)
						{
							parent.deleteFunctionFromList(i);
							i--;
							continue;
						}
						else
						{
							if(newMacro.charAt(0)=='%')
							{
								newMacro = newMacro.substring(1);
								parent.repleaceFunction(new Function(newMacro,new SterowanieOkuma()), i);
								
							}						
							continue;				
						}
					}
				}
				
				
				
				
				//H 
				if(!f.getH().equals("0"))
				{
					f.setH("0");
				}
				//D
				if(!f.getD().equals("0"))
				{
					
					f.setD("0");	
				}
	
				//m function
				if(f.getMFunctin() == 12)
				{
					
					f.setMFunction(51);
				}
				//gwintowanie sztywne
				else if(f.getMFunctin() == 29)
				
				{parent.deleteFunctionFromList(i);
				i--;
				continue;
				}

				else if(f.getMFunctin()==41)
				{
					f.setMFunction(279);
				}
				else if(f.getMFunctin()==43)
				{
					f.setMFunction(278);
				}
				else if(f.getMFunctin()==130 || f.getMFunctin()==128 || f.getMFunctin()==127)
				{
					f.setMFunction(60);
				}
				else if(f.getMFunctin()==99)
					f.setMFunction(30);
		
				
				//g function
				Integer[] functionType=f.getFunctionType().toArray(new Integer[0]);
				for(int k=0; k<functionType.length;k++)
				{
					
					//postój warunkowy 
					if(functionType[k]==4)
					{
						if(f.getPoint().getX()!=null)
						{	
							parent.repleaceFunction(new Function("G4 P"+f.getPoint().getX(),new SterowanieOkuma()), i);

						}
						
						
					}
					
					//pozycjonowanie palety do wymiany - niepotrzebne na okumie
					else if(functionType[k]==28 || functionType[k]==30)
					{
						parent.deleteFunctionFromList(i);
						i--;
					}
					
					
					
					
					
					//cykle
					else if(functionType[k] == 98 || functionType[k] == 99)
						functionType[k]=-2;
					
					
					else if((functionType[k] >80 && functionType[k]<90) ||  functionType[k]==76 || functionType[k]==73 )
					{
						f.setMFunction(53);
						Function temp = parent.getfunction(i-1);
						
						
						if(functionType[k] == 82 && f.getP()!=null)
						{
							float temporary = Float.parseFloat(f.getP());
							temporary/=1000;
							f.setP(Float.toString(temporary));
						}
					
						
						if(temp.getPoint().getZ()!=null)
						{
							
							for(int p=0;p<temp.getFunctionType().size();p++)
							{
								
								if(temp.getFunctionType().get(p)  == -1)
								{
								
									temp.getFunctionType().set(p, 71); 
									break;
								}
							
							
							}
						}
						else
						{
							try 
							{
								
								parent.addFunctionToList(new Function("G71 Z"+ temp.getCircle().get("R"),new SterowanieFanuc()),i-1);
								i++; 
							}
							catch (NullPointerException E)
							{
								
								parent.addFunctionToList(new Function("G71 Z350", new SterowanieFanuc()),i-1);
								i++;
							}
					
						}
						
						
					
						
						if(functionType[k]==76)
						{
							
							
							f.setCircle('J',f.getQ());
							f.setQ(null);
							
						}
						
					}
					
					//bazy
					else if(functionType[k] >=54 &&functionType[k]<=59)
					{
						f.setGFunction(k, 15);
						
						if(f.getP()!=null)
						{
							f.setH(f.getP());
							f.setP(null);
						}
						else
							f.setH("1");
						
						
						parent.addFunctionToList(new Function("G56 HA DA",new SterowanieOkuma()), i+1);
							i++;
	
					}
					else if(functionType[k]==43)
					{
						f.setGFunction(k, -2);
					}
													
				}	
			}
			
			
			
			parent.write();
			parent.getWind().setControls(new SterowanieOkuma());
			Wind.log.writeInfoLog("Konwertuj Fanuc->Okuma - wykonano", Konwertuj.class.getSimpleName());
			parent.writelog("Zmieniono sterowanie wraz z kodem na Okuma");
		}
		
		

	}
	
	void covertSinumericToFanuc()
	{
		int toolNumber =-1;
		int activeToolNumber=-1;
		String activeD="0";
		
		if(parent.analyzeProgram())
		{
			
		
			Function f;
			for(int i =0; i<parent.getListLength(); i++)
			{
				
				f = parent.getfunction(i);
				
				if(f.getMacro()!=null)
				{
					String temp = f.toString();
					
					if(temp.contains("%MPF"))
					{	
							parent.repleaceFunction(new Function("%", new SterowanieFanuc()), i);
							temp=temp.replace("%MPF","O");
							parent.addFunctionToList(new Function(temp, new SterowanieFanuc()), i+1);
							
							continue;
					}
				}
							
				
				
				
					
				
				
			
				
				if(f.getToolNumber()!=-1)
					toolNumber=f.getToolNumber();
	
				//D
				if(!f.getD().equals("0"))
				{
					activeD = f.getD();
					f.setD("0");	
					
									
				}
				//H
				if(!f.getH().equals("0"))
				{
					
					f.setH("0");	
					
									
				}

	
				//m function
				if(f.getMFunctin()==6)
				{
					
					if(toolNumber!=-1)
						activeToolNumber = toolNumber;
					else
					{
						if(f.getToolNumber()!=-1)
						{
							activeToolNumber = f.getToolNumber();
						}
						else 
						{
						
							activeToolNumber =1;
						}
		
					}
				}


				//g function
				Integer[] functionType=f.getFunctionType().toArray(new Integer[0]);
				for(int k=0; k<functionType.length;k++)
				{
					
					
					
					//H 
					if(functionType[k] >=54 && functionType[k]<=57)
					{
						
						parent.addFunctionToList(new Function("G0 G43 H"+activeToolNumber+ " Z"+Wind.options.getSafeRetraction(),new SterowanieFanuc()), ++i);
						
						
					}
					
					
					else if(functionType[k]==41 || functionType[k]==42)
					{
						if(!activeD.equals("0")) 
							f.setD(activeD);
						else
							f.setD(Integer.toString(activeToolNumber));
					}
					
					
					
					else if((functionType[k] >80 && functionType[k]<90))
					{
						
					Float[] R=new Float[1];
						
					
					
						
						R[0]=f.getRcycleParam()[2];
						f.getPoint().setZ(f.getRcycleParam()[3]);
						
					
						
						if(functionType[k] == 82 )
						{
					
							f.setP(Float.toString(f.getRcycleParam()[4]*1000));
						}
						else if(functionType[k] == 83)
						{
							f.setQ(f.getRcycleParam()[5]);
							
							
							
							if(functionType[k] == 73)
								f.setGFunction(k, 83);
							
						}
						//TODO dopisac kod gwintowania sztywnego
						
						
						
						
						
						else if(functionType[k]==85)
						{
							
							float r = f.getRcycleParam()[0];
							float z = f.getPoint().getZ();
							float feed = f.getFeed();
							
							parent.repleaceFunction(new Function("G0 Z" + r, new SterowanieFanuc()), i);
							parent.addFunctionToList(new Function(("G1 Z" + z + " F" + feed),new SterowanieFanuc()), i+1);
							parent.addFunctionToList(new Function(("G1 Z" + r + " F" + feed),new SterowanieFanuc()), i+2);
							parent.addFunctionToList(new Function((" G0 Z" + Wind.options.getSafeRetraction()),new SterowanieFanuc()), i+3);
							
							i+=3;
							continue;
	
						}
						
						
						
						if(functionType[k]!=76)
						{
							//TODO tu chyba coœ zle, naprawic
							f.setCircle(R[0]);
							f.setControls(new SterowanieSinumeric());
						}
						f.setPoint(null);
					}
					
					//bazy
					else if(f.getP()!=null && functionType[k] >=54 &&functionType[k]<=59)
					{

							f.setH(f.getP());
							f.setP(null);
					}			
				}	
				
				
			}

			
			parent.getWind().setControls(new SterowanieFanuc());
			parent.write();
			Wind.log.writeInfoLog("Konwertuj sinumeric->fanuc - wykonano", Konwertuj.class.getSimpleName());
			parent.writelog("Zmieniono sterowanie wraz z kodem na Fanuc");
		}
		
		
		
		
		
		
	}
	void covertFanucToHitachiFanuc()
	{
		int toolNumber=0;
		if(parent.analyzeProgram())
		{
			Function f;
			for(int i =0; i<parent.getListLength(); i++)
			{
				f = parent.getfunction(i);
				
				
				//macro
				if(f.getMacro()!=null)
				{
					Pattern p = Pattern.compile("O\\d{4}");
					Matcher m = p.matcher(f.getMacro());
					
					if(m.find())
					{
						parent.addFunctionToList(new Function("%", new SterowanieFanuc()), 0);
						parent.addFunctionToList(new Function("%"), parent.getListLength());
						i++;
						continue;
					}
				}
				
				//toolNumber
				if(f.getToolNumber()!=-1)
					toolNumber=f.getToolNumber();
					
	
				//m function
				if(f.getMFunctin() ==8 )
				{
					
					f.setMFunction(47);
				}
				else if(f.getMFunctin()==29)
				{
					parent.deleteFunctionFromList(i);
					i--;
					continue;
				}
				
				else if(f.getMFunctin()==127)
				{
					f.setMFunction(61);
				}
				else if(f.getMFunctin()==128)
				{
					f.setMFunction(62);
				}
				else if(f.getMFunctin()==130)
				{
					f.setMFunction(60);
				}
				
				
				
				if(f.getBRotation()!=null)
				{
					parent.addFunctionToList(new Function("M79",new SterowanieFanuc()),i );
					parent.addFunctionToList(new Function("M78",new SterowanieFanuc()),i+2 );
					i++;
					i++;
					
					continue;
					
				}
				
				
			
				//g function
				Integer[] functionType=f.getFunctionType().toArray(new Integer[0]);
				for(int k=0; k<functionType.length;k++)
				{

					
					//bazy
					if(functionType[k] ==54 && f.getP()!=null)
					{
						f.setGFunction(k, 54);				
					
							f.setP(null);	
					}
				
				}		
			}

			parent.write();
			parent.getWind().setControls(new SterowanieFanuc());
			Wind.log.writeInfoLog("Konwertuj Fanuc-> Hitachi Fanuc - wykonano", Konwertuj.class.getSimpleName());
			parent.writelog("Zmieniono sterowanie wraz z kodem na Hitachi");
		}	
	}
	
	
	
	
	
	void covertOkumaToFanuc()
	{
		int toolNumber=0;
		int activeToolNumber =0;
		if(parent.analyzeProgram())
		{
			Function f;
			for(int i =0; i<parent.getListLength(); i++)
			{
				f = parent.getfunction(i);
				
				
				//macro
				if(f.getMacro()!=null)
				{
					Pattern p = Pattern.compile("O\\d{4}");
					Matcher m = p.matcher(f.getMacro());
					
					if(m.find())
					{
						parent.addFunctionToList(new Function("%", new SterowanieFanuc()), 0);
						parent.addFunctionToList(new Function("%"), parent.getListLength());
						i++;
						continue;
					}
				}
				
				//toolNumber
				if(f.getToolNumber()!=-1)
					toolNumber=f.getToolNumber();
				
				
	
				//m function
				if(f.getMFunctin() ==51 || f.getMFunctin() ==52)
				{
					
					f.setMFunction(12);
				}

				else if(f.getMFunctin()==6)
				{
					activeToolNumber=toolNumber;
					toolNumber=-1;
				}
				else if(f.getMFunctin()==279)
				{
					f.setMFunction(41);
				}
				else if(f.getMFunctin()==278)
				{
					f.setMFunction(43);
				}
				else if(f.getMFunctin()==53 || f.getMFunctin()==52 || f.getMFunctin()==54)
				{
					f.setMFunction(-1);
				}
				else if(f.getMFunctin()==60)
				{
					parent.repleaceFunction(new Function("G28 G30 B0", new SterowanieFanuc()), i);
					parent.addFunctionToList(new Function("G91 G30 B0 Z0", new SterowanieFanuc()),++i);
					parent.addFunctionToList(new Function("M130", new SterowanieFanuc()),++i);
					continue;
				}
				
			
				//g function
				Integer[] functionType=f.getFunctionType().toArray(new Integer[0]);
				for(int k=0; k<functionType.length;k++)
				{
					//postój warunkowy 
					if(functionType[k]==4)
					{
						if(f.getP()!=null)
						{	
							parent.repleaceFunction(new Function("G4 X"+f.getP(),new SterowanieOkuma()), i);
							
							
						}
						else 
						{
							parent.repleaceFunction(new Function("G4 X2",new SterowanieOkuma()), i);
							
						}
						
					}
					else if(functionType[k]==71)
					{
						f.setGFunction(k, 0);			
					}
					
					else if((functionType[k] >80 && functionType[k]<90) ||  functionType[k]==76 || functionType[k]==73 )
					{
						int l=0;
						while(l<functionType.length-1)
						{
							if(functionType[l]!=-1)
							{
								++l;								
							}	
							else 
							{
								functionType[l]=98;
								break;
							}
						}
						
						if(functionType[k] == 82 && f.getP()!=null)
						{
							float temporary = Float.parseFloat(f.getP());
							temporary*=1000;
							f.setP(Float.toString(temporary));
						}
			
						if(functionType[k]==76)
						{
							float q=0f;
							
							if(f.getCircle().get('I')!=null)							
								q+=Math.pow(f.getCircle().get('I'), 2);
		
							if(f.getCircle().get('J')!=null)
								q+=Math.pow(f.getCircle().get('J'), 2);
								
							f.setQ((float)Math.sqrt(q));
						}	
					}
					
					//bazy
					else if(functionType[k] ==15)
					{
						f.setGFunction(k, 54);
						
						if(f.getH()!=null)
						{	
							f.setP(f.getH());
							f.setH("0");
						}

			
					}
					else if(functionType[k] == 56)
					{
						f.setGFunction(k, 43);
						
						int l=0;
						while(l<functionType.length)
						{
							l++;
							if(functionType[l]==-1)
							{
								f.setGFunction(l, 0);
								f.setPoint(new Point((float)Wind.options.getSafeRetraction()));
								break;
							}
						}	
						f.setH(Integer.toString(activeToolNumber));
						f.setD("0");
						
					}
					else if(functionType[k] == 41 || functionType[k] == 42)
						f.setD(Integer.toString(activeToolNumber));
				}		
			}

			parent.write();
			parent.getWind().setControls(new SterowanieFanuc());
			Wind.log.writeInfoLog("Konwertuj Okuma->Fanuc - wykonano", Konwertuj.class.getSimpleName());
			parent.writelog("Zmieniono sterowanie wraz z kodem na Okuma");
		}
		
		
		
		
		
		
		
		
	}
	
	void covertFanucToSinumeric()
	{
		int activeToolNumber=-1;
		
		if(parent.analyzeProgram())
		{
			Function f;
			for(int i =0; i<parent.getListLength(); i++)
			{
								
				f = parent.getfunction(i);
				if(f.getMacro()!=null)
				{
					String temp = f.toString();
					
					if(temp.contains("O") && i>0 && parent.getfunction(i-1).toString().contains("%"))
					{
							temp=temp.replace("O", "%MPF");
							parent.repleaceFunction(new Function(temp, new SterowanieSinumeric()), i-1);
							parent.deleteFunctionFromList(i);
							i--;
							continue;
					}
				}
				
				

				//H 
				if(!f.getH().equals("0"))
				{
					f.setH("0");
				}
				//D
				if(!f.getD().equals("0"))
				{
					
					f.setD("0");	
									
				}
				
				
				if(f.getToolNumber()!=-1)
					activeToolNumber=f.getToolNumber();
				
				
				
	
				//m function
				if(f.getMFunctin() == 12)
				{
					
					f.setMFunction(8);
				}
				//gwintowanie sztywne
				else if(f.getMFunctin() == 29)
				
				{parent.deleteFunctionFromList(i);
				i--;
				continue;
				}
				else if(f.getMFunctin()==130 || f.getMFunctin()==128 || f.getMFunctin()==127)
				{
					parent.deleteFunctionFromList(i);
					i--;
				}
				else if(f.getMFunctin()==6)
				{
					parent.addFunctionToList(new Function("D"+activeToolNumber), i+1);
					i++;
					continue;
				}
				
				if(f.getFeed()>8000)
					f.setFeed(8000);
		
				
				//g function
				Integer[] functionType=f.getFunctionType().toArray(new Integer[0]);
				for(int k=0; k<functionType.length;k++)
				{
					
					
					
					//pozycjonowanie palety do wymiany - niepotrzebne na hapce
					if(functionType[k]==28 || functionType[k]==30)
					{
						parent.deleteFunctionFromList(i);
						i--;
						
					}
					
					//cykle
					else if(functionType[k] == 98 || functionType[k] == 99||functionType[k]==43)
						functionType[k]=-2;
			
					
					else if((functionType[k] >80 && functionType[k]<90) ||  functionType[k]==76 || functionType[k]==73  )
					{
						
					
						
					
					
					//TODO sprawdzic czy dobrze
						
						f.setRCycleAray("R2", f.getRcycleParam()[0]);
						f.setRCycleAray("R3", f.getPoint().getZ());
						f.setRCycleAray("R10", (float)Wind.options.getSafeRetraction());

					
						
						if(functionType[k] == 82 &&  f.getP()!=null)
						{
							
							float temporary = Float.parseFloat(f.getP());	
							f.setRCycleAray("R4", temporary/1000);
							
						}
						else if(functionType[k] == 83 ||functionType[k] == 73)
						{
							f.setRCycleAray("R0", 1f);
							f.setRCycleAray("R1", f.getQ()*2);
							f.setRCycleAray("R4", 0.5f);
							f.setRCycleAray("R5", f.getQ());
			
							
							
							
							if(functionType[k] == 73)
								f.setGFunction(k, 83);
							
						}
						else if(functionType[k]==84)
						{
							f.setRCycleAray("R4", 0f);		
						}

						else if(functionType[k]==76)
						{
							
							float r = f.getRcycleParam()[0];
							float z = f.getPoint().getZ();
							float feed = f.getFeed();
							Float q = null;
							if(f.getQ()!=null)
								q = f.getQ();
							else q=0.1f;
							
							parent.repleaceFunction(new Function("G0 Z" + r, new SterowanieSinumeric()), i);
							parent.addFunctionToList(new Function(("G1 Z" + z + " F" + feed),new SterowanieSinumeric()), i+1);
							parent.addFunctionToList(new Function(("M5 "),new SterowanieSinumeric()), i+2);
							parent.addFunctionToList(new Function(("M19 "),new SterowanieSinumeric()), i+3);
							parent.addFunctionToList(new Function(("G4 X2 "),new SterowanieSinumeric()), i+4);
							parent.addFunctionToList(new Function(("G91 G0 X"+q),new SterowanieSinumeric()), i+5);
							parent.addFunctionToList(new Function(("G90 G0 Z" + r),new SterowanieSinumeric()), i+6);
							
							i+=6;
							continue;
							
							
						}
						else if(functionType[k]==85)
						{
							
							float r = f.getRcycleParam()[0];
							float z = f.getPoint().getZ();
							float feed = f.getFeed();
							
							parent.repleaceFunction(new Function("G0 Z" + r, new SterowanieSinumeric()), i);
							parent.addFunctionToList(new Function(("G1 Z" + z + " F" + feed),new SterowanieSinumeric()), i+1);
							parent.addFunctionToList(new Function(("G1 Z" + r + " F" + feed),new SterowanieSinumeric()), i+2);
							parent.addFunctionToList(new Function((" G0 Z" + Wind.options.getSafeRetraction()),new SterowanieSinumeric()), i+3);
							
							i+=3;
							continue;
	
							
						}
						
						
						
						if(functionType[k]!=76)
						{
							
							f.setControls(new SterowanieSinumeric());
						}
						f.setPoint(null);
					}
					
					//bazy
					else if(f.getP()!=null && functionType[k] >=54 &&functionType[k]<=59)
					{
							f.setH(f.getP());
							f.setP(null);		
					}
				}	
				
				//P
				if(f.getP()!=null)
				{
					
					f.setP(null);
				}
				//Q
				if(f.getQ()!=null)
					f.setQ(null);
			}
			
			
			
			parent.getWind().setControls(new SterowanieSinumeric());
			parent.write();
			Wind.log.writeInfoLog("Konwertuj Fanuc-> Sinumeric - wykonano", Konwertuj.class.getSimpleName());
			parent.writelog("Zmieniono sterowanie wraz z kodem na Sinumeric");
		}	
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o=e.getSource();
		
		if(o==konwertuj)
		{
			boolean isOk = false;
			String s=(String) sterowanie.getSelectedItem();
			if(!s.substring(0,1).equals(wykrytoSterowanie.getText().substring(0,1)))
			{	
				
				int pytanie = JOptionPane.showConfirmDialog(w, "Zadeklarowane sterowanie jest inne ni¿ wykryte, kontyuacja mo¿e nadpisaæ istotne elementy programu. Zmien sterowanie w menu opcje lub kontynuj je¿eli jestes pewny, ¿e sterowanie jest poprawne", "Uwaga", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE ,UIManager.getIcon("OptionPane.warningIcon") );
				if(pytanie == JOptionPane.YES_OPTION)
				{	
					isOk=true;
					
				}
				else isOk = false;
			}
			else isOk=true;
			
			if(isOk)
			{
				if((this.sterowanie.getSelectedItem().equals("Fanuc")) && (this.noweSterowanie.getSelectedItem().equals("Okuma")))
				{
					covertFanucToOkuma();
					this.cofnij.doClick();
		
				}
				else if(this.sterowanie.getSelectedItem().equals("Fanuc") && this.noweSterowanie.getSelectedItem().equals("Sinumeric"))
				{
					covertFanucToSinumeric();
					this.cofnij.doClick();
				}
				else if(this.sterowanie.getSelectedItem().equals("Sinumeric") && this.noweSterowanie.getSelectedItem().equals("Fanuc"))
				{	
					covertSinumericToFanuc();
					this.cofnij.doClick();	
				}
				else if(this.sterowanie.getSelectedItem().equals("Okuma") && this.noweSterowanie.getSelectedItem().equals("Fanuc"))
				{	
					covertOkumaToFanuc();
					this.cofnij.doClick();	
				}
				else if(this.sterowanie.getSelectedItem().equals("Fanuc") && this.noweSterowanie.getSelectedItem().equals("Hitachi Fanuc"))
				{
					covertFanucToHitachiFanuc();
					this.cofnij.doClick();
				}
	
			}
			
			
			
		}
		else if(o==sterowanie)
		{
			if(sterowanie.getSelectedItem().equals("Fanuc"))
			{
				noweSterowanie.removeAllItems();
				noweSterowanie.addItem("Sinumeric");
				noweSterowanie.addItem("Okuma");
				noweSterowanie.addItem("Hitachi Fanuc");
			}
			else if(sterowanie.getSelectedItem().equals("Sinumeric"))
			{
				noweSterowanie.removeAllItems();
				noweSterowanie.addItem("Fanuc");
				
			}
			else if(sterowanie.getSelectedItem().equals("Okuma"))
			{
				noweSterowanie.removeAllItems();
				noweSterowanie.addItem("Fanuc");
				
			}

		}
		
		
		
		
		else if(o==cofnij)
		{
			w.k=null;
			this.dispose();
			
		}
		
	}

}
