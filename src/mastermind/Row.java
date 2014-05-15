
package mastermind;

/**
 * Describes a single row that is drawn onto the Playfield.
 * Every row has the selected CodeLength at the beginning of the game 
 * and inherits different colors.
 * The Class Row is Serializable for saving the Object onto the Filesystem. 
 * <p>
 * <p>
 * @author      Birk Kauer
 * @author      Raphael Pavlidis
 * @version     %I%, %G%
 * @since       1.0
 */
public class Row implements java.io.Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5164485915316272767L;
	
	/** The red. */
	private int red;
	
	/** The white. */
	private int white;
	
	/** The code. */
	private int[] code;
	
	/**
	 * Instantiates a new row.
	 *
	 * @param code the code
	 * @param red the red
	 * @param white the white
	 */
	public Row(int[] code, int red, int white)
	{
		this.red = red;
		this.white = white;
		this.code = code;
	}
	
	/**
	 * Gets the red.
	 *
	 * @return the red
	 */
	public int getRed()
	{
		return red;
	}
	
	/**
	 * Gets the white.
	 *
	 * @return the white
	 */
	public int getWhite()
	{
		return white;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int[] getCode()
	{
		return code;
	}
	
	/**
	 * Gets the code length.
	 *
	 * @return the code length
	 */
	public int getCodeLength()
	{
		return code.length;
	}
}
