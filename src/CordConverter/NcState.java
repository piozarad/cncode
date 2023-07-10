package CordConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import BasicControls.Sterowanie;
import GCodeGroup.ActiveGCodes;
import NcStateErrors.BadNcState;

public class NcState {

	private NcState previousNcState;
	private Sterowanie sterowanie;
	private Point actualPoint;
	private Point destinationPoint;
	private float rotationB;
	private float rotationA;
	private float rotationC;
	private String h;
	private String d;
	private int actualToolNumber;
	private int nextToolNumber;
	private float feed;
	private int sprindleSpeed;
	private Float[] iJkR;
	private Map<String,Float> rCycleParam;
	private float q;
	private String p;
	private int blockNumber;
	private boolean bPreparation;
	private boolean basePointAfterToolChangeDeclared;
	private boolean toolPreparation;

	private Set<Integer> mFunctionActive;
	private ActiveGCodes activeGCodes;

	
	public ActiveGCodes getActiveGCodes() {
		return activeGCodes;
	}

	public void setActiveGCodes(ActiveGCodes activeGCodes) {
		this.activeGCodes = activeGCodes;
	}

	public NcState(Sterowanie sterowanie)
	{	
		initializeDefaultParameters();
		this.sterowanie=sterowanie;
	}

	//copy constructor
	public NcState(NcState oldState)
	{
		initializeContainers();
		this.sterowanie=oldState.sterowanie;
		this.feed=oldState.getFeed();
		this.sprindleSpeed=oldState.getSprindleSpeed();
		this.actualPoint=oldState.getActualPoint();
		this.destinationPoint=oldState.getDestinationPoint();
		this.actualToolNumber=oldState.actualToolNumber;
		this.nextToolNumber=oldState.nextToolNumber;
		this.blockNumber=oldState.blockNumber;
		this.iJkR = new Float[4];
		System.arraycopy(oldState.iJkR, 0, this.iJkR, 0, oldState.iJkR.length);
		this.d=oldState.d;
		this.h=oldState.h;
		this.p=oldState.p;
		this.q=oldState.q;
		this.mFunctionActive.addAll(oldState.mFunctionActive);
		bPreparation = false;
		basePointAfterToolChangeDeclared = false;
		this.toolPreparation=oldState.toolPreparation;
		
		this.activeGCodes=  new ActiveGCodes();
		activeGCodes.updateGroup(oldState.getActiveGCodes().getAll());
		
		this.rCycleParam.putAll((oldState.rCycleParam));
		this.rotationA=oldState.getRotationA();
		this.rotationB=oldState.getRotationB();
		this.rotationC=oldState.getRotationC();
	}
	
	private void initializeContainers()
	{
		this.mFunctionActive = new HashSet<>();
		this.rCycleParam = new HashMap<>();
	}	
	
	/**
	 * initializes default values of all internal Nc parameters
	 */
	private void initializeDefaultParameters()
	{
	
		this.setPreviousNcState(null);
		this.actualPoint= new Point();
		this.actualToolNumber=0;
		this.toolPreparation=true;
		this.blockNumber=0;
		this.d="0";
		this.feed=-1f;
		this.activeGCodes = new ActiveGCodes();
		activeGCodes.addNewCode(17);
		activeGCodes.addNewCode(80);
		activeGCodes.addNewCode(0);
		activeGCodes.addNewCode(40);
		activeGCodes.addNewCode(53);
		this.h="0";
		this.iJkR=new Float[4];
		this.mFunctionActive = new HashSet<>();
		this.nextToolNumber = -1;
		this.p="-1";
		this.destinationPoint = new Point();
		this.q=0.0f;
		this.rCycleParam = new HashMap<>();
		this.rotationA = 0.f;
		this.rotationB = 0.f;		
		this.rotationC = 0.f;
		this.sterowanie=null;
	}
	

	public void updateNcState(Function function)
	{
		this.previousNcState = new NcState(this);

		updatePosition(function);
		setBlockNumber(function.getBlock());
		setActiveToolCorectors(function);
		setActiveFeed(function);
		setSprinleSpeed(function);
		setToolNumber(function);
		updateABCAxisPositions(function);
		updateIJK(function);
		updateRCycleMap(function);
		activeGCodes.updateGroup(function.getFunctionType());
		updateP(function);
		updateQ(function);
		updatebPreparation(function);
		updateMFunction(function);
		
		if(!basePointAfterToolChangeDeclared)
			updateBasePointAfterToolChangeDeclared(function);
	}
	
	/*
	 * flaga do kontroli czy baza obróbcza zosta³¹ zmieniona po zmianie narzêdzia
	 */
	private void updateBasePointAfterToolChangeDeclared(Function f) {
		for(int code: f.getFunctionType())
		{
			for(int base: this.sterowanie.baseDefinitionCode())
			{
				if(code==base)
				{
					this.basePointAfterToolChangeDeclared = true;
				}
			}
		}
	}

	public boolean isBaseDeclared()
	{
		return basePointAfterToolChangeDeclared;
	}

	private void updatebPreparation(Function function) {
		if(function.getBRotation()!=null)
			this.bPreparation=true;		
	}

	private void updateQ(Function function) {
		if(function.getQ()!=null)
		{
			setQ(function.getQ());
		}
	}
	
	private void updateP(Function function) {
		if(function.getP()!=null)
		{
			setP(function.getP());
		}
	}

	private void updateRCycleMap(Function function) {
		if(function.getRArray()!=null)
		{
			Map<String,Float> map = function.getRArray();
			
			for(String s : map.keySet())
			{
				
				this.rCycleParam.put(s, map.get(s));
			}	
		}	
	}

	private void updateMFunction(Function function) {
		removeModalMCodes();
		if(function.getMFunctin()!=-1)
		{		
			mFunctionActive.add(function.getMFunctin());

			switch(function.getMFunctin())
			{
			case 6:
				this.activeGCodes.resetGroup(7);
				this.activeGCodes.resetGroup(1);
				this.activeGCodes.addNewCode(40);
				this.setFeed(-1);
				toolChangeMCodes();
				this.activeGCodes.addNewCode(0);
				this.bPreparation=false;
				basePointAfterToolChangeDeclared = false;
				this.h="0";
				this.d="0";
				Arrays.fill(iJkR, null);
	
			break;
			case 9:
				this.mFunctionActive.remove(this.sterowanie.chlodzeniePrzezDysze());
				this.mFunctionActive.remove(this.sterowanie.chlodzeniePrzezSufit());
				this.mFunctionActive.remove(this.sterowanie.chlodzeniePrzezWrzeciono());
				break;
			case 5:
			case 19:
				this.mFunctionActive.remove(3);
				this.mFunctionActive.remove(4);
				break;
			case 3:
				this.mFunctionActive.remove(4);
				this.mFunctionActive.remove(5);
				break;
			case 4:
				this.mFunctionActive.remove(3);
				this.mFunctionActive.remove(5);
				break;
			}
		}
		else this.mFunctionActive.remove(6);
		
		
		
		if(this.mFunctionActive.contains(sterowanie.chlodzeniePrzezDysze()) || this.mFunctionActive.contains(sterowanie.chlodzeniePrzezSufit())|| this.mFunctionActive.contains(sterowanie.chlodzeniePrzezWrzeciono()))
			this.mFunctionActive.remove(9);
	}

	private void removeModalMCodes() {
		this.mFunctionActive.remove(0);
		this.mFunctionActive.remove(1);
		this.mFunctionActive.remove(6);
		
	}

	private void toolChangeMCodes()
	{
		this.mFunctionActive.remove(this.sterowanie.chlodzeniePrzezDysze());
		this.mFunctionActive.remove(this.sterowanie.chlodzeniePrzezSufit());
		this.mFunctionActive.remove(this.sterowanie.chlodzeniePrzezWrzeciono());
		this.mFunctionActive.add(9);
		this.mFunctionActive.remove(3);
		this.mFunctionActive.remove(4);
		this.mFunctionActive.add(5);
	}

	private void updateIJK(Function function) {
		if(!function.getCircle().isEmpty())
		{
			if(function.getCircle().containsKey('I'))
			{
				this.iJkR[0]=function.getCircle().get('I');
			}
			if(function.getCircle().containsKey('J'))
			{
				this.iJkR[1]=function.getCircle().get('J');
			}
			if(function.getCircle().containsKey('K'))
			{
				this.iJkR[2]=function.getCircle().get('K');
			}
			if(function.getCircle().containsKey('R'))
			{
				this.iJkR[3]=function.getCircle().get('R');
			}
		}
	}


	private void updateABCAxisPositions(Function function) {
		if(function.getARotation()!=null)
		{
			setRotationA(function.getARotation());
		}
		if(function.getBRotation()!=null)
		{
			setRotationA(function.getBRotation());
		}
		if(function.getCRotation()!=null)
		{
			setRotationA(function.getCRotation());
		}
	}

	public boolean getToolPreparation()
	{
		return this.toolPreparation;
	}

	private void setToolNumber(Function function) {
		
			if(function.getMFunctin()==6)
			{
				if(function.getToolNumber()==-1)
				{
					setActualToolNumber(this.nextToolNumber);	
				}
				else
				{
				setActualToolNumber(function.getToolNumber());
				setNextToolNumber(-1);
				}
				this.toolPreparation=false;
			}
		
			else if(function.getToolNumber()!=-1)
			{
				this.toolPreparation=true;
				setNextToolNumber(function.getToolNumber());
			}	
	}


	private void setSprinleSpeed(Function function) {
		if(function.getSpeed()!=-1)
			setSprindleSpeed(function.getSpeed());
		
	}


	private void setActiveFeed(Function function) {
		if(function.getFeed()>0)	
			setFeed(function.getFeed());
	}


	private void setActiveToolCorectors(Function function) 
	{
		if(!function.getD().equals("0"))
		{
			setD(function.getD());
		}
		
		if(!function.getH().equals("0"))
		{
			setH(function.getH());
		}
	}

	private boolean checkState(BadNcState state)
	{	
		return false;
	}
	
	public Point getActualPoint() {
		return actualPoint;
	}

	public boolean isBPreparationSet()
	{
		return this.bPreparation;
	}

	public void setDestinationPoint(Point destinationPoint) {
		this.destinationPoint = destinationPoint;
	}

	public Point getDestinationPoint() {
		return destinationPoint;
	}

	public void setActualPoint(Point actualPoint) {
		this.actualPoint = actualPoint;
	}

	public float getRotationB() {
		return rotationB;
	}

	public void setRotationB(float rotationB) {
		this.rotationB = rotationB;
	}

	public float getRotationA() {
		return rotationA;
	}

	public void setRotationA(float rotationA) {
		this.rotationA = rotationA;
	}

	public float getRotationC() {
		return rotationC;
	}

	public void setRotationC(float rotationC) {
		this.rotationC = rotationC;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}


	public String getD() {
		return d;
	}


	public void setD(String d) {
		this.d = d;
	}


	public int getActualToolNumber() {
		return actualToolNumber;
	}


	public void setActualToolNumber(int actualToolNumber) {
		this.actualToolNumber = actualToolNumber;
	}


	public int getNextToolNumber() {
		return nextToolNumber;
	}


	public void setNextToolNumber(int nextToolNumber) {
		this.nextToolNumber = nextToolNumber;
	}


	public float getFeed() {
		return feed;
	}

	public void setFeed(float feed) {
		this.feed = feed;
	}

	public int getSprindleSpeed() {
		return sprindleSpeed;
	}

	public void setSprindleSpeed(int sprindleSpeed) {
		this.sprindleSpeed = sprindleSpeed;
	}


/**
 * 
 * @return returns array of circle parameter float values
 * [0] -I
 * [1] -J
 * [2] -K
 * [3] -R
 */
	public Float[] getiJk() {
		return iJkR;
	}

	public void setiJk(Float[] iJkR) {
		System.arraycopy(iJkR, 0,this.iJkR, 0, this.iJkR.length);
	
	}
	
	public void setSterowanie(Sterowanie sterowanie) 
	{
	this.sterowanie=sterowanie;
	}

	public Sterowanie getControls()
	{
		return this.sterowanie;
	}


	public Map<String, Float> getrCycleParam() {
		return rCycleParam;
	}




	public void setrCycleParam(Map<String, Float> rCycleParam) {
		this.rCycleParam = rCycleParam;
	}




	public float getQ() {
		return q;
	}




	public void setQ(float q) {
		this.q = q;
	}




	public String getP() {
		return p;
	}




	public void setP(String p) {
		this.p = p;
	}




	public int getBlockNumber() {
		return blockNumber;
	}




	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}




	public Set<Integer> getmFunctionActive() {
		return mFunctionActive;
	}
	




	public void setmFunctionActive(int mFunctionActive) {
		this.mFunctionActive.add(mFunctionActive);
	}



	private void updatePosition(Function f)
	{
		if(f.getPoint()!=null)
		{
			this.actualPoint = this.destinationPoint.clone();
			this.destinationPoint.updatePoint(f.getPoint());
		}
	}

	private void updateGCodeTable(Function f)
	{	
		if(f.getFunctionType().get(0)!=-1)
				this.activeGCodes.updateGroup(f.getFunctionType());	
	}

	public NcState getPreviousNcState() {
		return previousNcState;
	}


	public void setPreviousNcState(NcState previousNcState) {
		this.previousNcState = previousNcState;
	}	
	
	
	@Override
	public String toString()
	{
		return "Previous point " + this.actualPoint +
		"\nActual Point " +this.destinationPoint +
		"\nTool number:" +this.actualToolNumber +
		" Next toolNumber:" + this.nextToolNumber+
		"\nD:" + this.d + " H:" + this.h +
		"\nGCodes: "+ this.activeGCodes +
		"\nMcodes: "+this.mFunctionActive +
		"\nCircle parameters:" +Arrays.toString(this.iJkR) +
		"\nFixed cycle parameters: "+ this.rCycleParam +
		"\nP="+this.p + " Q="+this.q +
		"\nA="+this.rotationA+
		"\nB="+this.rotationB+		
		"\nC="+this.rotationC+
		"\nControls: "+this.sterowanie +
		"\nFlags: BaseDeclared=" + this.isBaseDeclared() + " BDeclared="+this.isBPreparationSet();
	}
	
	
	
}
