package mastermind;

import java.util.ArrayList;
import java.util.TreeSet;

public class SetStoneCode
{
	private TreeSet<Integer> set;
	
	private SetStoneCode(TreeSet<Integer> set)
	{
		this.set = set;
	}
	
	public SetStoneCode(boolean not, int colorLength, int code)
	{
		set = new TreeSet<Integer>();
		if(not)
		{
			set.add(code);
		}
		else
		{
			for(int i = 0; i < colorLength; i++)
			{
				set.add(i);
			}
			set.remove(code);
		}
	}
	
	public static SetStoneCode[] createRow(int[] resultCodes, int colorLenght, int[] stoneCodes)
	{
		SetStoneCode[] setStoneCodes = new SetStoneCode[resultCodes.length];
		for(int i = 0; i < resultCodes.length; i++)
		{
			switch(resultCodes[i])
			{
				case ResultCode.RED:
					setStoneCodes[i] = new SetStoneCode(true, colorLenght, stoneCodes[i]);
				break;
				case ResultCode.WHITE:
					setStoneCodes[i] = new SetStoneCode(false, colorLenght, stoneCodes[i]);
				break;
				case ResultCode.NOTHING:
					setStoneCodes[i] = new SetStoneCode(false, colorLenght, stoneCodes[i]);
				break;
			}
		}
		return setStoneCodes;
	}
	
	public static SetStoneCode[] unionRow(SetStoneCode[] array1, SetStoneCode[] array2)
	{
		SetStoneCode[] newArray = new SetStoneCode[array1.length];
		SetStoneCode newSet;
		for(int i = 0; i < array1.length; i++)
		{
			newSet = union(array1[i], array2[i]);
			if(newSet.getSize() == 0)
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
	
	public static SetStoneCode union(SetStoneCode set1, SetStoneCode set2)
	{
		TreeSet<Integer> newSet = new TreeSet<Integer>();
		Integer[] set1Array = (Integer[]) set1.set.toArray(new Integer[0]);
		
		for(int i = 0; i < set1Array.length; i++)
		{
			if(set2.set.contains(set1Array[i]))
			{
				newSet.add(set1Array[i]);
			}
		}
		
		return new SetStoneCode(newSet);
	}
	
	public static long getRowSize(SetStoneCode[] setStoneCodes)
	{
		long size = 1;

		for(SetStoneCode setStoneCode : setStoneCodes)
		{
			size *= setStoneCode.getSize();
		}

		return size;
	}

	public static ArrayList<SetStoneCode[]> unionSetStoneCodeArrayList(ArrayList<SetStoneCode[]> p1,
			ArrayList<SetStoneCode[]> p2)
	{
		ArrayList<SetStoneCode[]> newPossibilities = new ArrayList<SetStoneCode[]>();
		SetStoneCode[] newPossibility;
		
		for(SetStoneCode[] p1Element : p1)
		{
			for(SetStoneCode[] p2Element : p2)
			{
				newPossibility = SetStoneCode.unionRow(p1Element, p2Element);
				if(newPossibility != null)
				{
					newPossibilities.add(newPossibility);
				}
			}
		}

		return newPossibilities;
	}
	
	public boolean contains(int i)
	{
		return set.contains(i);
	}
	
	public int getSize()
	{
		return set.size();
	}
	
	public int getFirst()
	{
		return set.first();
	}
}
