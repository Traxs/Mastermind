/*
 * 
 */
package mastermind;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import view.Mastermind_View;
import file.Mastermind_File;

// TODO: Auto-generated Javadoc
/**
 * The Class Mastermind.
 */
public class Mastermind implements java.io.Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4438978518578456012L;
	
	/** The secret code. */
	private int[] secretCode;
	
	/** The code length. */
	private int codeLength;
	
	/** The row length. */
	private int rowLength;
	
	/** The color length. */
	private int colorLength;
	
	/** The rows. */
	private ArrayList<Row> rows;
	
	/** The state. */
	protected State state;
	
	/** The modus. */
	protected State modus;
	
	/** The ki. */
	private transient KI ki;
	
	/** The mastermind_ view. */
	private transient Mastermind_View mastermind_View;
	
	/** The Constant DEFAULTCODELENGTH. */
	public static final int DEFAULTCODELENGTH = 6;
	
	/** The Constant DEFAULTROWLENGTH. */
	public static final int DEFAULTROWLENGTH = 12;
	
	/** The Constant DEFAULTCOLORLENGTH. */
	public static final int DEFAULTCOLORLENGTH = 6;
	
	/**
	 * Instantiates a new mastermind.
	 *
	 * @param mastermind_View the mastermind_ view
	 */
	public Mastermind(Mastermind_View mastermind_View)
	{
		this(mastermind_View, Mastermind.DEFAULTCODELENGTH, 
		        Mastermind.DEFAULTROWLENGTH, Mastermind.DEFAULTCOLORLENGTH, 
		        State.playingHuman, State.playingHuman, null);
	}
	
	
	/**
	 * Instantiates a new mastermind.
	 *
	 * @param mastermind_View the mastermind_ view
	 * @param codeLength the code length
	 * @param rowLength the row length
	 * @param colorLength the color length
	 * @param state the state
	 * @param modus the modus
	 * @param secretCode the secret code
	 */
	public Mastermind(Mastermind_View mastermind_View, int codeLength, 
	        int rowLength, int colorLength, State state, State modus, int[] secretCode)
	{
		this.mastermind_View = mastermind_View;
		this.codeLength = codeLength;
		this.rowLength = rowLength;
		this.colorLength = colorLength;
		if(secretCode == null)
		{
			genSecretCode();
		}
		else
		{
			this.secretCode = secretCode;
		}
		rows = new ArrayList<Row>();
		this.state = state;
		this.modus = modus;
		ki = new KI(this, colorLength, codeLength);
	}

	/**
	 * Save mastermind.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void saveMastermind(File file) throws IOException
	{
	    stopKI();
		Mastermind_File.saveMastermind(this, file);
	}
	
	/**
	 * Load mastermind.
	 *
	 * @param file the file
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Gen secret code.
	 */
	private void genSecretCode()
	{
		secretCode = new int[codeLength];
		Random Rand = new Random();
		
		for(int i = 0; i < codeLength; i++)
		{
			secretCode[i] = Rand.nextInt(colorLength);
		}
	}

	/**
	 * Gets the hint.
	 *
	 * @return the hint
	 */
	public void getHint()
	{
		if(!ki.isKICalculating())
		{
	        setState(state == State.playingHuman ? State.caculateKI : 
	                State.caculateHelpKI);
		    ki.getHint();
		}
	}
	
	/**
	 * Finish hint.
	 *
	 * @param code the code
	 */
	public void finishHint(int[] code)
	{
	    mastermind_View.addHint(code);
	    switch(state)
	    {
	        case caculateKI:
	            setState(State.playingHuman);
	        break;
	        case caculateHelpKI:
	            setState(State.playingHumanHelp);
	        break;
	        default:
	        break;
	    }
	}

    /**
     * Checks if is possible.
     *
     * @param code the code
     */
    public void isPossible(final int[] code)
    {
        if(!ki.isKICalculating())
        {
            setState(State.checkPossible);
            ki.isPossible(code);
        }
    }
    
    /**
     * Finish check.
     *
     * @param code the code
     * @param isPossible the is possible
     */
    public void finishCheck(final int[] code, boolean isPossible)
    {
        setState(State.playingHumanHelp);
        if(isPossible)
        {
            addRow(code);
        }
        else
        {
            mastermind_View.isNotPossible();
        }
    }
	
    /**
     * Stop ki.
     */
    public void stopKI()
    {
        ki.stop();
        while(ki.isKICalculating())
        {
        	
        }
        setState(State.stoppedKI);
        switch(state)
        {
            case caculateKI:
                state = State.playingHuman;
            break;
            case caculateHelpKI:
            case checkPossible:
                state = State.playingHumanHelp;
            break;
            default:
            break;
        }
    }

	/**
	 * Adds the row.
	 *
	 * @param code the code
	 */
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
		else if(rows.size() >= rowLength && rowLength > 0)
		{
			setState(State.lose);
		}
	}
	
	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	private void setState(State state)
	{
		this.state = state;
		mastermind_View.setState(state);
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public State getState()
	{
		return state;
	}
	
	/**
	 * Gets the modus.
	 *
	 * @return the modus
	 */
	public State getModus()
	{
		return modus;
	}
	
	//*** SecretCode ***
	/**
	 * Gets the secret code.
	 *
	 * @return the secret code
	 */
	public int[] getSecretCode()
	{
		return secretCode;
	}
	
	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public ArrayList<Row> getRows()
	{
		return this.rows;
	}
	
	/**
	 * Gets the row size.
	 *
	 * @return the row size
	 */
	public int getRowSize()
	{
		return this.rows.size();
	}
	
	/**
	 * Gets the row.
	 *
	 * @param rowNumber the row number
	 * @return the row
	 */
	public Row getRow(int rowNumber)
	{
		return rows.get(rowNumber);
	}
	
	/**
	 * Gets the code length.
	 *
	 * @return the code length
	 */
	public int getCodeLength()
	{
		return codeLength;
	}
	
	/**
	 * Gets the row length.
	 *
	 * @return the row length
	 */
	public int getRowLength()
	{
		return rowLength;
	}
	
	/**
	 * Gets the color length.
	 *
	 * @return the color length
	 */
	public int getColorLength()
	{
		return colorLength;
	}
}
