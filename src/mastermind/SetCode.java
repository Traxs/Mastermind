package mastermind;

import java.util.ArrayList;

/*
 * The Class SetCode. A helper class of {@link mastermind.KI} to figure out 
 * possible color combinations.
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
	 * Instantiates a new sets the code.
	 */
	private SetCode(){}

	/**
	 * Creates the row.
	 *
	 * @param resultCodes the result codes
	 * @param colorLength the color length
	 * @param stoneCodes the stone codes
	 * @return the integer[]
	 */
	public static Integer[] createRow(int[] resultCodes, int colorLength, int[] stoneCodes)
	{
		Integer[] setCodes = new Integer[resultCodes.length];
		for(int i = 0; i < resultCodes.length; i++)
		{
			setCodes[i] = resultCodes[i] == ResultCode.RED ? set[stoneCodes[i]] 
					: setAll[colorLength] ^ set[stoneCodes[i]];
			/*
			switch(resultCodes[i])
			{
				case ResultCode.RED:
					setCodes[i] = set[stoneCodes[i]];
				break;
				case ResultCode.WHITE:
					setCodes[i] = (short) (setAll[colorLength] ^ set[stoneCodes[i]]);
				break;
				case ResultCode.NOTHING:
					setCodes[i] = (short) (setAll[colorLength] ^ set[stoneCodes[i]]);
				break;
			}*/
		}

		return setCodes;
	}

	/**
	 * Union row.
	 *
	 * @param row1 the row1
	 * @param row2 the row2
	 * @return the integer[]
	 */
	public static Integer[] unionRow(Integer[] row1, Integer[] row2)
	{
		Integer[] newArray = new Integer[row1.length];
		int newSet;
		for(int i = 0; i < row1.length; i++)
		{
			newSet = row1[i] & row2[i];

			if(newSet == 0)
			{
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
	 * Gets the row size.
	 *
	 * @param setCodes the set codes
	 * @return the row size
	 */
	public static long getRowSize(Integer[] setCodes)
	{
		long size = 1;

		for(int setCode : setCodes)
		{
			size *= getSize(setCode);
		}

		return size;
	}

	/**
	 * Union set code array list.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @param thread the thread
	 * @return the array list
	 */
	public static ArrayList<Integer[]> unionSetCodeArrayList(ArrayList<Integer[]> p1,
			ArrayList<Integer[]> p2, Thread thread)
	{
		ArrayList<Integer[]> newPossibilities = new ArrayList<Integer[]>();
		Integer[] newPossibility;
		
		for(Integer[] p1Element : p1)
		{	
			for(Integer[] p2Element : p2)
			{
				if(!thread.isInterrupted())
				{
				newPossibility = SetCode.unionRow(p1Element, p2Element);
				if(newPossibility != null)
				{
					newPossibilities.add(0, newPossibility);
				}
				}
				else
				{
					break;
				}
			}
		}

		return newPossibilities;
	}

	/**
	 * Contains.
	 *
	 * @param value the value
	 * @param code the code
	 * @return true, if successful
	 */
	public static boolean contains(int value, int code)
	{
		return (value & set[code]) != 0;
	}

	/**
	 * Gets the size.
	 *
	 * @param value the value
	 * @return the size
	 */
	public static int getSize(int value)
	{
		int size = 0;
		while(value != 0)
		{
			if(value % 2 == 1)
				size++;

			value >>= 1;
		}
		return size;
	}

	/**
	 * Gets the first.
	 *
	 * @param value the value
	 * @return the first
	 */
	public static int getFirst(int value)
	{
		for(int i = 0; i < 15; i++)
		{
			if(value % 2 == 1)
				return i;
			
			value >>= 1;
		}

		return -1;
	}
}
