package mastermind;

/**
* This enum describes different game states. The current game state 
* is tracked and coordinated by this enum. Player and KI actions 
* are defined as well as win or lose states.
* <p>
* @author      Birk Kauer
* @author      Raphael Pavlidis
* @version     %I%, %G%
* @since       1.0
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
