package mastermind;

/**
 * This enum describes the different States which the Programm can become.
 * <p>
 * 
 * @author Birk Kauer
 * @author Raphael Pavlidis
 * @version %I%, %G%
 * @since 1.0
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
    setCode, stopCaculateKI, stopCaculateHelpKI, stopCheckPossible,
    /** The lose. */
    lose,
    /** The win. */
    win
}
