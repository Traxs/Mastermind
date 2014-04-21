package File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Mastermind.Mastermind;

public class Mastermind_File
{
	/* SO HALLo*/
	public static void Save_Mastermind(Mastermind mastermind, String path)
	{
		try
		{
			FileOutputStream saveFile = new FileOutputStream(path);
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(mastermind);	
			save.close();
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
	}

	public static Mastermind Load_Mastermind(String path)
	{	
		Mastermind mastermind = null;
		try
		{
			FileInputStream saveFile = new FileInputStream(path);
			ObjectInputStream save = new ObjectInputStream(saveFile);
			mastermind = (Mastermind) save.readObject();
			save.close();
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		
		return mastermind;
	}	
}
