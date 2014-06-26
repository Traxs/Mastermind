package file;

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

import mastermind.Mastermind;

/**
 * Mastermind_File is an class for communicating with the current File System.
 * The class gets every Information it needs such as the Location where to load or put the File
 * as well as what to specifically save.
 * <p>
 * This class is using the Method to save Objects into a File which is possible via Serialization.
 * Objects who shouldn't be saved will be declared as Transient.
 * <p>
 * This class as well will load Images from Resources or the current HardDrive and compile it to
 * an usable Image or Icon.
 *<p>
 *<p>
 * @author      Birk Kauer
 * @author      Raphael Pavlidis
 * @version     %I%, %G%
 * @since       1.0
 */
public class Mastermind_File
{
	
	/**
	 * Save mastermind will try to write the Object {@link Mastermind} into the File System.
	 * <p>
	 * It will try to create a Data Stream which is ObjectOutputStream(Super class is OutputStream)
	 * If it does fail the routine will throw an IOException which is Handled in the GUI.
	 * <p>
	 * At the end it will Close the Data Stream. 
	 *
	 * @param 		mastermind the mastermind
	 * @param 		file the file
	 * @throws 		IOException Signals that an I/O exception has occurred.
	 * @see			Mastermind
	 * @since		1.0
	 */
	public static void saveMastermind(Mastermind mastermind, File file) throws IOException
	{
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
		objectOutputStream.writeObject(mastermind);	
		objectOutputStream.close();
	}

	/**
	 * Load mastermind.q	
	 *
	 * @param file the file
	 * @return the mastermind
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Mastermind loadMastermind(File file) throws IOException, ClassNotFoundException
	{	
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
		Mastermind mastermind = (Mastermind) objectInputStream.readObject();
		objectInputStream.close();

		return mastermind;
	}	

	/**
	 * To compatible image.
	 *
	 * @param image the image
	 * @return the buffered image
	 */
	private static BufferedImage toCompatibleImage(BufferedImage image)
	{
	    if(image==null)
	    	System.out.println("Image is null");
	    // obtain the current system graphical settings
	    GraphicsConfiguration gfx_config = GraphicsEnvironment.
	            getLocalGraphicsEnvironment().getDefaultScreenDevice().
	            getDefaultConfiguration();


	    // if image is already compatible and optimized for current system 
	    // settings, simply return it
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

	    return new_image; 
	}
	
	/**
	 * Load image.
	 *
	 * @param PathToFile the path to file
	 * @return the buffered image
	 */
	public static BufferedImage loadImage(String PathToFile)
	{
		try
		{
			return toCompatibleImage(ImageIO.read(Mastermind_File.class.getClassLoader().getResource("img/" + PathToFile)));
		} 
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Load icon.
	 *
	 * @param PathToFile the path to file
	 * @return the image icon
	 */
	public static ImageIcon loadIcon(String PathToFile) 
	{
		return new ImageIcon(Mastermind_File.class.getClassLoader().getResource("img/" + PathToFile));
	}
}
