/*

 */
package mastermind;

/**
 * The Class SetCode.
 * @author      Birk Kauer
 * @author      Raphael Pavlidis
 * @version     %I%, %G%
 * @since       1.0
 */
public class SetCode
{
	
	/** The Constant set. */
	private static final int[] set = { 
		0b000000000000001, 0b000000000000010, 0b000000000000100, 
		0b000000000001000, 0b000000000010000, 0b000000000100000,
		0b000000001000000, 0b000000010000000, 0b000000100000000, 
		0b000001000000000, 0b000010000000000, 0b000100000000000, 
		0b001000000000000, 0b010000000000000, 0b100000000000000};
	
	/** The Constant setAll. */
	private static final int[] setAll = {	  0b000000000000000,
		0b000000000000001, 0b000000000000011, 0b000000000000111, 
		0b000000000001111, 0b000000000011111, 0b000000000111111,
		0b000000001111111, 0b000000011111111, 0b000000111111111, 
		0b000001111111111, 0b000011111111111, 0b000111111111111, 
		0b001111111111111, 0b011111111111111, 0b111111111111111};
	
	/**
	 * Verhindert das eine SetCode Instanz erstellt wird.
	 */
	private SetCode(){}

	/**
	 * Erstellt eine Geheimcode-Menge(Array von Mengen) in Abhängigkeit der Parameter.
	 *
	 * @param resultCodes the result codes
	 * @param colorLength the color length
	 * @param stoneCodes the stone codes
	 * @return Geheimcode-Menge.
	 */
	public static Integer[] createRow(int[] resultCodes, int colorLength, int[] stoneCodes)
	{
		Integer[] setCodes = new Integer[resultCodes.length];
		for(int i = 0; i < resultCodes.length; i++)
		{
			setCodes[i] = resultCodes[i] == ResultCode.RED ? set[stoneCodes[i]] 
					: setAll[colorLength] ^ set[stoneCodes[i]];
		}

		return setCodes;
	}

	/**
	 * Gibt die Schnittmenge von zwei Geheimcode-Mengen(Array von Mengen).
	 *
	 * @param set1 die erste Geheimcode-Mengen.
	 * @param set2 die zweite Geheimcode-Mengen.
	 * @return die Schnittmenge von set1 und set2.
	 */
	public static Integer[] intersectRow(Integer[] set1, Integer[] set2)
	{
		Integer[] newArray = new Integer[set1.length];
		int newSet;
		for(int i = 0; i < set1.length; i++)
		{
			newSet = set1[i] & set2[i];

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

	/**
	 * Gibt die Anzahl der Elemente in der Geheimcode-Menge(Array von Mengen).
	 *
	 * @param set die Geheimcode-Menge.
	 * @return die Anzahl der Elemente in der Geheimcode-Menge.
	 */
	public static long getRowSize(Integer[] set)
	{
		long size = 1;

		for(int column : set)
		{
			size *= getSize(column);
		}

		return size;
	}

	/**
	 * Überpruft ob die Menge das Element enthält.
	 *
	 * @param set die Menge
	 * @param element das Element welches überpruft werden soll ob es in der Menge drin ist.
	 * @return true, wenn die Menge das Element enthält.
	 */
	public static boolean contains(int set, int element)
	{
		return (set & SetCode.set[element]) != 0;
	}

	/**
	 * Gibt die Anzahl der Elemente in der Menge.
	 *
	 * @param set die Menge
	 * @return die Anzahl der Elemente in der Menge.
	 */
	public static int getSize(int set)
	{
		int size = 0;
		while(set != 0)
		{
			if(set % 2 == 1)
				size++;

			set >>= 1;
		}
		return size;
	}

	/**
	 * Gibt das erste Element der Menge.
	 *
	 * @param set die Menge.
	 * @return das erste Element, wenn die Menge leer ist gibt es -1 zurück.
	 */
	public static int getFirst(int set)
	{
		for(int i = 0; i < 15; i++)
		{
			if(set % 2 == 1)
				return i;
			
			set >>= 1;
		}

		return -1;
	}
}
