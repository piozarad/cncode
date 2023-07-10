package CordConverter;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ComboBoxModel;


public class Options implements Serializable {

	private static final long serialVersionUID = -9118195754552896187L;
	
	private int safeRetraction;
	private int maxZAxisValue;
	private float circleTolerance;
	private boolean toolPreperationCheck;
	private boolean g90Check;
	private boolean bRotationCheck;
	private int toolChangeTime;
	private double feedRateReductionOnCircle;
	private int effectiveWorkTime;
	private int maxFeed;
	private int ABCAxisFeedRate;
	private int partFixureTime;
	private int maxToolNumber;
	private int machineComboSelectedItem;
	private List<Machine> machineList;
	
	
	
	public Options()
	{
		machineList= new LinkedList<>();
		setDefaultValues();
		
	}
	
	
	public int getMaxZAxisValue()
	{
		return this.maxZAxisValue;
	}
	public void setMaxZAxisValue(int maxZValue)
	{
		this.maxZAxisValue=maxZValue;
	}
	
	public int getSafeRetraction() {
		return safeRetraction;
	}
	public void setSafeRetraction(int safeRetraction) {
		this.safeRetraction = safeRetraction;
	}
	public float getCircleTolerance() {
		return circleTolerance;
	}
	public void setCircleTolerance(float circleTolerance) {
		this.circleTolerance = circleTolerance;
	}
	public boolean isToolPreperationCheck() {
		return toolPreperationCheck;
	}
	public void setToolPreperationCheck(boolean toolPreperationCheck) {
		this.toolPreperationCheck = toolPreperationCheck;
	}
	public boolean isG90Check() {
		return g90Check;
	}
	public void setG90Check(boolean g90Check) {
		this.g90Check = g90Check;
	}
	public boolean isbRotationCheck() {
		return bRotationCheck;
	}
	public void setbRotationCheck(boolean bRotationCheck) {
		this.bRotationCheck = bRotationCheck;
	}
	public int getToolChangeTime() {
		return toolChangeTime;
	}
	public void setToolChangeTime(int toolChangeTime) {
		this.toolChangeTime = toolChangeTime;
	}
	public double getFeedRateReductionOnCircle() {
		return feedRateReductionOnCircle;
	}
	public void setFeedRateReductionOnCircle(double feedRateReductionOnCircle) {
		this.feedRateReductionOnCircle = feedRateReductionOnCircle;
	}
	public int getEffectiveWorkTime() {
		return effectiveWorkTime;
	}
	public void setEffectiveWorkTime(int effectiveWorkTime) {
		this.effectiveWorkTime = effectiveWorkTime;
	}
	public int getMaxFeed() {
		return maxFeed;
	}
	public void setMaxFeed(int maxFeed) {
		this.maxFeed = maxFeed;
	}
	public int getABCAxisFeedRate() {
		return ABCAxisFeedRate;
	}
	public void setABCAxisFeedRate(int aBCAxisFeedRate) {
		ABCAxisFeedRate = aBCAxisFeedRate;
	}
	public int getPartFixureTime() {
		return partFixureTime;
	}
	public void setPartFixureTime(int toolFixureTime) {
		this.partFixureTime = toolFixureTime;
	}
	public int getMaxToolNumber() {
		return maxToolNumber;
	}
	public void setMaxToolNumber(int maxToolNumber) {
		this.maxToolNumber = maxToolNumber;
	}
	
	public Machine[] getMachineArray() {
		Machine[] result = new Machine[this.machineList.size()];
		return machineList.toArray(result);
	}
	public void setMachineList(ComboBoxModel<Machine> model)
	{
		this.machineList.clear();
		for(int i=0;i<model.getSize();i++)
		{
			machineList.add(model.getElementAt(i));
		}
	}
	
	public int getMachineComboSelectedItem()
	{
		return this.machineComboSelectedItem;
	}
	
	public void setMachineComboSelectedItem(int index)
	{
		machineComboSelectedItem=index;
	}
	
	private void setDefaultValues()
	{
		 safeRetraction=400;
		 maxZAxisValue=500;
		 circleTolerance=0.02f;
		 toolPreperationCheck=true;
		 g90Check=true;
		 bRotationCheck=true;
		 toolChangeTime=3;
		 feedRateReductionOnCircle=0.3;
		 effectiveWorkTime=390;
		 maxFeed=8000;
		 ABCAxisFeedRate=5000;
		 partFixureTime=120;
		 maxToolNumber=40;
		 machineComboSelectedItem=0;
		 this.machineList.add(new Machine("Domyœlna maszyna"));

	}
	
	
	
	
	@Override 
	public String toString()
	{
		return 	"safe retraction: " +safeRetraction + "\n "
		+ "max Z axis"+maxZAxisValue + "\n "
		+ "circle tolerance: " +circleTolerance + "\n "
		+ "toolPrep: " +toolPreperationCheck+ "\n "
		+ "g90 check:"+ g90Check+ "\n "
		+ "b rotation check: "+ bRotationCheck+ "\n "
		+"tool change Time: "+toolChangeTime+ "\n "
		+"feed reduction: "+feedRateReductionOnCircle+ "\n "
		+"effective work time: "+effectiveWorkTime+ "\n "
		+ "max feed: " +maxFeed+ "\n "
		+ "b axis feed: "+ABCAxisFeedRate+ "\n "
		+"part fixure time: "+ partFixureTime+ "\n "
		+"mag capacity: "+maxToolNumber+ "\n "
		+"combo box selected item: "+machineComboSelectedItem+ "\n "
		+"Machine list: "+ machineList.toString();
		
		
	}
	
	
}
