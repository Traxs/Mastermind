package View;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import File.Mastermind_File;
import Mastermind.Mastermind;
import Mastermind.Row;
import Mastermind.StoneCode;
import Network.Mastermind_Client;
import Network.Mastermind_Server;

import javax.swing.Box;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

public class Mastermind_View extends JFrame
{
	private static final long serialVersionUID = -809208636941136548L;
	private JScrollPane scrollPane_Playfield;
	private Box verticalBox_Playfield;
	private Mastermind mastermind;
	private Box horizontalBox_SelectionStone;
	private JLabel jLabel_Row;
	private int rowLength;
	private JScrollPane scrollPane_Secret;
	private Box horizontalBox_Secret;
	private Box verticalBox_Secret;
	private Stone_View stoneSelection_View;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new Mastermind_View();
			}
		});
	}

	public Mastermind_View()
	{
		this.mastermind = new Mastermind(this);
		this.rowLength = mastermind.getRowLength();
		start();
	}

	public Mastermind_View(int codeLength, int rowLength, int colorLength)
	{
		this.mastermind = new Mastermind(this, codeLength, rowLength, colorLength);
		this.rowLength = rowLength;
		start();
	}
	
	public void start()
	{
		setTitle("Mastermind");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(590, 620);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//*** Playfield ***
		{
			scrollPane_Playfield = new JScrollPane();
			scrollPane_Playfield.setBounds(12, 12, 450, 410);
			contentPane.add(scrollPane_Playfield);
		
			verticalBox_Playfield = Box.createVerticalBox();
			scrollPane_Playfield.setViewportView(verticalBox_Playfield);
		}

		JButton button_Add = new JButton("Add");
		button_Add.setBounds(474, 434, 98, 25);
		button_Add.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int Length = horizontalBox_SelectionStone.getComponentCount();
				StoneCode[] NewCode = new StoneCode[Length];
				for(int i = 0; i < Length; i++)
				{
					NewCode[i] = ((Stone_View)horizontalBox_SelectionStone.getComponent(i)).getCode();
				}
				
				mastermind.addRow(NewCode);
			}
		});
		JButton button_Tipp = new JButton("Tipp");
		button_Tipp.setBounds(474, 300, 98, 25);
		button_Tipp.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				mastermind.test();
			}
		});
		JButton button_Server = new JButton("Server");
		button_Server.setBounds(474, 200, 98, 25);
		button_Server.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				//SERVER
				Thread t = new Thread(new Runnable()
				{
				    public void run()
				    {
				    	new Mastermind_Server().Start_Server(2222);  
				    }
				});
				t.start();
				
			}
		});
		JButton button_Client = new JButton("Client");
		button_Client.setBounds(474, 250, 98, 25);
		button_Client.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				//CLIENT
				Thread t = new Thread(new Runnable()
				{
				    public void run()
				    {
				    	try {
							new Mastermind_Client().Connect("localhost", 2222, "ClientName");
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    }
				});
				t.start();
				
				
			}
		});
		JButton button_Send = new JButton("Send");
		button_Send.setBounds(474, 150, 98, 25);
		button_Send.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				//CLIENT
				Thread t = new Thread(new Runnable()
				{
				    public void run()
				    {
				    	Mastermind_Client.sendMessage("SO HALLO");
				    }
				});
				t.start();
				
				
			}
		});
		contentPane.add(button_Send);
		contentPane.add(button_Server);
		contentPane.add(button_Client);
		contentPane.add(button_Tipp);
		contentPane.add(button_Add);
		
		jLabel_Row = new JLabel("0/" + rowLength);
		jLabel_Row.setBounds(474, 12, 80, 30);
		contentPane.add(jLabel_Row);
		
		//*** Selection Box ***
		{
			JScrollPane scrollPane_Selection = new JScrollPane();
			scrollPane_Selection.setBounds(12, 434, 450, 58);
			contentPane.add(scrollPane_Selection);
		
			Box verticalBox_Selection = Box.createVerticalBox();
			scrollPane_Selection.setViewportView(verticalBox_Selection);
		
			horizontalBox_SelectionStone = Box.createHorizontalBox();
			verticalBox_Selection.add(horizontalBox_SelectionStone);
			
			StoneSelection_View stoneSelection_View;
			int Colms = mastermind.getCodeLength();

			for(int i = 0; i < Colms; i++)
			{
				stoneSelection_View = new StoneSelection_View(StoneCode.getStoneCode(0), mastermind.getColorLength());
				horizontalBox_SelectionStone.add(stoneSelection_View);
			}
		}
		
		//*** Secret Box ***
		{
			scrollPane_Secret = new JScrollPane();
			scrollPane_Secret.setBounds(12, 500, 450, 58);
			contentPane.add(scrollPane_Secret);
		
			verticalBox_Secret = Box.createVerticalBox();
			scrollPane_Secret.setViewportView(verticalBox_Secret);
		
			horizontalBox_Secret = Box.createHorizontalBox();
			verticalBox_Secret.add(horizontalBox_Secret);
			
			
			StoneCode[] SecretCode = mastermind.getSecretCode();

			for(int i = 0; i < SecretCode.length; i++)
			{
				stoneSelection_View = new Stone_View(SecretCode[i]);
				horizontalBox_Secret.add(stoneSelection_View);
			}
		}
		
		//*** JMenuBar ***
		{
			JMenuBar jMenuBar = new JMenuBar();
			JMenuItem jMenuItem_Save = new JMenuItem("Save");
			jMenuItem_Save.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					JFileChooser jFileChooser = new JFileChooser();
					jFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

					if(jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						try
						{
							Mastermind_File.Save_Mastermind(mastermind, jFileChooser.getSelectedFile());
						}
						catch (IOException e)
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error");
						}
					}
				}
			});

			JMenuItem jMenuItem_load = new JMenuItem("Load");
			jMenuItem_load.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					JFileChooser jFileChooser = new JFileChooser();
					jFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

					if(jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						Mastermind mastermindFile;
						try
						{
							mastermindFile = Mastermind_File.Load_Mastermind(jFileChooser.getSelectedFile());
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Wrong or Corrupted File");
							return;
						}
						catch (IOException e)
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Wrong or Corrupted File");
							return;
						}

						mastermind.setMastermind(mastermindFile);
						update();
					}
				}
			});

			jMenuBar.add(jMenuItem_Save);
			jMenuBar.add(jMenuItem_load);

			setJMenuBar(jMenuBar);
		}


		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void addRow(Row newRow)
	{
		StoneCode[] Code = newRow.getCode();
		Box horizontalBox = Box.createHorizontalBox();

		for(int i = 0; i < Code.length; i++)
		{
			horizontalBox.add(new Stone_View(Code[i]));
		}

		horizontalBox.add(new Result_View(newRow));

		verticalBox_Playfield.add(horizontalBox);
		jLabel_Row.setText(verticalBox_Playfield.getComponentCount() + "/" + rowLength);
		scrollPane_Playfield.updateUI();
	}
	
	// UPDATING after LOADING
	public void update()
	{
		verticalBox_Playfield.removeAll();
		jLabel_Row.setText(0 + "/" + rowLength);
		
		for(int i = 0; i < mastermind.getRowSize();i++)
		{
			StoneCode[] Code = mastermind.getRow(i).getCode();
			Box horizontalBox = Box.createHorizontalBox();

			for(int a = 0; a < Code.length; a++)
			{
				horizontalBox.add(new Stone_View(Code[a]));
			}

			horizontalBox.add(new Result_View(mastermind.getRow(i)));
			verticalBox_Playfield.add(horizontalBox);
			jLabel_Row.setText(verticalBox_Playfield.getComponentCount() + "/" + rowLength);
		}

		verticalBox_Secret.removeAll();
		horizontalBox_Secret.removeAll();
		horizontalBox_Secret = Box.createHorizontalBox();
		verticalBox_Secret.add(horizontalBox_Secret);
		StoneCode[] SecretCode = mastermind.getSecretCode();
		stoneSelection_View.removeAll();

		for(int i = 0; i < SecretCode.length; i++)
		{
			stoneSelection_View = new Stone_View(SecretCode[i]);
			horizontalBox_Secret.add(stoneSelection_View);
		}

		scrollPane_Secret.updateUI();
		scrollPane_Playfield.updateUI();
	}

	public void setGameLose()
	{
		JOptionPane.showMessageDialog(null, "Lose");
	}
	
	public void setGameWin()
	{
		JOptionPane.showMessageDialog(null, "Win");
	}
}
