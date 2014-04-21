package Mastermind;

import java.awt.Color;

public class StoneCode implements Comparable<StoneCode>, java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2092838942451480751L;
	private static final StoneCode white = new StoneCode(new Color(255, 255, 255), 0);
	private static final StoneCode black = new StoneCode(new Color(0, 0, 0), 1);
	private static final StoneCode red = new StoneCode(new Color(255, 0, 0), 2);
	private static final StoneCode green = new StoneCode(new Color(0, 128, 0), 3);
	private static final StoneCode orange = new StoneCode(new Color(255, 165, 0), 4);
	private static final StoneCode pink = new StoneCode(new Color(255, 192, 203), 5);
	private static final StoneCode brown = new StoneCode(new Color(165, 42, 42), 6);
	private static final StoneCode yellow = new StoneCode(new Color(255, 255, 0), 7);
	private static final StoneCode cyan = new StoneCode(new Color(0, 255, 255), 8);
	private static final StoneCode lime = new StoneCode(new Color(0, 255, 0), 9);
	private static final StoneCode purple = new StoneCode(new Color(128, 0, 128), 10);
	private static final StoneCode magenta = new StoneCode(new Color(255, 0, 255), 11);
	private static final StoneCode grey = new StoneCode(new Color(128, 128, 128), 12);
	private static final StoneCode darkblue = new StoneCode(new Color(0, 0, 139), 13);
	private static final StoneCode darkgreen = new StoneCode(new Color(0, 100, 0), 14);
	private static final StoneCode[] StoneColorArray = {white, black, red, green, orange, pink, brown, yellow, cyan, lime, purple, magenta, grey, darkblue, darkgreen};
	
	private Color color;
	private int Code;
	
	private StoneCode(Color color, int Code)
	{
		this.color = color;
		this.Code = Code;
	}
	
	public int getCode()
	{
		return Code;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public static StoneCode getStoneCode(int i)
	{
		return StoneColorArray[i];
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof StoneCode))
			return false;
		if(((StoneCode)obj).Code == Code)
			return true;
		return false;
	}

	@Override
	public int compareTo(StoneCode o)
	{
		return this.Code - o.Code;
	}
}
