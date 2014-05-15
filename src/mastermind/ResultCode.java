package mastermind;
/**
 * Pretty much a dummy class.
 * But it's needed because we use these elements nearly in every other class.
 * Support class of {@link mastermind.KI} and {@link mastermind.SetCode}.
 * These constants are used to help the KI figure out permutations and provide
 * a placeholder if result is unknown.
 * <p>
 * <p>
 * @author      Raphael Pavlidis
 * @version     %I%, %G%
 * @since       1.0
 */
public class ResultCode
{
	
	/**
	 * Instantiates a new result code.
	 */
	private ResultCode(){}
	
	/** The Constant RED. */
	public final static int RED = 3;
	
	/** The Constant WHITE. */
	public final static int WHITE = 2; //this is apparently not used anywhere
	
	/** The Constant NOTHING. */
	public final static int NOTHING = 1;
}
