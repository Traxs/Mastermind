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
		0x1, 0x2, 0x4, 
		0x8, 0x10, 0x20,
		0x40, 0x80, 0x100, 
		0x200, 0x400, 0x800, 
		0x1000, 0x2000, 0x4000};
	
	/** The Constant setAll. */
	private static final int[] setAll = {	  0x0,
		0x1, 0x3, 0x7, 
		0xF, 0x1F, 0x3F,
		0x7F, 0xFF, 0x1FF, 
		0x3FF, 0x7FF, 0xFFF, 
		0x1FFF, 0x3FFF, 0x7FFF};
	
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
	    // Creates a set for every column
		Integer[] setCodes = new Integer[resultCodes.length];
		for(int i = 0; i < resultCodes.length; i++)
		{
			// Creates the set in dependency of R and N (Look KI Doku)
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
		// Union of the two elements from the Secret-Code set(Integer[]) column by column
		for(int i = 0; i < set1.length; i++)
		{
			newSet = set1[i] & set2[i];

			// Checks if the new column is a NULL set
			if(newSet == 0)
			{
				// if yes you can't unite these Elements with Union
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
