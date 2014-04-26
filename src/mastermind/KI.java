package mastermind;

import java.util.ArrayList;

public class KI
{
	private int colorLength;
	private int codeLength;
	private int rowCal;
	private ArrayList<SetStoneCode[]> arrayList;
	private Thread thread;
	private Mastermind mastermind;
	
	public KI(Mastermind mastermind, int colorLenth, int codeLength)
	{
		this.mastermind = mastermind;
		this.colorLength = colorLenth;
		this.codeLength = codeLength;
		this.rowCal = 0;
		this.arrayList = new ArrayList<SetStoneCode[]>();
		this.thread = new Thread();

		if(mastermind.getState() == State.playingKI)
		{
			startKI();
		}
	}
	
	public void getHint(final ArrayList<Row> rows)
	{
		if(thread.isAlive())
			return;
		
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				mastermind.addRow(calculateHighestProbability(mastermind.getRows()));
			}
		});

		thread.start();
	}
	
	private void startKI()
	{
		if(thread.isAlive())
			return;

		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while(mastermind.getState() == State.playingKI)
				{
					try
					{
						Thread.sleep(2000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					
					mastermind.addRow(calculateHighestProbability(mastermind.getRows()));
				}
			}
		});

		thread.start();
	}

	private int[] calculateHighestProbability(final ArrayList<Row> rows)
	{
		Row[] rowArray = (Row[])rows.toArray(new Row[0]);
		int[] stoneCodes = new int[codeLength];
		
		if(rowArray.length == 0)
		{
			for(int i = 0; i < codeLength; i++)
			{
				stoneCodes[i] = i % colorLength; 
			}

			return stoneCodes;
		}
		
		if(rowCal == 0)
		{
			arrayList = getPossibilities(rowArray[0]);
			rowCal = 1;
		}
		
		System.out.println("rowCal:" + rowCal + " rowArray:" + rowArray.length);
		
		for(int i = rowCal; i < rowArray.length; i++)
		{
			arrayList = unionPossibilities(arrayList, getPossibilities(rowArray[i]));
		}
		
		rowCal = rowArray.length;
		
		int index = 0, max = SetStoneCode.getRowSize(arrayList.get(0)), size = arrayList.size();
		int sum = max;
		for(int i = 1; i < size; i++)
		{
			sum += SetStoneCode.getRowSize(arrayList.get(i));
			if(max < SetStoneCode.getRowSize(arrayList.get(i)))
			{
				max = SetStoneCode.getRowSize(arrayList.get(i));
				index = i;
			}
		}
		
		System.out.println("Summe:" + sum);
		
		SetStoneCode[] setStoneCodes = arrayList.get(index);
		
		
		for(int i = 0; i < setStoneCodes.length; i++)
		{
			stoneCodes[i] = setStoneCodes[i].getFirst();
		}
		
		return stoneCodes;
	}
	
	private ArrayList<SetStoneCode[]> getPossibilities(Row row)
	{
		ArrayList<SetStoneCode[]> arrayList = new ArrayList<SetStoneCode[]>();
		int[] stoneCodes = row.getCode();
		int red = row.getRed(), white = row.getWhite();
		int[] permutation = new int[stoneCodes.length];

		for(int i = stoneCodes.length - 1; i > -1; i--)
		{
			if(red > 0)
			{
				permutation[i] = ResultCode.RED;
				red--;
			}
			else if(white > 0)
			{
				//permutation[i] = ResultCode.WHITE;
				permutation[i] = ResultCode.NOTHING;
				white--;
			}
			else
			{
				permutation[i] = ResultCode.NOTHING;
			}
		}
		
		arrayList.add(SetStoneCode.createRow(permutation, colorLength, stoneCodes));
		while(nextPermutation(permutation))
		{
			arrayList.add(SetStoneCode.createRow(permutation, colorLength, stoneCodes));
		}

		return arrayList;
	}
	
	private static ArrayList<SetStoneCode[]> unionPossibilities(ArrayList<SetStoneCode[]> p1,
			ArrayList<SetStoneCode[]> p2)
	{
		ArrayList<SetStoneCode[]> newPossibilities = new ArrayList<SetStoneCode[]>();
		SetStoneCode[][] p1Array = (SetStoneCode[][]) p1.toArray(new SetStoneCode[0][0]);
		SetStoneCode[][] p2Array = (SetStoneCode[][]) p2.toArray(new SetStoneCode[0][0]);
		SetStoneCode[] newPossibility;
		
		for(int i = 0; i < p1Array.length; i++)
		{
			for(int j = 0; j < p2Array.length; j++)
			{
				newPossibility = SetStoneCode.unionRow(p1Array[i], p2Array[j]);
				if(newPossibility != null)
				{
					newPossibilities.add(newPossibility);
				}
			}
		}
		return newPossibilities;
	}
	
	private static boolean nextPermutation(int []permutation)
	{
		int index, buffer;
		for(int i = permutation.length - 1; i > 0; i--)
		{
			if(permutation[i-1] < permutation[i])
			{
				index = KI.nextBigger(permutation, i);
				buffer = permutation[index];
				permutation[index] = permutation[i-1];
				permutation[i-1] = buffer;
				quickSort(permutation, i, permutation.length - 1);
				
				return true;
			}
		}
		
		
		return false;
	}
	
	private static int nextBigger(int[] array, int low)
	{
		int a = array[low-1], c = Integer.MAX_VALUE, cindex = -1;
		for(int i = low; i < array.length; i++)
		{
			if(array[i] > a)
			{
				if(c > array[i])
				{
					c = array[i];
					cindex = i;
				}
			}
		}
		
		return cindex;
	}
	
	public static void quickSort(int[] arr, int low, int high)
	{
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];
 
		int i = low, j = high;
		while (i <= j)
		{
			while (arr[i] < pivot)
			{
				i++;
			}
 
			while (arr[j] > pivot)
			{
				j--;
			}
 
			if (i <= j)
			{
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}

		if (low < j)
			quickSort(arr, low, j);
 
		if (high > i)
			quickSort(arr, i, high);
	}
}
