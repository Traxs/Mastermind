package mastermind;

import java.util.ArrayList;

public class SetCode
{
	//private TreeSet<Integer> set;
	private static final short[] set = { 
			0b000000000000001, 0b000000000000010, 0b000000000000100, 
			0b000000000001000, 0b000000000010000, 0b000000000100000,
			0b000000001000000, 0b000000010000000, 0b000000100000000, 
			0b000001000000000, 0b000010000000000, 0b000100000000000, 
			0b001000000000000, 0b010000000000000, 0b100000000000000};
	
	private SetCode(){}
	
	public static short createCodeSet(boolean not, int colorLength, int code)
	{
		if(not)
		{
			return set[code];
		}
		else
		{
			short temp = 0;
			for(int i = 0; i < colorLength; i++)
			{
				temp |= set[i];
			}
			
			temp ^= set[code];
			
			return temp;
		}
	}
	
	public static Short[] createRow(int[] resultCodes, int colorLength, int[] stoneCodes)
	{
		Short[] setCodes = new Short[resultCodes.length];
		for(int i = 0; i < resultCodes.length; i++)
		{
			switch(resultCodes[i])
			{
				case ResultCode.RED:
					setCodes[i] = createCodeSet(true, colorLength, stoneCodes[i]);
				break;
				case ResultCode.WHITE:
					setCodes[i] = createCodeSet(false, colorLength, stoneCodes[i]);
				break;
				case ResultCode.NOTHING:
					setCodes[i] = createCodeSet(false, colorLength, stoneCodes[i]);
				break;
			}
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

	public static ArrayList<Short[]> unionSetStoneCodeArrayList(ArrayList<Short[]> p1,
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
	
	public static void main(String[] args)
	{
		System.out.println(Integer.toBinaryString(createCodeSet(true, 0, 4)));
	}
}
