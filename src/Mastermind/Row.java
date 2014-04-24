package Mastermind;

public class Row implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5164485915316272767L;
	private int red;
	private int white;
	private int[] code;
	
	public Row(int[] code, int red, int white)
	{
		this.red = red;
		this.white = white;
		this.code = code;
	}
	
	public int getRed()
	{
		return red;
	}
	
	public int getWhite()
	{
		return white;
	}

	public int[] getCode()
	{
		return code;
	}
	
	public int getCodeLength()
	{
		return code.length;
	}
}
