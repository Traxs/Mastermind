package mastermind;

import java.util.ArrayList;

/**
 * Die Klasse KI wird benötigt um gegen den Computer zu spielen, um ein Tipp zu bekommen und 
 * für die Assistent-Funktion.
 * @author      Birk Kauer
 * @author      Raphael Pavlidis
 * @version     %I%, %G%
 * @since       1.0
 */
public class KI
{
	/** The color length. */
	private int colorLength;
	
	/** The code length. */
	private int codeLength;
	
	/** Bis zu welcher Zeile man schon berechnet hat. */
	private int rowCal;
	
	/** Mengen von Geheimcode-Mengen die schon berechent wurden. */
	private ArrayList<Integer[]> arrayList;
	
	/** Der Thread der die Berechnung im Hintergrund macht. */
	private Thread thread;
	
	/** The mastermind. */
	private Mastermind mastermind;
	
	/**
	 * Instantiates a new KI.
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
	 * Stop the KI.
	 */
	public void stop()
	{
		thread.interrupt();
	}

	/**
	 * Überprüft ob es möglich das dieser Code der Geheimcode ist in Abhängigkeit der vorherigene Eingaben.
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
                catch (InterruptKIException e)
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
                catch (InterruptKIException e)
                {
                    return;
                }
			}
		});

		thread.start();
	}
	
	/**
	 * Startet das Spiel gegen die KI.
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
                    catch (InterruptKIException e)
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
	 * Berechnet die Geheimcodes die durch die vorherigen Eingaben gemacht wurden sind.
	 * 
	 * @throws InterruptKIException wenn die KI während der Berechnung gestoppt wird.
	 * @see mastermind.InterruptKIException
	 */
	private void calculatePossibilities() throws InterruptKIException
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
     * Erstellt die Schnittmenge zwichen zwei Mengen von Geheimcode-Mengen.
     *
     * @param p1 die erste Menge
     * @param p2 die zweite Menge
     * @return die Schnittmenge von p1 und p2
	 * @throws InterruptKIException wenn die KI während der Berechnung gestoppt wird.
	 * @see mastermind.InterruptKIException
     */
    public ArrayList<Integer[]> intersectSetCodeArrayList(ArrayList<Integer[]> p1,
            ArrayList<Integer[]> p2) throws InterruptKIException
    {
        ArrayList<Integer[]> newPossibilities = new ArrayList<Integer[]>();
        Integer[] newPossibility;
        
        for(Integer[] p1Element : p1)
        {           
            if (thread.isInterrupted())
                throw new InterruptKIException();

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
	 * Gibt denn Wahrscheinlichsten Geheimcode in Abhängigkeit der vorherigen Eingaben.
	 *
	 * @return the highest probability
	 * @throws InterruptKIException wenn die KI während der Berechnung gestoppt wird.
	 * @see mastermind.InterruptKIException
	 */
	private int[] getHighestProbability() throws InterruptKIException
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
	 * Gibt eine Menge von Geheimcode-Menge die in Abhängigkeit der Codeeingabe und des Resultates(Rot, Weiß) vom Parameter row möglich sind. 
	 *
	 * @param row die Zeile welches die Codeeingabe und Resultat enthält nachwelchem die Geheimcode-Menge erstellt werden soll.
	 * @return die Geheimcode-Menge.
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
	 * Gibt die nächste Permutation an.
	 *
	 * @param permutation die Permutation.
	 * @return wahr, wenn es eine nächste Permutation gibt, ansonsten falsch.
	 */
	private static boolean nextPermutation(int[] permutation)
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
	 * Gibt die Position im Array der nächst größeren Zahl im Intervall [low, array.length] an.
	 *
	 * @param array im welchen gesucht werden soll.
	 * @param low die untere grenze des Intervalls.
	 * @return die Position der nächst größeren Zahl im Array.
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
	 * Sortiert das Array im Intervall [low, high] mittels Quick sort.
	 *
	 * @param array im welches sortiert werden soll.
	 * @param low die untere grenze des Intervalls.
	 * @param high die obere grenze des Intervalls.
	 */
	public static void quickSort(int[] array, int low, int high)
	{
		if (array == null || array.length == 0)
			return;
 
		if (low >= high)
			return;
 
		int middle = low + (high - low) / 2;
		int pivot = array[middle];
 
		int i = low, j = high;
		while (i <= j)
		{
			while (array[i] < pivot)
			{
				i++;
			}
 
			while (array[j] > pivot)
			{
				j--;
			}
 
			if (i <= j)
			{
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
				i++;
				j--;
			}
		}

		if (low < j)
			quickSort(array, low, j);
 
		if (high > i)
			quickSort(array, i, high);
	}
}
