package CordConverter;

import java.io.Serializable;

public class Machine implements Serializable {



	private static final long serialVersionUID = 72554899149936138L;
	private String name;
	private int maxZ;
	private int maxFeed;
	private int toolChangeTime;
	private int abcAxisRotationFeed;
	private int maxToolNumber;
	
	
	//default machine
	private Machine()
	{
		 maxZ=400;
		 maxFeed=10000;
		 toolChangeTime=2;
		 abcAxisRotationFeed=4000;
		 maxToolNumber=40;
	}
	
	public Machine(String name)
	{
		this();
		this.name=name;
	}
	

	
	private Machine(final MachineBuilder machineBuilder)
	{
		this.name=machineBuilder.name;
		this.maxZ=machineBuilder.maxZ;
		this.maxFeed=machineBuilder.maxFeed;
		this.toolChangeTime=machineBuilder.toolChangeTime;
		this.abcAxisRotationFeed=machineBuilder.abcAxisRotationFeed;
		this.maxToolNumber=machineBuilder.maxToolNumber;
		
	}


	
	 public static class MachineBuilder
	{
		 	private String name;
			private int maxZ;
			private int maxFeed;
			private int toolChangeTime;
			private int abcAxisRotationFeed;
			private int maxToolNumber;
		

			public MachineBuilder name(final String name)
			{
				this.name=name;
				return this;
			}
			
			public MachineBuilder maxZ(int maxZ)
			{
				this.maxZ=maxZ;
				return this;
			}
			
			public MachineBuilder maxFeed(int maxFeed)
			{
				this.maxFeed=maxFeed;
				return this;
			}
			
			
			public MachineBuilder toolChangeTime(int toolChangeTime)
			{
				this.toolChangeTime=toolChangeTime;
				return this;
			}
			
			public MachineBuilder abcAxisRotationFeed(int abcAxisRotationFeed)
			{
				this.abcAxisRotationFeed=abcAxisRotationFeed;
				return this;
			}
			
			public MachineBuilder maxToolNumber(int maxToolNumber)
			{
				this.maxToolNumber=maxToolNumber;
				return this;
			}
			
			public Machine build()
			{
				return new Machine(this);
			}
	}

	 
	 
	 
	 @Override
	 public String toString()
	 {
		 return this.name;
	 }


	public int getMaxZ() {
		return maxZ;
	}


	public void setMaxZ(int maxZ) {
		this.maxZ = maxZ;
	}



	public String getName() {
		return name;
	}


	public int getMaxFeed() {
		return maxFeed;
	}


	public int getToolChangeTime() {
		return toolChangeTime;
	}


	public int getAbcAxisRotationFeed() {
		return abcAxisRotationFeed;
	}


	public int getMaxToolNumber() {
		return maxToolNumber;
	}

	public void updateValues(Machine values) {
		this.name=values.name;
		this.abcAxisRotationFeed=values.abcAxisRotationFeed;
		this.maxFeed=values.maxFeed;
		this.maxToolNumber=values.maxToolNumber;
		this.toolChangeTime=values.toolChangeTime;
		this.maxZ=values.maxZ;
		
	}
	
	
}
