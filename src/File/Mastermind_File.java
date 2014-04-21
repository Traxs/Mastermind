package File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import Mastermind.Mastermind;

public class Mastermind_File
{
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
		ObjectInputStream save = null;
		Mastermind mastermind = null;
		try
		{
			FileInputStream saveFile = new FileInputStream(path);
			try
			{
				save = new ObjectInputStream(saveFile);
				mastermind = (Mastermind) save.readObject();
				save.close();
			} catch (StreamCorruptedException streamException) 
			{    
		        return null;
		    }
			
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		
		return mastermind;
	}	
}
