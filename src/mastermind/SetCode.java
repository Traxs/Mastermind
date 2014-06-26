/*

 */
package mastermind;

/**
 * The Class SetCode is a help-class for handle the Integer-Sets and SecretCode-set
 * @author      Birk Kauer
 * @author      Raphael Pavlidis
 * @version     %I%, %G%
 * @since       1.0
 */
public class SetCode
{
	
	/** The Constant set. */
	private static final int[] set = { 
		0b000000000000001, 0b000000000000010, 0b000000000000100, 
		0b000000000001000, 0b000000000010000, 0b000000000100000,
		0b000000001000000, 0b000000010000000, 0b000000100000000, 
		0b000001000000000, 0b000010000000000, 0b000100000000000, 
		0b001000000000000, 0b010000000000000, 0b100000000000000};
	
	/** The Constant setAll. */
	private static final int[] setAll = {	  0b000000000000000,
		0b000000000000001, 0b000000000000011, 0b000000000000111, 
		0b000000000001111, 0b000000000011111, 0b000000000111111,
		0b000000001111111, 0b000000011111111, 0b000000111111111, 
		0b000001111111111, 0b000011111111111, 0b000111111111111, 
		0b001111111111111, 0b011111111111111, 0b111111111111111};
	
	/**
	 * denies another instance of SetCode.
	 */
	private SetCode(){}

	/**
	 * Creates a SecretCode-set based on parameters.
	 * @param resultCodes the result codes
	 * @param colorLength the color length
	 * @param stoneCodes the stone codes
	 * @return SecretCode-set.
	 */
	public static Integer[] createRow(int[] resultCodes, int colorLength, int[] stoneCodes)
	{
	    // Erstellt für jede Spalte die Menge
		Integer[] setCodes = new Integer[resultCodes.length];
		for(int i = 0; i < resultCodes.length; i++)
		{
		    // Erstellt die Menge in Abhängigkeit von R und N (siehe KI Doku)
			setCodes[i] = resultCodes[i] == ResultCode.RED ? set[stoneCodes[i]] 
					: setAll[colorLength] ^ set[stoneCodes[i]];
		}

		return setCodes;
	}

	/**
	 * Delivers the intersection between two SecretCode-sets.
	 * @param 	set1 first SecretCode-set.
	 * @param 	set2 second SecretCode-set.
	 * @return 	the intersection between set1 and set2.
	 */
	public static Integer[] intersectRow(Integer[] set1, Integer[] set2)
	{
		Integer[] newArray = new Integer[set1.length];
		int newSet;
		// Vereinigt(Schnitt) die zwei Elemente der Geheimcode-Menge(Integer[]) Spaltenweise
		for(int i = 0; i < set1.length; i++)
		{
			newSet = set1[i] & set2[i];

			// Überprüft ob die neue Spalte(Menge) die Leere Menge ist
			if(newSet == 0)
			{
			    // wenn ja bedeutet es das man die Elemente nicht vereinigen(Schnitt) kann.
				return null;
			}
			else
			{
				newArray[i] = newSet;
			}
		}

		return newArray;		
	}

	/**
	 * Delivers the amount of elements in the parameter SecretCode-set[set].
	 * 
	 * @param 	set The SecretCode-set.
	 * @return 	the amount of elements in set.
	 */
	public static long getRowSize(Integer[] set)
	{
		long size = 1;

		for(int column : set)
		{
			size *= getSize(column);
		}

		return size;
	}

	/**
	 * Checks if Set contains the parameter element.
	 * @param 	set 	set
	 * @param 	element the element to be checked on.
	 * @return 	delivers true if the set contains the element.
	 */
	public static boolean contains(int set, int element)
	{
		return (set & SetCode.set[element]) != 0;
	}

	/**
	 * Delivers the amount of elements in the set.
	 * @param 	set 	set
	 * @return 	the amount of elemts in the set.
	 */
	public static int getSize(int set)
	{
		int size = 0;
		while(set != 0)
		{
			if(set % 2 == 1)
				size++;

			set >>= 1;
		}
		return size;
	}

	/**
	 * Delivers the first element in the set.
	 * @param 	set 	set.
	 * @return 	returns the first element of the set. If it's empty it will return -1.
	 */
	public static int getFirst(int set)
	{
		for(int i = 0; i < 15; i++)
		{
			if(set % 2 == 1)
				return i;
			
			set >>= 1;
		}

		return -1;
	}
}
