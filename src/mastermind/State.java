/**
 * This enum describes the different States which the Programm can become.
 * <p>
 * @author      Birk Kauer
 * @author      Raphael Pavlidis
 * @version     %I%, %G%
 * @since       1.0
 */
package mastermind;

// TODO: Auto-generated Javadoc
/**
 * The Enum State.
 */
public enum State
{
	
/** The playing human. */
	playingHuman, 
/** The playing human help. */
	playingHumanHelp, 
/** The playing ki. */
	playingKI, 
	
/** The caculate ki. */
	caculateKI, 
/** The caculate help ki. */
	caculateHelpKI, 
/** The check possible. */
	checkPossible,
	
/** The code set. */
	codeSet,
/** The stop ki. */
	stopKI, 
 /** The stopped ki. */
	stoppedKI,
/** The lose. */
	lose, 
 /** The win. */
	win
}
