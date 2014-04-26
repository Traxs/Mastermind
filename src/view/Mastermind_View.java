package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.Box;
import javax.swing.JButton;

import mastermind.Mastermind;
import mastermind.Row;
import mastermind.State;
import file.Mastermind_File;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Mastermind_View extends JFrame
{
	private static final long serialVersionUID = -809208636941136548L;
	private Mastermind mastermind;
	private int rowLength;
	private int codeLength;
	private int colorLength;
	private Box verticalBox_Playfield;
	private JScrollPane scrollPane_Playfield;
	private JScrollPane scrollPane_Selection;
	private JScrollPane scrollPane_Secret;
	private Box horizontalBox_SelectionStone;
	private Box horizontalBox_Secret;
	private JLabel jLabel_Row;
	private JButton jbutton_Tipp;
	private JButton jbutton_Add;
	
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

		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
				
		//*** Playfield ***
		{
			scrollPane_Playfield = new JScrollPane();
			contentPane.add(scrollPane_Playfield);
		
			verticalBox_Playfield = Box.createVerticalBox();
			verticalBox_Playfield.setOpaque(true);
			verticalBox_Playfield.setBackground(new Color(42,42,42));
			scrollPane_Playfield.setViewportView(verticalBox_Playfield);
		}

		jbutton_Add = new JButton("Add");
		jbutton_Add.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(!jbutton_Add.isEnabled())
					return;
				
				int[] newCode = new int[codeLength];
				for(int i = 0; i < codeLength; i++)
				{
					newCode[i] = ((Stone_View)horizontalBox_SelectionStone.getComponent(i)).getCode();
				}
				
				if(mastermind.getState() == State.playingHumanHelp)
				{
					mastermind.isPossible(newCode);
				}
				else
				{
					mastermind.addRow(newCode);
				}	
			}
		});
		jbutton_Tipp = new JButton("Tipp");
		jbutton_Tipp.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(!jbutton_Tipp.isEnabled())
					return;
				
				mastermind.test();
			}
		});
		jLabel_Row = new JLabel("0/" + rowLength);
		contentPane.add(jbutton_Add);
		contentPane.add(jbutton_Tipp);
		contentPane.add(jLabel_Row);
		
		//*** Selection Box ***
		{
			scrollPane_Selection = new JScrollPane();
			contentPane.add(scrollPane_Selection);

			horizontalBox_SelectionStone = Box.createHorizontalBox();
			horizontalBox_SelectionStone.setOpaque(true);
			horizontalBox_SelectionStone.setBackground(new Color(42,42,42));
			scrollPane_Selection.setViewportView(horizontalBox_SelectionStone);
		}
		
		//*** Secret Box ***
		{
			scrollPane_Secret = new JScrollPane();
			contentPane.add(scrollPane_Secret);

			horizontalBox_Secret = Box.createHorizontalBox();
			horizontalBox_Secret.setOpaque(true);
			horizontalBox_Secret.setBackground(new Color(42,42,42));
			scrollPane_Secret.setViewportView(horizontalBox_Secret);
		}
		
		//*** JMenuBar ***
		{
			JMenuBar jMenuBar = new JMenuBar();
			JMenu datei  = new JMenu("Datei");
			
			JMenuItem jMenuItem_NewGame = new JMenuItem("Neues Spiel");
			jMenuItem_NewGame.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					//Ein Fenster mit den Optionen anzeigen
					JPanel settingpanel = new JPanel();
					JFrame settingframe = new JFrame("Neues Spiel");
					JSlider ColorNumber = new JSlider(JSlider.HORIZONTAL, 3, 15, Mastermind.DEFAULTCOLORLENGTH);
					JSlider RowNumber = new JSlider(JSlider.HORIZONTAL, 10, 50, Mastermind.DEFAULTROWLENGTH);
					JSlider CodeNumber = new JSlider(JSlider.HORIZONTAL, 3, 15, Mastermind.DEFAULTCODELENGTH);
					JLabel ColorLabel = new JLabel("Anzahl der Farben:\n");
					JLabel RowLabel = new JLabel("\n\nAnzahl der max. Versuche:\n");
					JLabel CodeLabel = new JLabel("\n\nL�nge des Codes:\n");
					settingpanel.setLayout(new BoxLayout(settingpanel, BoxLayout.PAGE_AXIS));
					ColorNumber.setMajorTickSpacing(3);
					ColorNumber.setMinorTickSpacing(1);
					ColorNumber.setPaintTicks(true);
					ColorNumber.setPaintLabels(true);
					CodeNumber.setMajorTickSpacing(3);
					CodeNumber.setMinorTickSpacing(1);
					CodeNumber.setPaintTicks(true);
					CodeNumber.setPaintLabels(true);
					RowNumber.setMajorTickSpacing(5);
					RowNumber.setMinorTickSpacing(1);
					RowNumber.setPaintTicks(true);
					RowNumber.setPaintLabels(true);
					settingpanel.add(ColorLabel);
					settingpanel.add(ColorNumber);
					settingpanel.add(CodeLabel);
					settingpanel.add(CodeNumber);
					settingpanel.add(RowLabel);
					settingpanel.add(RowNumber);			
					//Anzeigen
					JOptionPane.showMessageDialog(settingframe, settingpanel, "Neues Spiel", JOptionPane.PLAIN_MESSAGE);		
					//Werte verarbeiten
					int newColorLength = ColorNumber.getValue();
					int newCodeLength = CodeNumber.getValue();
					int newRowLength = RowNumber.getValue();
					//Updaten
					startNewGame(newColorLength, newRowLength, newCodeLength);
				}
			});
			
			JMenuItem jMenuItem_Save = new JMenuItem("Speichern",  Mastermind_File.loadIcon("save.png"));
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
							mastermind.saveMastermind(jFileChooser.getSelectedFile());
						}
						catch (IOException e)
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error");
						}
					}
				}
			});

			JMenuItem jMenuItem_load = new JMenuItem("Laden", Mastermind_File.loadIcon("load.png"));
			jMenuItem_load.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{	
					JFileChooser jFileChooser = new JFileChooser();
					jFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

					if(jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						try
						{
							mastermind.loadMastermind(jFileChooser.getSelectedFile());
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Class not found.");
							return;
						}
						catch (IOException e)
						{
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Wrong or corrupted file.");
							return;
						}

						update();
					}
				}
			});

			datei.add(jMenuItem_NewGame);
			datei.add(jMenuItem_load);
			datei.add(jMenuItem_Save);
			jMenuBar.add(datei);

			setJMenuBar(jMenuBar);
			
			update();
		}

		setResizable(false);
		setVisible(true);
	}

	public void addRow(Row newRow)
	{
		int[] code = newRow.getCode();
		Box horizontalBox = Box.createHorizontalBox();

		for(int i = 0; i < code.length; i++)
		{
			horizontalBox.add(new Stone_View(code[i]));
		}

		horizontalBox.add(new Result_View(newRow));

		verticalBox_Playfield.add(horizontalBox, 1);
		jLabel_Row.setText(verticalBox_Playfield.getComponentCount() - 1 + "/" + rowLength);
		scrollPane_Playfield.updateUI();
	}
	
	//Zum Updaten nach einem Laden
	public void update()
	{
		verticalBox_Playfield.removeAll();
		horizontalBox_Secret.removeAll();
		horizontalBox_SelectionStone.removeAll();
		
		rowLength = mastermind.getRowLength();
		codeLength = mastermind.getCodeLength();
		colorLength = mastermind.getColorLength();
		
		//Berechnung der Gr��en der Fensterinhalte dynamisch anhand der Codel�nge
		int rowSize = Stone_View.STONESIZE * codeLength + (Result_View.RESULTSIZE *
				 (codeLength % 2 == 0 ? codeLength / 2 : codeLength / 2 + 1)) + 30;

		//Setze Fenstergr��e
		setSize(rowSize + 135, 620);
		scrollPane_Playfield.setBounds(12, 12, rowSize, 410);
		scrollPane_Selection.setBounds(12, 434, rowSize, 58);
		scrollPane_Secret.setBounds(12, 500, rowSize, 58);
		
		jbutton_Add.setBounds(rowSize+24, 434, 98, 25);
		jbutton_Tipp.setBounds(rowSize+24, 300, 98, 25);
		jLabel_Row.setBounds(rowSize+24, 12, 80, 30);

		jbutton_Add.setEnabled(true);
		jbutton_Tipp.setEnabled(true);
		jLabel_Row.setText(0 + "/" + rowLength);
		
		Box startBox = Box.createHorizontalBox();

		for(int i = 0; i < mastermind.getCodeLength(); i++)
		{
			startBox.add(new Stone_View(-1));
		}
		startBox.add(new Result_View(new Row(new int[codeLength], 0, 0)));
		verticalBox_Playfield.add(startBox);

		for(int i = 0; i < mastermind.getRowSize();i++)
		{
			addRow(mastermind.getRow(i));
		}		

		Stone_View stoneSelection_View;
		int[] secretCode = mastermind.getSecretCode();
		for(int i = 0; i < secretCode.length; i++)
		{
			stoneSelection_View = new Stone_View(secretCode[i]);
			horizontalBox_Secret.add(stoneSelection_View);
		}

		for(int i = 0; i < codeLength; i++)
		{
			stoneSelection_View = new StoneSelection_View(colorLength);
			horizontalBox_SelectionStone.add(stoneSelection_View);
		}
		
		setState(mastermind.getState());

		scrollPane_Selection.updateUI();
		scrollPane_Secret.updateUI();
		scrollPane_Playfield.updateUI();
	}
	
	//Wird aufgerufen wenn der Benutzer w�hrend eines laufenden Spiels ein neues Spiel startet
	public void startNewGame(int newColorLength, int newRowLength, int newCodeLength)
	{
		//Neues Mastermind
		this.mastermind = new Mastermind(this, newCodeLength, newRowLength, newColorLength);
		update();
	}
	
	public void setState(State state)
	{
		switch(state)
		{
			case playingHuman:
			case playingHumanHelp:
				jbutton_Add.setEnabled(true);
				jbutton_Tipp.setEnabled(true);
			break;
			case playingKI:
				jbutton_Add.setEnabled(false);
				jbutton_Tipp.setEnabled(false);
			break;
			default:
				verticalBox_Playfield.remove(0);
				jbutton_Add.setEnabled(false);
				jbutton_Tipp.setEnabled(false);
				jLabel_Row.setText(verticalBox_Playfield.getComponentCount() + "/" + rowLength);
				JOptionPane.showMessageDialog(null, (state == State.win ? "Win" : "Lose"));
			break;
		}
	}
}
