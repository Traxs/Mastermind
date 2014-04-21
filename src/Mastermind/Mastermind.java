package Mastermind;

import java.util.ArrayList;
import java.util.Random;
import View.Mastermind_View;

public class Mastermind implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4438978518578456012L;
	private StoneCode[] SecretCode;
	private int codeLength;
	private int rowLength;
	private int colorLength;
	private ArrayList<Row> rows;
	private transient KI ki;
	private transient Mastermind_View mastermind_View;
	public static final int DefaulCodeLength = 5;
	public static final int DefaultRowLength = 15;
	public static final int DefaultColorLength = 5;
	
	
	public Mastermind(Mastermind_View mastermind_View)
	{
		this(mastermind_View, Mastermind.DefaulCodeLength, Mastermind.DefaultRowLength, Mastermind.DefaultColorLength);
	}
	
	public Mastermind(Mastermind_View mastermind_View, int CodeLength, int RowLength, int ColorLength)
	{
		this.mastermind_View = mastermind_View;
		this.codeLength = CodeLength;
		this.rowLength = RowLength;
		this.colorLength = ColorLength;
		genSecretCode();
		this.rows = new ArrayList<Row>();
		this.ki = new KI(this, ColorLength, CodeLength);
	}

	public void setMastermind(Mastermind mastermind)
	{
		if(mastermind.getClass().isInstance(this))
		{
			this.SecretCode = mastermind.SecretCode;
			this.codeLength = mastermind.codeLength;
			this.rowLength = mastermind.rowLength;
			this.colorLength = mastermind.codeLength;
			this.rows = mastermind.rows;
			this.ki = new KI(this, colorLength, codeLength);
		}else{
			System.out.print("Class or Header");
		}
	}
	
	private void genSecretCode()
	{
		SecretCode = new StoneCode[codeLength];
		Random Rand = new Random();
		
		for(int i = 0; i < codeLength; i++)
		{
			SecretCode[i] = StoneCode.getStoneCode(Rand.nextInt(colorLength));
		}
	}
	
	
	public void test()
	{
		ki.getHint(rows);
	}

	public void addRow(StoneCode[] Code)
	{
		if(rows.size() >= rowLength)
		{
			return;
		}
		
		boolean[] Mark = new boolean[codeLength];
		int Red = 0, White = 0;
		
		for(int i = 0; i < codeLength; i++)
		{
			if(Code[i].equals(SecretCode[i]))
			{
				Mark[i] = true;
				Red++;
			}
			else
			{
				int j;
				for(j = 0; j < codeLength; j++)
				{
					if(!Mark[j] & !Code[j].equals(SecretCode[j]) & Code[i].equals(SecretCode[j]))
					{
						break;
					}
				}
				
				if(j < codeLength)
				{
					Mark[j] = true;
					White++;
				}
			}
		}

		Row newRow = new Row(Code, Red, White);
		rows.add(newRow);
		mastermind_View.addRow(newRow);

		if(newRow.getRed() == codeLength)
		{
			mastermind_View.setGameWin();
		}
		else if(rows.size() == rowLength)
		{
			mastermind_View.setGameLose();
		}
	}
	
	//*** SecretCode ***
	public StoneCode[] getSecretCode()
	{
		return SecretCode;
	}
	
	public int getRowSize(){
		return this.rows.size();
	}
	
	public Row getRow(int RowNumber)
	{
		return rows.get(RowNumber);
	}
	
	public int getCodeLength()
	{
		return codeLength;
	}
	
	public int getRowLength()
	{
		return rowLength;
	}
	
	public int getColorLength()
	{
		return colorLength;
	}
}
