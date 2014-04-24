package File;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Mastermind.Mastermind;

public class Mastermind_File
{
	public static void saveMastermind(Mastermind mastermind, File file) throws IOException
	{
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
		objectOutputStream.writeObject(mastermind);	
		objectOutputStream.close();
	}

	public static Mastermind loadMastermind(File file) throws IOException, ClassNotFoundException
	{	
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
		Mastermind mastermind = (Mastermind)objectInputStream.readObject();
		objectInputStream.close();

		return mastermind;
	}	
	
	//Bugfix gegen Lag
	private static BufferedImage toCompatibleImage(BufferedImage image)
	{
	    if(image==null)System.out.println("Image is null");
	    // obtain the current system graphical settings
	    GraphicsConfiguration gfx_config = GraphicsEnvironment.
	            getLocalGraphicsEnvironment().getDefaultScreenDevice().
	            getDefaultConfiguration();

	    /*
	     * if image is already compatible and optimized for current system 
	     * settings, simply return it
	     */
	    if (image.getColorModel().equals(gfx_config.getColorModel()))
	            return image;

	    // image is not optimized, so create a new image that is
	    BufferedImage new_image = gfx_config.createCompatibleImage(
	                    image.getWidth(), image.getHeight(), image.getTransparency());

	    // get the graphics context of the new image to draw the old image on
	    Graphics2D g2d = (Graphics2D) new_image.getGraphics();

	    // actually draw the image and dispose of context no longer needed
	    g2d.drawImage(image, 0, 0, null);
	    g2d.dispose();

	    // return the new optimized image
	    return new_image; 
	}
	
	public static BufferedImage loadImage(String PathToFile)
	{
		try {
			return toCompatibleImage(ImageIO.read(Mastermind_File.class.getClassLoader().getResource("img/" + PathToFile)));
		} catch(IOException e) {
			System.out.println("img/" + PathToFile);
			//e.printStackTrace();
			return null;
		}
	}
	
	public static ImageIcon loadIcon(String PathToFile) 
	{
		return new ImageIcon(Mastermind_File.class.getClassLoader().getResource("img/" + PathToFile));
	}
}
