/**
 * This class are the single rows which we are implementing into the Playfield
 * every row withstands out of the selected CodeLength and inherits the different colors.
 * <p>
 * <p>
 * @author      Birk Kauer
 * @author      Raphael Pavlidis
 * @version     %I%, %G%
 * @since       1.0
 */
package mastermind;

// TODO: Auto-generated Javadoc
/**
 * The Class Row is Serializable for saving the Object onto the Filesystem.
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
