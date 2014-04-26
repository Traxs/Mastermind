package mastermind;

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
	
	public static int getRowSize(SetStoneCode[] setStoneCodes)
	{
		int size = 1;
		for(int i = 0; i < setStoneCodes.length; i++)
		{
			size*= setStoneCodes[i].getSize();
		}
		
		return size;
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
