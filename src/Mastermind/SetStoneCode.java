package Mastermind;

import java.util.TreeSet;

public class SetStoneCode
{
	private TreeSet<StoneCode> set;
	
	private SetStoneCode(TreeSet<StoneCode> set)
	{
		this.set = set;
	}
	
	public SetStoneCode(boolean not, int colorLength, int code)
	{
		set = new TreeSet<StoneCode>();
		if(not)
		{
			set.add(StoneCode.getStoneCode(code));
		}
		else
		{
			for(int i = 0; i < colorLength; i++)
			{
				set.add(StoneCode.getStoneCode(i));
			}
			set.remove(StoneCode.getStoneCode(code));
		}
	}
	
	public static SetStoneCode[] createRow(int[] resultCodes, int colorLenght, StoneCode[] stoneCodes)
	{
		SetStoneCode[] setStoneCodes = new SetStoneCode[resultCodes.length];
		for(int i = 0; i < resultCodes.length; i++)
		{
			switch(resultCodes[i])
			{
				case ResultCode.RED:
					setStoneCodes[i] = new SetStoneCode(true, colorLenght, stoneCodes[i].getCode());
				break;
				case ResultCode.WHITE:
					setStoneCodes[i] = new SetStoneCode(false, colorLenght, stoneCodes[i].getCode());
				break;
				case ResultCode.NOTHING:
					setStoneCodes[i] = new SetStoneCode(false, colorLenght, stoneCodes[i].getCode());
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
		TreeSet<StoneCode> newSet = new TreeSet<StoneCode>();
		StoneCode[] set1Array = (StoneCode[]) set1.set.toArray(new StoneCode[0]);
		
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
	
	public StoneCode getFirst()
	{
		return set.first();
	}
}
