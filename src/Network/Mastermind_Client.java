package Network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Mastermind_Client implements Runnable{
	  // The client socket
	  private static Socket clientSocket = null;
	  // The output stream
	  private static PrintStream os = null;
	  // The input stream
	  private static DataInputStream is = null;

	  private static BufferedReader inputLine = null;
	  private static boolean closed = false;
	
	public void Connect(String Server, int Port, String ClientName) throws UnknownHostException, IOException{
		 // The default port.
	    int defaultportNumber = 2222;
	    // The default host.
	    String defaulthost = "localhost";

	    if (Server == "" && Port == 0) {
	      System.out
	          .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
	              + "Now using host=" + defaulthost + ", portNumber=" + defaultportNumber);
	    } else {
	    }

	    /*
	     * Open a socket on a given host and port. Open input and output streams.
	     */
	    try {
	      clientSocket = new Socket(Server, Port);
	      inputLine = new BufferedReader(new InputStreamReader(System.in));
	      os = new PrintStream(clientSocket.getOutputStream());
	      is = new DataInputStream(clientSocket.getInputStream());
	    } catch (UnknownHostException e) {
	      System.err.println("Don't know about host " + Server);
	    } catch (IOException e) {
	      System.err.println("Couldn't get I/O for the connection to the host "
	          + Server);
	    }

	    /*
	     * If everything has been initialized then we want to write some data to the
	     * socket we have opened a connection to on the port portNumber.
	     */
	    if (clientSocket != null && os != null && is != null) {
	      try {

	        /* Create a thread to read from the server. */
	        new Thread(new Mastermind_Client()).start();
	        while (!closed) {
	        	os.println(ClientName);
	          //os.println(inputLine.readLine().trim());
	        }
	        /*
	         * Close the output stream, close the input stream, close the socket.
	         */
	        os.close();
	        is.close();
	        clientSocket.close();
	      } catch (IOException e) {
	        System.err.println("IOException:  " + e);
	      }
	    }
		
		
		
		
		}

	public void close() throws IOException{
		 	os.close();
	        is.close();
	        clientSocket.close();
	}
	
	public static void sendMessage(String test){
		os.println(test);
	}
	
	@Override
	public void run() {
		 /*
	     * Keep on reading from the socket till we receive "Bye" from the
	     * server. Once we received that then we want to break.
	     */
	    String responseLine;
	    try {
	      while ((responseLine = is.readLine()) != null) {
	        System.out.println(responseLine);
	        if (responseLine.indexOf("*** Bye") != -1)
	          break;
	      }
	      closed = true;
	    } catch (IOException e) {
	      System.err.println("IOException:  " + e);
	    }
	}
	
	
}