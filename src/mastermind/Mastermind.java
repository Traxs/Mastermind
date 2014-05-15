package mastermind;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import view.Mastermind_View;
import file.Mastermind_File;

// TODO: Auto-generated Javadoc
/**
 * This class is basically the heart out of the whole project.
 * inside of it is the structure and the control of every other object/class.
 * Implements Serializable for saving the whole Object, with a few exceptions.
 * <p>
 * <p>
 * @author      Birk Kauer
 * @author      Raphael Pavlidis
 * @version     %I%, %G%
 * @since       1.0
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
	
	/** The rows. 
	 * @see mastermind.Row
	 */
	private ArrayList<Row> rows;
	
	/** The state. 
	 * <p>
	 * describes the different states which are possible in the programm.
	 * <p>
	 * @see mastermind.State
	 */
	protected State state;
	
	/** The modus.
	 * <p>
	 * describes the different states which are possible in the programm.
	 * <p> 
	 * @see mastermind.State
	 */
	protected State modus;
	
	/** The ki.
	 * Ki is transient for not beeing put into file via Serializable
	 * @see mastermind.KI
	 */
	private transient KI ki;
	
	/** The mastermind_ view. 
	 * @see view.Mastermind_View
	 */
	private transient Mastermind_View mastermind_View;
	
	/** The Constant DEFAULTCODELENGTH. */
	public static final int DEFAULTCODELENGTH = 6;
	
	/** The Constant DEFAULTROWLENGTH. */
	public static final int DEFAULTROWLENGTH = 12;
	
	/** The Constant DEFAULTCOLORLENGTH. */
	public static final int DEFAULTCOLORLENGTH = 6;
	
	/**
	 * Instantiates a new mastermind.
	 * <p>
	 * Parse the Gui Window into the Mastermind Object
	 * and calling the big overloaded Constructor with default values.
	 * <p>
	 * @param 	mastermind_View 	the mastermind_ view
	 * @see 	view.Mastermind_View
	 */
	public Mastermind(Mastermind_View mastermind_View)
	{
		this(mastermind_View, Mastermind.DEFAULTCODELENGTH, 
		        Mastermind.DEFAULTROWLENGTH, Mastermind.DEFAULTCOLORLENGTH, 
		        State.playingHuman, State.playingHuman, null);
	}
	
	
	/**
	 * Instantiates a new mastermind.
	 * <p>
	 * Overloaded Constructor with Values which are parsed through parameters.
	 * <p>
	 * @param 	mastermind_View the mastermind_ view
	 * @param 	codeLength 		the code length
	 * @param 	rowLength 		the row length
	 * @param 	colorLength 	the color length
	 * @param 	state 			the state
	 * @param 	modus			the modus
	 * @param 	secretCode 		the secret code
	 * @see 	view.Mastermind_View
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
	 * <p>
	 * Stopping every Thread in KI.
	 * <p>
	 * then it will call {@link file.Mastermind_File#saveMastermind(Mastermind, File)} with Values
	 * which are parsed out of {@link view.Mastermind_View}
	 * <p>
	 * @param 	file 		the file
	 * @throws 	IOException Signals that an I/O exception has occurred.
	 * @see 	file.Mastermind_File#saveMastermind(Mastermind, File)
	 */
	public void saveMastermind(File file) throws IOException
	{
	    stopKI();
		Mastermind_File.saveMastermind(this, file);
	}
	
	/**
	 * Load mastermind.
	 * <p>
	 * Calls {@link file.Mastermind_File#loadMastermind(File)} 
	 * and loads every instance of the loaded mastermind object into the current 
	 * object
	 * <p>
	 * It will also create a new Instance of KI so it can calculate from scratch.
	 * <p>
	 * @param file the file
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see mastermind.KI#KI(Mastermind, int, int)
	 * @see file.Mastermind_File#loadMastermind(File)
	 * @see mastermind.Mastermind#secretCode
	 * @see mastermind.Mastermind#codeLength
	 * @see mastermind.Mastermind#rowLength
	 * @see mastermind.Mastermind#colorLength
	 * @see mastermind.Mastermind#rows
	 * @see mastermind.Mastermind#state
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
	 * Generates a secret code.
	 * <p>
	 * This snippet creates a random secretCode if it's not manually set.
	 * @see 	java.util.Random
	 * @see		mastermind.Mastermind#secretCode
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
	 * Gets a hint from the AI (KI).
	 * <p> 
	 * getHint will start the calculating functions in KI with {@link mastermind.KI#getHint()}
	 * if the KI isn't already calculating which is checked via {@link mastermind.KI#isKICalculating()}
	 * <p>
	 * @see 	mastermind.Mastermind#state
	 * @see 	mastermind.KI#getHint()
	 * @see 	mastermind.KI#isKICalculating()
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
	 * <p>
	 * finishHint is called from KI when the calculation of the next hint is done.
	 * <p>
	 * it will add a hint with {@link view.Mastermind_View#addHint(int[])} and the parameter is the
	 * calculated Hint code.
	 * <p>
	 * at the End the function will set the current state into an updated state({@link mastermind.Mastermind#setState(State)}.
	 * <p>
	 * @param code the code
	 * @see 	view.Mastermind_View#addHint(int[])
	 * @see 	mastermind.Mastermind#state
	 * @see 	mastermind.Mastermind#setState(State)
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
     * <p>
     * isPossible will ask the KI (as long as the KI is not calculating) if the current selected Code is possible to be selected.
     * <p>
     * @param code the code
     * @see	mastermind.KI#isPossible(int[])
     * @see mastermind.KI#isKICalculating()
     * @see mastermind.Mastermind#setState(State)
     */
    public void isPossible(final int[] code) //final?
    {
        if(!ki.isKICalculating())
        {
            setState(State.checkPossible);
            ki.isPossible(code);
        }
    }
    
    /**
     * Finish check.
     * <p>
     * Last check if the Code is possible with the current calculations to be set on the field.
     * <p>
     * If it's not possible it will display a Dialog which tells the User that it's not possible.
     * <p>
     * @param code the code
     * @param isPossible the is possible
     * @see mastermind.Mastermind#setState(State)
     * @see mastermind.Mastermind#isPossible(int[])
     * @see mastermind.Mastermind#addRow(int[])
     * @see view.Mastermind_View#isNotPossible()
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
     * <p>
     * StopKI will try to interrupt the KI in its current calculations with {@link mastermind.KI#stop()}
     * The routine will wait till its clear that the calculating Thread has stopped({@link mastermind.KI#isKICalculating()}).
     * <p>
     * After the calculating thread has stopped it will set a new State with {@link mastermind.Mastermind#setState(State)}.
     * 
     * @see 	mastermind.KI#stop()
     * @see 	mastermind.KI#isKICalculating()
     * @see 	mastermind.Mastermind#setState(State)
     */
    public void stopKI()
    {
        ki.stop();
        while(ki.isKICalculating())
        {
        	
        } //??? lol dat thread callback; macht man normalerweise mit Observer Pattern/Listeners
        //siehe http://gameprogrammingpatterns.com/observer.html
        //jedes mal neuen thread erstellen u. starten kann u.U auch viele resourcen aufbrauchen
        
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
	 * <p>
	 * This method will add a selected Code into the playfield(The code lies within the parameter).
	 * <p>
	 * At first it will check if the user has already won or lose his game.
	 * <p>
	 * Next thing is it will calculate the difference between the added Row and the Secret Code
	 * the difference will be stored in the locale variable red and white.
	 * <p>
	 * Then it will add the complete row with the red and whites into the playfield with the function
	 * {@link view.Mastermind_View#addRow(Row)}
	 * <p>
	 * It will also set at the end if you now won or lose the game with {@link mastermind.Mastermind#setState(State)}.
	 * <p>
	 * @param code the code
	 * @see 	mastermind.Mastermind#state
	 * @see 	mastermind.Mastermind#secretCode
	 * @see		mastermind.Mastermind#rows
	 * @see 	view.Mastermind_View#addRow(Row)
	 * @see 	mastermind.Mastermind#setState(State)
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
	 * Sets the state of Mastermind and Mastermind_View.
	 * 
	 * @param state the new state
	 * @see 	mastermind.Mastermind#state
	 * @see 	view.Mastermind_View#setState(State)
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
	 * @see 	mastermind.Mastermind#state
	 */
	public State getState()
	{
		return state;
	}
	
	/**
	 * Gets the modus.
	 *
	 * @return the modus
	 * @see 	mastermind.Mastermind#state
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
	 * @see 	mastermind.Mastermind#secretCode
	 */
	public int[] getSecretCode()
	{
		return secretCode;
	}
	
	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 * @see 	mastermind.Mastermind#rows
	 */
	public ArrayList<Row> getRows()
	{
		return this.rows;
	}
	
	/**
	 * Gets the row size.
	 *
	 * @return the row size
	 * @see 	mastermind.Mastermind#rows
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
	 * @see 	mastermind.Mastermind#rows
	 */
	public Row getRow(int rowNumber)
	{
		return rows.get(rowNumber);
	}
	
	/**
	 * Gets the code length.
	 *
	 * @return the code length
	 * @see 	mastermind.Mastermind#codeLength
	 */
	public int getCodeLength()
	{
		return codeLength;
	}
	
	/**
	 * Gets the row length.
	 *
	 * @return the row length
	 * @see 	mastermind.Mastermind#rowLength
	 */
	public int getRowLength()
	{
		return rowLength;
	}
	
	/**
	 * Gets the color length.
	 *
	 * @return the color length
	 * @see 	mastermind.Mastermind#colorLength
	 */
	public int getColorLength()
	{
		return colorLength;
	}
}
