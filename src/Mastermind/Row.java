package Mastermind;

public class Row implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5164485915316272767L;
	private int Red;
	private int White;
	private StoneCode[] Code;
	
	public Row(StoneCode[] Code, int Red, int White)
	{
		this.Red = Red;
		this.White = White;
		this.Code = Code;
	}
	
	public int getRed()
	{
		return Red;
	}
	
	public int getWhite()
	{
		return White;
	}

	public StoneCode[] getCode()
	{
		return Code;
	}
}
