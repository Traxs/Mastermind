/*
 * 
 */
package mastermind;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class KI.
 */
public class KI
{
	
	/** The color length. */
	private int colorLength;
	
	/** The code length. */
	private int codeLength;
	
	/** The row cal. */
	private int rowCal;
	
	/** The array list. */
	private ArrayList<Integer[]> arrayList;
	
	/** The thread. */
	private Thread thread;
	
	/** The mastermind. */
	private Mastermind mastermind;
	
	/**
	 * Instantiates a new ki.
	 *
	 * @param mastermind the mastermind
	 * @param colorLenth the color lenth
	 * @param codeLength the code length
	 */
	public KI(Mastermind mastermind, int colorLenth, int codeLength)
	{
		this.mastermind = mastermind;
		this.colorLength = colorLenth;
		this.codeLength = codeLength;
		this.rowCal = 0;
		this.arrayList = new ArrayList<Integer[]>();
		this.thread = new Thread();
	}
	
	/**
	 * Stop.
	 */
	public void stop()
	{
		thread.interrupt();
	    //thread.
	}

	/**
	 * Checks if is possible.
	 *
	 * @param code the code
	 */
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

				try
                {
                    calculatePossibilities();
                }
                catch (InterruptException e)
                {
                    return;
                }
				
				int i;
				for(Integer[] arrayListElement : arrayList)
				{
					if (thread.isInterrupted())
					    return;
					
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
	
	/**
	 * Gets the hint.
	 *
	 * 
	 */
	public void getHint()
	{
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
                {
                    mastermind.finishHint(getHighestProbability());
                }
                catch (InterruptException e)
                {
                    return;
                }
			}
		});

		thread.start();
	}
	
	/**
	 * Start ki.
	 */
	public void startKI()
	{
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while(mastermind.getState() == State.playingKI)
				{
					if (thread.isInterrupted())
					        return;

                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    try
                    {
                        mastermind.addRow(getHighestProbability());
                    }
                    catch (InterruptException e)
                    {
                        return;
                    }					
				 }
			}
		});

		thread.start();
	}

	/**
	 * Checks if is KI calculating.
	 *
	 * @return true, if is KI calculating
	 */
	public boolean isKICalculating()
	{
	    return thread.isAlive();
	}
	
	/**
	 * Calculate possibilities.
	 * @throws InterruptException 
	 */
	private void calculatePossibilities() throws InterruptException
	{
		final ArrayList<Row> rows = mastermind.getRows();

		Row[] rowArray = (Row[])rows.toArray(new Row[0]);

		if(rowCal == 0)
		{
			arrayList = getPossibilities(rowArray[0]);
			rowCal = 1;
		}

		for(; rowCal < rowArray.length; rowCal++)
		{
			arrayList = intersectSetCodeArrayList(arrayList, 
			        getPossibilities(rowArray[rowCal]));
		}
	}
	
	/**
     * Intersect set code array list.
     *
     * @param p1 the p1
     * @param p2 the p2
     * @param thread the thread
     * @return the array list
	 * @throws InterruptException 
     */
    public ArrayList<Integer[]> intersectSetCodeArrayList(ArrayList<Integer[]> p1,
            ArrayList<Integer[]> p2) throws InterruptException
    {
        ArrayList<Integer[]> newPossibilities = new ArrayList<Integer[]>();
        Integer[] newPossibility;
        
        for(Integer[] p1Element : p1)
        {           
            if (thread.isInterrupted())
                throw new InterruptException();

            for(Integer[] p2Element : p2)
            {   
                newPossibility = SetCode.intersectRow(p1Element, p2Element);
                if(newPossibility != null)
                {
                    newPossibilities.add(0, newPossibility);
                }
            }
        }

        return newPossibilities;
    }

	/**
	 * Gets the highest probability.
	 *
	 * @return the highest probability
	 * @throws InterruptException 
	 */
	private int[] getHighestProbability() throws InterruptException
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
	
	/**
	 * Gets the possibilities.
	 *
	 * @param row the row
	 * @return the possibilities
	 */
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
	
	/**
	 * Next permutation.
	 *
	 * @param permutation the permutation
	 * @return true, if successful
	 */
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
	
	/**
	 * Next bigger.
	 *
	 * @param array the array
	 * @param low the low
	 * @return the int
	 */
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
	
	/**
	 * Quick sort.
	 *
	 * @param arr the arr
	 * @param low the low
	 * @param high the high
	 */
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
