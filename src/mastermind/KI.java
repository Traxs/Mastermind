package mastermind;

import java.util.ArrayList;

public class KI
{
	private int colorLength;
	private int codeLength;
	private int rowCal;
	private ArrayList<Short[]> arrayList;
	private Thread thread;
	private Mastermind mastermind;
	private long timestap;
	
	public KI(Mastermind mastermind, int colorLenth, int codeLength)
	{
		this.mastermind = mastermind;
		this.colorLength = colorLenth;
		this.codeLength = codeLength;
		this.rowCal = 0;
		this.arrayList = new ArrayList<Short[]>();
		this.thread = new Thread();

		if(mastermind.getState() == State.playingKI)
		{
			startKI();
		}
	}
	
	public void isPossible(final int[] code)
	{
		if(thread.isAlive())
			return;
		
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				if(mastermind.getRowSize() == 0)
				{
					mastermind.addRow(code);
					return;
				}

				calculatePossibilities();

				int i;
				for(Short[] arrayListElement : arrayList)
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
						mastermind.addRow(code);
						return;
					}
				}
	
				System.out.println("FEHLER");
			}
		});

		thread.start();
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
				timestap = System.currentTimeMillis();
				mastermind.addRow(getHighestProbability());
				System.out.println("TIME:" + (System.currentTimeMillis() - timestap));
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
			arrayList = SetCode.unionSetStoneCodeArrayList(arrayList, getPossibilities(rowArray[i]));
		}
		
		rowCal = rowArray.length;
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

		Short[] setStoneCodesMax = null;
		long max = 0, buffer, sum = 0;

		for(Short[] setStoneCodesBuffer : arrayList)
		{
			buffer = SetCode.getRowSize(setStoneCodesBuffer);
			sum += buffer;
			if(max < buffer)
			{
				setStoneCodesMax = setStoneCodesBuffer;
				max = buffer;
			}
		}

		System.out.println("Summe:" + sum + " ArrayList:" + arrayList.size());

		for(int i = 0; i < setStoneCodesMax.length; i++)
		{
			stoneCodes[i] =  SetCode.getFirst(setStoneCodesMax[i]);
		}

		return stoneCodes;
	}
	
	private ArrayList<Short[]> getPossibilities(Row row)
	{
		ArrayList<Short[]> arrayList = new ArrayList<Short[]>();
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
		
		arrayList.add(SetCode.createRow(permutation, colorLength, stoneCodes));
		while(nextPermutation(permutation))
		{
			arrayList.add(SetCode.createRow(permutation, colorLength, stoneCodes));
		}

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
