package mastermind;

import java.util.ArrayList;

public class SetCode
{
	private static final short[] set = { 
		0b000000000000001, 0b000000000000010, 0b000000000000100, 
		0b000000000001000, 0b000000000010000, 0b000000000100000,
		0b000000001000000, 0b000000010000000, 0b000000100000000, 
		0b000001000000000, 0b000010000000000, 0b000100000000000, 
		0b001000000000000, 0b010000000000000, 0b100000000000000};
	
	private static final short[] setAll = {	  0b000000000000000,
		0b000000000000001, 0b000000000000011, 0b000000000000111, 
		0b000000000001111, 0b000000000011111, 0b000000000111111,
		0b000000001111111, 0b000000011111111, 0b000000111111111, 
		0b000001111111111, 0b000011111111111, 0b000111111111111, 
		0b001111111111111, 0b011111111111111, 0b111111111111111};
	
	private SetCode(){}

	public static Short[] createRow(int[] resultCodes, int colorLength, int[] stoneCodes)
	{
		Short[] setCodes = new Short[resultCodes.length];
		for(int i = 0; i < resultCodes.length; i++)
		{
			setCodes[i] = resultCodes[i] == ResultCode.RED ? set[stoneCodes[i]] 
					: (short) (setAll[colorLength] ^ set[stoneCodes[i]]);
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

	public static Short[] unionRow(Short[] row1, Short[] row2)
	{
		Short[] newArray = new Short[row1.length];
		short newSet;
		for(int i = 0; i < row1.length; i++)
		{
			newSet = (short) (row1[i] & row2[i]);

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

	public static long getRowSize(Short[] setCodes)
	{
		long size = 1;

		for(short setCode : setCodes)
		{
			size *= getSize(setCode);
		}

		return size;
	}

	public static ArrayList<Short[]> unionSetCodeArrayList(ArrayList<Short[]> p1,
			ArrayList<Short[]> p2)
	{
		ArrayList<Short[]> newPossibilities = new ArrayList<Short[]>();
		Short[] newPossibility;
		
		for(Short[] p1Element : p1)
		{	
			for(Short[] p2Element : p2)
			{
				newPossibility = SetCode.unionRow(p1Element, p2Element);
				if(newPossibility != null)
				{
					newPossibilities.add(0, newPossibility);
				}
			}
		}

		return newPossibilities;
	}

	public static boolean contains(short value, int code)
	{
		return (value & set[code]) != 0;
	}

	public static int getSize(short value)
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

	public static int getFirst(short value)
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
