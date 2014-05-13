package mastermind;

import java.util.ArrayList;

public class KI
{
	private int colorLength;
	private int codeLength;
	private int rowCal;
	private ArrayList<Integer[]> arrayList;
	private Thread thread;
	private Mastermind mastermind;
	
	public KI(Mastermind mastermind, int colorLenth, int codeLength)
	{
		this.mastermind = mastermind;
		this.colorLength = colorLenth;
		this.codeLength = codeLength;
		this.rowCal = 0;
		this.arrayList = new ArrayList<Integer[]>();
		this.thread = new Thread();

		if(mastermind.getState() == State.playingKI)
		{
			startKI();
		}
	}
	
	public void stop()
	{
	    //thread.
	}
	
	public void isPossible(final int[] code)
	{
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				if(mastermind.getRowSize() == 0)
				{
				    mastermind.finishCheck(code, true);
					return;
				}

				calculatePossibilities();

				int i;
				for(Integer[] arrayListElement : arrayList)
				{
					for(i = 0; i < codeLength; i++)
					{
						if(!SetCode.contains(arrayListElement[i], code[i]))
						{
							break;
						}
					}
					
					if(i == codeLength)
					{
					    mastermind.finishCheck(code, true);
						return;
					}
				}

				mastermind.finishCheck(code, false);
			}
		});

		thread.start();
	}
	
	public void getHint()
	{
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				mastermind.finishHint(getHighestProbability());
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

					mastermind.addRow(getHighestProbability());
				}
			}
		});

		thread.start();
	}

	public boolean isKICalculating()
	{
	    return thread.isAlive();
	}
	
	private void calculatePossibilities()
	{
		final ArrayList<Row> rows = mastermind.getRows();

		Row[] rowArray = (Row[])rows.toArray(new Row[0]);

		if(rowCal == 0)
		{
			arrayList = getPossibilities(rowArray[0]);
			rowCal = 1;
		}

		for(int i = rowCal; i < rowArray.length; i++)
		{
			arrayList = SetCode.unionSetCodeArrayList(arrayList, 
			        getPossibilities(rowArray[i]));
		}
	}

	private int[] getHighestProbability()
	{
		int[] stoneCodes = new int[codeLength];
		
		if(mastermind.getRowSize() == 0)
		{
			for(int i = 0; i < codeLength; i++)
			{
				stoneCodes[i] = i % colorLength; 
			}

			return stoneCodes;
		}

		calculatePossibilities();

		Integer[] setStoneCodesMax = null;
		long max = 0, buffer;

		for(Integer[] setStoneCodesBuffer : arrayList)
		{
			buffer = SetCode.getRowSize(setStoneCodesBuffer);
			if(max < buffer)
			{
				setStoneCodesMax = setStoneCodesBuffer;
				max = buffer;
			}
		}
		

		for(int i = 0; i < setStoneCodesMax.length; i++)
		{
			stoneCodes[i] =  SetCode.getFirst(setStoneCodesMax[i]);
		}

		return stoneCodes;
	}
	
	private ArrayList<Integer[]> getPossibilities(Row row)
	{
		ArrayList<Integer[]> arrayList = new ArrayList<Integer[]>();
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
				permutation[i] = ResultCode.NOTHING;
				white--;
			}
			else
			{
				permutation[i] = ResultCode.NOTHING;
			}
		}

		do
		{
			arrayList.add(SetCode.createRow(permutation, colorLength, stoneCodes));
		}
		while(nextPermutation(permutation));

		return arrayList;
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
