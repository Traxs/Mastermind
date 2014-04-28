package mastermind;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import view.Mastermind_View;
import file.Mastermind_File;

public class Mastermind implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4438978518578456012L;
	private int[] secretCode;
	private int codeLength;
	private int rowLength;
	private int colorLength;
	private ArrayList<Row> rows;
	private State state;
	private transient KI ki;
	private transient Mastermind_View mastermind_View;
	public static final int DEFAULTCODELENGTH = 6;
	public static final int DEFAULTROWLENGTH = 12;
	public static final int DEFAULTCOLORLENGTH = 6;
	
	public Mastermind(Mastermind_View mastermind_View)
	{
		this(mastermind_View, Mastermind.DEFAULTCODELENGTH, Mastermind.DEFAULTROWLENGTH, Mastermind.DEFAULTCOLORLENGTH);
	}
	
	public Mastermind(Mastermind_View mastermind_View, int codeLength, int rowLength, int colorLength)
	{
		this.mastermind_View = mastermind_View;
		this.codeLength = codeLength;
		this.rowLength = rowLength;
		this.colorLength = colorLength;
		genSecretCode();
		rows = new ArrayList<Row>();
		state = State.playingHuman;
		ki = new KI(this, colorLength, codeLength);
	}

	public void saveMastermind(File file) throws IOException
	{
		Mastermind_File.saveMastermind(this, file);
	}
	
	public void loadMastermind(File file) throws ClassNotFoundException, IOException
	{
		Mastermind mastermind = Mastermind_File.loadMastermind(file);
		this.secretCode = mastermind.secretCode;
		this.codeLength = mastermind.codeLength;
		this.rowLength = mastermind.rowLength;
		this.colorLength = mastermind.colorLength;
		this.rows = mastermind.rows;
		this.state = mastermind.state;
		ki = new KI(this, colorLength, codeLength);
	}

	private void genSecretCode()
	{
		secretCode = new int[codeLength];
		Random Rand = new Random();
		
		for(int i = 0; i < codeLength; i++)
		{
			secretCode[i] = Rand.nextInt(colorLength);
		}
	}

	public void test()
	{
		ki.getHint(rows);
	}

	public void addRow(int[] code)
	{
		switch(state)
		{
			case win:
			case lose:
				return;
			default:
			break;
		}
		
		boolean[] mark = new boolean[codeLength];
		int red = 0, white = 0;
		
		for(int i = 0; i < codeLength; i++)
		{
			if(code[i] == secretCode[i])
			{
				mark[i] = true;
				red++;
			}
			else
			{
				int j;
				for(j = 0; j < codeLength; j++)
				{
					if(!mark[j] & (code[j] != secretCode[j]) & code[i] == secretCode[j])
					{
						break;
					}
				}
				
				if(j < codeLength)
				{
					mark[j] = true;
					white++;
				}
			}
		}

		Row newRow = new Row(code, red, white);
		
		rows.add(newRow);
		mastermind_View.addRow(newRow);

		if(newRow.getRed() == codeLength)
		{
			setState(State.win);
		}
		else if(rows.size() >= rowLength)
		{
			setState(State.lose);
		}
	}
	
	public void isPossible(final int[] code)
	{
		ki.isPossible(code);
	}
	
	private void setState(State state)
	{
		this.state = state;
		mastermind_View.setState(state);
	}
	
	public State getState()
	{
		return state;
	}
	
	//*** SecretCode ***
	public int[] getSecretCode()
	{
		return secretCode;
	}
	
	public ArrayList<Row> getRows()
	{
		return this.rows;
	}
	
	public int getRowSize()
	{
		return this.rows.size();
	}
	
	public Row getRow(int rowNumber)
	{
		return rows.get(rowNumber);
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
