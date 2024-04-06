package CordConverter;

import java.util.Arrays;

import javax.swing.JOptionPane;

public class Wektor {
	
	
static final int PRAWO=2;
static final int LEWO=3;
	
	float x;
	float y;
	Point punktPoczatkowy;
	Point punktKoncowy;
	
	
	
	
	
	public Wektor()
	{
		this(0f,0f);
		
		
	}
	public Wektor(float []f)
	{
		this(f[0], f[1]);
		
	}
		public Wektor(float x, float y)
		{
			this(new Point(0f, 0f,TYPE.XY_POINT),new Point(x,y,TYPE.XY_POINT));
	
		}
	
			public Wektor(Point punktPoczatkowy, Point punktKoncowy)
			{
				this.punktPoczatkowy=punktPoczatkowy;
				this.punktKoncowy=punktKoncowy;
		
				this.x=punktKoncowy.getX()-punktPoczatkowy.getX();
				this.y = punktKoncowy.getY() - punktPoczatkowy.getY();
			}
	/**
	 * Ponownie oblicza wektor po zmianie jego punktow poczatkowego lub koncowego
	 */
	
	public void rebuild()
	{
		this.x=this.punktKoncowy.getX()-punktPoczatkowy.getX();
		this.y=this.punktKoncowy.getY()-punktPoczatkowy.getY();
	}
	
	
	public void odwrocWektor()
	{	
		Point pomocniczy = new Point(this.getStartingPoint().getX(),this.getStartingPoint().getY(),TYPE.XY_POINT);
		
		this.setPunktPoczatkowy(this.getEndPoint());
		this.setPunktKoncowy(pomocniczy);
		rebuild();
	}
	
	/**
	 * 
	 * @return Zwraca dlugosc wektora I J czyli wartosc promienia 
	 */
	
	public float dlugosc()
	{
		return (float)(Math.sqrt((x*x)+ (y*y)));
	}
	
	
	/**
	 * @param w wektor wzgledem ktorego ma byc mierzony kat
	 * @return Zwraca najmniejszy kat, miêdzy dwoma wektorami
	 * @throws ArithmeticException jesli wektor ma dlugosc 0
	 */
	
	public float calculateAngle(Wektor w) throws ArithmeticException
	{
		if(this.equals(w))
			return 0f;
		else if(this.dlugosc()==0 || w.dlugosc()==0)
			throw new ArithmeticException("Dlugoœæ jednego z wektorow jest rowna zero");
		else
		{
			float result=0f;
			
				result = (float) ((180/Math.PI)*Math.acos((iloczynSkalarny(this, w))/(this.dlugosc() * w.dlugosc())));

			return result;
		}
	
	}
	
	/**
	 * 
	 * @return Zwraca kat miedzy wektorem a osia ukladu wspolrzednych Ox podany w stopniach
	 */
	public float katUkladuBiegunowego()
	{
		float result;
		
		if(this.x>0)
		{
			if(this.y>=0)
				result= (float)Math.atan(this.y/this.x);
			//y<0
			else result = (float)(Math.atan(this.y/this.x) + 2* Math.PI);
		}
		// x<0
		else if(this.x<0)
		{
			result = (float)(Math.atan(this.y/this.x) + Math.PI);
		}
		//x==0
		else 
		{
			if( y>0)
				result = (float)Math.PI /2;
			//y<0
			else result = (float) ((3*Math.PI)/2);
			
		}

		return Point.roundToThree((float)((result*180)/Math.PI));
	}
	/**
	 * 
	 * @param kierunek kierunek przedstawiony w nast sposob: 2 prawy, 3 lewy
	 * @param pierwszy pierwszy wektor
	 * @param drugi drugi wektor
	 * @return zwraca wartosc kata skierowanego podana w stopniach
	 * @throws IllegalArgumentException kiedy kierunek jest inny ni¿ 2 lub 3
	 */
	
	public Float katSkierowanyMiedzyWektorami(int kierunek, Wektor drugi) throws IllegalArgumentException
	{
		
		if(kierunek!=PRAWO && kierunek !=LEWO)
		{
			throw new IllegalArgumentException("kierunek ruchu po okregu zdefiniowany inaczej ni¿ 2 lub 3(w prawo/ w lewo)");
		}
		
		else {
			Float result = this.calculateAngle(drugi);
			if(result <0.01)
				return 360f;
		
			//kat prawy
			if(kierunek == PRAWO)
			{
				result =  Math.abs((360-this.katUkladuBiegunowego())-(360-drugi.katUkladuBiegunowego()));
				
				if(drugi.katUkladuBiegunowego()-this.katUkladuBiegunowego() <0.01)
			
					return result;
				else return 360-result;
			}
			//lewy
			else if(kierunek == LEWO)
			{
				result = Math.abs(this.katUkladuBiegunowego()-drugi.katUkladuBiegunowego());
			
				//pierwszy przed drugim - prawidlowo
				if(this.katUkladuBiegunowego()-drugi.katUkladuBiegunowego() <0.01 )
				{
				
					return result;
				
				}
				//pierwszy wektor za drugim - trzeba odjac 360st
				else 
				{
				
					return 360-result;
				}	
			}
			}
		return 0f;
		
		}
	
	/**
	 * 
	 * @param w bracany wektor
	 * @param kat w stopniach
	 * @return wektor po obrocie
	 */
	
	
	public float getX()
	{
		return this.x;
		
	}
	public float getY()
	{
		return this.y;
	}
	
	public Point getStartingPoint()
	{
		return this.punktPoczatkowy;
	}
	public Point getEndPoint()
	{
		return this.punktKoncowy;	
	}
	
	
	public void setPunktPoczatkowy(Point poczatkowy)
	{
		this.punktPoczatkowy=poczatkowy;
		this.rebuild();
	}
	public void setPunktKoncowy(Point koncowy)
	{
		this.punktKoncowy=koncowy;
		this.rebuild();
	}
	
	/**
	 * 
	 * @param w pierwszy wektor
	 * @param z drugi wektor
	 * @return zwraca iloczin skalarny obu wektorow
	 */
	
	public float iloczynSkalarny(Wektor w, Wektor z)
	{
		return (w.getX() * z.getX()) + (w.getY() * z.getY());
	}
	
	public static Wektor dodajWektor(Wektor w,Wektor t)
	{
		Wektor result = new Wektor(w.punktPoczatkowy,w.punktKoncowy);
		result.x+=t.x;
		result.y+=t.y;
		result.punktKoncowy.setX(result.punktPoczatkowy.getX()+result.x);
		result.punktKoncowy.setY(result.punktPoczatkowy.getY()+result.y);
		
		return result;
	}
	
	public int cwiartka()
	{
		if(this.x>0)
		{
			if(y>=0)
				return 1;
			//y<0
			else return 4;				
		}
		else if(x==0)
		{
			if(y>0)
				return 2;
			//x==0 y<0
			else return 4;
		}
		//x<0
		else
		{
			if(y>0)
				return 2;
			//y<=0
			else return 3;		
		}
	}
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Wektor)
		{
			Wektor q = (Wektor) o;
			return (this.punktPoczatkowy.equals(q.punktPoczatkowy)) && (this.punktKoncowy.equals(q.punktKoncowy));
		
		}
		else return false;
	}
		
	

	@Override
	public String toString()
	{
		Float [][]f = new Float[4][2];
		
		f[0][0] = x;
		f[0][1] = y; 
		f[1][0] = punktPoczatkowy.getX();
		f[1][1] = punktPoczatkowy.getY();
		f[2][0] = punktKoncowy.getX();
		f[2][1] = punktKoncowy.getY();
		f[3][0] = this.dlugosc();
		f[3][1] = 0.f;
		
		return Arrays.toString(new float[]{x,y});
		
		
		
	}
			
	
	
	
}
