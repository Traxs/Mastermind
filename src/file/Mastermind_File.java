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
 * @author		Nico
 * @version     %I%, %G%
 * @since       1.0
 */
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

// TODO: Auto-generated Javadoc
public class Mastermind_File
{
	
	/**
	 * saveMastermind will try to write the Object {@link mastermind.Mastermind} into the File System.
	 * <p>
	 * It will try to create a Data Stream which is {@link java.io.ObjectOutputStream}(Super class is {@link java.io.OutputStream})
	 * If it does fail the routine will throw an IOException which is Handled in the GUI.
	 * <p>
	 * At the end it will Close the Data Stream. 
	 *
	 * @param 		mastermind 		The current {@link mastermind.Mastermind} Object
	 * @param 		file 			File Location to put at
	 * @throws 		IOException 	Signals that an I/O exception has occurred.
	 * @see			mastermind.Mastermind
	 * @see			java.io.ObjectOutputStream
	 * @see			java.io.IOException
	 * @since		1.0
	 */
	public static void saveMastermind(Mastermind mastermind, File file) throws IOException
	{
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
		objectOutputStream.writeObject(mastermind);	
		objectOutputStream.close();
	}

	/**
	 * loadMastermind will try to load and saved Object of {@link mastermind.Mastermind} into the Memory of 
	 * the running Mastermind process.
	 * <p>
	 * It will create a Data Stream which is {@link java.io.ObjectInputStream}(Super class is {@link java.io.InputStream})
	 * If it does fail the routine will throw an {@link java.io.IOException} or an 
	 * {@link java.lang.ClassNotFoundException} which is Handled in the {@link view.Mastermind_View}.
	 * <p>
	 * At the end it will Close the Data Stream.
	 * 
	 * @param 	file	 				File Location to put at
	 * @return 	mastermind				{@link mastermind.Mastermind}
	 * @throws 	IOException 			Signals that an I/O exception has occurred.
	 * @throws 	ClassNotFoundException 	the class not found exception
	 * @see		mastermind.Mastermind
	 * @see		java.io.ObjectInputStream
	 * @see		java.io.IOException
	 * @see		java.lang.ClassNotFoundException
	 * @since	1.0
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
	 * <p>
	 * This method will check the current Configuration of the Display in {@link java.awt.GraphicsConfiguration}.
	 * <p>
	 * then it will compare the parameter <code>image</code> with the Display configuration
	 * if it is optimized for the current System the function will simply return it.
	 * if not it will create a new image and draw the old one in optimized settings on it.
	 * <p>
	 * At the end it will return the image.
	 *
	 * @param 	image		 	java.awt.image
	 * @return 	buffered image	java.awt.image.BufferedImage
	 * @see		java.awt.Graphics2D
	 * @see 	java.awt.image.BufferedImage
	 * @see 	java.awt.GraphicsConfiguration
	 * @see		java.awt.GraphicsEnvironment
	 * @since 	1.0
	 */
	private static BufferedImage toCompatibleImage(BufferedImage image)
	{
	    if(image==null)
	    	System.out.println("Image is null");
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
	
	/**
	 * Load image.
	 * <p>
	 * This method will load an image via ImageIO.read from the drive.
	 * The path where the file should be located will be transmitted via the parameter
	 * PathToFile which is a String.
	 * <p>
	 * If it finds a compatible image on the Path it will return it via {@link #toCompatibleImage(BufferedImage)} into 
	 * an BufferedImage.
	 * <p>
	 * if not it will catch an IOException and return <code>null</code>
	 * 
	 * @param 	PathToFile	 	the path to file
	 * @return 	BufferedImage 	java.awt.image.BufferedImage
	 * @see 	#toCompatibleImage(BufferedImage)
	 * @see 	javax.imageio.ImageIO
	 * @see		java.io.IOException
	 * @since 	1.0
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
	 * <p>
	 * this function will load an Icon out of resources (img/...) into Memory so it can be used.
	 * It simply returns the Pointer to the allocated Memory where the Icon sits.
	 * 
	 * @param 	PathToFile		the path to file
	 * @return 	ImageIcon		the image icon
	 * @see 	javax.swing.ImageIcon
	 * @since	1.0	
	 */
	public static ImageIcon loadIcon(String PathToFile) 
	{
		return new ImageIcon(Mastermind_File.class.getClassLoader().getResource("img/" + PathToFile));
	}
}
