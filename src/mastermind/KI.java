package mastermind;

import java.util.ArrayList;

/**
 * The Class KI is used to play against the computer, getting a tipp to play with or for 
 * complete assistent function.
 * 
 * @author      Birk Kauer
 * @author      Raphael Pavlidis
 * @since       1.0
 */
public class KI
{
	/** The color length. */
	private int colorLength;
	
	/** The code length. */
	private int codeLength;
	
	/** 
	 * Displays the row which was already calculated.
	 * */
	private int rowCal;
	
	/**  
	 * Sets of SecretCode-sets which were already calculated.
	 * */
	private ArrayList<Integer[]> arrayList;
	
	/** 
	 * Thread for background calculation.
	 * */
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
	 * Checks if the current parameter is in the SecretCode-sets which were calculated by previous Inputs.
	 * 
	 * @param code 		the Input Code to check.
	 */
	public void isPossible(final int[] code)
	{
	    // Erzeuge einen neuen Thread
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
			    // Überprüft ob keine Eingaben gemacht wurden sind
				if(mastermind.getRowSize() == 0)
				{
				    // Akzeptiere die Eingabe code[] als möglich
				    mastermind.finishCheck(code, true);
					return;
				}

				try
                {
				    // Berechne die Geheimcode-Menge.
                    calculatePossibilities();
                }
                catch (InterruptKIException e)
                {
                    return;
                }
				
				// Überpruft ob code[] in der Geheimcode-Menge ist
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
                        // code[] ist in der Geheimcode-Menge
                        // Akzeptiere die Eingabe code[] als möglich
                        mastermind.finishCheck(code, true);
                        return;
                    }
				}

				// code[] ist nicht in der Geheimcode-Menge
				// Akzeptiere die Eingabe code[] als nicht möglich
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
	    // Erzeuge einen neuen Thread
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
                {
				    // Berechne den Wahrscheinlichste Geheimcode
				    // Zeige den errechneten Geheimcode als Tipp
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
	 * Initialise a game against the KI
	 */
	public void startKI()
	{
	    // Erzeuge einen neuen Thread
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
			    // wiederhole solange bis das Spiel beendet ist
				while(mastermind.getState() == State.playingKI)
				{
					if (thread.isInterrupted())
					    return;

                    try
                    {
                        // warte 2 Sekunden
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    try
                    {
                        // Berechne den Wahrscheinlichste Geheimcode
                        // Gebe den errechneten Geheimcode als Eingabe
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
	 * Calculates the secretcodes based by the previous inputs.
	 * @throws 	InterruptKIException 	will throw the Exception when the KI is forced to stop while calculating. 
	 * @see 	mastermind.InterruptKIException
	 */
	private void calculatePossibilities() throws InterruptKIException
	{
		final ArrayList<Row> rows = mastermind.getRows();

		Row[] rowArray = (Row[])rows.toArray(new Row[0]);
		
		// Überpruft ob keine Eingaben(Zeilen) errechnet wurden
		if(rowCal == 0)
		{
			arrayList = getPossibilities(rowArray[0]);
			rowCal = 1;
		}

		// Berechene alle noch offene Eingaben(Zeilen)
		for(; rowCal < rowArray.length; rowCal++)
		{
		    // Füge die neue Geheimcode-Menge mit der Schnittmengen Operation ein
			arrayList = intersectSetCodeArrayList(arrayList, 
			        getPossibilities(rowArray[rowCal]));
		}
	}
	
	/**
     * Creates the intersection between the two sets of SecretCode.
     * @param 	p1 	first set
     * @param 	p2 	second set
     * @return 	the intersection of p1 and p2
	 * @throws 	InterruptKIException 	will throw the Exception when the KI is forced to stop while calculating. 
	 * @see 	mastermind.InterruptKIException
     */
    public ArrayList<Integer[]> intersectSetCodeArrayList(ArrayList<Integer[]> p1,
            ArrayList<Integer[]> p2) throws InterruptKIException
    {
        ArrayList<Integer[]> newPossibilities = new ArrayList<Integer[]>();
        Integer[] newPossibility;
        
        // Jedes Element von p1
        for(Integer[] p1Element : p1)
        {       
            if (thread.isInterrupted())
                throw new InterruptKIException();
            
            // und Jedes Element von p2
            for(Integer[] p2Element : p2)
            {
                // wird mit der Schnitmenge Operation vereinigt
                newPossibility = SetCode.intersectRow(p1Element, p2Element);
                
                // Überprüft ob die Vereinigung nicht Null ergibt
                // (Ergibt Null wenn man die Elemente nicht zusammen führen kann)
                if(newPossibility != null)
                {
                    // Wenn ja dann in der Neuen Menge aufnehmen
                    newPossibilities.add(0, newPossibility);
                }
            }
        }

        return newPossibilities;
    }

	/**
	 * Returns 	the highest probability of the secret code based on previous inputs.
	 * @return 	the highest probability
	 * @throws 	InterruptKIException 	will throw the Exception when the KI is forced to stop while calculating. 
	 * @see 	mastermind.InterruptKIException
	 */
	private int[] getHighestProbability() throws InterruptKIException
	{
		int[] stoneCodes = new int[codeLength];
		
		// Überprüft ob keine Eingaben gemacht wurden sind.
		if(mastermind.getRowSize() == 0)
		{
		    // Wenn ja dann Rate.
			for(int i = 0; i < codeLength; i++)
			{
				stoneCodes[i] = i % colorLength; 
			}

			return stoneCodes;
		}

		// Ansonsten berechne alle Möglichkeiten für denn Geheimcode.
		calculatePossibilities();

		// Berechne denn Wahrscheinlichste Geheimcode aus denn Möglichkeiten
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
	 * Returns a Set of SecretCode-set which is based on the codeinput and the result of red and white pins of the parameter row.
	 * @param 	row 	the row which includes the codeinput and the result which will generates a secretcode-set.
	 * @return the secretcode-set.
	 */
	private ArrayList<Integer[]> getPossibilities(Row row)
	{
	    // Erzeuge die Initial-Permutation
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
		    // Erzeuge eine Geiheimcode-Menge in Abhängigkeit der Permutation
			arrayList.add(SetCode.createRow(permutation, colorLength, stoneCodes));
		}
		while(nextPermutation(permutation));// Wiederhole bis es keine Permutationen mehr gibt

		return arrayList;
	}
	
	/**
	 * Will return the next permutation.
	 * @param 	permutation 	the permutation.
	 * @return 	will deliver true if there is another permutation otherwise false.
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
	 * This Method will be used by the nextPermutation Method.
	 * Delivers the position of the next increased number of the intervall [low,array.length].
	 * 
	 * @param The array which the method will look into.
	 * @param low the lower border of the array.
	 * @return the position of the increased number in the array.
	 * @see #nextPermutation(int[])
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
	 * will sort the array inside of the intervall [low, high] by using quick sort.
	 * @param 	array 	array to be sorted.
	 * @param 	low 	lower border of the intervall.
	 * @param 	high 	upper border of the intervall.
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
