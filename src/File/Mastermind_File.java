package File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Mastermind.Mastermind;

public class Mastermind_File
{
	public static void Save_Mastermind(Mastermind mastermind, File file) throws IOException
	{
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
		objectOutputStream.writeObject(mastermind);	
		objectOutputStream.close();
	}

	public static Mastermind Load_Mastermind(File file) throws IOException, ClassNotFoundException
	{	
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
		Mastermind mastermind = (Mastermind)objectInputStream.readObject();
		objectInputStream.close();

		return mastermind;
	}	
}
